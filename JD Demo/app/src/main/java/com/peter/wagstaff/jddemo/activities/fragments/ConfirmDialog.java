package com.peter.wagstaff.jddemo.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.peter.wagstaff.jddemo.R;

public abstract class ConfirmDialog<Type> {

    AlertDialog dialog;
    Type object;

    public ConfirmDialog(Activity activity, String title, String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View customLayout = activity.getLayoutInflater().inflate(R.layout.confirm_dialog, null);
        TextView titleView = customLayout.findViewById(R.id.title_textView);
        TextView messageView = customLayout.findViewById(R.id.message_textView);
        Button yesButton = customLayout.findViewById(R.id.yes_button);
        Button noButton = customLayout.findViewById(R.id.no_button);
        builder.setView(customLayout);
        builder.setCancelable(true);

        titleView.setText(title);
        if(title.equals("")) {
            titleView.setHeight(0);
        }

        messageView.setText(message);
        if(message.equals("")) {
            messageView.setHeight(0);
        }

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveClicked(object);
                dialog.dismiss();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
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