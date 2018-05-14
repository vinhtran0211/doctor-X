package com.vinh.doctor_x;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vinh.doctor_x.Fragment.Frg_Map;
import com.vinh.doctor_x.Fragment.Frg_bookappointment;
import com.vinh.doctor_x.Fragment.Frg_main_doctor;
import com.vinh.doctor_x.Fragment.Frg_main_patient;
import com.vinh.doctor_x.User.Location_cr;
import com.vinh.doctor_x.User.Patient_class;


public class Main_Screen_Acitivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private Boolean checkBtnSearch = false;
    public Patient_class patient = new Patient_class();

    public static Location_cr location_cr = new Location_cr();

    public Boolean getCheckBtnSearch() {
        return checkBtnSearch;
    }

    public void setCheckBtnSearch(Boolean checkBtnSearch) {
        this.checkBtnSearch = checkBtnSearch;
    }

    private static String locationpicker;

    public static String getLocationpicker() {
        return locationpicker;
    }

    public static void setLocationpicker(String locationpicker) {
        Main_Screen_Acitivity.locationpicker = locationpicker;
    }

    private static Double picker_Lat = 0.0,picker_Log = 0.0;

    public static Double getPicker_Lat() {
        return picker_Lat;
    }

    public static void setPicker_Lat(Double picker_Lat) {
        Main_Screen_Acitivity.picker_Lat = picker_Lat;
    }

    public static Double getPicker_Log() {
        return picker_Log;
    }

    public static void setPicker_Log(Double picker_Log) {
        Main_Screen_Acitivity.picker_Log = picker_Log;
    }

    FragmentManager manager;
    FragmentTransaction transaction;
    Frg_main_patient frg_main_patient = new Frg_main_patient();
    Frg_main_doctor frg_main_doctor = new Frg_main_doctor();

    Frg_Map map = new Frg_Map();
    private int type ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_acitivity);

     //   mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        setupDrawer();

        contextOfApplication = getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //View header = navigationView.inflateHeaderView(R.layout.nav_header_music);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();


        Intent intent = getIntent();
        Bundle packageFromCaller = intent.getBundleExtra("gettype");
        type = packageFromCaller.getInt("type");
        //setup fragment
        manager= getSupportFragmentManager();
        transaction= manager.beginTransaction();

        //type = 1;
        if(type == 1)
        {
            transaction.replace(R.id.frg_patient_main, map, "Fragment_Fill_Patient");
            //Intent i = new Intent(this,Realtime_Location_Map_Activity.class);
            //startActivity(i);
        }
        else if(type == 2)
        {
            transaction.replace(R.id.frg_patient_main, frg_main_doctor, "Fragment_Fill_Doctor");
        }
        transaction.addToBackStack(null);
        transaction.commit();

        //myRef.child("request_zone").child("nguyenvana_dothanhnam").setValue("0");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Log.i("clicked",id+"");
                    Log.i("clickedsome",R.id.nav_home+"");
                    map.trainfrg(1);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
    }
    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
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
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        Toast.makeText(contextOfApplication, item.getItemId(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //close navigation drawer

        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
