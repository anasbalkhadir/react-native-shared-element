//
//  RNVisualClone.h
//  react-native-visual-clone
//

#ifndef RNVisualClone_h
#define RNVisualClone_h

#import <React/RCTView.h>
#import <UIKit/UIKit.h>
#import "RNVisualCloneSourceManager.h"
#import "RNVisualCloneDelegate.h"

typedef NS_ENUM(NSInteger, RNVisualCloneContentType) {
    RNVisualCloneContentTypeSnapshot = 0,
    RNVisualCloneContentTypeImage = 1,
    RNVisualCloneContentTypeRawImage = 2
};

@interface RNVisualClone : RCTView <RNVisualCloneDelegate>

//@property (nonatomic, strong) RNVisualCloneSource cloneSource;
@property (nonatomic, assign) RNVisualCloneContentType contentType;
@property (nonatomic, assign) CGFloat blurRadius;

- (instancetype)initWithSourceManager:(RNVisualCloneSourceManager*)sourceManager;
- (void)setCloneSource:(NSNumber*)reactTag view:(UIView*)view;
- (void)refresh;

@end

#endif /* RNVisualClone_h */
