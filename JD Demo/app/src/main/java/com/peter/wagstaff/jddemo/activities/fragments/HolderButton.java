package com.peter.wagstaff.jddemo.activities.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.peter.wagstaff.jddemo.R;
import com.peter.wagstaff.jddemo.backend.Secret;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.TextViewCompat;

@SuppressLint("AppCompatCustomView")
public class HolderButton extends LinearLayout {

    Secret member;
    TextView mainText;

    public HolderButton(Context context, Secret object) {
        super(context);
        float scale = context.getResources().getDisplayMetrics().density;

        mainText = new TextView(context);
        mainText.setHeight((int) (48 * scale + 0.5f));
        mainText.setTypeface(ResourcesCompat.getFont(context, R.font.baloo_chettan));
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(mainText, 10, 28, 1, TypedValue.COMPLEX_UNIT_DIP);
        mainText.setTextColor(Color.WHITE);
        mainText.setPadding(10,  0, 10, 4);
        mainText.setGravity(Gravity.CENTER);
        addView(mainText);
        this.member = object;
    }

    private HolderButton(Context context) {
        super(context);
        float scale = context.getResources().getDisplayMetrics().density;
        mainText = new TextView(context);
        mainText.setHeight((int) (48 * scale + 0.5f));
        addView(mainText);
        setAlpha(0);
        setEnabled(false);
    }

    public static HolderButton getFake(Context context) {
        return new HolderButton(context);
    }

    public void setText(String main, String time) {
        mainText.setText(main);
    }

    public void setWidth(int width) {
        mainText.setWidth(width);
    }

    public void setMember(Secret member) {
        this.member = member;
    }

    public Secret getMember() { return member; }
}