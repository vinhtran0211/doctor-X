package com.vinh.doctor_x.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import com.vinh.doctor_x.R;
import com.vinh.doctor_x.SignUp_Activity;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nntd290897 on 3/12/18.
 */

public class Frg_fillinfro_doctor extends Fragment {

    private static final int CAM_REQUEST = 1313;
    private Dialog dialog;
    private View view;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frg_fillinfor_doctor,container,false);


        spn_specialist = (Spinner)view.findViewById(R.id.spn_specialist);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == CAM_REQUEST) {
            if(requestCode == CAM_REQUEST){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                img_ava_patient.setImageBitmap(thumbnail);
                dialog.dismiss();
            }
        }
        else if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //  textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                Context applicationContext = SignUp_Activity.getContextOfApplication();
                bitmap = BitmapFactory.decodeStream( applicationContext.getContentResolver().openInputStream(targetUri));
                img_ava_patient.setImageBitmap(bitmap);
                dialog.dismiss();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}
