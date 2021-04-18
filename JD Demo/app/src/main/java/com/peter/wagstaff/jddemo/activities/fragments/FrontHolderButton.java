
package com.peter.wagstaff.jddemo.activities.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.peter.wagstaff.jddemo.backend.Secret;

public class FrontHolderButton extends HolderButton {

    TextView timeText;

    public FrontHolderButton(Context context, Secret object) {
        super(context, object);
        timeText = new TextView(context);
        timeText.setTypeface(Typeface.MONOSPACE);
        timeText.setTextSize(17);
        timeText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        timeText.setTextColor(Color.WHITE);
        timeText.setPadding(10,  0, 5, 0);
        timeText.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        addView(timeText);
    }

    @Override
    public void setText(String main, String time) {
        mainText.setText(main);
        timeText.setText(time);
    }

    @Override
    public void setWidth(int width) {
        mainText.setWidth((int) (width * .725));
        timeText.setWidth((int) (width * .275));
    }
}