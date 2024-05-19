package com.example.pharmacymobilapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pharmacymobilapp.DB.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        dbHelper = new UserDatabaseHelper(this);
    }

    public void OnLogin(View view) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlertDialog("Please fill out all fields");
        } else {
            if (authenticateUser(username, password)) {
                Intent intent = new Intent(this, PharmacyActivity.class);
                startActivity(intent);
                finish();
            } else {
                showAlertDialog("Invalid username or password");
            }
        }
    }

    public void showAlertDialog(String message) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        TextView textViewMessage = dialogView.findViewById(R.id.textViewMessage);
        textViewMessage.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("OK", (dialog, id) -> {
                    Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
                    dialogView.startAnimation(fadeOut);
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            dialog.dismiss();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                });

        AlertDialog alert = builder.create();

        alert.setOnShowListener(dialog -> {
            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            dialogView.startAnimation(fadeIn);
        });

        alert.show();
    }

    private boolean authenticateUser(String username, String password) {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + UserDatabaseHelper.TABLE_USERS +
                        " WHERE " + UserDatabaseHelper.COLUMN_USERNAME + "=? AND " +
                        UserDatabaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{username, password});
        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        return isAuthenticated;
    }

    public void OnRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}