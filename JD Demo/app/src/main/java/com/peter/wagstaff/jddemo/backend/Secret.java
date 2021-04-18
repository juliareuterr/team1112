package com.peter.wagstaff.jddemo.backend;

import com.google.firebase.database.DataSnapshot;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public abstract class Secret {

    String id, account, username, password;    //Unique ID of Secret, non-unique name

    /**
     * Creates a Secret from a JSON representing it
     * @param jsonAsString JSON as String
     * @return Secret created from JSON
     */
    public static Secret createFromJSON(String jsonAsString) {
        try {
            JSONObject asJSON = new JSONObject(jsonAsString);

            String id = asJSON.getString("secret_ID");
            String account = asJSON.getString("account");
            String username = asJSON.getString("username");
            String password = asJSON.getString("password");
            return new FakeSecret(id, account, username, password);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getID() { return id; }

    public String getAccount() { return account; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    /**
     * Puts the essential aspects of the Secret into a JSON which can be converted back
     * @return Secret as JSON
     */
    public abstract JSONObject toJSON();

    /**
     * Converts a list of Secrets to a list of Strings representing themselves
     * @param memberList List of Secrets to be converted
     * @return List of Strings representing the Secrets as JSONs
     */
    public static List<String> secretListToStringList(List<Secret> memberList) {
        ArrayList<String> output = new ArrayList<>();
        output.add("PLACEHOLDER");

        for(Secret member: memberList) {
            if(member != null) output.add(member.toJSON().toString());
        }
        return output;
    }

    /**
     * Converts an Iterable DataSnapshots to a list of Secrets
     * @param snapshots Iterable of Snapshots, each containing a JSON representation of a Secret
     * @return List of Secrets represented by DataSnapshots
     */
    public static List<Secret> snapshotsToSecretList(Iterable<DataSnapshot> snapshots) {
        ArrayList<Secret> output = new ArrayList<>();

        for(DataSnapshot snapshot: snapshots) {
            String asString = snapshot.getValue().toString();
            if(!asString.equals("PLACEHOLDER")) output.add(createFromJSON(asString));
        }
        return output;
    }

    /**
     * Returns true of the other Secret shares an ID
     * @param other Object, should be a Secret
     * @return If the input is a Secret with the same ID
     */
    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(! (other instanceof Secret)) return false;
        Secret otherSecret = (Secret) other;
        return this.id.equals(otherSecret.getID());
    }
}
