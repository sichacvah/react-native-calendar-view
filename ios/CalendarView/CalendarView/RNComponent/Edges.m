//
//  Edges.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "Edges.h"


@implementation StartingDay

- (void) layoutSubviews {
  [self roundCorners:UIRectCornerTopLeft|UIRectCornerBottomLeft radius:4.0];
}

@end


@implementation EndingDay

- (void) layoutSubviews {
  [self roundCorners:UIRectCornerTopRight|UIRectCornerBottomRight radius:4.0];
}

@end
