package com.example.pharmacymobilapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacymobilapp.DB.PharmacyDatabaseHelper;
import com.example.pharmacymobilapp.model.Pharmacy;

import java.util.List;

public class PharmacyActivity extends AppCompatActivity {

    private PharmacyDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private PharmacyAdapter adapter;
    private List<Pharmacy> pharmacyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pharmacy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new PharmacyDatabaseHelper(this);

        Button btnAddPharmacy = findViewById(R.id.btnAddPharmacy);
        btnAddPharmacy.setOnClickListener(v -> {
            Intent intent = new Intent(PharmacyActivity.this, AddPharmacyActivity.class);
            startActivity(intent);
        });

        Button btnDeleteOrUpdate = findViewById(R.id.btnDeleteOrUpdate);
        btnDeleteOrUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(PharmacyActivity.this, DeleteorupdateActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPharmacies();
    }

    public void loadPharmacies() {
        pharmacyList = dbHelper.getAllPharmacies();
        adapter = new PharmacyAdapter(pharmacyList, PharmacyActivity.this);
        recyclerView.setAdapter(adapter);
    }

    public void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }
}