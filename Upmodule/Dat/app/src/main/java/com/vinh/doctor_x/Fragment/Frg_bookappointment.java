package com.vinh.doctor_x.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.vinh.doctor_x.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nntd290897 on 3/18/18.
 */

public class Frg_bookappointment extends Fragment {
    View view;
    Spinner spn_specialist ;
    EditText txt_chooseLocation;
    private Dialog dialog;
    private FragmentManager fragmentManager ;
    private FragmentTransaction fragmentTransaction ;
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

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_DarkHAB);

        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        view = inflater.inflate(R.layout.frg_bookappointment,container,false);
        txt_chooseLocation = (EditText)view.findViewById(R.id.txt_chooselocation);


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
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spn_specialist.setAdapter(adapter);
        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_chooselocation);
        dialog.setTitle("Choose Yours Location");

        Button btn_getdefault = (Button) dialog.findViewById(R.id.btn_getdefault);
        Button btn_getcurrently = (Button) dialog.findViewById(R.id.btn_getcurrently);

        btn_getdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_chooseLocation.setText("05 Hang Trong, Ho Chi Minh, Viet Nam");
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

        txt_chooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        //return localInflater.inflate(R.layout.frg_bookappointment, container, false);

        return view;
    }

}
