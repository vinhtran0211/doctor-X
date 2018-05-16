package com.vinh.doctor_x;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vinh.doctor_x.Fragment.Frg_Map;
import com.vinh.doctor_x.Fragment.Frg_bookappointment;
import com.vinh.doctor_x.Fragment.Frg_main_doctor;
import com.vinh.doctor_x.Fragment.Frg_main_patient;
import com.vinh.doctor_x.User.Doctor_class;
import com.vinh.doctor_x.User.Location_cr;
import com.vinh.doctor_x.User.Patient_class;


public class Main_Screen_Acitivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    public Patient_class patient = new Patient_class();

    public static Location_cr location_cr = new Location_cr();

    private static Boolean checkBtnSearch = false;
    public static Boolean getCheckBtnSearch() {
        return checkBtnSearch;
    }

    public static void setCheckBtnSearch(Boolean checkBtnSearch) {
        Main_Screen_Acitivity.checkBtnSearch = checkBtnSearch;
    }

    private static String locationpicker = "";

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


    public static Patient_class patient_class = new Patient_class();

    public static Patient_class getPatient() {
        return patient_class;
    }

    public void setPatient(Patient_class patient) {
        this.patient_class = patient;
    }

    public static Doctor_class getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor_class doctor) {
        this.doctor = doctor;
    }

    public static Doctor_class doctor = new Doctor_class();

    public static String key;

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        screen.key = key;
    }

    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;
    FragmentManager manager;
    FragmentTransaction transaction;
    Frg_main_patient frg_main_patient = new Frg_main_patient();

    private NavigationView navigationView;
    private int type ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_acitivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = new Intent(Main_Screen_Acitivity.this, service.class);
        startService(i);

     //   mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        setupDrawer();

        contextOfApplication = getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_dark)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();
        MenuItem nav_home = menu.findItem(R.id.nav_home);

        //View header = navigationView.inflateHeaderView(R.layout.nav_header_music);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        Intent intent = getIntent();
        Bundle packageFromCaller = intent.getBundleExtra("gettype");
        type = packageFromCaller.getInt("type");
        //setup fragment
        manager= getSupportFragmentManager();
        transaction= manager.beginTransaction();
        Frg_Map map = new Frg_Map();
        Frg_main_doctor frg_main_doctor = new Frg_main_doctor();
        //type = 1;
        if(type == 1)
        {
            transaction.replace(R.id.frg_patient_main, map, "Fragment_Fill_Patient");
        }
        else if(type == 2)
        {
            transaction.replace(R.id.frg_patient_main, frg_main_doctor, "Fragment_Fill_Doctor");
        }
        transaction.addToBackStack(null);
        transaction.commit();
        setup();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Log.i("clicked",id+"");
                    Log.i("clickedsome",R.id.nav_home+"");
                    //map.trainfrg(1);
                    changeFragment(new Frg_main_patient(), true);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });


        mFragmentManager = getSupportFragmentManager();

        if(screen.getKey() != null && getDoctor().getPhone() != null)
        {
            myRef.child("doctor").child(screen.getKey()).child("type").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        Log.d("typecheck",dataSnapshot.getValue(String.class));
                        if(dataSnapshot.getValue(String.class).equals("2"))
                        {
                            Effectstype effect;
                            NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(Main_Screen_Acitivity.this);
                            effect = Effectstype.Shake;
                            dialogBuilder
                                    .withTitle("Nguyen Van An")                                  //.withTitle(null)  no title
                                    .withTitleColor("#FFFFFF")                                  //def
                                    .withDividerColor("#11000000")                              //def
                                    .withMessage("You have a request ! \nDetail infor: Nguyen Van A \nLocation :")                     //.withMessage(null)  no Msg
                                    .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                                    .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                                    .withIcon(getResources().getDrawable(R.drawable.doctor))
                                    .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                                    .withDuration(700)                                          //def
                                    .withEffect(effect)                                         //def Effectstype.Slidetop
                                    .withButton1Text("OK")                                      //def gone
                                    .withButton2Text("Cancel")                                  //def gone
                                    //.setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                                    .setButton1Click(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent i = new Intent(Main_Screen_Acitivity.this,Doctor_Realtime_Map_Activity.class);
                                            i.putExtra("type","forapatient");
                                            startActivity(i);
                                            Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setButton2Click(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .show();

                            //TODO
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void changeFragment(Fragment fragment, boolean needToAddBackstack) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frg_patient_main, fragment);
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (needToAddBackstack)
            mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private void setup(){
        if(screen.getKey() != null)
        {
            setKey(screen.getKey());
            setDoctor(screen.getDoctor());
            setPatient(screen.getPatient());

            Toast.makeText(contextOfApplication, "Set up success with data screen \nKey: "+screen.getKey()+"\nDoctor: "+getDoctor().getName()+"\nPatient: "+getPatient().getName(), Toast.LENGTH_LONG).show();
        }
        else if(Login_Activity.getKey() != null){
            setKey(Login_Activity.getKey());
            setDoctor(Login_Activity.getDoctor());
            setPatient(Login_Activity.getPatient());

            Toast.makeText(contextOfApplication, "Set up success with data Login_Activity \nKey: "+Login_Activity.getKey()+"\nDoctor: "+getDoctor().getName()+"\nPatient: "+getPatient().getName(), Toast.LENGTH_LONG).show();
        }
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
                getSupportActionBar().setTitle("");
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
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        } else if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Log.i(TAG, "onOptionsItemSelected: Home Button Clicked");
            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
            } else {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        }
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
