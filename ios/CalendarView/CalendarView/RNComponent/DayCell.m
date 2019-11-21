//
//  DayCell.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "DayCell.h"

@implementation DayCell


- (id) initWithFrame:(CGRect) frame  {
  if (self = [super initWithFrame:frame]) {
     [self setup:frame];
  }
 
  return self;
}

- (void) setHighlighted:(BOOL)highlighted {
  [super setHighlighted:highlighted];
  [self setNeedsDisplay];
}

- (void)drawRect:(CGRect)rect {
    [super drawRect:rect];

    if (self.highlighted) {
        CGContextRef context = UIGraphicsGetCurrentContext();
        UIColor* gray  = [UIColor colorWithRed:(201.0 / 255.0) green: (219.0 / 255.0) blue:(216.0 / 255.0) alpha:1];
        CGContextSetFillColorWithColor(context, gray.CGColor);
        CGContextFillRect(context, self.bounds);
        self.startingDay.alpha = 0;
        self.endingDay.alpha = 0;
    } else {
      self.startingDay.alpha = 1.0;
      self.endingDay.alpha = 1.0;
    }
}


- (void) setup:(CGRect) frame {
  self.contentView.backgroundColor = [UIColor clearColor];
  self.backgroundColor = [UIColor clearColor];
  
  
  self.textLabel = [[UILabel alloc] initWithFrame:CGRectZero];
  
  self.textLabel.textAlignment = NSTextAlignmentCenter;
  
  self.borderView = [UIView new];
  self.startingDay = [StartingDay new];
  self.endingDay = [EndingDay new];
  

  [self.contentView insertSubview:self.startingDay atIndex:0];
  [self.contentView insertSubview:self.endingDay atIndex:1];
  [self.contentView insertSubview:self.borderView atIndex:2];
  [self.contentView insertSubview:self.textLabel atIndex:3];
  
  
  self.textLabel.translatesAutoresizingMaskIntoConstraints = NO;
  self.startingDay.translatesAutoresizingMaskIntoConstraints = NO;
  self.endingDay.translatesAutoresizingMaskIntoConstraints = NO;
  self.borderView.translatesAutoresizingMaskIntoConstraints = NO;
  
  
  [self.borderView.topAnchor constraintEqualToAnchor:self.contentView.topAnchor].active = YES;
  [self.borderView.bottomAnchor constraintEqualToAnchor:self.contentView.bottomAnchor].active = YES;
  [self.borderView.leadingAnchor constraintEqualToAnchor:self.contentView.leadingAnchor].active = YES;
  [self.borderView.trailingAnchor constraintEqualToAnchor:self.contentView.trailingAnchor].active = YES;
  
  [self.textLabel.topAnchor constraintEqualToAnchor:self.contentView.topAnchor].active = YES;
  [self.textLabel.bottomAnchor constraintEqualToAnchor:self.contentView.bottomAnchor].active = YES;
  [self.textLabel.leadingAnchor constraintEqualToAnchor:self.contentView.leadingAnchor].active = YES;
  [self.textLabel.trailingAnchor constraintEqualToAnchor:self.contentView.trailingAnchor].active = YES;
  
  [self.endingDay.topAnchor constraintEqualToAnchor:self.contentView.topAnchor].active = YES;
  [self.endingDay.bottomAnchor constraintEqualToAnchor:self.contentView.bottomAnchor].active = YES;
  [self.endingDay.leftAnchor constraintEqualToAnchor:self.contentView.leftAnchor].active = YES;
  [self.endingDay.widthAnchor constraintEqualToAnchor:self.contentView.widthAnchor multiplier:0.5].active = YES;

  [self.startingDay.topAnchor constraintEqualToAnchor:self.contentView.topAnchor].active = YES;
  [self.startingDay.bottomAnchor constraintEqualToAnchor:self.contentView.bottomAnchor].active = YES;
  [self.startingDay.rightAnchor constraintEqualToAnchor:self.contentView.rightAnchor].active = YES;
  [self.startingDay.widthAnchor constraintEqualToAnchor:self.contentView.widthAnchor multiplier:0.5].active = YES;
  
}

@end
