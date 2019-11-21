//
//  CalendarMonthConfig.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//


#import <Foundation/Foundation.h>
#import "CalendarDay.h"
#import "CalendarMonth.h"
#import "CalendarDate.h"
#import "CalendarDate.h"

@interface CalendarMonthConfig : NSObject

- (instancetype _Nonnull ) initWith:(int) firstDayOfWeek
                         startMonth:(NSDate *_Nonnull) startMonth
                           endMonth:(NSDate *_Nonnull) endMonth;

- (CalendarMonthConfig *_Nonnull) changeEndMonth:(NSDate *_Nonnull)endMonth;

@property (nonatomic, nonnull) NSArray<CalendarMonth*>* months;

@end
