//
//  UIView+EdgeBorders.h
//  Pods
//
//  Created by Сергей Курочкин on 21/11/2019.
//

#import <UIKit/UIKit.h>

@interface UIView (EdgeBorders)

- (void)roundCorners:(UIRectCorner)corners radius:(float)radius;

- (void)setEdgeBorder:(UIRectEdge)option color:(UIColor *)color;

- (void)setEdgeBorder:(UIRectEdge)option color:(UIColor *)color thickness:(CGFloat)thickness;

- (void)setEdgeBorder:(UIRectEdge)option color:(UIColor *)color thickness:(CGFloat)thickness configuration:(void (^)(UIRectEdge option, UIView *border))block;

@end

