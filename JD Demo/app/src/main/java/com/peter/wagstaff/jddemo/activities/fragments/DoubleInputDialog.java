package com.peter.wagstaff.jddemo.activities.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public abstract class DoubleInputDialog {

    AlertDialog alertDialog;
    EditText inputOne, inputTwo;

    public DoubleInputDialog(final Context context, String title, String message, String hintOne, String hintTwo, final String errorMessage) {

        inputOne = new EditText(context);
        inputTwo = new EditText(context);
        inputOne.setHint(hintOne);
        inputTwo.setHint(hintTwo);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputOne);
        layout.addView(inputTwo);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setView(layout);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(onPositiveClicked(inputOne.getText().toString(), inputTwo.getText().toString())) {
                    inputOne.setText("");
                    inputTwo.setText("");
                    dialog.dismiss();
                }
                else {
                    if(!errorMessage.equals("")) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onNegativeClicked();
                inputOne.setText("");
                inputTwo.setText("");
                dialog.dismiss();
            }
        });

        alertDialog = builder.create();
    }

    public DoubleInputDialog(Context context, String title, String message, String hintOne, String hintTwo, String errorMessage, InputFilter inputFilterOne, InputFilter inputFilterTwo) {
        this(context, title, message, hintOne, hintTwo, errorMessage);
        inputOne.setFilters(new InputFilter[]{inputFilterOne});
        inputTwo.setFilters(new InputFilter[]{inputFilterTwo});
    }

    public DoubleInputDialog(Context context, String title, String message, String hintOne, String hintTwo, String errorMessage, int inputTypeOne, int inputTypeTwo) {
        this(context, title, message, hintOne, hintTwo, errorMessage);
        inputOne.setInputType(inputTypeOne);
        inputTwo.setInputType(inputTypeTwo);
    }

    public void onNegativeClicked() {}

    abstract public boolean onPositiveClicked(String userInputOne, String userInputTwo);

    public void show() {
        alertDialog.show();
    }
}