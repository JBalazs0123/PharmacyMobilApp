package com.example.pharmacymobilapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacymobilapp.model.Pharmacy;
import com.example.pharmacymobilapp.PharmacyActivity;

import java.util.List;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder> {

    private List<Pharmacy> pharmacyList;
    private PharmacyActivity activity;

    public PharmacyAdapter(List<Pharmacy> pharmacyList, PharmacyActivity activity) {
        this.pharmacyList = pharmacyList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacy_item, parent, false);
        return new PharmacyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyViewHolder holder, int position) {
        Pharmacy pharmacy = pharmacyList.get(position);
        holder.textViewName.setText(pharmacy.getName());
        holder.textViewAddress.setText(pharmacy.getAddress());
        holder.textViewOpeningHours.setText(pharmacy.getOpeningHours());
    }

    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }

    public static class PharmacyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewAddress, textViewOpeningHours;
        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewOpeningHours = itemView.findViewById(R.id.textViewOpeningHours);
        }
    }
}

