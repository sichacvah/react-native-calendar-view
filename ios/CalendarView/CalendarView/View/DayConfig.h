//
//  DayConfig.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CalendarDelegates.h"


@interface DayConfig : UIView

- (instancetype _Nonnull ) init:(id<CalendarDayDelegate>_Nonnull)dayDelegate
                          width:(int)width
                         height:(int)height
                   dayCellClass:(Class _Nonnull)dayCellClass;

@property (nonatomic, nonnull) id<CalendarDayDelegate> dayDelegate;
@property (nonatomic) int width;
@property (nonatomic) int height;
@property (nonatomic, nonnull) Class dayCellClass;

@end
