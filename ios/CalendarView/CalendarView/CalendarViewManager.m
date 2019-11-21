//
//  CalendarView.m
//  PaulCamper
//
//  Created by Сергей Курочкин on 01/10/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTViewManager.h>
//#import "CalendarViewComponent.h"
#import "RNComponent/CalendarViewComponent.h"
#import <React/RCTRootView.h>
#import <React/RCTBridge.h>

@interface CalendarViewManager : RCTViewManager
@end


@implementation CalendarViewManager

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

RCT_EXPORT_MODULE(CalendarView)

- (UIView *)view {
  CalendarViewComponent * calendar = [[CalendarViewComponent alloc] initWithFrame:CGRectZero];
  calendar.bridge = self.bridge;
  return calendar;
}

RCT_CUSTOM_VIEW_PROPERTY(theme, NSDictionary*, CalendarViewComponent) {
  NSDictionary* theme = @{};
  if (json != nil) {
    theme = json;
  }
  if (![theme isEqualToDictionary:view.theme.rawTheme]) {
    [view setTheme:[[ComponentTheme alloc] init:theme]];
  }
}
RCT_EXPORT_VIEW_PROPERTY(markedDates, NSDictionary)
RCT_EXPORT_VIEW_PROPERTY(onDatePress, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAnimatedEvent, RCTDirectEventBlock)
RCT_CUSTOM_VIEW_PROPERTY(from, NSString*, CalendarViewComponent) {
  [view setFromString:json];
}
RCT_CUSTOM_VIEW_PROPERTY(to, NSString*, CalendarViewComponent) {
  [view setToString:json];
}

@end
