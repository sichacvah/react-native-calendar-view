//
//  CalendarDelegates.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CalendarDay.h"
#import "CalendarMonth.h"
#import "CalendarViewProtocol.h"

@protocol CalendarScrollDelegate <NSObject>
  - (void) onScroll:(UIScrollView *) scrollView calendarView:(id<CalendarViewProtocol>) calendarView;
@end

@protocol CalendarDayDelegate <NSObject>

- (void) bindDay:(CalendarDay *)day cell:(UICollectionViewCell *)cell;

@optional
- (float) width;
- (float) height;
- (void) onPress:(CalendarDay *)day;

@end

@protocol MonthHeaderDelegate <NSObject>

@required
- (void) bindMonth:(CalendarMonth *)month cell:(UICollectionViewCell *)cell;
- (float) height;
@optional
- (float) width;


@end
