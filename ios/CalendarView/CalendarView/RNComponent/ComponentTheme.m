//
//  ComponentTheme.m
//  CalendarView
//
//  Created by Сергей Курочкин on 21/11/2019.
//  Copyright © 2019 SergeiKurochkin. All rights reserved.
//

#import "ComponentTheme.h"


@implementation ComponentTheme


- (void) setup {
  self.monthTitleColor = [self getColor:@"monthTitleColor"];
  self.selectionEdgeColor = [self getColor:@"selectionEdgeColor"];
  self.selectionTextColor = [self getColor:@"selectionTextColor"];
  self.selectionColor     = [self getColor:@"selectionColor"];
  self.dayBackgroundColor = [self getColor:@"dayBackgroundColor"];
  self.headerBackgroundColor = [self getColor:@"headerBackgroundColor"];
  self.weekdayBackgroundColor = [self getColor:@"weekdayBackroundColor"];
  self.weekdayColor = [self getColor:@"weekdayColor"];
  self.dayColor = [self getColor:@"dayColor"];
  self.pastDayColor = [self getColor:@"pastDayColor"];
  self.pastDayFont = [RCTFont updateFont:[self defaultFont]
                              withFamily:self.rawTheme[@"pastDayFontFamily"]
                                    size:self.rawTheme[@"pastDayFontSize"]
                                  weight:self.rawTheme[@"pastDayFontWeight"]
                                   style:nil
                                 variant:nil
                         scaleMultiplier:1.0];
  self.dayBorderColor = [self getColor:@"borderColor"];
  self.weekdayFont = [RCTFont updateFont:[self defaultFont]
                              withFamily:self.rawTheme[@"weekdayFontFamilyt"]
                                    size:self.rawTheme[@"weekdayFontSize"]
                                  weight:self.rawTheme[@"weekdayFontWeight"]
                                   style:nil
                                 variant:nil
                         scaleMultiplier:1.0];
  self.dayFont = [RCTFont updateFont:[self defaultFont]
                          withFamily:self.rawTheme[@"dayFontFamily"]
                                size:self.rawTheme[@"dayFontSize"]
                              weight:self.rawTheme[@"dayFontWeight"]
                               style:nil variant:nil scaleMultiplier:1.0];
  self.monthTitleFont = [RCTFont updateFont:[self defaultFont]
                                 withFamily:self.rawTheme[@"monthTitleFontFamily"]
                                       size:self.rawTheme[@"monthTitleFontSize"]
                                     weight:self.rawTheme[@"monthTitleFontWeight"]
                                      style:nil
                                    variant:nil
                            scaleMultiplier:1.0];
  
}

- (UIFont *)defaultFont {
  return [UIFont systemFontOfSize:[UIFont systemFontSize]];
}

- (UIColor *)getColor:(NSString *)key {
  UIColor* color = [RCTConvert UIColor:self.rawTheme[key]];
  if (color == nil) {
    return [UIColor clearColor];
  }
  return color;
}

- (instancetype)init:(NSDictionary *) rawTheme;
{
  self = [super init];
  if (self) {
    self.rawTheme = rawTheme;
    [self setup];
  }
  return self;
}

@end

