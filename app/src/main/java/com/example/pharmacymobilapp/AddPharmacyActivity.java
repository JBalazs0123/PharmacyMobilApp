package com.example.pharmacymobilapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pharmacymobilapp.DB.PharmacyDatabaseHelper;
import com.example.pharmacymobilapp.model.Pharmacy;

public class AddPharmacyActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextOpeningHours;
    private PharmacyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addpharmacy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new PharmacyDatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextOpeningHours = findViewById(R.id.editTextOpeningHours);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(
                        1.0f, 1.5f, 1.0f, 1.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(300);
                scaleAnimation.setRepeatCount(1);
                scaleAnimation.setRepeatMode(Animation.REVERSE);
                v.startAnimation(scaleAnimation);
                addPharmacy();
            }
        });
    }

    private void addPharmacy() {
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String openingHours = editTextOpeningHours.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || openingHours.isEmpty()) {
            showAlertDialog("Please fill out all fields");
            return;
        }

        Pharmacy pharmacy = new Pharmacy(name, address, openingHours);
        dbHelper.addPharmacy(pharmacy);
        Intent intent = new Intent(this, PharmacyActivity.class);
        startActivity(intent);
        finish();
    }

    public void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }
}