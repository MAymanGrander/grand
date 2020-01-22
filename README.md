# Grand inc.
Use all of grand custom views and classes easily with just one line of code :smile:.

## Prerequisites 
Make sure to use:

* minSdkVersion >= 21.
* enable support for vector drawables.
* compile with JAVA 8.
* use the MaterialDesignTheme as the main AppTheme.

### Getting Started
1- In your app build.gradle set

```
defaultConfig {  
       minSdkVersion 21
       vectorDrawables.useSupportLibrary = true
}

compileOptions {
        sourceCompatibility = '1.8'
        targetCompatibility = '1.8'
}
```
    
2- Set the AppTheme parent to MaterialTheme and use it as the main theme for your app:

download: `implementation 'com.google.android.material:material:(latest-version)'` 
```
<style name="AppTheme" parent="Theme.MaterialComponents.Light.NoActionBar">
        <!-- Customize your theme here. -->
</style>
```
### Installing
In root project build.gradle
```
allprojects {
     maven { url "https://jitpack.io" }
}
```
In app build.gradle download the dependency
```
debugImplementation('com.github.MAymanGrander:grand:master-SNAPSHOT') {
        transitive=true
        changing=true
}
```
### Authors
With all of Love :heart: to share our knowledge to all Granders :muscle: 
* Mahmoud Ayman
* Osama Mohsen
* Mohammed Atef
* Mostafa Elnagar
## License
All rights reserved to [Grand Company](https://2grand.net/) © 2003-forever
