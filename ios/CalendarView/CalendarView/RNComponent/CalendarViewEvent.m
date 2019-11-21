//
//  CalendarViewEvent.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "CalendarViewEvent.h"


@implementation CalendarViewEvent
{
  NSDictionary *_userData;
  CalendarView *_calendarView;
  uint16_t _coalescingKey;
}

@synthesize viewTag = _viewTag;
@synthesize eventName = _eventName;

- (instancetype)initWithEventName:(NSString *)eventName
                         reactTag:(NSNumber *)reactTag
                     calendarView:(CalendarView *)calendarView
                         userData:(NSDictionary *)userData
                    coalescingKey:(uint16_t)coalescingKey
{
    if ((self = [super init])) {
        _eventName = [eventName copy];
        _viewTag = reactTag;
        _calendarView = calendarView;
        _userData = userData;
        _coalescingKey = coalescingKey;
    }
    return self;
}

RCT_NOT_IMPLEMENTED(- (instancetype)init)

- (uint16_t)coalescingKey
{
    return _coalescingKey;
}

- (NSDictionary *)body
{
    return _userData;
}

- (BOOL)canCoalesce
{
    return YES;
}

- (CalendarViewEvent *)coalesceWithEvent:(CalendarViewEvent *)newEvent
{
    newEvent->_userData = _userData;
    
    return newEvent;
}

+ (NSString *)moduleDotMethod
{
    return @"RCTEventEmitter.receiveEvent";
}

- (NSArray *)arguments
{
    return @[self.viewTag, RCTNormalizeInputEventName(self.eventName), [self body]];
}

@end
