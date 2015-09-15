package com.srii_systems.myrouteplan;

import android.content.Context;
import android.content.res.Configuration;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private ActionBarDrawerToggle mDrawerToggle;

    private EditText fromEditText;
    private EditText toEditText;
   // private String requestString = "http://api.reittiopas.fi/hsl/1_2_1/?request=route&userhash=b43b1faa155034ea77333e303d273f4696c80e69101f&format=json";
    private String requestString = "http://api.reittiopas.fi/hsl/1_2_1/?request=route&userhash=12b3f24ee7d3175f8e2358f736aa76167723f9ce6ede&format=json&from=2548196,6678528&to=2549062,6678638";

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
               makeRouteRequestUri();
            }
        });
    }
    void getGeocode() {
        String locationFrom = routePlan.getmFromEditText();
        String locationTo = routePlan.getmToEditText();
        /*Double latitudeFrom = Double.valueOf(0);
        Double longitudeFrom = Double.valueOf(0);
        Double latitudeTo = Double.valueOf(0);
        Double longitudeTo = Double.valueOf(0);*/

        //Geocoder gc = new Geocoder(this);
        Geocoder gc = new Geocoder(getApplicationContext(), Locale.ENGLISH);
        boolean isgcpresent = gc.isPresent();
        try {
           /* List<Address> listFrom = gc.getFromLocationName(locationFrom, 1);
            List<Address> listTo = gc.getFromLocationName(locationTo, 1);

            routePlan.setFromLatitude(listFrom.get(0).getLatitude());
            routePlan.setFromLongitude(listFrom.get(0).getLongitude());
            routePlan.setToLatitude(listTo.get(0).getLatitude());
            routePlan.setToLongitude(listTo.get(0).getLongitude());*/

            /*latitudeFrom = listFrom.get(0).getLatitude();
            longitudeFrom = listFrom.get(0).getLongitude();
            latitudeTo = listTo.get(0).getLatitude();
            longitudeTo = listTo.get(0).getLongitude();*/
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* Toast.makeText(getBaseContext(), routePlan.getFromLatitude() + "," +
                routePlan.getFromLongitude() , Toast.LENGTH_LONG).show();

        Toast.makeText(getBaseContext(), routePlan.getToLatitude()+ "," +
                routePlan.getToLongitude() , Toast.LENGTH_LONG).show();*/
        //epsg_in=4326
    }

  void makeRouteRequestUri()
  {

     // String requestUrl = requestString + "&from="+ routePlan.getFromLongitude()+","+ routePlan.getFromLatitude()+
     //         "&to="+ routePlan.getToLongitude()+","+ routePlan.getToLatitude();

   // String requestUrl = "http://api.reittiopas.fi/hsl/1_2_1/?request=route&userhash=b43b1faa155034ea77333e303d273f4696c80e69101f&format=json&from=2548196,6678528&to=2549062,6678638";
      //HttpURLConnection httpUrlcon = null;
     // String url = null;
      ConnectivityManager connMgr = (ConnectivityManager)
              getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
      if (networkInfo != null && networkInfo.isConnected()) {
          new RouteRequestTask().execute(requestString);
      } else {
          //textView.setText("No network connection available.");
      }

  }
    private class RouteRequestTask extends AsyncTask<String, Void, String> {
        @Override

        protected String doInBackground(String... urls) {

     //       String requestUrl = "http://api.reittiopas.fi/hsl/1_2_1/?request=route&userhash=b43b1faa155034ea77333e303d273f4696c80e69101f&format=json&from=2548196,6678528&to=2549062,6678638";

            String data = null;
            String result = null;
            String contentAsString = null;
            int status = 0;
            // web page content.
            int len = 500;
            InputStream is = null;
            // params comes from the execute() call: params[0] is the url.
            try {
                //Connect
                URL url = new URL(requestString);
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                // httpUrlcon = (HttpURLConnection) ((new URL("http://api.reittiopas.fi/hsl/1_2_1/?request=route&userhash=b43b1faa155034ea77333e303d273f4696c80e69101f&format=json&from=2548196,6678528&to=2549062,6678638").openConnection()));
                httpCon.setReadTimeout(10000 /* milliseconds */);
                httpCon.setConnectTimeout(15000 /* milliseconds */);
                httpCon.setRequestMethod("GET");
                httpCon.setDoInput(true);


                httpCon.connect();
                status = httpCon.getResponseCode();

                is = httpCon.getInputStream();

                // Convert the InputStream into a string
                contentAsString = readIt(is, len);
                return contentAsString;
                //System.out.println(contentAsString);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (IOException e) {
                Toast.makeText(getBaseContext(), status, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return "Unable to retrieve web page. URL may be invalid.";

            }finally {
                if (is != null) {
                    //is.close();
                }
            }
           // Toast.makeText(getBaseContext(), contentAsString, Toast.LENGTH_LONG).show();

            return contentAsString;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //textView.setText(result);
        }

        // Reads an InputStream and converts it to a String.
        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
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
