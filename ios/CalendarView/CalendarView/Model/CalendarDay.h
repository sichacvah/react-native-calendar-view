//
//  CalendarDay.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CalendarDayOwner.h"


@interface CalendarDay : NSObject

@property (nonatomic) CalendarDayOwner dayOwner;
@property (nonatomic, nonnull) NSDate* date;

- (instancetype _Nonnull) init:(NSDate *_Nonnull) date dayOwner:(CalendarDayOwner) dayOwner;

@end
