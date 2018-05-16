package com.vinh.doctor_x.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vinh.doctor_x.Login_Activity;
import com.vinh.doctor_x.Main_Screen_Acitivity;
import com.vinh.doctor_x.R;
import com.vinh.doctor_x.Realtime_Location_Map_Activity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nntd290897 on 3/18/18.
 */

public class Frg_bookappointment extends Fragment {
    View view;
    Spinner spn_specialist ;
    EditText txt_chooseLocation,txt_pickertime_patient;
    private Dialog dialog,datetimepicker;
    private FragmentManager fragmentManager ;
    private FragmentTransaction fragmentTransaction ;
    private ImageButton imb_imagepatient;
    private static final int CAM_REQUEST = 1313;
    private Dialog dialog_chooseImg;
    Bitmap thumbnail;
    private Button date_time_set,btn_search;


    private static String key_patient_request_zone;

    public static String getKey_patient_request_zone() {
        return key_patient_request_zone;
    }

    public static void setKey_patient_request_zone(String key_patient_request_zone) {
        Frg_bookappointment.key_patient_request_zone = key_patient_request_zone;
    }





    private static  String specialist = "";

    public static String getSpecialist() {
        return specialist;
    }

    public  void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

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

    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();


    String item = "";
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_DarkHAB);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        view = inflater.inflate(R.layout.frg_bookappointment,container,false);
        txt_chooseLocation = (EditText)view.findViewById(R.id.txt_chooselocation);
        txt_pickertime_patient = (EditText)view.findViewById(R.id.txt_pickertime_patient);
        imb_imagepatient = (ImageButton)view.findViewById(R.id.imb_imagepatient_status);
        spn_specialist = (Spinner)view.findViewById(R.id.spn_specialist);
        btn_search = (Button)view.findViewById(R.id.btn_search);
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
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spn_specialist.setAdapter(adapter);
        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_chooselocation);
        dialog.setTitle("Choose Yours Location");

        datetimepicker = new Dialog(view.getContext());
        datetimepicker.setContentView(R.layout.date_time_picker);
        datetimepicker.setTitle("Date time picker");

        Button btn_getdefault = (Button) dialog.findViewById(R.id.btn_getdefault);
        Button btn_getcurrently = (Button) dialog.findViewById(R.id.btn_getcurrently);

        btn_getdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_chooseLocation.setText(Main_Screen_Acitivity.getPatient().getAddress());
                dialog.dismiss();
            }
        });

        btn_getcurrently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Frg_Map map = new Frg_Map();
                fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                fragmentTransaction.replace(R.id.frg_patient_main, map);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        dialog_chooseImg = new Dialog(view.getContext());
        dialog_chooseImg.setContentView(R.layout.dialog_getavatar);
        dialog_chooseImg.setTitle("Choose Image");




        Button btn_chooseImg = (Button) dialog_chooseImg.findViewById(R.id.btn_choosenGallery);
        Button btn_takeaphoto = (Button) dialog_chooseImg.findViewById(R.id.btn_choosenTakephoto);

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
        txt_chooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        imb_imagepatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_chooseImg.show();
            }
        });

        //return localInflater.inflate(R.layout.frg_bookappointment, container, false);
        Calendar cal = Calendar.getInstance();
        txt_pickertime_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetimepicker.show();
            }
        });


        DatePicker datePicker = (DatePicker) datetimepicker.findViewById(R.id.date_picker);
        TimePicker timePicker = (TimePicker) datetimepicker.findViewById(R.id.time_picker);
        date_time_set = (Button)datetimepicker.findViewById(R.id.date_time_set);
        date_time_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                Log.i("datetime",calendar.getInstance().getTime()+"");
                String time = timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute() +" "+datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear() ;

                Log.d("Time",time);
                txt_pickertime_patient.setText(time);
                datetimepicker.dismiss();
            }
        });


        Toast.makeText(contextThemeWrapper, Main_Screen_Acitivity.getPatient().getPhone(), Toast.LENGTH_SHORT).show();
        spn_specialist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = spn_specialist.getSelectedItem().toString();
               // Toast.makeText(contextThemeWrapper, spn_specialist.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpecialist(item);
                //String key = myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).push().getKey();
                String key =txt_pickertime_patient.getText().toString().trim();
                setKey_patient_request_zone(txt_pickertime_patient.getText().toString().trim());
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Specialist").setValue(item);
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Address").setValue(txt_chooseLocation.getText().toString());
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Doctor").setValue("null");
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Time").setValue(txt_pickertime_patient.getText().toString());
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Lat").setValue(Main_Screen_Acitivity.getPicker_Lat());
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Log").setValue(Main_Screen_Acitivity.getPicker_Log());
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("WhoCome").setValue("Doctor");
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Type").setValue("Appointment Normal");
                Intent i = new Intent(getActivity(), Realtime_Location_Map_Activity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == CAM_REQUEST) {
            if(requestCode == CAM_REQUEST){
                thumbnail = (Bitmap) data.getExtras().get("data");
                imb_imagepatient.setImageBitmap(thumbnail);
                dialog_chooseImg.dismiss();
            }
        }
        else if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //  textTargetUri.setText(targetUri.toString());

            try {
                Context applicationContext = Main_Screen_Acitivity.getContextOfApplication();
                thumbnail = BitmapFactory.decodeStream( applicationContext.getContentResolver().openInputStream(targetUri));
                imb_imagepatient.setImageBitmap(thumbnail);
                dialog_chooseImg.dismiss();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public void onResume(){
        super.onResume();

        String item = ((Main_Screen_Acitivity)getActivity()).getLocationpicker();

        txt_chooseLocation.setText(item);
        //if item != null do some crazy shizzle
        //set MainActivity searchitem to null to avoid reloading it
    }

}
