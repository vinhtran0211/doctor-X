package com.vinh.doctor_x.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;
import com.vinh.doctor_x.Main_Screen_Acitivity;
import com.vinh.doctor_x.Modules.DirectionFinder;
import com.vinh.doctor_x.Modules.DirectionFinderListener;
import com.vinh.doctor_x.Modules.Route;
import com.vinh.doctor_x.R;
import com.vinh.doctor_x.Realtime_Location_Map_Activity;
import com.vinh.doctor_x.User.Doctor_class;
import com.vinh.doctor_x.User.Location_cr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.os.SystemClock.sleep;
import static com.vinh.doctor_x.Main_Screen_Acitivity.location_cr;

/**
 * Created by nntd290897 on 4/12/18.
 */

public class Frg_Map extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationChangeListener, DirectionFinderListener {


    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;

    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFrag;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private FusedLocationProviderClient mFusedLocationClient;
    private EditText txt_getlocationcurrent, txt_Origin, txt_Destination;
    private ImageButton button;
    private View view;
    private FloatingActionButton fab1, fab2, fab3, fab;
    boolean isFABOpen = false;
    private Location closestLocation;
    float smallestDistance = -1;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    public ProgressDialog progressDialog;
    private Button btn_findpath, btn_getit_frg_map;
    private LinearLayout lnl_findpath;
    private Dialog dialog_findpath;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private MarkerOptions options = new MarkerOptions();
    private Boolean swipe_1_up = true, swipe_2_up = true, swipe_3_up = true;
    private static List<Location_cr> locations = new ArrayList<Location_cr>();

    public static List<Location_cr> getLocations() {
        return locations;
    }

    private ArrayList<Marker> markers = new ArrayList<>();
    View myView, view_shortly_detail, view_cr;
    boolean isUp;
    private double lat_object, log_object, lat_object_picker, log_object_picker;
    private List<LatLng> polyline = new ArrayList<LatLng>();

    private FragmentManager manager;
    private FragmentTransaction fragmentTransaction = null;
    protected boolean _active = true;
    protected int _splashTime = 5000;

    private void writeLocation() {
        // Location_cr location_cr1 = new Location_cr(10.845539, 106.765556,"Diem 1","Thu duc");
        //Location_cr location_cr2 = new Location_cr(10.862619, 106.760842,"Diem 2","Thu duc");
        //Location_cr location_cr3 = new Location_cr(10.859711, 106.748182,"Diem 3","Thu duc");

        //  String key = myRef.child("loglat_current").push().getKey();
        // myRef.child("loglat_current").child(key).setValue(location_cr1);

        // String key1 = myRef.child("loglat_current").push().getKey();
        //  myRef.child("loglat_current").child(key1).setValue(location_cr2);

        // String key2 = myRef.child("loglat_current").push().getKey();
        // myRef.child("loglat_current").child(key2).setValue(location_cr3);
    }

    private void sentrequest(String namepatient, String namedoctor) {

        //String key2 = myRef.child("request_zone").push().getKey();
        myRef.child("request_zone").child(namepatient + "_" + namedoctor).setValue("0");
    }


    public void startdistancenow(String k) {
        Log.i("checkacti_st", k);
        //   progressDialog.dismiss();
        distance(mCurrLocationMarker.getPosition().latitude, mCurrLocationMarker.getPosition().longitude);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Context mContext = getActivity();
        progressDialog = new ProgressDialog(mContext);
        view = inflater.inflate(R.layout.frg_map, container, false);
        txt_getlocationcurrent = (EditText) view.findViewById(R.id.txt_locationcurrent);
        btn_getit_frg_map = (Button) view.findViewById(R.id.btn_getit_frg_map);
        myView = (View) view.findViewById(R.id.my_view);
        myView.setVisibility(View.INVISIBLE);
        view_shortly_detail = (View) view.findViewById(R.id.detail_sortly_view);
        view_shortly_detail.setVisibility(View.INVISIBLE);
        view_cr = (View) view.findViewById(R.id.view_cr);
        view_cr.setVisibility(View.INVISIBLE);
        manager = getActivity().getSupportFragmentManager();
        fragmentTransaction = manager.beginTransaction();

        //button = (ImageButton)view.findViewById(R.id.btn_getLocation);
        //getActivity().getSupportActionBar().setTitle("Map Location Activity");
        lnl_findpath = (LinearLayout) view.findViewById(R.id.lnl_findpath);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) view.findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });
        dialog_findpath = new Dialog(view.getContext());
        dialog_findpath.setContentView(R.layout.dialog_findpath);
        dialog_findpath.setTitle("Choose Yours Location");
        txt_Origin = (EditText) dialog_findpath.findViewById(R.id.txt_Origin);
        txt_Destination = (EditText) dialog_findpath.findViewById(R.id.txt_Destination);
        btn_findpath = (Button) dialog_findpath.findViewById(R.id.btn_FindPath);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // dialog_findpath.show();
            }
        });

        btn_findpath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //distance();
                //dialog_findpath.dismiss();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager = getActivity().getSupportFragmentManager();
                fragmentTransaction = manager.beginTransaction();
                Frg_bookappointment bookappointment = new Frg_bookappointment();
                fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                fragmentTransaction.replace(R.id.frg_patient_main, bookappointment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swipe_3_up == false) {
                    slideDown(myView);
                    //myButton.setText("Slide up");
                } else {
                    slideUp(myView);
                    //myButton.setText("Slide down");
                }
                swipe_3_up = !swipe_3_up;
            }
        });
        setupmapifneed();
        //myRef.child("request_zone").child("abc").setValue("1");
        return view;
    }


    public void loadingMarkernear() {
        myRef.child("loglat_current").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("dataSnapshot", dataSnapshot.exists() + "");
                Location_cr location_cr = dataSnapshot.getValue(Location_cr.class);
                if (checkCopy(location_cr.getMobile())) {
                    //if(checknearly(new LatLng(location_cr.getLat(),location_cr.getLog())))
                    if (PolyUtil.isLocationOnPath(new LatLng(location_cr.getLat(), location_cr.getLog()), polyline,
                            true,
                            50000)) {
                        locations.add(location_cr);
                        Log.d("checkchange", location_cr.getName() + "");
                        Marker marker;
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.markerdoctor);
                        options.position(new LatLng(location_cr.getLat(), location_cr.getLog()));
                        options.title(location_cr.getName());
                        options.snippet(location_cr.getMobile());
                        options.icon(icon);
                        marker = mGoogleMap.addMarker(options);
                        //mGoogleMap.addMarker(options);
                        markers.add(marker);
                    } else {
                        Log.d("checkchange", location_cr.getName() + " fail check ht" + polyline.get(0).longitude);
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.i("Showmarker", locations.size() + "");
        //Location_cr location1 = new Location_cr(10.357264,106.223163,"diem2","ben tre");
        //  Location_cr location2 = new Location_cr(10.331439,106.037153,"diem1","ca mau");
        //  locations.add(location1);
        // locations.add(location2);
        //ArrayList<LatLng> latLngArrayList = new ArrayList<>();
    }

    public boolean checkCopy(String newphone) {
        if (locations.size() > 0) {
            for (Location_cr location_cr : locations) {
                if (!location_cr.getMobile().equals(newphone)) {
                    return true;
                }
            }
        } else if (locations.size() == 0) {
            return true;

        }
        return false;
    }


    public void distance(Double lat, Double log) {

        progressDialog.dismiss();

        Log.i("checkacti-distance-now", progressDialog.isShowing() + "");
        final ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int smallest = -1;
        for (int i = 0; i < locations.size(); i++) {
            float[] results = new float[3];
            Location.distanceBetween(locations.get(i).getLat(), locations.get(i).getLog(),
                    mCurrLocationMarker.getPosition().latitude, mCurrLocationMarker.getPosition().longitude, results);
            if (smallestDistance == -1 || results[0] < smallestDistance) {
                smallestDistance = results[0];
                smallest = i;
            }
            Log.i("Distance", results[0] + "");
            Log.i("smallest", smallest + "");
            arrayList.add((int) results[0]);
        }
        Log.i("checkact-latlog", lat + "," + log);
        sendRequest(lat + "," + log, locations.get(smallest).getLat() + "," + locations.get(smallest).getLog());
    }


    private void showFABMenu() {
        isFABOpen = true;
        fab.animate().rotationBy(180);
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab.animate().rotationBy(-180);
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupmapifneed();
    }

    private void setupmapifneed() {
        if (mGoogleMap == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

            mapFrag = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);

            if (mGoogleMap != null) {
                //setUpMap();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupmapifneed();

    }

    private void setUpMap() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                // mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }


        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(Main_Screen_Acitivity.getCheckBtnSearch())
                {
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();
                    }
                    Toast.makeText(getContext(), latLng.toString(), Toast.LENGTH_SHORT).show();
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.patient_marker);
                    MarkerOptions markerOption = new MarkerOptions();
                    markerOption.position(latLng).icon(icon);
                    mCurrLocationMarker = googleMap.addMarker(markerOption);
                    lat_object_picker = latLng.latitude;
                    log_object_picker = latLng.longitude;
                    txt_getlocationcurrent.setText(getLocationName(latLng.latitude, latLng.longitude));


                    Effectstype effect;
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
                    effect = Effectstype.Newspager;
                    dialogBuilder
                            .withTitle("Becarefull")                                  //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#11000000")                              //def
                            .withMessage("\n Do you get location current at there ?")                     //.withMessage(null)  no Msg
                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                            .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                            //.withIcon(getResources().getDrawable(R.drawable.doctor))
                            .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                            .withDuration(700)                                          //def
                            .withEffect(effect)                                         //def Effectstype.Slidetop
                            .withButton1Text("OK")                                      //def gone
                            .withButton2Text("Cancel")                                  //def gone
                            //.setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                            .setButton1Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    lat_object_picker = latLng.latitude;
                                    log_object_picker = latLng.longitude;
                                    FragmentManager frg_map = getActivity().getSupportFragmentManager();
                                    dialogBuilder.dismiss();
                                    Main_Screen_Acitivity.setLocationpicker(txt_getlocationcurrent.getText().toString());
                                    if (frg_map.getBackStackEntryCount() > 0) {
                                        Log.i("MainActivity", "popping backstack");
                                        frg_map.popBackStack();
                                    } else {
                                        Log.i("MainActivity", "nothing on backstack, calling super");
                                    }
                                }
                            })
                            .setButton2Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialogBuilder.dismiss();
                                }
                            })
                            .show();
                }
            }
        });


        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(!Main_Screen_Acitivity.getLocationpicker().equals(""))
                {
                    if (swipe_1_up == false) {
                        slideDown(view_cr);
                        swipe_1_up = true;
                    }
                    if (swipe_3_up == false) {
                        slideDown(myView);
                        swipe_3_up = true;
                    }
                }
            }
        });

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(Main_Screen_Acitivity.getCheckBtnSearch())
                {
                    Log.i("titlemar", marker.getPosition().latitude + "..." + lat_object);
                    if (marker.getPosition().latitude == lat_object) {
                        if (swipe_1_up == false) {
                            //slideDown(myView);
                            //slideDown(view_shortly_detail);
                            //myButton.setText("Slide up");
                            slideDown(view_cr);
                        } else {
                            slideUp(view_cr);
                            btn_getit_frg_map.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Set location current yours at there ? ");
                                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        lat_object_picker = marker.getPosition().latitude;
                                        log_object_picker = marker.getPosition().latitude;
                                        FragmentManager frg_map = getActivity().getSupportFragmentManager();
                                        Main_Screen_Acitivity.setLocationpicker(txt_getlocationcurrent.getText().toString());
                                        if (frg_map.getBackStackEntryCount() > 0) {
                                            Log.i("MainActivity", "popping backstack");
                                            frg_map.popBackStack();
                                        } else {
                                            Log.i("MainActivity", "nothing on backstack, calling super");
                                        }
                                    }
                                });
                                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //TODO
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();*/
                                    Effectstype effect;
                                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
                                    effect = Effectstype.Newspager;
                                    dialogBuilder
                                            .withTitle("Set location current yours at there ?")                                  //.withTitle(null)  no title
                                            .withTitleColor("#FFFFFF")                                  //def
                                            .withDividerColor("#11000000")                              //def
                                            // .withMessage("\nWe did not find the sutable doctor for you.  \nPlease try again !")                     //.withMessage(null)  no Msg
                                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                                            .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                                            //.withIcon(getResources().getDrawable(R.drawable.doctor))
                                            .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                                            .withDuration(700)                                          //def
                                            .withEffect(effect)                                         //def Effectstype.Slidetop
                                            .withButton1Text("OK")                                      //def gone
                                            .withButton2Text("Cancel")                                  //def gone
                                            //.setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                                            .setButton1Click(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    lat_object_picker = marker.getPosition().latitude;
                                                    log_object_picker = marker.getPosition().latitude;
                                                    FragmentManager frg_map = getActivity().getSupportFragmentManager();
                                                    dialogBuilder.dismiss();
                                                    Main_Screen_Acitivity.setLocationpicker(txt_getlocationcurrent.getText().toString());
                                                    if (frg_map.getBackStackEntryCount() > 0) {
                                                        Log.i("MainActivity", "popping backstack");
                                                        frg_map.popBackStack();
                                                    } else {
                                                        Log.i("MainActivity", "nothing on backstack, calling super");
                                                    }
                                                }
                                            })
                                            .setButton2Click(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    dialogBuilder.dismiss();
                                                }
                                            })
                                            .show();
                                }
                            });
                        }
                        swipe_1_up = !swipe_1_up;
                    } else {
                        if (swipe_1_up == false) {
                            slideDown(view_cr);
                        }
                        if (isUp) {
                            slideDown(view_shortly_detail);
                            //myButton.setText("Slide up");
                        } else {
                            slideUp(view_shortly_detail);
                            //myButton.setText("Slide down");
                        }
                        isUp = !isUp;
                        return false;
                    }
                    return false;
                }
                else if(Main_Screen_Acitivity.getCheckBtnSearch() == false){
                    if (marker.getPosition().latitude == lat_object)
                    {
                        slideUp(view_shortly_detail);
                        marker.showInfoWindow();
                    }
                    else{
                        for(Location_cr location_cr:locations)
                        {
                            if(location_cr.getLat() == marker.getPosition().latitude)
                            {
                                getInfordoctor(location_cr.getKey_user());

                            }
                            else{
                                slideDown(view_shortly_detail);
                            }
                        }

                    }
                }
                return true;
            }
        });


        //mGoogleMap.setMyLocationEnabled(true);
        //mGoogleMap.setOnMyLocationChangeListener(this);
    }

    private Doctor_class getInfordoctor(String key_user)
    {
        final Doctor_class[] doctor_class = new Doctor_class[1];
        myRef.child("Doctor").child(key_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doctor_class[0] = dataSnapshot.getValue(Doctor_class.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return doctor_class[0];
    }

    public String getLocationName(double lattitude, double longitude) {
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
        Main_Screen_Acitivity.setPicker_Lat(lattitude);
        Main_Screen_Acitivity.setPicker_Log(longitude);
        try {
            List<Address> addresses = gcd.getFromLocation(lattitude, longitude,
                    10);

            cityName = addresses.get(0).getAddressLine(0);
            Log.d("ADD", cityName);
            if (cityName == null) {
                cityName = "Not Found";
            }
            // addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;

    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                txt_getlocationcurrent.setText(getLocationName(location.getLatitude(), location.getLongitude()));
                //Place current location marker
                BitmapDescriptor icon;
                if(Main_Screen_Acitivity.getDoctor().getPhone() == null)
                {
                     icon = BitmapDescriptorFactory.fromResource(R.drawable.patient_marker);
                }
                else {
                     icon = BitmapDescriptorFactory.fromResource(R.drawable.markerdoctor);
                }
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                //markerOptions = new MarkerOptions();
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(icon);
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                polyline.add(new LatLng(location.getLatitude(), location.getLongitude()));
                lat_object = location.getLatitude();
                log_object = location.getLongitude();

                Log.i("LocationCP", lat_object + "," + log_object);
                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
                if(Main_Screen_Acitivity.getDoctor().getPhone()  != null)
                {
                    myRef.child("loglat_current").child(Main_Screen_Acitivity.getKey()).child("Lat").setValue(lat_object);
                    myRef.child("loglat_current").child(Main_Screen_Acitivity.getKey()).child("Log").setValue(log_object);
                }

                loadingMarkernear();
                /*if (((Main_Screen_Acitivity) getActivity()).getCheckBtnSearch()) {

                    Log.i("Showmarker",""+((Main_Screen_Acitivity) getActivity()).getCheckBtnSearch());
                    //((Main_Screen_Acitivity) getActivity()).setCheckBtnSearch(false);
                    sentrequest("nguyenvana","dothanhnam");
                    //distance(mCurrLocationMarker.getPosition().latitude,mCurrLocationMarker.getPosition().longitude);
                }*/
            }
        }

        ;

    };


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?-
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i = 0; i < maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

            String fnialAddress = builder.toString(); //This is the complete address.
            Toast.makeText(getActivity(), fnialAddress, Toast.LENGTH_LONG);

        } catch (IOException e) {
            // Handle IOException
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMyLocationChange(Location location) {

    }

    private void sendRequest(String latlog_begin, String latlog_end) {
        // String origin = txt_Origin.getText().toString();
        // String destination = txt_Destination.getText().toString();
        Log.i("run", "sendRequest");
        String origin = latlog_begin;
        String destination = latlog_end;
        if (origin.isEmpty()) {
            Toast.makeText(getContext(), "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(getContext(), "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {
//        progressDialog = ProgressDialog.show(getActivity(), "Please wait.",
        // "Finding direction..!", true);
        Log.i("run", "onDirectionFinderStart");
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        //progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 15));
            ((TextView) view.findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) view.findViewById(R.id.tvDistance)).setText(route.distance.text);
            mCurrLocationMarker.remove();
            for (Marker marker : markers) {
                marker.remove();
            }
            originMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.patient_marker))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerdoctor))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        }

    }


    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }


    public void trainfrg(int position) {
        Log.i("clicked", position + "");

        Frg_main_patient frg_main_patient = new Frg_main_patient();
        if (position == 1) {
            fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
            fragmentTransaction.replace(R.id.frg_patient_main, frg_main_patient);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
