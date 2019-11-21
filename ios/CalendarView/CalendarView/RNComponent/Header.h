//
//  Header.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WeekdaysHeader.h"

@interface Header : UICollectionViewCell

@property (nonatomic) int firstDayOfWeek;
@property (nonatomic) UILabel* label;
- (instancetype) initWithFrame:(CGRect)frame;
- (void) setWeekdayConfig:(HeaderWeekdayConfig *) weekdayConfig;

@end
