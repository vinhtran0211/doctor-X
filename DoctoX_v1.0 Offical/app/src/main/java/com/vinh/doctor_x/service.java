package com.vinh.doctor_x;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;

/**
 * Created by nntd290897 on 5/16/18.
 */

public class service extends Service {

    private NotificationCompat.Builder Builder;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;

    private BubblesManager bubblesManager;
    public service() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    ShakeDetector mShakeDetector;
    @Override
    public void onCreate() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
            }
        });
        super.onCreate();
        Toast.makeText(service.this, "type: " + Main_Screen_Acitivity.getDoctor().getPhone(), Toast.LENGTH_SHORT).show();
        DatabaseReference data = FirebaseDatabase.getInstance().getReference();
        if(Main_Screen_Acitivity.getKey() != null)
        {
            data.child("doctor").child(Main_Screen_Acitivity.getKey()).child("type").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(service.this, "type: " + dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        if (!dataSnapshot.getValue(String.class).equals("waiting")) {

                            initializeBubblesManager();
                            addNewBubble();
                            Intent intent = new Intent(service.this, Doctor_Realtime_Map_Activity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
                            PendingIntent pIntent = PendingIntent.getActivity(service.this, (int) System.currentTimeMillis(), intent, 0);
                            Notification n = new Notification.Builder(service.this)
                                    .setContentTitle("New request ")
                                    .setContentText("You have new request appointment")
                                    .setSmallIcon(R.drawable.patient)
                                    .setContentIntent(pIntent)
                                    .setAutoCancel(true)
                                    .build();
                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            notificationManager.notify(0, n);

                            if(dataSnapshot.getValue(String.class).split("_")[7].trim().equals("emer"))
                            {
                                   mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
                                mSensorManager.unregisterListener(mShakeDetector);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }





    private void initializeBubblesManager() {
        bubblesManager = new BubblesManager.Builder(this)
                .setTrashLayout(R.layout.bubble_trash_layout)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
                        addNewBubble();
                    }
                })
                .build();
        bubblesManager.initialize();
    }

    private void addNewBubble() {
        BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(service.this).inflate(R.layout.bubble_layout, null);
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) { }
        });
        bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                Toast.makeText(getApplicationContext(), "Clicked !",
                        Toast.LENGTH_SHORT).show();
                PackageManager packageManager = getPackageManager();
                startActivity(packageManager.getLaunchIntentForPackage("com.vinh.doctor_x"));

                mSensorManager.unregisterListener(mShakeDetector);
            }
        });
        bubbleView.setShouldStickToWall(true);
        bubblesManager.addBubble(bubbleView, 60, 20);
    }


}
