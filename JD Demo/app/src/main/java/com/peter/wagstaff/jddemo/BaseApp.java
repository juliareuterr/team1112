package com.peter.wagstaff.jddemo;

import android.app.Application;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.peter.wagstaff.jddemo.backend.User;
import com.peter.wagstaff.jddemo.backend.firebase.FireBaseAdapter;

import java.util.Calendar;

//Base of the App, used to store things and run code that persists so long as the app does
//Is a Singleton
public class BaseApp extends Application {

    private static BaseApp instance;    //Singleton instance
    private static boolean connected;   //If the app is connected to the database

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //Try to initialize the user from the stored FireBase auth asap
        User.getCurrentUser();

        //Create listener to record if the app is connected to the database
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                connected = snapshot.getValue(Boolean.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                connected = false;
            }
        });

        //Log this devices token for notification purposes
        logToken();
    }

    public static void logToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("Token: " + token);
        if(User.getCurrentUser() != null) {
            FireBaseAdapter.setData("USERS/" + User.getCurrentUser().getID() + "/OPTOKEN", token);
        }
    }
    /**
     * @return If the app is connected to the database
     */
    public static boolean isConnected() { return connected; }
}
