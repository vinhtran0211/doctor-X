package com.vinh.doctor_x.Fragment;



import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vinh.doctor_x.FirstSetAcc_Activity;
import com.vinh.doctor_x.R;
import com.vinh.doctor_x.SignUp_Activity;

import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nntd290897 on 3/9/18.
 */

public class Frg_fillinfro_patient extends Fragment {

    private static final int CAM_REQUEST = 1313;
    private Dialog dialog;
    private View view;

    final int CROP_PIC = 2;
    private Uri picUri;


    ImageButton img_ava_patient;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frg_fillinfor_patient,container,false);



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

              //  picUri = data.getData();

                img_ava_patient.setImageBitmap(thumbnail);
                dialog.dismiss();
            }
        }
        else if (resultCode == RESULT_OK){
            picUri = data.getData();

            // Uri targetUri = data.getData();
            //  textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                Context applicationContext = SignUp_Activity.getContextOfApplication();
                bitmap = BitmapFactory.decodeStream( applicationContext.getContentResolver().openInputStream(picUri));
                img_ava_patient.setImageBitmap(bitmap);


                dialog.dismiss();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }




}
