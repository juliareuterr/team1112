package com.peter.wagstaff.jddemo.backend;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Class for calling on global functions
public class GlobalFunctions {

  public static boolean checkUserEmailRequirements(String email, Context context) {

    if(email.equals("") || email == null) {
      Toast.makeText(context, "Enter Email", Toast.LENGTH_LONG).show();
      return false;
    }
    return true;
  }

  public static boolean checkUserNameRequirements(String name, Context context) {

    if(name.equals("") || name == null) {
      Toast.makeText(context, "Enter Name", Toast.LENGTH_LONG).show();
      return false;
    }
    if (name.length() > 16) {
      Toast.makeText(context, "Name too long, enter maximum of 16 characters", Toast.LENGTH_LONG).show();
      return false;
    }
    if(!name.replaceAll("\\s+","").matches("[a-zA-Z0-9']*")) {
      Toast.makeText(context, "Name can only contain letters, numbers, and spaces", Toast.LENGTH_LONG).show();
      return false;
    }
    return true;
  }

  public static boolean checkUserPasswordRequirements(String password, Context context) {

    if(password.equals("") || password == null) {
      Toast.makeText(context, "Enter Password", Toast.LENGTH_LONG).show();
      return false;
    }
    if(password.length() < 6) {
      Toast.makeText(context, "Password too short, enter minimum 6 characters", Toast.LENGTH_LONG).show();
      return false;
    }
        /*if(!password.matches("[a-zA-Z0-9]*")) {
            Toast.makeText(context, "Password can only contain letters and numbers", Toast.LENGTH_LONG).show();
            return false;
        }*/
    return true;
  }

  public static boolean checkPasswordMatch(String password, String confirm, Context context) {

    if(password.equals(confirm)) {
      return true;
    }
    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show();
    return false;
  }

  /**
   * Hash a password so it is not known directly to the database
   * @param unhashedPassword Password in plain text to be hashed
   * @return Unrecognizable hash of the password
   */
  public static String hashPassword(String unhashedPassword) {

    try {
      // Create MD5 Hash
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(unhashedPassword.getBytes());
      byte messageDigest[] = digest.digest();

      // Create Hex String
      StringBuffer hexString = new StringBuffer();
      for (int i=0; i<messageDigest.length; i++)
        hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
      return hexString.toString();

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }
}