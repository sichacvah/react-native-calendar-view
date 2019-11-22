//
//  CalendarViewComponent.m
//
//  Created by Сергей Курочкин on 15/11/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CalendarViewComponent.h"

#import "CalendarDate.h"
#import "DayCell.h"
#import "Header.h"

@implementation CalendarViewComponent {
  int _coalescingKey;
}

@synthesize theme = _theme;

- (ComponentTheme *) getTheme {
  return _theme;
}

- (void) setReactFrame:(CGRect)frame {}

- (void) setTheme:(ComponentTheme *)theme {
  _theme = theme;
  [self.calendar reloadData];
}

- (int) firstDayOfWeek {
  return [CalendarDate firstWeekday];
}

- (void) setup {
  _loadingMore = NO;
  _coalescingKey = 0;
  self.markedDates = @{};

  self.theme = [[ComponentTheme alloc] init:@{}];
  UICollectionViewFlowLayout* layout = [UICollectionViewFlowLayout new];
  self.calendar = [[CalendarView alloc] initWithFrame:CGRectZero collectionViewLayout:layout];
  NSDate* from = [[NSDate alloc] init];
  NSDate* to = [CalendarDate addMonths:from months:10];
  self.backgroundColor = [UIColor clearColor];
  self.clipsToBounds = YES;
  self.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
  
  [self.calendar setup:from
           endMonth:to
     firstDayOfWeek:[self firstDayOfWeek]
calendarDayDelegate:self
monthHeaderDelegate:self
       dayCellClass:[DayCell class]
   monthHeaderClass:[Header class]];
  self.calendar.calendarScrollDelegate = self;
  [self addSubview:self.calendar];
  self.calendar.translatesAutoresizingMaskIntoConstraints = NO;
  [self.calendar.topAnchor constraintEqualToAnchor:self.topAnchor].active = YES;
  [self.calendar.bottomAnchor constraintEqualToAnchor:self.bottomAnchor].active = YES;
  [self.calendar.leadingAnchor constraintEqualToAnchor:self.leadingAnchor].active = YES;
  [self.calendar.trailingAnchor constraintEqualToAnchor:self.trailingAnchor].active = YES;
  [self.calendar cofigureDayConfig];
}


- (instancetype)initWithFrame:(CGRect)frame
{
  self = [super initWithFrame:frame];
  if (self) {
    [self setup];
  }
  return self;
}

- (void)bindMonth:(CalendarMonth *)month cell:(Header *)cell {
  if (cell.firstDayOfWeek != [self firstDayOfWeek]) {
    cell.firstDayOfWeek = [self firstDayOfWeek];
  }
  [cell.label setFont:self.theme.monthTitleFont];
  cell.backgroundColor = self.theme.headerBackgroundColor;
  cell.label.backgroundColor = self.theme.headerBackgroundColor;
  cell.label.text = [CalendarDate formatHeader:month.month];
  [cell.label setTextColor:self.theme.monthTitleColor];
  [cell setWeekdayConfig:[[HeaderWeekdayConfig alloc] init:self.theme.weekdayBackgroundColor
                                                        font:self.theme.weekdayFont
                                                weekdayColor:self.theme.weekdayColor]];
}

- (void) layoutSubviews {
  [self.calendar setBounds:self.bounds];
  [self.calendar setFrame:self.bounds];
  [self.calendar.collectionViewLayout invalidateLayout];
}

- (float)height {
  return 80;
}

- (NSDictionary *) getMarkedFromDate:(CalendarDay *) day {
  if (day.dayOwner != CURRENTMONTH || [CalendarDate isPast:day.date]) return nil;
  id markedDate = self.markedDates[[CalendarDate format:day.date]];
  if (markedDate == nil) return nil;
  if ([markedDate isKindOfClass:[NSDictionary class]]) {
    return markedDate;
  }
  return nil;
}

- (UIColor *) getColorByKey:(NSDictionary*) markedDate key:(NSString *) key {
  if (markedDate == nil) return nil;
  return [RCTConvert UIColor:markedDate[key]];
}

- (UIColor *) getColor:(NSDictionary *)markedDate {
  return [self getColorByKey:markedDate key:@"color"];
}

- (UIColor *) getEdgeColor:(NSDictionary *)markedDate key:(NSString *) key  {
  if (markedDate == nil) return nil;
  id edge = markedDate[key];
  if (edge == nil || ![edge isKindOfClass:[NSDictionary class]]) return nil;
  return [self getColorByKey:edge key:@"color"];
}

- (UIColor *) getTextColor:(NSDictionary *)markedDate {
  return [self getColorByKey:markedDate key:@"textColor"];
}

- (UIColor *) getStartingColor:(NSDictionary *) markedDate {
  return [self getEdgeColor:markedDate key:@"starting"];
}

- (UIColor *) getEndingColor:(NSDictionary *) markedDate {
  return [self getEdgeColor:markedDate key:@"ending"];
}


- (void)onScroll:(UIScrollView *)scrollView calendarView:(CalendarView *) calendarView {
  long offsetY = scrollView.contentOffset.y;
  long contentHeight = scrollView.contentSize.height;
  _coalescingKey += 1;
  if (self.onAnimatedEvent != nil) {
    NSDictionary* userData = @{
      @"x": @(scrollView.contentOffset.x),
      @"y": @(scrollView.contentOffset.y)
    };
    
    CalendarViewEvent* event = [[CalendarViewEvent alloc]
                                    initWithEventName:@"onAnimatedEvent"
                                reactTag:self.reactTag
                                calendarView:self.calendar
                                userData:userData
                                coalescingKey:_coalescingKey];
    [[self->_bridge eventDispatcher] sendEvent:event];
  }
  
  if (offsetY > contentHeight - scrollView.frame.size.height * 3 && !self.loadingMore) {
    self.loadingMore = true;
    [calendarView updateMonthRange:calendarView.startMonth endMonth:[CalendarDate addMonths:calendarView.endMonth months:4] completion:^{
      NSRange range = NSMakeRange(calendarView.monthConfig.months.count - 5, 4);
      NSIndexSet* sections = [[NSIndexSet alloc] initWithIndexesInRange:range];
      [calendarView insertSections:sections];
      self.loadingMore = false;
    }];
  }
  
}

- (void) onPress:(CalendarDay *)day {
  if (self.onDatePress) {
    NSDate* month = day.date;
    if (day.dayOwner == PREVMONTH) {
      month = [CalendarDate addMonths:day.date months:-1];
    }
    if (day.dayOwner == NEXTMONTH) {
      month = [CalendarDate addMonths:day.date months:1];
    }
    NSLog(@"DATE - %@", [CalendarDate format:day.date]);
    self.onDatePress(@{@"date": [CalendarDate format:day.date], @"month": [CalendarDate format:month]});
  }
}

- (BOOL) isLastWeek:(CalendarDay*) day {
  NSDate* date = [CalendarDate setWeekDay:day.date weekday:[self firstDayOfWeek]];
  NSDate* nextWeekDate = [CalendarDate addDays:date days:7];
  return [CalendarDate getYear:nextWeekDate] > [CalendarDate getYear:day.date] || [CalendarDate getMonth:nextWeekDate] > [CalendarDate getMonth:day.date];
}


- (void) setFromString:(NSString *) from {
  if (from != nil && self.from != nil && ![CalendarDate equal:[CalendarDate parse:from] to:self.from]) {
    self.from = [CalendarDate parse:from];
    [self.calendar reloadData];
    return;
  }
  if (from == nil && self.from != nil) {
    self.from = nil;
    [self.calendar reloadData];
    return;
  }
  if (from != nil && self.from == nil) {
    self.from = [CalendarDate parse:from];
    [self.calendar reloadData];
    return;
  }
  
}

- (void) setToString:(NSString *)to {
  if (to != nil && self.to != nil && ![CalendarDate equal:[CalendarDate parse:to] to:self.to]) {
    self.to = [CalendarDate parse:to];
    [self.calendar reloadData];
    return;
  }
  if (to == nil && self.to != nil) {
    self.to = nil;
    [self.calendar reloadData];
    return;
  }
  if (to != nil && self.to == nil) {
    self.to = [CalendarDate parse:to];
    [self.calendar reloadData];
    return;
  }
}
- (void)bindDay:(CalendarDay *)day cell:(DayCell *)cell {
  cell.backgroundColor = [UIColor clearColor];
  cell.textLabel.backgroundColor = [UIColor clearColor];
  cell.textLabel.text = [NSString stringWithFormat:@"%i", [CalendarDate getDate:day.date]];
  int weekday = [CalendarDate getDay:day.date];
  
  for (UIView* subview in [cell.borderView subviews]) {
    [subview removeFromSuperview];
  }
  
  if (day.dayOwner == CURRENTMONTH && ![CalendarDate isPast:day.date]) {
    [cell.borderView setEdgeBorder:UIRectEdgeTop | UIRectEdgeLeft
                             color:_theme.dayBorderColor];
  }
  
  if (weekday % 7 == 0 && day.dayOwner == CURRENTMONTH && ![CalendarDate isPast:day.date]) {
    [cell.borderView setEdgeBorder:UIRectEdgeRight
                             color:_theme.dayBorderColor];
  }
  
  if (day.dayOwner == NEXTMONTH) {
    if (![CalendarDate isPast:day.date] && [CalendarDate getMonth:[CalendarDate addDays:day.date days:-1]] != [CalendarDate getMonth:day.date]) {
      [cell.borderView setEdgeBorder:UIRectEdgeLeft color:_theme.dayBorderColor];
    }
    if (![CalendarDate isPast:day.date]) {
      [cell.borderView setEdgeBorder:UIRectEdgeTop color:_theme.dayBorderColor];
    }
  }
  
  if (day.dayOwner == CURRENTMONTH) {
    if ([self isLastWeek:day] && ![CalendarDate isPast:day.date]) {
      [cell.borderView setEdgeBorder:UIRectEdgeBottom color:_theme.dayBorderColor];
    }
  }

  if (day.dayOwner != CURRENTMONTH || [CalendarDate isPast:day.date]) {
    [cell.textLabel setFont:_theme.pastDayFont];
    [cell.textLabel setTextColor:_theme.pastDayColor];
    cell.backgroundColor = _theme.dayBackgroundColor;
  } else {
    [cell.textLabel setFont:_theme.dayFont];
    [cell.textLabel setTextColor:_theme.dayColor];
    cell.backgroundColor = _theme.dayBackgroundColor;
  }
  
  cell.startingDay.backgroundColor = [UIColor clearColor];
  cell.endingDay.backgroundColor = [UIColor clearColor];
  NSDictionary* markedDate = [self getMarkedFromDate:day];
  if (markedDate != nil) {
    UIColor* color = [self getColor:markedDate];
    if (color != nil) {
      cell.backgroundColor = color;
    }
    UIColor* textColor = [self getTextColor:markedDate];
    if (textColor != nil) {
      [cell.textLabel setTextColor:textColor];
    }
    UIColor* starting = [self getStartingColor:markedDate];
    if (starting != nil) {
      cell.startingDay.backgroundColor = starting;
    }
    UIColor* ending = [self getEndingColor:markedDate];
    if (ending != nil) {
      cell.endingDay.backgroundColor = ending;
    }
  }

  if (![CalendarDate isPast:day.date] && day.dayOwner == CURRENTMONTH) {
    if (self.to && self.from && [CalendarDate isBetweenDates:day.date from:self.from to:self.to]) {
      cell.startingDay.backgroundColor = [UIColor clearColor];
      cell.endingDay.backgroundColor = [UIColor clearColor];
      cell.backgroundColor = _theme.selectionColor;
      if (![_theme.selectionTextColor isEqual:[UIColor clearColor]]) {
        [cell.textLabel setTextColor:_theme.selectionTextColor];
      }
    }
    if (self.from != nil && [CalendarDate equal:self.from to:day.date]) {
      cell.startingDay.backgroundColor = _theme.selectionEdgeColor;
    }
    if (self.to != nil && [CalendarDate equal:self.to to:day.date]) {
      cell.endingDay.backgroundColor = _theme.selectionEdgeColor;
    }
  }

  
  

}

@end
