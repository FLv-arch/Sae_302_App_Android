package com.example.myapplication.activities.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activities.models.DossierClient;

import java.util.List;

public class DossierAdapter extends RecyclerView.Adapter<DossierAdapter.ViewHolder> {

    public interface OnDossierClickListener {
        void onClick(DossierClient dossier);
    }

    private final List<DossierClient> dossiers;
    private final OnDossierClickListener listener;

    public DossierAdapter(List<DossierClient> dossiers, OnDossierClickListener listener) {
        this.dossiers = dossiers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dossier, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DossierClient dossier = dossiers.get(position);
        holder.ref.setText(dossier.getReference());
        holder.client.setText(dossier.getClient());
        holder.itemView.setOnClickListener(v -> listener.onClick(dossier));
    }

    @Override
    public int getItemCount() {
        return dossiers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ref, client;

        ViewHolder(View itemView) {
            super(itemView);
            ref = itemView.findViewById(R.id.textRef);
            client = itemView.findViewById(R.id.textClient);
        }
    }
}

