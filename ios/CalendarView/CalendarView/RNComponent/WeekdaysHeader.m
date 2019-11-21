//
//  WeekdaysHeader.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "WeekdaysHeader.h"


@implementation HeaderWeekdayConfig

- (instancetype)init:(UIColor *)weekdayBackgroundColor font:(UIFont *)weekdayFont weekdayColor:(UIColor *) weekdayColor
{
  self = [super init];
  if (self) {
    self.weekdayBackgroundColor = weekdayBackgroundColor;
    self.weekdayFont = weekdayFont;
    self.weekdayColor = weekdayColor;
  }
  return self;
}

- (BOOL) isEqualToHeaderWeekdayConfig:(HeaderWeekdayConfig *) weekdayConfig {
  return  [self.weekdayBackgroundColor isEqual:weekdayConfig.weekdayBackgroundColor] &&
          [self.weekdayFont isEqual:weekdayConfig.weekdayFont] &&
          [self.weekdayColor isEqual:weekdayConfig.weekdayColor];
}

@end

@implementation WeekdaysHeader {
  NSString* _cellID;
}

@synthesize firstDayOfWeek = _firstDayOfWeek;

- (int) firstDayOfWeek {
  return _firstDayOfWeek;
}

- (void) setFirstDayOfWeek:(int)firstDayOfWeek {
  _firstDayOfWeek = firstDayOfWeek;
  [self reloadData];
}

@synthesize weekdayConfig = _weekdayConfig;

- (HeaderWeekdayConfig *) getWeekdayConfig {
  return _weekdayConfig;
}

- (void) setWeekdayConfig:(HeaderWeekdayConfig *)weekdayConfig {
  if (![_weekdayConfig isEqualToHeaderWeekdayConfig:weekdayConfig]) {
    _weekdayConfig = weekdayConfig;
    [self reloadData];
  }
}

- (float) getSideSize {
  float width = floorf(self.frame.size.width);
  float sideSize = floorf(width / 7);
  return sideSize;
}

- (void) setup {
  _cellID = @"cellID";
  [self setClipsToBounds:YES];
  [self setBounces:NO];
  self.bounces = NO;
  self.alwaysBounceVertical = NO;
  self.scrollEnabled = NO;
  self.backgroundColor = [UIColor clearColor];
  [self setDelegate:self];
  [self setDataSource:self];
  [self registerClass:[WeekdaysHeaderItem class] forCellWithReuseIdentifier:_cellID];
}

- (int) getDayNumber:(int) day {
  int weekday = day % 7;
  return weekday == 0 ? 7 : weekday;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
  WeekdaysHeaderItem* headerItem = [collectionView dequeueReusableCellWithReuseIdentifier:_cellID forIndexPath:indexPath];
  int index = [self getDayNumber:(int)indexPath.row + _firstDayOfWeek];
  NSDate* now = [[NSDate alloc] init];
  NSDate* nextDate = [CalendarDate setWeekDay:now weekday:index];
  headerItem.label.text = [CalendarDate formatWeekDay:nextDate];
  headerItem.contentView.backgroundColor = self.weekdayConfig.weekdayBackgroundColor;
  headerItem.label.backgroundColor = [UIColor clearColor];
  [headerItem.label setFont:self.weekdayConfig.weekdayFont];
  return headerItem;
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
  float sideSize = [self getSideSize];
  return CGSizeMake(sideSize, 40);
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
  return 7;
}

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
  return 1;
}


- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section {
  return 0.0;
}

- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumLineSpacingForSectionAtIndex:(NSInteger)section {
  return 0.0;
}

- (instancetype) initWithFrame:(CGRect)frame collectionViewLayout:(UICollectionViewLayout *)layout firstDayOfWeek:(int)firstDayOfWeek
{
  self = [super initWithFrame:frame collectionViewLayout:layout];
  if (self) {
    [self setFirstDayOfWeek:firstDayOfWeek];
    [self setup];
  }
  return self;
}

@end

