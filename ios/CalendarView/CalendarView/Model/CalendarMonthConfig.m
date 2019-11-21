//
//  CalendarMonthConfig.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//


#import "CalendarMonthConfig.h"

@implementation CalendarMonthConfig {
  int _firstDayOfWeek;
  NSDate* _startMonth;
  NSDate* _endMonth;
}

- (CalendarMonthConfig *) changeEndMonth:(NSDate *_Nonnull)endMonth {
  NSMutableArray* nextMonths = [NSMutableArray new];
  [nextMonths addObjectsFromArray:self.months];
  [nextMonths addObjectsFromArray:[self getMonths:[CalendarDate addMonths:self.months[self.months.count - 1].month months:1] endMonth:endMonth]];
  self.months = nextMonths;
  return self;
}

- (id) initWith:(int)firstDayOfWeek startMonth:(NSDate *)startMonth endMonth:(NSDate *)endMonth {
  if (self = [super init]) {
    _firstDayOfWeek = firstDayOfWeek;
    _startMonth = startMonth;
    _endMonth = endMonth;
    self.months = [self getMonths:_startMonth endMonth:_endMonth];
  }
  return self;
}

- (CalendarMonth *) generateMonth:(NSDate *)month indexInSameMonth:(int)indexInSameMonth {
  return [[CalendarMonth alloc] init:month weekDays:[CalendarDate page:month firstDayOfWeek:_firstDayOfWeek] indexInSameMonth:indexInSameMonth];
}

- (NSArray<CalendarMonth *> *) getMonths:(NSDate *) startMonth endMonth:(NSDate *)endMonth {
  NSDate* currentMonth = startMonth;
  NSMutableArray<CalendarMonth *>* months = [NSMutableArray new];
  while ([CalendarDate equal:currentMonth to:endMonth] || [CalendarDate isPrevMonth:currentMonth month:endMonth]) {
    CalendarMonth* month = [self generateMonth:currentMonth indexInSameMonth:(int)months.count];
    [months addObject:month];
    currentMonth = [CalendarDate addMonths:currentMonth months:1];
  }
  return months;
}

@end
