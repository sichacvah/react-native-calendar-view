//
//  MonthView.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "MonthView.h"


@implementation MonthView {
  NSString* _cellID;
}

@synthesize dayConfig = _dayConfig;
@synthesize month = _month;

- (void) setMonth:(CalendarMonth *)month {
  if ([_month isEqualToMonth:month]) return;
  _month = month;
  [self reloadData];
}

- (void) setDayConfig:(DayConfig *)dayConfig {
  if (_dayConfig != dayConfig) {
    _dayConfig = dayConfig;
    [self reloadData];
    [self.collectionViewLayout invalidateLayout];
  }
}

- (CalendarMonth *) getMonth {
  return _month;
}

- (DayConfig *) getDayConfig {
  return _dayConfig;
}

- (void) setup {
  self.backgroundColor = [UIColor clearColor];
  [self setClipsToBounds:YES];
  [self setDelegate:self];
  [self setDataSource:self];
  [self setBounces:NO];
  [self setClipsToBounds:YES];
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
  UICollectionViewCell* cell = [collectionView dequeueReusableCellWithReuseIdentifier:_cellID forIndexPath:indexPath];
  [_dayConfig.dayDelegate bindDay:_month.weekDays[indexPath.section][indexPath.row] cell:cell];
  return cell;
}


- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
  return CGSizeMake(_dayConfig.width, _dayConfig.height);
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
  if (_month == nil) return 0;
  return _month.weekDays[section].count;
}

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
  if (_month == nil) return 0;
  return _month.weekDays.count;
}


- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section {
  return 0.0;
}

- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumLineSpacingForSectionAtIndex:(NSInteger)section {
  return 0.0;
}


- (BOOL) shouldBeTapable:(NSIndexPath *) indexPath {
  CalendarDay* day = self.month.weekDays[indexPath.section][indexPath.row];
  return day.dayOwner == CURRENTMONTH && ![CalendarDate isPast:day.date];
}

- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
  if ([self shouldBeTapable:indexPath] && [_dayConfig.dayDelegate respondsToSelector:@selector(onPress:)]) {
    CalendarDay* day = self.month.weekDays[indexPath.section][indexPath.row];
    [_dayConfig.dayDelegate onPress:day];
  }
}


- (BOOL)collectionView:(UICollectionView *)collectionView shouldHighlightItemAtIndexPath:(NSIndexPath *)indexPath {
  return [self shouldBeTapable:indexPath];
}


- (instancetype)initWithFrame:(CGRect)frame collectionViewLayout:(UICollectionViewLayout *)layout dayConfig:(DayConfig *)dayConfig
{
  self = [super initWithFrame:frame collectionViewLayout:layout];
  if (self) {
    _dayConfig = dayConfig;
    _cellID = @"cellID";
    [self registerClass:dayConfig.dayCellClass forCellWithReuseIdentifier:_cellID];
    [self setup];
  }
  return self;
}

@end
