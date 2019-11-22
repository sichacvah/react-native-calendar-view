# React-native Native calendar view.

## Installation

`yarn add https://github.com/sichacvah/react-native-calendar-view`
or
`npm save https://github.com/sichacvah/react-native-calendar-view`

For ios:

```cd ios && pod install```

For android:

Add in `android/app/build.gradle` add

```implementation 'com.jakewharton.threetenabp:threetenabp:1.2.1'```

in MainApplication.java add

```import com.jakewharton.threetenabp.AndroidThreeTen;```

and in MainApplication#onCreate

```
  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
    initializeFlipper(this); // Remove this line if you don't want Flipper enabled
    AndroidThreeTen.init(this);
  }
```

## Screenshots

![Android](./AndroidExample.gif)

![iOS](./IOSVideo.gif)
