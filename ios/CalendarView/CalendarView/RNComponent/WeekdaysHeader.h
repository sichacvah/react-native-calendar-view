//
//  WeekdaysHeader.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WeekdaysHeaderItem.h"
#import "CalendarDate.h"


@interface HeaderWeekdayConfig : NSObject

@property (nonatomic) UIColor* weekdayBackgroundColor;
@property (nonatomic) UIFont*  weekdayFont;
@property (nonatomic) UIColor* weekdayColor;

- (instancetype) init:(UIColor*)weekdayBackgroundColor
                 font:(UIFont*) weekdayFont
         weekdayColor:(UIColor *) weekdayColor;

- (BOOL) isEqualToHeaderWeekdayConfig:(HeaderWeekdayConfig *) weekdayConfig;

@end

@interface WeekdaysHeader : UICollectionView<UICollectionViewDataSource, UICollectionViewDelegateFlowLayout>

@property (nonatomic) int firstDayOfWeek;
- (instancetype) initWithFrame:(CGRect)frame collectionViewLayout:(UICollectionViewLayout *)layout firstDayOfWeek:(int)firstDayOfWeek;
@property (nonatomic) HeaderWeekdayConfig* weekdayConfig;

@end

