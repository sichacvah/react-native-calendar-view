//
//  WeekdaysHeaderItem.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "WeekdaysHeaderItem.h"


@implementation WeekdaysHeaderItem

- (void) setup {
  [self setClipsToBounds:YES];
  self.contentView.backgroundColor = [UIColor purpleColor];
  self.label = [[UILabel alloc] initWithFrame:CGRectZero];
  self.label.translatesAutoresizingMaskIntoConstraints = NO;
  [self.contentView addSubview:self.label];
  [self.label.centerXAnchor constraintEqualToAnchor:self.contentView.centerXAnchor].active = YES;
  [self.label.centerYAnchor constraintEqualToAnchor:self.contentView.centerYAnchor].active = YES;
}

- (instancetype)initWithFrame:(CGRect)frame
{
  self = [super initWithFrame:frame];
  if (self) {
    [self setup];
  }
  return self;
}

@end
