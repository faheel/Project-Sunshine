package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
	        startActivity(new Intent(this, SettingsActivity.class));
	        return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

	    private static final String forecastShareHashtag = " #SunshineApp";
	    private String forecastString;

        public PlaceholderFragment() {
	        setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

	        Intent intent = getActivity().getIntent();
	        if (intent != null && intent.hasExtra("WEATHER_DATA")) {
		        forecastString = intent.getStringExtra("WEATHER_DATA");
		        ((TextView) rootView.findViewById(R.id.textview_detail)).setText(forecastString);
	        }

            return rootView;
        }

	    @Override
	    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		    inflater.inflate(R.menu.detailfragment, menu);

		    MenuItem menuItem = menu.findItem(R.id.action_share);

		    ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
		    shareActionProvider.setShareIntent(createShareForecastIntent());
	    }

	    private Intent createShareForecastIntent() {
		    Intent shareIntent = new Intent(Intent.ACTION_SEND);
		    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
		    shareIntent.setType("text/plain")
				    .putExtra(Intent.EXTRA_TEXT,
						    (getActivity().getIntent().getStringExtra("WEATHER_DATA"))
								    + forecastShareHashtag);

		    return shareIntent;
	    }
    }
}