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
#import "CalendarMonthConfig.h"
#import "CalendarViewMonthCell.h"
#import "CalendarDelegates.h"
#import "CalendarViewProtocol.h"

@interface CalendarView : UICollectionView<UICollectionViewDataSource, UICollectionViewDelegateFlowLayout, CalendarViewProtocol>


- (void) setup:(NSDate *_Nonnull) startMonth
      endMonth:(NSDate *_Nonnull) endMonth
firstDayOfWeek:(int) firstDayOfWeek
calendarDayDelegate:(id<CalendarDayDelegate>_Nonnull)calendarDayDelegate
monthHeaderDelegate:(id<MonthHeaderDelegate>_Nonnull)monthHeaderDelegate
dayCellClass:(Class _Nonnull)dayCellClass
monthHeaderClass:(Class _Nonnull)monthHeaderClass;


- (void) cofigureDayConfig;
- (void) updateMonthRange:(NSDate *_Nonnull) startMonth endMonth:(NSDate *_Nonnull) endMonth;
@property (nonatomic, weak) id<CalendarDayDelegate> _Nullable calendarDayDelegate;
@property (nonatomic, weak) id<MonthHeaderDelegate> _Nullable monthHeaderDelegate;
@property (nonatomic, weak) id<CalendarScrollDelegate> _Nullable calendarScrollDelegate;
@property (nonatomic, nonnull) NSDate* startMonth;
@property (nonatomic, nonnull) NSDate* endMonth;
@property (nonatomic) int firstDayOfWeek;
@property (nonatomic, nonnull) CalendarMonthConfig* monthConfig;


@end
