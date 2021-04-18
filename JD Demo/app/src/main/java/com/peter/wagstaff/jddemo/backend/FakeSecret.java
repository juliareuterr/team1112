package com.peter.wagstaff.jddemo.backend;

import org.json.JSONException;
import org.json.JSONObject;

public class FakeSecret extends Secret {

    public FakeSecret(String id, String account, String username, String password) {
        this.id = id;
        this.account = account;
        this.username = username;
        this.password = password;
    }

    public FakeSecret(String account, String username, String password) {
        this.id = getAlphaNumericString(10);
        this.account = account;
        this.username = username;
        this.password = password;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject asJSON = new JSONObject();
        try {
            asJSON.put("secret_ID", id);
            asJSON.put("account", account);
            asJSON.put("username", username);
            asJSON.put("password", password);
            return  asJSON;

        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Used to get a unique ID for a FakeSecret
     * There is no promise it is unique, however it is virtually impossible
     * And the consequences would not be bad
     * @param n Length of String to generate
     * @return Random alpha numeric String
     */
    private static String getAlphaNumericString(int n)
    {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
