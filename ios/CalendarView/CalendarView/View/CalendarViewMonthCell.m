//
//  CalendarViewMonthCell.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "CalendarViewMonthCell.h"


@implementation CalendarViewMonthCell

@synthesize dayConfig = _dayConfig;

- (DayConfig*) getDayConfig {
  return _dayConfig;
}

- (void) setDayConfig:(DayConfig *)dayConfig {
  if (_dayConfig == nil) {
    _dayConfig = dayConfig;
    [self addMonthView:dayConfig];
  }
}



- (void) addMonthView:(DayConfig *)dayConfig {
  UICollectionViewFlowLayout* layout = [[UICollectionViewFlowLayout alloc] init];
  _monthView = [[MonthView alloc] initWithFrame:self.frame collectionViewLayout:layout dayConfig:dayConfig];

  [self.contentView addSubview:_monthView];
  [self.contentView setClipsToBounds:YES];
  _monthView.translatesAutoresizingMaskIntoConstraints = NO;
  
  [_monthView.topAnchor constraintEqualToAnchor:self.contentView.topAnchor].active = YES;
  [_monthView.bottomAnchor constraintEqualToAnchor:self.contentView.bottomAnchor constant:10].active = YES;
  [_monthView.leadingAnchor constraintEqualToAnchor:self.contentView.leadingAnchor].active = YES;
  [_monthView.trailingAnchor constraintEqualToAnchor:self.contentView.trailingAnchor].active = YES;
}


@end
