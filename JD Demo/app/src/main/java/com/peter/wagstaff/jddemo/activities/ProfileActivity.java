package com.peter.wagstaff.jddemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.peter.wagstaff.jddemo.R;
import com.peter.wagstaff.jddemo.activities.fragments.ConfirmDialog;
import com.peter.wagstaff.jddemo.activities.fragments.InputDialog;
import com.peter.wagstaff.jddemo.backend.GlobalFunctions;
import com.peter.wagstaff.jddemo.backend.GlobalVariables;
import com.peter.wagstaff.jddemo.backend.User;
import com.peter.wagstaff.jddemo.backend.UserRestore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView userIDView;
    Button changeUsernameButton, changePasswordButton, deleteUserButton, signOutButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userIDView = findViewById(R.id.user_id_textView);
        changeUsernameButton = findViewById(R.id.change_username_button);
        changePasswordButton = findViewById(R.id.change_password_button);
        deleteUserButton = findViewById(R.id.delete_user_button);
        signOutButton = findViewById(R.id.sign_out_button);
        progressBar = findViewById(R.id.progressBar);

        userIDView.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        final InputDialog usernameInputDialog = new InputDialog(ProfileActivity.this, "Enter New Username", "", "Username", "") {
            @Override
            public boolean onPositiveClicked(final String userInput) {
                if(!GlobalFunctions.checkUserNameRequirements(userInput, getApplicationContext())) {return false;}

                setWaiting(true);
                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(userInput).build();
                User.getCurrentUser().getAssociatedFireBaseUser().updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            User.getCurrentUser().setName(userInput);
                            userIDView.setText(userInput);
                            Toast.makeText(ProfileActivity.this, "Username is updated", Toast.LENGTH_SHORT).show();
                            setWaiting(false);
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to update username", Toast.LENGTH_SHORT).show();
                            setWaiting(false);
                        }
                    }
                });
                return true;
            }
        };

        changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { usernameInputDialog.show(); }
        });

        final InputDialog passwordInputDialog = new InputDialog(ProfileActivity.this, "Enter New Password", "", "Password", "", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            @Override
            public boolean onPositiveClicked(String userInput) {
                if(!GlobalFunctions.checkUserPasswordRequirements(userInput, getApplicationContext())) {return false;}

                setWaiting(true);
                User.getCurrentUser().getAssociatedFireBaseUser().updatePassword(userInput).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                            setWaiting(false);
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to update password, try signing out and back in", Toast.LENGTH_SHORT).show();
                            setWaiting(false);
                        }
                    }
                });
                return true;
            }
        };

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { passwordInputDialog.show(); }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ConfirmDialog deleteDialog = new ConfirmDialog(ProfileActivity.this, "Delete Your Profile", "") {
                    @Override
                    public boolean onPositiveClicked(Object object) {
                        final UserRestore savedValues = User.getCurrentUser().destroy();

                        setWaiting(true);
                        User.getCurrentUser().getAssociatedFireBaseUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    User.releaseCurrentUser();
                                    Toast.makeText(ProfileActivity.this, "Your profile is deleted", Toast.LENGTH_SHORT).show();
                                    setWaiting(false);
                                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                    finish();

                                } else {
                                    User.getCurrentUser().restore(savedValues);
                                    Toast.makeText(ProfileActivity.this, "Failed to delete your account, try signing out and back in", Toast.LENGTH_SHORT).show();
                                    setWaiting(false);
                                }
                            }
                        });
                        return true;
                    }

                    @Override
                    public void onNegativeClicked() {}
                };

                deleteDialog.show(null);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getCurrentUser().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });
    }

    private void setWaiting(boolean isWaiting) {
        if(isWaiting) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
        changeUsernameButton.setClickable(!isWaiting);
        changePasswordButton.setClickable(!isWaiting);
        deleteUserButton.setClickable(!isWaiting);
        signOutButton.setClickable(!isWaiting);
    }
}