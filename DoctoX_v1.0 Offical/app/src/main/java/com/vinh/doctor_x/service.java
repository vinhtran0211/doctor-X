package com.vinh.doctor_x;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by nntd290897 on 5/16/18.
 */

public class service extends Service {

    private NotificationCompat.Builder Builder;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;

    public service() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
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
                        if (dataSnapshot.getValue(String.class).equals("2")) {
                            Intent intent = new Intent(service.this, Doctor_Realtime_Map_Activity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
                            PendingIntent pIntent = PendingIntent.getActivity(service.this, (int) System.currentTimeMillis(), intent, 0);
                            Notification n = new Notification.Builder(service.this)
                                    .setContentTitle("New request ")
                                    .setContentText("You have new request appointment")
                                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                                    .setContentIntent(pIntent)
                                    .setAutoCancel(true)
                                    .build();
                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            notificationManager.notify(0, n);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
