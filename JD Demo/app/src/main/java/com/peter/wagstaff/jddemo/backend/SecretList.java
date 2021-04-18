package com.peter.wagstaff.jddemo.backend;

import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.jddemo.backend.firebase.DataUpdateAction;
import com.peter.wagstaff.jddemo.backend.firebase.FireBaseAdapter;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class SecretList extends Observable {

    private List<Secret> secrets;

    /**
     * Declares new SecretList
     */
    public SecretList() {
        secrets = new LinkedList();

        //Set listener on List's members
        FireBaseAdapter.onUpdate("USERS/" + User.getCurrentUser().getID() + "/SECRETS", new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                updateSecretsFromDatabase(snapshot);
                setChanged();
                notifyObservers();
            }
        });
    }

    public void addSecret(Secret secret) {
        secrets.add(secret);
        updateDatabaseFromSecrets();
    }

    public Secret removeSecret(int position) {
        if(secrets.size() < 1 || position >= secrets.size()) return null;
        Secret output = secrets.remove(position);
        updateDatabaseFromSecrets();
        return output;
    }

    public Secret removeSecret(Secret secret) {
        if(!secrets.contains(secret)) return null;
        int index = secrets.indexOf(secret);
        return removeSecret(index);
    }

    public void asyncRemoveSecret(final Secret secret) {
        FireBaseAdapter.onGrab("USERS/" + User.getCurrentUser().getID() + "/SECRETS", new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                updateSecretsFromDatabase(snapshot);
                removeSecret(secret);
            }
        });
    }

    public int getPosition(Secret secret) {
        return secrets.indexOf(secret);
    }

    /**
     * Updates the data in the database concerning this List's Secrets
     */
    public void updateDatabaseFromSecrets() {
        FireBaseAdapter.setData("USERS/" + User.getCurrentUser().getID() + "/SECRETS_COUNT", secrets.size());
        FireBaseAdapter.setData("USERS/" + User.getCurrentUser().getID() + "/SECRETS", Secret.secretListToStringList(secrets));
    }

    private void updateSecretsFromDatabase(DataSnapshot snapshot) {
        if(snapshot.exists()) {
            secrets = Secret.snapshotsToSecretList(snapshot.getChildren());
        }
    }

    public int getSize() { return secrets.size(); }

    public boolean containsSecret(Secret testSecret) { return secrets.contains(testSecret); }

    public Secret getSecret(int index) { return secrets.get(index); }

    public List<Secret> getSecrets() { return secrets; }

    /**
     * Deletes this List, but also clears the database
     */
    public void delete() {
        secrets.clear();
        FireBaseAdapter.setData("USERS/" + User.getCurrentUser().getID() + "/SECRETS", null);
    }
}