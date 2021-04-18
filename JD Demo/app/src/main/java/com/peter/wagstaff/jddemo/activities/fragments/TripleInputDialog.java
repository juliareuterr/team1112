package com.peter.wagstaff.jddemo.activities.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public abstract class TripleInputDialog {

    AlertDialog alertDialog;
    EditText inputOne, inputTwo, inputThree;

    public TripleInputDialog(final Context context, String title, String message, String hintOne, String hintTwo, String hintThree, final String errorMessage) {

        inputOne = new EditText(context);
        inputTwo = new EditText(context);
        inputThree = new EditText(context);
        inputOne.setHint(hintOne);
        inputTwo.setHint(hintTwo);
        inputThree.setHint(hintThree);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputOne);
        layout.addView(inputTwo);
        layout.addView(inputThree);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setView(layout);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(onPositiveClicked(inputOne.getText().toString(), inputTwo.getText().toString(), inputThree.getText().toString())) {
                    inputOne.setText("");
                    inputTwo.setText("");
                    inputThree.setText("");
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
                inputThree.setText("");
                dialog.dismiss();
            }
        });

        alertDialog = builder.create();
    }

    public TripleInputDialog(Context context, String title, String message, String hintOne, String hintTwo, String hintThree, String errorMessage, InputFilter inputFilterOne, InputFilter inputFilterTwo, InputFilter inputFilterThree) {
        this(context, title, message, hintOne, hintTwo, hintThree, errorMessage);
        inputOne.setFilters(new InputFilter[]{inputFilterOne});
        inputTwo.setFilters(new InputFilter[]{inputFilterTwo});
        inputThree.setFilters(new InputFilter[]{inputFilterThree});
    }

    public TripleInputDialog(Context context, String title, String message, String hintOne, String hintTwo, String hintThree, String errorMessage, int inputTypeOne, int inputTypeTwo, int inputTypeThree) {
        this(context, title, message, hintOne, hintTwo, hintThree, errorMessage);
        inputOne.setInputType(inputTypeOne);
        inputTwo.setInputType(inputTypeTwo);
        inputThree.setInputType(inputTypeThree);
    }

    public void onNegativeClicked() {}

    abstract public boolean onPositiveClicked(String userInputOne, String userInputTwo, String userInputThree);

    public void show() {
        alertDialog.show();
    }
}