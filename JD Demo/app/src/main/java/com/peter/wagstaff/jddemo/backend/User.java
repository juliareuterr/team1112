package com.peter.wagstaff.jddemo.backend;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.jddemo.BaseApp;
import com.peter.wagstaff.jddemo.backend.firebase.DataUpdateAction;
import com.peter.wagstaff.jddemo.backend.firebase.FireBaseAdapter;

//Class to represent the User currently logged in via FireBase Auth
//Acts like a Singleton
public class User {

    public static User currentUser; //Single instance

    String ID, name;    //Unique ID, non-unique name, unique phone number, ID of line currently waiting in
    FirebaseUser associatedFireBaseUser; //FireBase auth user associated

    private User(FirebaseUser firebaseUser) {
        associatedFireBaseUser = firebaseUser;
        ID = firebaseUser.getUid();

        FireBaseAdapter.onUpdate("USERS/" + ID + "/DISPLAY_NAME", new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) name = snapshot.getValue().toString();
            }
        });
    }

    /**
     * Check if this User is in keeping with the current FireBase auth user
     * @return If this User is valid
     */
    public boolean check() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid().equals(ID);
    }

    public String getID() { return ID; }
    public String getName() { return name; }
    public FirebaseUser getAssociatedFireBaseUser() { return associatedFireBaseUser; }

    public void setName(String name) {
        this.name = name;
        FireBaseAdapter.setData("USERS/" + ID + "/DISPLAY_NAME", name);
    }

    /**
     * Destroys this User and removes any trace from the database
     * @return A UserRestore that could restore this User
     */
    public UserRestore destroy() {
        UserRestore savedValues = new UserRestore(name);
        FireBaseAdapter.setData("USERS/" + ID, null);
        //Remove from the list of used numbers
        return savedValues;
    }

    /**
     * Restores this User using a UserRestore
     * @param savedValues UserRestore containing values of a destroyed User
     */
    public void restore(UserRestore savedValues) {
        setName(savedValues.name);
        BaseApp.logToken();
    }

    /**
     * Sign out the current FireBase Auth user, also release the current User object
     */
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        releaseCurrentUser();
    }

    /**
     * Gets the current User, or if the current User does not exist or is not valid, generate a new one
     * @return The current User instance
     */
    public static User getCurrentUser() {
        if((currentUser == null || !currentUser.check()) && FirebaseAuth.getInstance().getCurrentUser() != null) {
            currentUser = new User(FirebaseAuth.getInstance().getCurrentUser());
            GlobalVariables.secretList = new SecretList();
            //If the user is recreated, log the token again
            BaseApp.logToken();
        }
        return currentUser;
    }

    /**
     * Destroys the current User instance
     */
    public static void releaseCurrentUser() {
        currentUser = null;
    }
}