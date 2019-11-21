//
//  DayCell.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//


#import <UIKit/UIKit.h>
#import "Edges.h"

@interface DayCell : UICollectionViewCell

@property (nonatomic) UILabel * textLabel;
@property (nonatomic) UIView * borderView;
@property (nonatomic) EndingDay * endingDay;
@property (nonatomic) StartingDay * startingDay;


@end

