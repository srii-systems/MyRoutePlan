package com.srii_systems.myrouteplan;

import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.srii_systems.myrouteplan.model.RoutePlan;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private ActionBarDrawerToggle mDrawerToggle;

    private EditText fromEditText;
    private EditText toEditText;

    final RoutePlan routePlan = new RoutePlan();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.myrouteplan_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        // set a custom shadow that overlays the main content when the drawer opens
        //  mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                //R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            //selectItem(0);
        }

        Button button = (Button) findViewById(R.id.searchRouteButton);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                fromEditText = (EditText) findViewById(R.id.fromEditText);
                toEditText = (EditText) findViewById(R.id.toEditText);


                routePlan.setmFromEditText(fromEditText.getText().toString());
                routePlan.setmToEditText(toEditText.getText().toString());
                getGeocode();
            }
        });
    }
    void getGeocode() {
        String locationFrom = routePlan.getmFromEditText();
        String locationTo = routePlan.getmToEditText();
        Double latitudeFrom = Double.valueOf(0);
        Double longitudeFrom = Double.valueOf(0);
        Double latitudeTo = Double.valueOf(0);
        Double longitudeTo = Double.valueOf(0);

        Geocoder gc = new Geocoder(this);
        try {
            List<Address> listFrom = gc.getFromLocationName(locationFrom, 1);
            List<Address> listTo = gc.getFromLocationName(locationTo, 1);
            latitudeFrom = listFrom.get(0).getLatitude();
            longitudeFrom = listFrom.get(0).getLongitude();
            latitudeTo = listTo.get(0).getLatitude();
            longitudeTo = listTo.get(0).getLongitude();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(getBaseContext(), latitudeFrom / 1E6 + "," +
                longitudeFrom / 1E6, Toast.LENGTH_LONG).show();

        Toast.makeText(getBaseContext(), latitudeTo / 1E6 + "," +
                longitudeTo / 1E6, Toast.LENGTH_LONG).show();

    }


    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //selectItem(position);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
