//
//  CalendarView.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "CalendarView.h"

@implementation CalendarView {
  NSString* _cellID;
  NSString* _headerID;
  Class _dayCellClass;
  DayConfig* _dayConfig;
}



- (void) setup:(NSDate *)startMonth
      endMonth:(NSDate *)endMonth
firstDayOfWeek:(int)firstDayOfWeek
calendarDayDelegate:(id<CalendarDayDelegate>)calendarDayDelegate
monthHeaderDelegate:(id<MonthHeaderDelegate>)monthHeaderDelegate
dayCellClass:(Class _Nonnull)dayCellClass
monthHeaderClass:(Class _Nonnull)monthHeaderClass
{
    _cellID = @"CalendarViewMonthViewID";
    _headerID = @"CalendarViewMonthHeaderID";
    self.firstDayOfWeek = firstDayOfWeek;
    self.startMonth     = startMonth;
    self.backgroundColor = [UIColor clearColor];
    self.endMonth       = endMonth;
    [self setClipsToBounds:YES];
    [self setShowsVerticalScrollIndicator:NO];
    [self setDelegate:self];
    [self setDataSource:self];
    [self registerClass:[CalendarViewMonthCell class] forCellWithReuseIdentifier:_cellID];
    [self registerClass:monthHeaderClass forSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:_headerID];
    self.monthConfig = [[CalendarMonthConfig alloc] initWith:self.firstDayOfWeek startMonth:self.startMonth endMonth:self.endMonth];
    self.calendarDayDelegate = calendarDayDelegate;
    self.monthHeaderDelegate = monthHeaderDelegate;
    if (![self.calendarDayDelegate conformsToProtocol:@protocol(CalendarDayDelegate)]) {
      NSLog(@"Error calendarDayDelegate must conforms CalendarDayDelegate protocol");
    }
    if (![self.monthHeaderDelegate conformsToProtocol:@protocol(MonthHeaderDelegate)]) {
      NSLog(@"Error monthHeaderDelegate must conforms MonthHeaderDelegate protocol");
    }
    _dayCellClass = dayCellClass;
}

- (void) updateMonthRange:(NSDate *)startMonth endMonth:(NSDate *)endMonth {
  if ([[CalendarDate format:startMonth] isEqualToString:[CalendarDate format:self.startMonth]] && [endMonth timeIntervalSince1970] > [self.endMonth timeIntervalSince1970]) {
    self.startMonth = startMonth;
    self.endMonth = endMonth;
    self.monthConfig = [self.monthConfig changeEndMonth:endMonth];
    return;
  }
  self.startMonth = startMonth;
  self.endMonth = endMonth;

  self.monthConfig = [[CalendarMonthConfig alloc] initWith:self.firstDayOfWeek startMonth:self.startMonth endMonth:self.endMonth];
}

- (void) updateMonthRange:(NSDate *)startMonth endMonth:(NSDate *)endMonth completion:(void (^)(void))completion {
  NSOperationQueue* callbackQueue = [NSOperationQueue currentQueue];
  [[[NSOperationQueue alloc] init] addOperationWithBlock:^{
    [self updateMonthRange:startMonth endMonth:endMonth];
    [callbackQueue addOperationWithBlock:^{
      if (completion) {
        completion();
      }
    }];
  }];
}


- (float) getSideSize {
  float width = floorf(self.bounds.size.width);
  float sideSize = floorf(width / 7);
  return sideSize;
}

- (void) layoutSubviews {
  [super layoutSubviews];
  [self cofigureDayConfig];
}


- (void) cofigureDayConfig {
  float sideSize = [self getSideSize];
  if (sideSize != _dayConfig.width || sideSize != _dayConfig.height) {
    _dayConfig = [[DayConfig alloc] init:self.calendarDayDelegate width:sideSize height:sideSize dayCellClass:_dayCellClass];
    [self reloadData];
  }
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
  CalendarViewMonthCell* cellView = [collectionView dequeueReusableCellWithReuseIdentifier:_cellID forIndexPath:indexPath];
  cellView.dayConfig = _dayConfig;
  cellView.monthView.dayConfig = _dayConfig;
  cellView.monthView.month = _monthConfig.months[indexPath.section];
  [cellView.monthView reloadData];
//  NSLog(@"BIND_MONTH");
  return cellView;
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
  return 1;
}

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
  return self.monthConfig.months.count;
}


- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section {
  return 0.0;
}

- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumLineSpacingForSectionAtIndex:(NSInteger)section {
  return 0.0;
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout referenceSizeForHeaderInSection:(NSInteger)section {
  float height = 80;
  if ([self.monthHeaderDelegate respondsToSelector:@selector(height)]) {
    height = [self.monthHeaderDelegate height];
  }
  float width = _dayConfig.width * 7;
  if ([self.monthHeaderDelegate respondsToSelector:@selector(width)]) {
    width = [self.monthHeaderDelegate width];
  }
  return CGSizeMake(width, height);
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
  CalendarMonth* month = self.monthConfig.months[indexPath.section];
  long weeksCount = month.weekDays.count;
  float height = weeksCount * _dayConfig.height;
  float width = _dayConfig.width * 7;
  return CGSizeMake(width, height);
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
  if (self.calendarScrollDelegate) {
    [self.calendarScrollDelegate onScroll:scrollView calendarView:self];
  }
}

- (UIEdgeInsets)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout insetForSectionAtIndex:(NSInteger)section {
  return UIEdgeInsetsMake(8, 0, 24, 0);
}

- (UICollectionReusableView *)collectionView:(UICollectionView *)collectionView viewForSupplementaryElementOfKind:(NSString *)kind atIndexPath:(NSIndexPath *)indexPath {
  
  CalendarViewMonthCell *header = [collectionView dequeueReusableSupplementaryViewOfKind:UICollectionElementKindSectionHeader
                                                                  withReuseIdentifier:_headerID
                                                                         forIndexPath:indexPath];
  [self.monthHeaderDelegate bindMonth:self.monthConfig.months[indexPath.section] cell:header];
  return header;
}

@end
