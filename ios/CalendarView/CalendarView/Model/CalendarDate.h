//
//  CalendarDate.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CalendarDay.h"


@interface CalendarDate : NSObject
+ (int)getYear:(NSDate *) date;
+ (int)getMonth:(NSDate *) date;
+ (NSDate *)addDays:(NSDate *) date days:(int) days;
+ (NSString *)format:(NSDate *) date;
+ (NSDate *)addMonths:(NSDate *) date months:(int) months;
+ (NSArray<NSDate *> *)getNextPage:(NSDate *) date;
+ (NSArray<CalendarDay *> *)from:(NSDate *) from to:(NSDate *)to;
+ (NSArray<CalendarDay *> *)from:(NSDate *) from to:(NSDate *)to dayOwner:(CalendarDayOwner) dayOwner;
+ (bool)equal:(NSDate *) date1 to:(NSDate *) date2;
+ (NSArray<CalendarDay *> *)month:(NSDate *) date;
+ (NSArray<NSArray <CalendarDay *> *> *)page:(NSDate *) date firstDayOfWeek:(int)first;
+ (NSArray<NSArray<NSDate *>*>*)monthByWeek:(NSArray<NSDate *> *) days;
+ (int)getDay:(NSDate *) date;
+ (int)getDate:(NSDate *) date;
+ (bool)isSameMonth:(NSDate *) date month:(NSDate *) month;
+ (bool)isPrevMonth:(NSDate *) date month:(NSDate *) month;
+ (bool)isNextMonth:(NSDate *) date month:(NSDate *) month;
+ (bool)isPast:(NSDate *) date;
+ (NSString *)formatHeader:(NSDate *) date;
+ (NSDate *) setWeekDay:(NSDate *) date weekday:(int) weekday;
+ (NSString *)formatWeekDay:(NSDate *) date;
+ (int) firstWeekday;
+ (NSDate *)parse:(NSString *) dateString;
+ (BOOL) isBetweenDates:(NSDate *) date from: (NSDate *) from to:(NSDate *) to;
@end
