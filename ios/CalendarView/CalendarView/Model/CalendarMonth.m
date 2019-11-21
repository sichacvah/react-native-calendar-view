//
//  CalendarMonth.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//


#import "CalendarMonth.h"

@implementation CalendarMonth

- (id)init:(NSDate *)month weekDays:(NSArray<NSArray<CalendarDay *> *> *)weekDays indexInSameMonth:(int)indexInSameMonth {
  if (self = [super init]) {
    self.month = month;
    self.weekDays = weekDays;
    self.indexInSameMonth = indexInSameMonth;
  }
  return self;
}

- (BOOL)isEqualToMonth:(CalendarMonth *)other {
  if (other == nil) return false;
  NSArray<CalendarDay*>* lastWeek = self.weekDays[self.weekDays.count - 1];
  NSArray<CalendarDay*>* otherLastWeek = other.weekDays[other.weekDays.count - 1];
  return [CalendarDate isSameMonth:self.month month:other.month] &&
         [CalendarDate equal:lastWeek[0].date to:otherLastWeek[0].date] &&
         [CalendarDate equal:lastWeek[lastWeek.count - 1].date to:otherLastWeek[otherLastWeek.count - 1].date];
}

- (BOOL)isEqual:(id)object {
  if (object == nil) return false;
  if (self == object) return true;
  if (![object isKindOfClass:[CalendarMonth class]]) return false;
  CalendarMonth* other = (CalendarMonth*) object;
  NSArray<CalendarDay*>* lastWeek = _weekDays[_weekDays.count - 1];
  NSArray<CalendarDay*>* otherLastWeek = other.weekDays[other.weekDays.count - 1];
  return [CalendarDate isSameMonth:self.month month:other.month] &&
          [CalendarDate equal:lastWeek[0].date to:otherLastWeek[0].date] &&
          [CalendarDate equal:lastWeek[lastWeek.count - 1].date to:otherLastWeek[otherLastWeek.count - 1].date];
  
}

@end
