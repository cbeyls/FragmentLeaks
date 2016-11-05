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

## LeakCanary analysis

Using the [LeakCanary](https://github.com/square/leakcanary) library from Square, the leak can be traced back to a variable named ```mHost``` in ```LoadermanagerImpl```:

```
In be.digitalia.sample.fragmentleaks:1.0:1.
* be.digitalia.sample.fragmentleaks.MainActivity has leaked:
* GC ROOT static android.view.WindowManagerGlobal.sDefaultWindowManager
* references android.view.WindowManagerGlobal.mRoots
* references java.util.ArrayList.array
* references array java.lang.Object[].[0]
* references android.view.ViewRootImpl.mContext
* references be.digitalia.sample.fragmentleaks.MainActivity.mFragments
* references android.support.v4.app.FragmentController.mHost
* references android.support.v4.app.FragmentActivity$HostCallbacks.mAllLoaderManagers
* references android.support.v4.util.SimpleArrayMap.mArray
* references array java.lang.Object[].[1]
* references android.support.v4.app.LoaderManagerImpl.mHost
* references android.support.v4.app.FragmentActivity$HostCallbacks.this$0
* leaks be.digitalia.sample.fragmentleaks.MainActivity instance
```

Both issues produce the same leak trace so they seem to be caused by the same bug.
