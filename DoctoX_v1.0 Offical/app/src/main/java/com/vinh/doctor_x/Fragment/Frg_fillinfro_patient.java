package com.vinh.doctor_x.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vinh.doctor_x.Main_Screen_Acitivity;
import com.vinh.doctor_x.R;
import com.vinh.doctor_x.SignUp_Activity;
import com.vinh.doctor_x.User.Patient_class;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nntd290897 on 3/9/18.
 */

public class Frg_fillinfro_patient extends Fragment {

    private static final int CAM_REQUEST = 1313;
    private Dialog dialog;
    private View view;

    FirebaseDatabase database;
    DatabaseReference reference;


    private FirebaseAuth mAuth;

    final int CROP_PIC = 2;
    private Uri picUri;

    EditText _nameText;
    EditText _addressText;
    EditText _emailText;
    EditText _mobileText;

    EditText _DOB;


    Button _signupButton;
    RadioButton rdb_man ;
    RadioButton rdb_woman ;
    RadioGroup rdg_chooseType ;
    String gender;
    Bitmap thumbnail;

    ImageButton img_ava_patient;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frg_fillinfor_patient,container,false);



        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_getavatar);
        dialog.setTitle("Choose Avatar Image");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        Button btn_chooseImg = (Button) dialog.findViewById(R.id.btn_choosenGallery);
        Button btn_takeaphoto = (Button) dialog.findViewById(R.id.btn_choosenTakephoto);
        _signupButton = view.findViewById(R.id.btn_signup);


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

        rdg_chooseType = (RadioGroup)view.findViewById(R.id.rdg_Gender);
        rdb_man = (RadioButton)view.findViewById(R.id.rdb_man);
        rdb_woman = (RadioButton)view.findViewById(R.id.rdb_woman);



        _signupButton = (Button) view.findViewById(R.id.btn_signup);
        _nameText = (EditText) view.findViewById(R.id.input_name);
        _addressText= (EditText) view.findViewById(R.id.input_address);;
        _emailText= (EditText) view.findViewById(R.id.input_email);;
        _mobileText= (EditText) view.findViewById(R.id.input_mobile);;

        _DOB= (EditText) view.findViewById(R.id.input_DOB);;



        rdg_chooseType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(rdb_man.isChecked())
                {
                    gender = "man";
                }
                else if(rdb_woman.isChecked())
                {
                    gender = "nu";
                }
            }
        });
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key =  mAuth.getCurrentUser().getUid();
                Patient_class drv =new Patient_class();
                drv.setAvatar(convertToBase64(thumbnail));
                drv.setName(_nameText.getText().toString());
                drv.setGender(gender);
                drv.setBirthday(_DOB.getText().toString());
                drv.setPhone(_mobileText.getText().toString());
                drv.setEmail(_emailText.getText().toString());
                drv.setAddress(_addressText.getText().toString());

                drv.setType("1");
                reference.child("patient").child(key).setValue(drv);
                Intent intent = new Intent(getActivity(),Main_Screen_Acitivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
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
    private  String convertToBase64(Bitmap bm)

    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;

    }



    public void profilepictureOnClick(){
        takeNewProfilePicture();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == CAM_REQUEST) {
            if(requestCode == CAM_REQUEST){
                thumbnail = (Bitmap) data.getExtras().get("data");

                //  picUri = data.getData();

                img_ava_patient.setImageBitmap(thumbnail);
                dialog.dismiss();
            }
        }
        else if (resultCode == RESULT_OK){
            picUri = data.getData();

            // Uri targetUri = data.getData();
            //  textTargetUri.setText(targetUri.toString());

            try {
                Context applicationContext = SignUp_Activity.getContextOfApplication();
                thumbnail = BitmapFactory.decodeStream( applicationContext.getContentResolver().openInputStream(picUri));
                img_ava_patient.setImageBitmap(thumbnail);


                dialog.dismiss();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
