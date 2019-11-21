//
//  ComponentTheme.h
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <React/RCTConvert.h>
#import <React/RCTFont.h>


@interface ComponentTheme : NSObject

@property (nonatomic) UIColor* _Nullable selectionEdgeColor;
@property (nonatomic) UIColor* _Nullable selectionTextColor;
@property (nonatomic) UIColor* _Nullable selectionColor;
@property (nonatomic) UIColor* _Nullable dayBackgroundColor;
@property (nonatomic) UIColor* _Nullable dayBorderColor;
@property (nonatomic) UIFont*  _Nullable dayFont;
@property (nonatomic) UIColor* _Nullable headerBackgroundColor;
@property (nonatomic) UIFont*  _Nullable monthTitleFont;
@property (nonatomic) UIColor* _Nullable weekdayBackgroundColor;
@property (nonatomic) UIFont*  _Nullable weekdayFont;
@property (nonatomic) UIFont*  _Nullable pastDayFont;
@property (nonatomic) UIColor* _Nullable pastDayColor;
@property (nonatomic) UIColor* _Nullable dayColor;
@property (nonatomic) UIColor* _Nullable weekdayColor;
@property (nonatomic, nonnull) NSDictionary* rawTheme;

- (instancetype _Nonnull )init:(NSDictionary * _Nonnull) rawTheme;

@end
