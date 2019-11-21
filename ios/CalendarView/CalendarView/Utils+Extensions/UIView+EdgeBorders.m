//
//  UIView+EdgeBorders.m
//  DoubleConversion
//
//  Created by Сергей Курочкин on 21/11/2019.
//


#import "UIView+EdgeBorders.h"

@implementation UIView (EdgeBorders)

- (void) roundCorners:(UIRectCorner) corners radius:(float) radius {
  UIBezierPath* path = [UIBezierPath bezierPathWithRoundedRect:self.bounds byRoundingCorners:corners cornerRadii:CGSizeMake(radius, radius)];
  CAShapeLayer* maskLayer = [[CAShapeLayer alloc] init];
  maskLayer.frame = self.bounds;
  maskLayer.path = path.CGPath;
  self.layer.mask = maskLayer;
//  self.clipsToBounds = YES;
}


- (void)setEdgeBorder:(UIRectEdge)option color:(UIColor *)color
{
    [self setEdgeBorder:option color:color thickness:1.0];
}

- (void)setEdgeBorder:(UIRectEdge)option color:(UIColor *)color thickness:(CGFloat)thickness
{
    [self setEdgeBorder:option color:color thickness:thickness configuration:nil];
}

- (void)setEdgeBorder:(UIRectEdge)option color:(UIColor *)color thickness:(CGFloat)thickness configuration:(void (^)(UIRectEdge option, UIView *border))block
{
#define EdgeConstraint(__attribute__, __view__, __constant__)      [NSLayoutConstraint constraintWithItem: __view__ attribute: __attribute__ relatedBy: NSLayoutRelationEqual toItem: self attribute: __attribute__ multiplier: 1.0 constant : __constant__]

#define WidthConstraint(__const_height__, __view__)  [NSLayoutConstraint constraintWithItem: __view__ attribute: NSLayoutAttributeWidth relatedBy: NSLayoutRelationEqual toItem: self attribute: NSLayoutAttributeWidth multiplier : 0 constant: __const_height__]

#define HeightConstraint(__const_height__, __view__) [NSLayoutConstraint constraintWithItem:__view__ attribute: NSLayoutAttributeHeight relatedBy: NSLayoutRelationEqual toItem: self attribute: NSLayoutAttributeHeight multiplier : 0 constant: __const_height__]

    UIView * (^ createBorder)(UIRectEdge) = ^UIView *(UIRectEdge op) {
        UIView *border = [UIView new];

        border.translatesAutoresizingMaskIntoConstraints = NO;
        border.backgroundColor = color;
        !block ? : block(op, border);
        [self addSubview:border];
        return border;
    };

    if (option & UIRectEdgeTop) {
        UIView *view = createBorder(UIRectEdgeLeft);

        [self addConstraint:EdgeConstraint(NSLayoutAttributeTop, view, 0)];
        [self addConstraint:EdgeConstraint(NSLayoutAttributeLeft, view, 0)];
        [self addConstraint:EdgeConstraint(NSLayoutAttributeRight, view, 0)];
        [self addConstraint:HeightConstraint(thickness, view)];
    }

    if (option & UIRectEdgeLeft) {
        UIView *view = createBorder(UIRectEdgeLeft);

        [self addConstraint:EdgeConstraint(NSLayoutAttributeTop, view, 0)];
        [self addConstraint:EdgeConstraint(NSLayoutAttributeLeft, view, 0)];
        [self addConstraint:EdgeConstraint(NSLayoutAttributeBottom, view, 0)];
        [self addConstraint:WidthConstraint(thickness, view)];
    }

    if (option & UIRectEdgeBottom) {
        UIView *view = createBorder(UIRectEdgeBottom);

        [self addConstraint:EdgeConstraint(NSLayoutAttributeLeft, view, 0)];
        [self addConstraint:EdgeConstraint(NSLayoutAttributeRight, view, 0)];
        [self addConstraint:EdgeConstraint(NSLayoutAttributeBottom, view, 0)];
        [self addConstraint:HeightConstraint(thickness, view)];
    }

    if (option & UIRectEdgeRight) {
        UIView *view = createBorder(UIRectEdgeRight);

        [self addConstraint:EdgeConstraint(NSLayoutAttributeTop, view, 0)];
        [self addConstraint:EdgeConstraint(NSLayoutAttributeRight, view, 0)];
        [self addConstraint:EdgeConstraint(NSLayoutAttributeBottom, view, 0)];
        [self addConstraint:WidthConstraint(thickness, view)];
    }
}

@end
