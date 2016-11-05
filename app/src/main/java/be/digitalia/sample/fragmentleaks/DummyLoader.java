package be.digitalia.sample.fragmentleaks;

import android.content.Context;
import android.support.v4.content.Loader;

/**
 * A dummy Loader than immediately returns "true" as result.
 */

class DummyLoader extends Loader<Boolean> {
	DummyLoader(Context context) {
		super(context);
	}

	@Override
	protected void onStartLoading() {
		deliverResult(true);
	}
}
