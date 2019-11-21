//
//  CalendarMonth.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CalendarDayOwner.h"
#import "CalendarDay.h"
#import "CalendarMonth.h"
#import "CalendarDate.h"

@interface CalendarMonth : NSObject

@property (nonatomic, nonnull) NSDate* month;
@property (nonatomic, nonnull) NSArray<NSArray<CalendarDay*>*>* weekDays;
@property (nonatomic) int indexInSameMonth;

- (instancetype _Nonnull ) init:(NSDate* _Nonnull) month
                       weekDays:(NSArray<NSArray<CalendarDay*>*>* _Nonnull) weekDays
               indexInSameMonth:(int) indexInSameMonth;
- (BOOL)isEqualToMonth:(CalendarMonth* _Nullable)other;

@end

