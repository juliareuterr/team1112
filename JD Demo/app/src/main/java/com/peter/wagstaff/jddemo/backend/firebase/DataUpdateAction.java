package com.peter.wagstaff.jddemo.backend.firebase;

import com.google.firebase.database.DataSnapshot;

//Abstract class used to perform actions asynchronously with FireBase DataSnapshots
public abstract class DataUpdateAction {

    /**
     * Undefined action to be performed using a FireBase DataSnapshot
     * @param snapshot FireBase DataSnapshot
     */
    public abstract void doAction(DataSnapshot snapshot);
}
