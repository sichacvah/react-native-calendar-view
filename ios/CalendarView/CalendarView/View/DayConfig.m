//
//  DayConfig.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "DayConfig.h"
#import "CalendarDelegates.h"


@implementation DayConfig

- (id) init:(id<CalendarDayDelegate>)dayDelegate width:(int)width height:(int)height dayCellClass:(Class)dayCellClass {
  if (self = [super init]) {
    self.dayDelegate = dayDelegate;
    self.dayCellClass = dayCellClass;
    self.width = width;
    self.height = height;
  }
  return self;
}

@end
