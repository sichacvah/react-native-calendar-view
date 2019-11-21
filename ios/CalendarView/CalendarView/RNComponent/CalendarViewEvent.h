//
//  CalendarViewEvent.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//


#import <React/RCTConvert.h>
#import "CalendarView.h"
#import <React/RCTEventDispatcher.h>

@interface CalendarViewEvent : NSObject <RCTEvent>

- (instancetype)initWithEventName:(NSString *)eventName
                         reactTag:(NSNumber *)reactTag
                     calendarView:(CalendarView *)calendarView
                         userData:(NSDictionary *)userData
                    coalescingKey:(uint16_t)coalescingKey NS_DESIGNATED_INITIALIZER;

@end

