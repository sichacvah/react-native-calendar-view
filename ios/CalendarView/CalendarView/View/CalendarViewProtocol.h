//
//  CalendarViewProtocol.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol CalendarViewProtocol <NSObject>

- (void) updateMonthRange:(NSDate *)startMonth endMonth:(NSDate *)endMonth completion:(void(^)(void))completion;

@property (nonatomic) NSDate* startMonth;
@property (nonatomic) NSDate* endMonth;

@end
