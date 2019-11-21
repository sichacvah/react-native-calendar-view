//
//  MonthView.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DayConfig.h"
#import "CalendarMonth.h"

@interface MonthView : UICollectionView<UICollectionViewDataSource, UICollectionViewDelegateFlowLayout>

@property (nonatomic) DayConfig* dayConfig;
@property (nonatomic) CalendarMonth* month;
- (instancetype) initWithFrame:(CGRect)frame collectionViewLayout:(UICollectionViewLayout *)layout dayConfig:(DayConfig*)dayConfig;


@end
