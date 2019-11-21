//
//  CalendarViewMonthCell.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIView+EdgeBorders.h"
#import "DayConfig.h"
#import "MonthView.h"
#import "CalendarMonth.h"



@interface CalendarViewMonthCell : UICollectionViewCell

@property (nonatomic) MonthView* monthView;
@property (nonatomic) DayConfig* dayConfig;

@end
