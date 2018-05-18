package com.vinh.doctor_x;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vinh.doctor_x.Fragment.Frg_Map;
import com.vinh.doctor_x.Fragment.Frg_bookappointment;
import com.vinh.doctor_x.Modules.DirectionFinder;
import com.vinh.doctor_x.Modules.DirectionFinderListener;
import com.vinh.doctor_x.Modules.Route;
import com.vinh.doctor_x.User.Doctor_class;
import com.vinh.doctor_x.User.Location_cr;

public class Realtime_Location_Map_Activity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private List<Location_cr> locations = new ArrayList<Location_cr>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    float smallestDistance = -1;
    private Location_cr location_des = new Location_cr();
    private List<Location_cr> locations_nearly = Frg_Map.getLocations();
    private Double lat_cr_user, log_cr_user;

    private String address_cr_user;
    private String request = null;

    private List<String> send_for_doctor = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realtime_location_map_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       // btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = (EditText) findViewById(R.id.etDestination);

        myRef.child("doctor").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Doctor_class doctor = dataSnapshot.getValue(Doctor_class.class);
                for (Location_cr location_cr : locations_nearly) {
                    if (location_cr.getMobile().equals(doctor.getPhone())) {
                        if (doctor.getSpecialist().equals(Frg_bookappointment.getSpecialist())) {
                            send_for_doctor.add(key);
                            String query= Frg_bookappointment.getKey_patient_request_zone()+"_"+Main_Screen_Acitivity.getPatient().getPhone()+"_"+Main_Screen_Acitivity.getPatient().getName()+"_"+Main_Screen_Acitivity.getKey()+"_"+getLocationName(Main_Screen_Acitivity.getPicker_Lat(),Main_Screen_Acitivity.getPicker_Log());
                            Log.i("query",query);
                            myRef.child("doctor").child(key).child("type").setValue(query);
                            //2 LA trang thai requested
                        }
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
        myRef.child("loglat_current").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Location_cr location_cr = dataSnapshot.getValue(Location_cr.class);
                if (checkCopy(location_cr.getMobile())) {
                    locations.add(location_cr);
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
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding Doctor for you..!", true);
        myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(Frg_bookappointment.getKey_patient_request_zone()).child("Doctor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String value = dataSnapshot.getValue(String.class);
                    if (!value.equals("null")) {
                        //distance();
                        myRef.child("loglat_current").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                location_des = dataSnapshot.getValue(Location_cr.class);
                                if (location_des.getMobile().equals(value)) {

                                    sendRequest(Main_Screen_Acitivity.getPicker_Lat() + "," + Main_Screen_Acitivity.getPicker_Log(), location_des.getLat() + "," + location_des.getLog());

                                    Log.i("request", lat_cr_user + "," + log_cr_user);
                                    Log.i("pos_patient", location_des.getLat() + "," + location_des.getLog());
                                    //Toast.makeText(this, route.toString(), Toast.LENGTH_SHORT).show();
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
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        CountDownTimer yourCountDownTimer = new CountDownTimer(60000, 1000) {                     //geriye sayma

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(Frg_bookappointment.getKey_patient_request_zone()).setValue(null);
                    for(String k:send_for_doctor)
                    {
                        myRef.child("doctor").child(k).child("type").setValue("waiting");
                    }

                    Effectstype effect;
                    NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(Realtime_Location_Map_Activity.this);
                    effect = Effectstype.RotateBottom;
                    dialogBuilder
                            .withTitle("SORRY")                                  //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#11000000")                              //def
                            .withMessage("\nWe did not find the sutable doctor for you.  \nPlease try again !")                     //.withMessage(null)  no Msg
                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                            .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                            //.withIcon(getResources().getDrawable(R.drawable.doctor))
                            .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                            .withDuration(700)                                          //def
                            .withEffect(effect)                                         //def Effectstype.Slidetop
                            .withButton1Text("OK")                                      //def gone
                           // .withButton2Text("Cancel")                                  //def gone
                            //.setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                            .setButton1Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                    dialogBuilder.dismiss();
                                }
                            })
                            .setButton2Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();
                }

            }
        }.start();
    }

    public String getLocationName(double lattitude, double longitude) {
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        Main_Screen_Acitivity.setPicker_Lat(lattitude);
        Main_Screen_Acitivity.setPicker_Log(longitude);
        try {
            List<Address> addresses = gcd.getFromLocation(lattitude, longitude,
                    10);

            cityName = addresses.get(0).getAddressLine(0);
            Log.d("ADD",cityName);
            if(cityName == null)
            {
                cityName = "Not Found";
            }
            // addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;

    }


    public boolean checkCopy(String newphone) {
        if (locations.size() > 0) {
            for (Location_cr location_cr : locations) {
                if (location_cr.getMobile().equals(newphone)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void distance() {
        //loadingMarkernear();


        final ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int smallest = -1;
        for (int i = 0; i < locations.size(); i++) {
            float[] results = new float[3];
            Location.distanceBetween(10.8556827, 106.760839, locations.get(i).getLat(), locations.get(i).getLog(), results);
            if (smallestDistance == -1 || results[0] < smallestDistance) {
                smallestDistance = results[0];
                smallest = i;
            }
            Log.i("Distance", results[0] + "");
            Log.i("smallest", smallest + "");
            arrayList.add((int) results[0]);
        }
        //Log.i("checkact-latlog", lat+ "," + log);
        // sendRequest(10.8556827 + "," +106.760839, locations.get(smallest).getLat() + "," + locations.get(smallest).getLog());

    }


    private void sendRequest(String latlog_begin, String latlog_end) {
        //String origin = etOrigin.getText().toString();
        // String destination = etDestination.getText().toString();

        String origin = latlog_begin;
        String destination = latlog_end;
        Toast.makeText(this, latlog_begin + "," + latlog_end, Toast.LENGTH_SHORT).show();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng hcmus = new LatLng(10.8556827, 106.760839);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("null position")
                .position(hcmus)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
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
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.patient_marker))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerdoctor))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
            etOrigin.setText(getLocationName(Main_Screen_Acitivity.getPicker_Lat(),Main_Screen_Acitivity.getPicker_Log()));
            etDestination.setText(getLocationName(location_des.getLat(), location_des.getLog()));
        }
    }
}

///Realtime_Location_Map_Activity   setContentView(R.layout.realtime_location_map_layout);