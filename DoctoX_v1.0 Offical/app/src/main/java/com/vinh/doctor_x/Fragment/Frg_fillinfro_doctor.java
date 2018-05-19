package com.vinh.doctor_x.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vinh.doctor_x.Main_Screen_Acitivity;
import com.vinh.doctor_x.R;
import com.vinh.doctor_x.SignUp_Activity;
import com.vinh.doctor_x.Testmap;
import com.vinh.doctor_x.User.Doctor_class;
import com.vinh.doctor_x.User.Location_cr;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nntd290897 on 3/12/18.
 */

public class Frg_fillinfro_doctor extends Fragment implements OnMapReadyCallback {

    private static final int CAM_REQUEST = 1313;
    private Dialog dialog;
    private View view;

    private FragmentManager fragmentManager ;
    private FragmentTransaction fragmentTransaction ;
    private ProgressDialog message;
    FirebaseDatabase database;
    DatabaseReference reference;
    Bitmap thumbnail;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseauthlistener;


    EditText _nameText;
    EditText _addressText;
    EditText _emailText;
    EditText _mobileText;
    EditText _doctorcode;
    EditText _DOB;
    EditText _workingat;
    EditText _workinghour;
    RadioButton rdb_man ;
    RadioButton rdb_women ;
    RadioGroup rdg_chooseType ;
    String gender;
    String specialist;
    FragmentTransaction ft ;
    private Fragment frg_fill_map;

    Button _signupButton;
    private static final String TAG = "my_log";
    ImageButton img_ava_patient;
    String [] values =
            {
                    "Select an item...",
                    "1. Anaesthesiologist",
                    "2. Andrologist",
                    "3. Cardiologist",
                    "4. Cardiac Electrophysiologist",
                    "5. Dermatologist",
                    "6. Emergency Medicine / Emergency (ER) Doctors",
                    "7. Endocrinologist",
                    "8. Epidemiologist",
                    "9. Family Medicine Physician",
                    "10. Gastroenterologist",
                    "11. Geriatrician",
                    "12. Hyperbaric Physician",
                    "13. Hematologist",
                    "14. Hepatologist",
                    "15. Immunologist",
                    "16. Infectious Disease Specialist",
                    "17. Intensivist",
                    "18. Internal Medicine Specialist",
                    "19. Maxillofacial Surgeon / Oral Surgeon",
                    "20. Medical Geneticist",
                    "21. Neonatologist",
                    "22. Nephrologist",
                    "23. Neurologist",
                    "24. Neurosurgeon",
                    "25. Nuclear Medicine Specialist",
                    "26. Obstetrician/Gynecologist (OB/GYN)",
                    "27. Occupational Medicine Specialist",
                    "28. Oncologist",
                    "29. Ophthalmologist",
                    "30. Orthopedic Surgeon / Orthopedist",
                    "31. Otolaryngologist (also ENT Specialist)",
                    "32. Parasitologist",
                    "33. Pathologist",
                    "34. Perinatologist",
                    "35. Periodontist",
                    "36. Pediatrician",
                    "37. Physiatrist",
                    "38. Plastic Surgeon",
                    "39. Psychiatrist",
                    "40. Pulmonologist",
                    "41. Radiologist",
                    "42. Rheumatologist",
                    "43. Sleep Doctor / Sleep Disorders Specialist",
                    "44. Spinal Cord Injury Specialist",
                    "45. Sports Medicine Specialist",
                    "46. Surgeon",
                    "47. Thoracic Surgeon",
                    "48. Urologist",
                    "49. Vascular Surgeon",
                    "51. Allergist",

            };
    Spinner spn_specialist ;



    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    LinearLayout lnl_fillinfor_doctor, lnl_getposition_doctor;

    private Double getLog,getLat;
    private String nameAddress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frg_fillinfor_doctor,container,false);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        mAuth = FirebaseAuth.getInstance();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mapFrag = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        lnl_fillinfor_doctor = (LinearLayout)view.findViewById(R.id.lnl_fill_infor_doctor);
        lnl_getposition_doctor = (LinearLayout)view.findViewById(R.id.lnl_getpositon_doctor);
        spn_specialist = (Spinner)view.findViewById(R.id.spn_specialist);
        _signupButton = (Button) view.findViewById(R.id.btn_signup);
        _nameText = (EditText) view.findViewById(R.id.input_name);
        _addressText= (EditText) view.findViewById(R.id.input_address);
        _emailText= (EditText) view.findViewById(R.id.input_email);
        _mobileText= (EditText) view.findViewById(R.id.input_mobile);
        _doctorcode= (EditText) view.findViewById(R.id.txt_doctorCode);
        _DOB= (EditText) view.findViewById(R.id.input_DOB);
        _workingat= (EditText) view.findViewById(R.id.txt_workat);
        _workinghour= (EditText) view.findViewById(R.id.txt_WorkingHour);
        rdg_chooseType = (RadioGroup)view.findViewById(R.id.rdg_Gender);
        rdb_man = (RadioButton)view.findViewById(R.id.rdb_man);
        rdb_women = (RadioButton)view.findViewById(R.id.rdb_woman);
        _addressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnl_fillinfor_doctor.setVisibility(View.GONE);
                lnl_getposition_doctor.setVisibility(View.VISIBLE);
            }
        });


        rdg_chooseType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(rdb_man.isChecked())
                {
                    gender = "man";
                }
                else if(rdb_women.isChecked())
                {
                    gender = "woman";
                }
            }
        });

        final List<String> speciallist = new ArrayList<>(Arrays.asList(values));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(),R.layout.spinner_item,speciallist){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.WHITE);
                return view;
            }

        };
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spn_specialist.setAdapter(adapter);


        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_getavatar);
        dialog.setTitle("Choose Avatar Image");

        Button btn_chooseImg = (Button) dialog.findViewById(R.id.btn_choosenGallery);
        Button btn_takeaphoto = (Button) dialog.findViewById(R.id.btn_choosenTakephoto);


        btn_chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFromGallery();
            }
        });

        btn_takeaphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profilepictureOnClick();
            }
        });

        img_ava_patient = (ImageButton) view.findViewById(R.id.img_ava_patient);
        img_ava_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key =  mAuth.getCurrentUser().getUid();
                Doctor_class drv = new Doctor_class();
                drv.setAvatar(convertToBase64(thumbnail));
                drv.setName(_nameText.getText().toString());
                drv.setType("waiting");
                drv.setAddress(_addressText.getText().toString());
                drv.setCode(_doctorcode.getText().toString());
                drv.setDOB(_DOB.getText().toString());
                drv.setPhone(_mobileText.getText().toString());
                drv.setSpecialist(spn_specialist.getSelectedItem().toString());
                drv.setWorkat(_workingat.getText().toString());
                drv.setWorkinghour(_workinghour.getText().toString());
                drv.setGender(gender);
                drv.setLat(getLat);
                drv.setLog(getLog);
                drv.setRating(0.0);
                reference.child("doctor").child(key).setValue(drv);

                //reference.child("loglat_current").child(key).setValue(location_cr);
                ((SignUp_Activity)getActivity()).location_cr.setName(_nameText.getText().toString());
                ((SignUp_Activity)getActivity()).location_cr.setMobile(_mobileText.getText().toString());
                ((SignUp_Activity)getActivity()).location_cr.setKey_user(key);
                ((SignUp_Activity)getActivity()).location_cr.setLog(getLog);
                ((SignUp_Activity)getActivity()).location_cr.setLat(getLat);
                ((SignUp_Activity)getActivity()).location_cr.setAddress(_addressText.getText().toString());
                reference.child("loglat_current").child(key).setValue( ((SignUp_Activity)getActivity()).location_cr);


                Intent intent = new Intent(getActivity(),Main_Screen_Acitivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type",2);
                intent.putExtra("gettype",bundle);
                startActivity(intent);
            }
        });




        return view;
    }


    public void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }


    private void takeNewProfilePicture(){
        Fragment profileFrag = this;
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        profileFrag.startActivityForResult(cameraintent, CAM_REQUEST);
    }
    public void profilepictureOnClick(){
        takeNewProfilePicture();
    }


    private  String convertToBase64(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;

    }


    public void showHideFragment(final Fragment fragment){

        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);

        if (fragment.isHidden()) {
            fragTransaction.show(fragment);
            Log.d("hidden","Show");
        } else {
            fragTransaction.hide(fragment);
            Log.d("Shown","Hide");
        }

        fragTransaction.commit();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == CAM_REQUEST) {
            if(requestCode == CAM_REQUEST){
                thumbnail = (Bitmap) data.getExtras().get("data");
                img_ava_patient.setImageBitmap(thumbnail);
                dialog.dismiss();
            }
        }
        else if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //  textTargetUri.setText(targetUri.toString());

            try {
                Context applicationContext = SignUp_Activity.getContextOfApplication();
                thumbnail = BitmapFactory.decodeStream( applicationContext.getContentResolver().openInputStream(targetUri));
                img_ava_patient.setImageBitmap(thumbnail);
                dialog.dismiss();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public Bitmap loadSampleResource(int imageId, int targetHeight, int targetwidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), imageId, options);
        final int originalHeight = options.outHeight;
        final int originalWidth = options.outWidth;
        int inSamplesize = 1;
        while ((originalHeight / (inSamplesize * 2)) > targetHeight && (originalWidth / (inSamplesize * 2)) > targetwidth) {
            inSamplesize *= 2;
        }
        options.inSampleSize = inSamplesize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), imageId, options);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }

        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                Toast.makeText(getContext(),latLng.toString(),Toast.LENGTH_SHORT).show();
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.patient_marker);
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(latLng).icon(icon);
                mCurrLocationMarker = googleMap.addMarker(markerOption);
                createAndShowAlertDialog("locationcurrent",latLng.latitude,latLng.longitude);


                lnl_getposition_doctor.setVisibility(View.GONE);
                lnl_fillinfor_doctor.setVisibility(View.VISIBLE);
                //txt_getlocationcurrent.setText(getLocationName(latLng.latitude,latLng.longitude));
                //(Main_Screen_Acitivity)getActivity()).location_cr.setLat(latLng.latitude);
                //((Main_Screen_Acitivity)getActivity()).location_cr.setLog(latLng.longitude);
                //((Main_Screen_Acitivity)getActivity()).location_cr.setAddress(getLocationName(latLng.latitude,latLng.longitude));
            }
        });
    }

    private void createAndShowAlertDialog(String type,double lathere,double loghere) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do you wnat get here is your's address");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(type == "locationcurrent")
                {
                    getLat = lathere;
                    getLog = loghere;
                    _addressText.setText(getLocationName(lathere,loghere));
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(type == "locationcurrent")
                {
                    lnl_fillinfor_doctor.setVisibility(View.GONE);
                    lnl_getposition_doctor.setVisibility(View.VISIBLE);
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public String getLocationName(double lattitude, double longitude) {
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
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

    LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.patient_marker);
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(icon);
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
            }
        };

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
                new AlertDialog.Builder(getActivity())
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
                        MY_PERMISSIONS_REQUEST_LOCATION );
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
}
