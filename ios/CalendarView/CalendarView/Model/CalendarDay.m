//
//  CalendarDay.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//


#import "CalendarDay.h"
#import "CalendarDate.h"

@implementation CalendarDay

- (id) init:(NSDate *)date dayOwner:(CalendarDayOwner)dayOwner {
  if (self = [super init]) {
    self.date = date;
    self.dayOwner = dayOwner;
  }
  return self;
}

- (BOOL) isEqual:(id)object {
  if (object == nil) return false;
  if (self == object) return true;
  if (![object isKindOfClass:[CalendarDay class]]) return false;
  CalendarDay* other = (CalendarDay *) object;
  return self.dayOwner == other.dayOwner && [CalendarDate equal:self.date to:other.date];
}

@end
