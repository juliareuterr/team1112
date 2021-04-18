package com.peter.wagstaff.jddemo.backend.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.peter.wagstaff.jddemo.backend.GlobalVariables;

import androidx.annotation.NonNull;

//Class used to communicate with the FireBase database
public class FireBaseAdapter {

    //Database reference
    private static final DatabaseReference ROOT_REF = GlobalVariables.rootRef;

    /**
     * Set node in the database to a specified value
     * @param path Path to node
     * @param data Data to put in node
     */
    public static void setData(String path, Object data) { ROOT_REF.child(path).setValue(data); }

    /**
     * Specifiy action to perform on the grabbing of a DataSnapshot of a node
     * @param path Path to node
     * @param action DataUpdateAction to perform on grab
     */
    public static void onGrab(String path, final DataUpdateAction action) {
        ROOT_REF.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                action.doAction(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    /**
     * Specifiy action to perform on the update of a DataSnapshot of a node
     * @param path Path to node
     * @param action DataUpdateAction to perform on update
     */
    public static void onUpdate(String path, final DataUpdateAction action) {
        ROOT_REF.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                action.doAction(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
