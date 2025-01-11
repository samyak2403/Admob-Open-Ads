[![Version](https://img.shields.io/badge/version-1.0.samu-green.svg)](https://shields.io/)
[![Open Source Love svg1](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)
![pv](https://pageview.vercel.app/?github_user=Admob-Open-Ads)

## Contributors

Please Feel free to contribute by submitting a Pull Request!
[![](https://contrib.rocks/image?repo=samyak2403/Admob-Open-Ads)](https://github.com/samyak2403/IPTVmine/graphs/contributors)



# AdmobOpenAds
Implement admob open ads in your android app.<br/><br/>
 <img src="https://user-images.githubusercontent.com/46995327/122922701-8b862b80-d381-11eb-8431-4030ef740f81.jpg" width="250"  alt="DEMO"/>

# How to Implement
To get a Git project into your build:
> Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories: <br/>
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
    
> Step 2. Add the dependency

Add it in your root app.gradle at the end of repositories: <br/>
```gradle
dependencies {
	...
		implementation 'com.github.samyak2403:Admob-Open-Ads:1.0.samu'
	...
}
```

# How do I use Admob open Ads
Simple use cases will look something like this:
> Step 1. Create a class like MyApplication.class <br/>
```java
package arrowwould.in.admobopenads;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import arrowwould.in.admobopenads.AppOpenManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this);

        new AppOpenManager(this, "ca-app-pub-3940256099942544/9257395921");

    }

}
```
> Step 2. Update menifest
```xml
<application 
	name=".MyApplication"
	...
>
```

<br/>

# Connect With Us


## Made with :sparkling_heart: [mr_samyakkamble X miss_samruddhi](https://www.instagram.com/mr_samyakkamble/?hl=en/)     
