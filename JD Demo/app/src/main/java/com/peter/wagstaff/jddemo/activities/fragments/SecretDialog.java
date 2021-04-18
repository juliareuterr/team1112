package com.peter.wagstaff.jddemo.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.peter.wagstaff.jddemo.R;

public abstract class SecretDialog<Type> {

    AlertDialog dialog;
    Type object;

    public SecretDialog(Activity activity, String account, String username, String password) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View customLayout = activity.getLayoutInflater().inflate(R.layout.secret_dialog, null);
        TextView accountView = customLayout.findViewById(R.id.account_textView);
        TextView usernameView = customLayout.findViewById(R.id.username_textView);
        TextView passwordView = customLayout.findViewById(R.id.password_textView);
        Button editButton = customLayout.findViewById(R.id.edit_button);
        Button closeButton = customLayout.findViewById(R.id.close_button);
        builder.setView(customLayout);
        builder.setCancelable(true);

        accountView.setText(account);
        if(account.equals("")) {
            accountView.setHeight(0);
        }

        usernameView.setText(username);
        if(username.equals("")) {
            usernameView.setHeight(0);
        }

        passwordView.setText(password);
        if(password.equals("")) {
            passwordView.setHeight(0);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveClicked(object);
                dialog.dismiss();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNegativeClicked();
                dialog.dismiss();
            }
        });

        dialog = builder.create();
    }

    abstract public boolean onPositiveClicked(Type object);

    public void onNegativeClicked() {}

    public void show(Type object) {
        this.object = object;
        dialog.show();
    }
}