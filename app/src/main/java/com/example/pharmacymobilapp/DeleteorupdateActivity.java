package com.example.pharmacymobilapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pharmacymobilapp.DB.PharmacyDatabaseHelper;
import com.example.pharmacymobilapp.model.Pharmacy;

import java.util.ArrayList;
import java.util.List;

public class DeleteorupdateActivity extends AppCompatActivity {

    private Spinner spinnerPharmacies;
    private EditText editTextOpeningHours;
    private Button btnDelete, btnUpdate;
    private PharmacyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deleteorupdate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new PharmacyDatabaseHelper(this);
        spinnerPharmacies = findViewById(R.id.spinnerPharmacies);
        editTextOpeningHours = findViewById(R.id.editTextOpeningHours);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Gyógyszertárak neveinek lekérése és beállítása a Spinner-be
        List<String> pharmacyNames = getPharmacyNamesFromDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pharmacyNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPharmacies.setAdapter(adapter);

        // Törlés gomb eseménykezelő hozzáadása
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePharmacy();
            }
        });

        // Frissítés gomb eseménykezelő hozzáadása
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePharmacy();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // spinner frissítése
        refreshSpinner();
    }

    // Adatbázisból gyógyszertár nevek lekérése
    private List<String> getPharmacyNamesFromDatabase() {
        List<String> pharmacyNames = new ArrayList<>();
        List<Pharmacy> pharmacies = dbHelper.getAllPharmacies();
        for (Pharmacy pharmacy : pharmacies) {
            pharmacyNames.add(pharmacy.getName());
        }
        return pharmacyNames;
    }

    // Gyógyszertár törlése
    private void deletePharmacy() {
        String selectedPharmacyName = spinnerPharmacies.getSelectedItem().toString();
        dbHelper.deletePharmacy(selectedPharmacyName);
        Intent intent = new Intent(this, PharmacyActivity.class);
        startActivity(intent);
    }

    // Gyógyszertár frissítése
    private void updatePharmacy() {
        String selectedPharmacyName = spinnerPharmacies.getSelectedItem().toString();
        String newOpeningHours = editTextOpeningHours.getText().toString();
        if (newOpeningHours.isEmpty()) {
            showAlertDialog("Kérjük, adja meg az új nyitvatartási időt");
            return;
        }

        List<Pharmacy> pharmacies = dbHelper.getAllPharmacies();
        for (Pharmacy pharmacy : pharmacies) {
            if (pharmacy.getName().equals(selectedPharmacyName)) {
                Pharmacy updatedPharmacy = new Pharmacy(pharmacy.getName(), pharmacy.getAddress(), newOpeningHours);
                dbHelper.updatePharmacy(updatedPharmacy.getName(), updatedPharmacy);
                Intent intent = new Intent(this, PharmacyActivity.class);
                startActivity(intent);
            }
        }
        refreshSpinner();
    }

    public void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    // Spinner frissítése a gyógyszertárak törlése vagy frissítése után
    private void refreshSpinner() {
        List<String> pharmacyNames = getPharmacyNamesFromDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pharmacyNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPharmacies.setAdapter(adapter);
    }
}