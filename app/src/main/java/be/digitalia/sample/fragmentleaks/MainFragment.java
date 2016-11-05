package be.digitalia.sample.fragmentleaks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

	private static final int LOADER_ID = 1;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Uncomment this line to test the behaviour with a retained fragment
		//setRetainInstance(true);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getLoaderManager().initLoader(LOADER_ID, null, mLoaderCallbacks);
	}

	final LoaderManager.LoaderCallbacks<Boolean> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Boolean>() {
		@Override
		public Loader<Boolean> onCreateLoader(int id, Bundle args) {
			return new DummyLoader(getActivity());
		}

		@Override
		public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
			// Ignore the result
		}

		@Override
		public void onLoaderReset(Loader<Boolean> loader) {
		}
	};
}
