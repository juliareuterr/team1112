package com.peter.wagstaff.jddemo.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.peter.wagstaff.jddemo.BaseApp;
import com.peter.wagstaff.jddemo.R;
import com.peter.wagstaff.jddemo.activities.fragments.ConfirmDialog;
import com.peter.wagstaff.jddemo.activities.fragments.SecretDialog;
import com.peter.wagstaff.jddemo.activities.fragments.TripleInputDialog;
import com.peter.wagstaff.jddemo.activities.fragments.holders.ButtonTable;
import com.peter.wagstaff.jddemo.backend.FakeSecret;
import com.peter.wagstaff.jddemo.backend.GlobalVariables;
import com.peter.wagstaff.jddemo.backend.ListenerAction;
import com.peter.wagstaff.jddemo.backend.Secret;
import com.peter.wagstaff.jddemo.backend.User;

import java.util.Observable;
import java.util.Observer;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class SecretsActivity extends AppCompatActivity implements Observer {

    TextView titleText, totalText;
    ButtonTable secretTable;
    ScrollView scroller;
    Button addButton, deleteButton;
    Typeface typeface;
    boolean deleteMode = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secrets);
        titleText = findViewById(R.id.title_textView);
        totalText = findViewById(R.id.more_textView);
        secretTable = findViewById(R.id.secret_tableLayout);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);
        scroller = findViewById(R.id.back_scroll);
        typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.baloo_chettan);

        final TripleInputDialog fakeSecretInputDialog = new TripleInputDialog(SecretsActivity.this, "Add Secret",
                "Add a secret you can access later.",
                "Acount", "Username", "Password", "error") {
            @Override
            public boolean onPositiveClicked(final String userInputOne, final String userInputTwo, final String userInputThree) {
                GlobalVariables.secretList.addSecret(new FakeSecret(userInputOne, userInputTwo, userInputThree));
                return true;
            }
        };

        final ConfirmDialog<Secret> deleteConfirmDialog = new ConfirmDialog<Secret>(SecretsActivity.this, "Delete this Secret?", "") {
            @Override
            public boolean onPositiveClicked(Secret object) {
                GlobalVariables.secretList.removeSecret(object);
                deleteMode = false;
                deleteButton.setAlpha((float) 1);
                return true;
            }
            @Override
            public void onNegativeClicked() {
                deleteMode = false;
                deleteButton.setAlpha((float) 1);
            }
        };

        scroller.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                totalText.setAlpha(getAlphaFromScroll());
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!BaseApp.isConnected()) {
                    Toast.makeText(SecretsActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                } else {
                    fakeSecretInputDialog.show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMode = !deleteMode;
                if(deleteMode) { deleteButton.setAlpha((float) 0.5);
                } else { deleteButton.setAlpha((float) 1); }
            }
        });

        secretTable.setPressedAction(new ListenerAction<Secret>() {
            @Override
            public void doAction(final Secret input) {
                if(!BaseApp.isConnected()) {
                    Toast.makeText(SecretsActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                } else {
                    if(deleteMode) { deleteConfirmDialog.show(input); }
                }
            }
        });

        secretTable.setLongPressedAction(new ListenerAction<Secret>() {
            @Override
            public void doAction(final Secret input) {
                SecretDialog<Secret> promptDialog = new SecretDialog<Secret>(SecretsActivity.this, input.getAccount(), "Username: " + input.getUsername(), "Password: " + input.getPassword()) {
                    @Override
                    public boolean onPositiveClicked(Secret object) {
                        //Do
                        return true;
                    }
                };
                promptDialog.show(null);
            }
        });

        GlobalVariables.secretList.addObserver(this);

        updateLineFields();
        updateLineTables();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateLineFields();
    }

    private void updateLineFields() {
        totalText.setText(GlobalVariables.secretList.getSize() + " Total Secrets");
    }

    private void updateLineTables() {
        secretTable.clear();

        for(int i = 0; i < GlobalVariables.secretList.getSize(); i++) {
            Secret current = GlobalVariables.secretList.getSecret(i);
            secretTable.addRow(current.getAccount(), current, R.drawable.rounded_corner_button2);
        }
    }

    private float getAlphaFromScroll() {
        double scroll = scroller.getScrollY();
        double max = scroller.getChildAt(0).getHeight() - (scroller.getHeight());
        double diff = max - scroll;
        double alpha = 20 * (diff / max);
        return (float) Math.min(1, alpha);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SecretsActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        finish();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(GlobalVariables.secretList != null) {
            updateLineFields();
            updateLineTables();
        }
    }
}
