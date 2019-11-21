//
//  CalendarDate.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "CalendarDate.h"

@implementation CalendarDate

+ (NSDate *)addDays:(NSDate *) date days:(int) days {
  return [NSCalendar.currentCalendar dateByAddingUnit:NSCalendarUnitDay value:days toDate:date options:0];
}

+ (NSDate *)addMonths:(NSDate *) date months:(int) months {
  NSDateComponents *components = [[NSDateComponents alloc] init];
  [components setMonth:months];
  return [NSCalendar.currentCalendar dateByAddingUnit:NSCalendarUnitMonth value:months toDate:date options:0];
}

+ (int)getYear:(NSDate *) date {
  NSDateComponents *components = [[NSCalendar currentCalendar] components:NSCalendarUnitYear fromDate:date];
  return (int)[components year];
}

+ (int)getMonth:(NSDate *) date {
  NSDateComponents *components = [[NSCalendar currentCalendar] components:NSCalendarUnitMonth fromDate:date];
  return (int)[components month];
}


+ (NSString *)formatHeader:(NSDate *) date {
  NSDateFormatter *dateFormatter = [[NSDateFormatter alloc]init];
  [dateFormatter setDateFormat:@"MMMM yyyy"];
  return [dateFormatter stringFromDate:date];
}

+ (NSString *)formatWeekDay:(NSDate *) date {
  NSDateFormatter *dateFormatter = [[NSDateFormatter alloc]init];
  [dateFormatter setDateFormat:@"EEEEEE"];
  return [dateFormatter stringFromDate:date];
}

+ (NSDate *) setWeekDay:(NSDate *) date weekday:(int) weekday {
  int currentWeekday = [CalendarDate getDay:date];
  if (currentWeekday == weekday) return date;
  return  [CalendarDate addDays:date days:(weekday - currentWeekday)];
}

+ (BOOL) isBetweenDates:(NSDate *) date from: (NSDate *) from to:(NSDate *) to {
  return [date timeIntervalSince1970] > [from timeIntervalSince1970] && [date timeIntervalSince1970] < [to timeIntervalSince1970];
}

+ (NSString *)format:(NSDate *) date {
  NSDateFormatter *dateFormatter = [[NSDateFormatter alloc]init];
  [dateFormatter setDateFormat:@"yyyy-MM-dd"];
  return [dateFormatter stringFromDate:date];
}

+ (NSDate *)parse:(NSString *) dateString {
  NSDateFormatter *dateFormatter = [[NSDateFormatter alloc]init];
  [dateFormatter setDateFormat:@"yyyy-MM-dd"];
  return [dateFormatter dateFromString:dateString];
}

+ (NSArray<NSDate *> *)getNextPage:(NSDate *) date {
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (int i = 0; i <= 8; i++) {
    [array addObject:[CalendarDate addMonths:date months:i]];
  }
  return [array copy];
}

+ (NSArray<CalendarDay *> *)from:(NSDate *) from to:(NSDate *)to {
  return [CalendarDate from:from to:to dayOwner:CURRENTMONTH];
}

+ (NSArray<CalendarDay *> *)from:(NSDate *) from to:(NSDate *)to dayOwner:(CalendarDayOwner)dayOwner {
  NSMutableArray<CalendarDay *> *array = [[NSMutableArray alloc] init];
  for (NSDate * i = from; [CalendarDate equal:i to:to] || [to laterDate:i] == to; i = [CalendarDate addDays:i days:1]) {
    [array addObject:[[CalendarDay alloc] init:i dayOwner:dayOwner]];
  }
  return array;
}

+ (int)getDay:(NSDate *) date {
  NSDateComponents *components = [[NSCalendar currentCalendar] components:NSCalendarUnitWeekday fromDate:date];
  return (int)([components weekday]);
}

+ (int)getDate:(NSDate *) date {
  NSDateComponents *components = [[NSCalendar currentCalendar] components:NSCalendarUnitDay fromDate:date];
  return (int)([components day]);
}

+ (bool)isSameMonth:(NSDate *) date month:(NSDate *) month {
  NSDateComponents *components1 = [[NSCalendar currentCalendar] components:NSCalendarUnitMonth | NSCalendarUnitYear fromDate:date];
  NSDateComponents *components2 = [[NSCalendar currentCalendar] components:NSCalendarUnitMonth | NSCalendarUnitYear fromDate:month];
  
  return [components1 year] == [components2 year] && [components1 month] == [components2 month];
}

+ (bool)isPrevMonth:(NSDate *) date month:(NSDate *) month {
  return ![CalendarDate isSameMonth:date month:month] && [date timeIntervalSince1970] < [month timeIntervalSince1970];
}

+ (bool)isNextMonth:(NSDate *) date month:(NSDate *) month {
  return ![CalendarDate isSameMonth:date month:month] && [date timeIntervalSince1970] > [month timeIntervalSince1970];
}

+ (bool)isPast:(NSDate *) date {
  NSDate* currentDate = [[NSDate alloc] init];
  return ![CalendarDate equal:date to:currentDate] && ([date timeIntervalSince1970] < [currentDate timeIntervalSince1970]);
}

+ (NSArray<CalendarDay *> *)month:(NSDate *) date {
  NSDateComponents *components1 = [[NSCalendar currentCalendar] components:NSCalendarUnitDay | NSCalendarUnitMonth | NSCalendarUnitYear fromDate:date];
   NSDateComponents *components2 = [[NSCalendar currentCalendar] components:NSCalendarUnitDay | NSCalendarUnitMonth | NSCalendarUnitYear fromDate:date];

  NSRange range = [[NSCalendar currentCalendar] rangeOfUnit:NSCalendarUnitDay inUnit:NSCalendarUnitMonth forDate:date];
  [components1 setDay:1];
  NSDate* from = [[NSCalendar currentCalendar] dateFromComponents:components1];
  [components2 setDay:range.length];
  NSDate* to = [[NSCalendar currentCalendar] dateFromComponents:components2];
  return [CalendarDate from:from to:to];
}

+ (NSArray<NSArray<NSDate *>*>*)monthByWeek:(NSArray<NSDate *> *) days {
  NSArray<NSArray<NSDate *>*>* array = @[];
  for (int i = 0; i < days.count; i = i + 7) {
    array = [array arrayByAddingObject: [days subarrayWithRange:NSMakeRange(i, 7)]];
  }
  return array;
}

+ (int) firstWeekday {
  return (int)([NSCalendar.currentCalendar firstWeekday]);
}


+ (NSArray<NSArray<CalendarDay*>*>*)page:(NSDate *) date firstDayOfWeek:(int)first {
  int _fdow = ((7 + first - 1) % 7);
  int fdow = (_fdow == 0 ? 7 : _fdow);
  int ldow = (fdow + 6) % 7;
 
  NSArray<CalendarDay *>* days = [CalendarDate month:date];
  NSDate* _fromDate = days[0].date;
  int fromDay = [CalendarDate getDay:_fromDate] - 1;
  NSDate* from = fromDay == fdow ? _fromDate : [CalendarDate addDays:_fromDate days:-(fromDay + 7 - fdow) % 7];
  
  NSDate* _toDate = days[days.count - 1].date;
  int toDay = [CalendarDate getDay:_toDate] - 1;
  NSDate* to = toDay != ldow ? [CalendarDate addDays:_toDate days:(ldow + 7 - toDay) % 7] : _toDate;
  
  NSArray<CalendarDay *> *before = @[];
  if ([from earlierDate:days[0].date] == from) {
    before = [CalendarDate from:from to:days[0].date dayOwner:PREVMONTH];
    before = [before subarrayWithRange:NSMakeRange(0, before.count - 1)];
  }
  
  NSArray<CalendarDay *> *after = @[];
  if ([to laterDate:days[days.count - 1].date] == to || [CalendarDate equal:to to:days[days.count - 1].date]) {
    after = [CalendarDate from:days[days.count - 1].date to:to dayOwner:NEXTMONTH];
    after = [after subarrayWithRange:NSMakeRange(1, after.count - 1)];
  }
  
  NSArray<CalendarDay*>* monthDays = [[before arrayByAddingObjectsFromArray:days] arrayByAddingObjectsFromArray:after];
  NSMutableArray<NSArray<CalendarDay*>*>* weekDays = [NSMutableArray new];
  NSMutableArray<CalendarDay*>* week = [NSMutableArray new];
  for (int i = 0; i < monthDays.count; i++) {
    if (week.count == 7) {
      [weekDays addObject:week];
      week = [NSMutableArray new];
    }
    [week addObject:monthDays[i]];
  }
  if (week.count > 0) {
    [weekDays addObject:week];
  }
  
  return weekDays;
}



+ (bool)equal:(NSDate *) date1 to:(NSDate *) date2 {

  NSDateComponents *components1 = [[NSCalendar currentCalendar] components:NSCalendarUnitDay | NSCalendarUnitMonth | NSCalendarUnitYear fromDate:date1];
  NSDateComponents *components2 = [[NSCalendar currentCalendar] components:NSCalendarUnitDay | NSCalendarUnitMonth | NSCalendarUnitYear fromDate:date2];
  return [components1 year] == [components2 year] && [components1 month] == [components2 month] && [components1 day] == [components2 day];
}

@end
