//
//  CalendarView.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <React/RCTConvert.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTUtils.h>
#import <React/RCTFont.h>
#import <React/RCTBridge.h>
#import <React/RCTComponent.h>
#import <React/UIView+React.h>
#import <React/RCTView.h>
#import "CalendarDelegates.h"
#import "ComponentTheme.h"
#import "CalendarView.h"
#import "CalendarViewEvent.h"

@interface CalendarViewComponent : RCTView<MonthHeaderDelegate, CalendarDayDelegate, CalendarScrollDelegate>


@property (nonatomic) CalendarView* _Nonnull calendar;
@property (nonatomic) RCTDirectEventBlock _Nonnull onAnimatedEvent;
@property (nonatomic) RCTBridge * _Nonnull bridge;
@property (nonatomic, nonnull) NSDictionary* markedDates;
@property (atomic) BOOL loadingMore;
@property (nonatomic) ComponentTheme* _Nonnull theme;
@property (nonatomic) RCTBubblingEventBlock _Nullable onDatePress;
@property (nonatomic) NSDate* _Nullable from;
@property (nonatomic) NSDate* _Nullable to;

- (void) setFromString:(NSString *_Nullable)from;
- (void) setToString:(NSString *_Nullable)to;
- (instancetype _Nonnull )initWithFrame:(CGRect)frame;


@end
