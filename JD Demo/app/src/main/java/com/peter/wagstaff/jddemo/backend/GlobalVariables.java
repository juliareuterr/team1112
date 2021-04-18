package com.peter.wagstaff.jddemo.backend;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Class for storing a global variables
public class GlobalVariables {
    public static SecretList secretList;   //Line the user is currently in, only relevant if they delete their account, Line currently operating
    public static boolean justLoggedOut = false;    //If the User just logged out
    public static String databaseBranch = "DEFAULT_BRANCH"; //Branch of the database
    public static final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child(databaseBranch);    //Root of the database
}
