package com.peter.wagstaff.jddemo.activities.fragments.holders;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.peter.wagstaff.jddemo.R;
import com.peter.wagstaff.jddemo.activities.fragments.FrontHolderButton;
import com.peter.wagstaff.jddemo.activities.fragments.HolderButton;
import com.peter.wagstaff.jddemo.backend.ListenerAction;
import com.peter.wagstaff.jddemo.backend.Secret;
import java.util.LinkedList;
import java.util.List;

//TableLayout used to display and select an undefined number of Buttons
//Each button is associated with an Object of Type
public class ButtonTable extends TableLayout  {

    List<HolderButton> buttonList = new LinkedList<>();
    ListenerAction<Secret> longAction;

    //Listener action to specify the behavior of each Button
    private ListenerAction<Secret> action;

    /**
     * Declare ButtonTable
     * @param context
     */
    public ButtonTable(Context context) {
        super(context);
    }

    /**
     * Declare ButtonTable
     * @param context
     * @param attrs
     */
    public ButtonTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        float scale = context.getResources().getDisplayMetrics().density;
    }

    /**
     * Sets the action that will occur when a Button is pressed
     * @param action
     */
    public void setPressedAction(ListenerAction<Secret> action) {
        this.action = action;
    }

    /**
     * Sets the action that will occur when a Button is long pressed
     * @param longAction
     */
    public void setLongPressedAction(ListenerAction<Secret> longAction) { this.longAction = longAction; }

    /**
     * Adds a row with a button storing an Object of type
     * @param buttonMember Object of Type associated with the Button
     */
    public void addRow(String name, final Secret buttonMember, int backGroundRes) {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        newRow.setPadding(5,5,5,5);
        newRow.setGravity(Gravity.CENTER_HORIZONTAL);

        HolderButton newButton = new HolderButton(getContext(), buttonMember);
        newButton.setText(name, "");
        newButton.setWidth((int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.6));
        newButton.setBackgroundResource(backGroundRes);

        newButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                action.doAction(buttonMember);
            }
        });
        newButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longAction.doAction(buttonMember);
                return true;
            }
        });

        newRow.addView(newButton);
        addView(newRow);

        buttonList.add(newButton);
    }

    public void addRow(String name, String time, final Secret buttonMember, int backGroundRes) {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        newRow.setPadding(5,5,5,5);
        newRow.setGravity(Gravity.CENTER_HORIZONTAL);

        HolderButton newButton = new FrontHolderButton(getContext(), buttonMember);
        newButton.setText(name, time);
        newButton.setWidth((int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.6));
        newButton.setBackgroundResource(backGroundRes);

        newButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                action.doAction(buttonMember);
            }
        });
        newButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longAction.doAction(buttonMember);
                return true;
            }
        });

        newRow.addView(newButton);
        addView(newRow);

        buttonList.add(newButton);
    }

    public void addEmptyRow() {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        newRow.setPadding(5,5,5,5);

        HolderButton newButton = HolderButton.getFake(getContext());
        newButton.setBackgroundResource(R.drawable.rounded_corner_button);

        newRow.addView(newButton);
        addView(newRow);
    }

    public void clear() {
        removeAllViews();
        buttonList.clear();
    }

    public List<HolderButton> getButtonList() {
        return buttonList;
    }
}
