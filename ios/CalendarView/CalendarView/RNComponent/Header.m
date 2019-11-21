//
//  Header.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "Header.h"

@implementation Header {
  WeekdaysHeader* _wkHeader;
}

@synthesize firstDayOfWeek = _firstDayOfWeek;


- (void) setWeekdayConfig:(HeaderWeekdayConfig *)weekdayConfig {
  _wkHeader.weekdayConfig = weekdayConfig;
}

- (int) firstDayOfWeek {
  return _firstDayOfWeek;
}

- (void) setFirstDayOfWeek:(int)firstDayOfWeek {
  _firstDayOfWeek = firstDayOfWeek;
  if (_wkHeader) {
    _wkHeader.firstDayOfWeek = firstDayOfWeek;
  }
}

- (void) layoutSubviews {
  [_wkHeader.collectionViewLayout invalidateLayout];
}


- (void) setup {
  [self setClipsToBounds:YES];
  _firstDayOfWeek = 1;
  self.contentView.backgroundColor = [UIColor clearColor];
  self.translatesAutoresizingMaskIntoConstraints = false;
  self.label = [[UILabel alloc] initWithFrame:CGRectZero];
  self.label.backgroundColor = [UIColor grayColor];
  [self addSubview:self.label];
  self.label.translatesAutoresizingMaskIntoConstraints = NO;
  [self.label.topAnchor constraintEqualToAnchor:self.topAnchor].active = true;
  [self.label.widthAnchor constraintEqualToAnchor:self.widthAnchor].active = true;
  [self.label.heightAnchor constraintEqualToAnchor:self.heightAnchor multiplier:0.5].active = true;
  self.label.textAlignment = NSTextAlignmentCenter;
  UICollectionViewFlowLayout* layout = [UICollectionViewFlowLayout new];
  _wkHeader = [[WeekdaysHeader alloc] initWithFrame:CGRectZero collectionViewLayout:layout firstDayOfWeek:_firstDayOfWeek];
  
  _wkHeader.translatesAutoresizingMaskIntoConstraints = NO;
  [self addSubview:_wkHeader];
  [_wkHeader.bottomAnchor constraintEqualToAnchor:self.bottomAnchor].active = true;
  [_wkHeader.leadingAnchor constraintEqualToAnchor:self.leadingAnchor].active = true;
  [_wkHeader.trailingAnchor constraintEqualToAnchor:self.trailingAnchor].active = true;
  [_wkHeader.topAnchor constraintEqualToAnchor:self.label.bottomAnchor constant:11].active = true;
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
