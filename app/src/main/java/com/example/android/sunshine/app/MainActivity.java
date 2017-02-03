package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
	        startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        else if (id == R.id.action_location) {
	        openPreferredLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	public void openPreferredLocationInMap() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		String location = pref.getString(getString(R.string.pref_location_key),
				getString(R.string.pref_location_default_value));
		Uri locationUri = Uri.parse("geo:0,0?").buildUpon()
				.appendQueryParameter("q", location)
				.build();

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(locationUri);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}
}
