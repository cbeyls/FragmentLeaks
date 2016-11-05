# Memory leaks in Fragments using Loaders

A sample project to demonstrate memory leak issues in the Android Support Library when using Fragments in combination with the Loader API.

## Known issues

* Since version 24.0.0 of the support library, a Fragment using a Loader *which is detached* will leak the Activity through the LoaderManager. Fragments are usually detached when switching between sections inside an app or when swiping in a ```ViewPager``` using a ```FragmentPagerAdapter```.

* Since way before that (maybe since forever), a Fragment using a Loader with ```setRetainInstance(true)``` will leak the Activity through the LoaderManager. The fragment does not need to be detached for the leak to occur.

## Steps to reproduce

- Launch this project on any device
- Press the button to detach the fragment
- Rotate the screen to trigger a configuration change
- In the Memory Monitor in Android Studio, initiate a few garbage collections then press the button to dump the Java heap.
- In the heap analyzer, look for the ```MainActivity``` class and notice there are two instances of it in memory instead of one.

