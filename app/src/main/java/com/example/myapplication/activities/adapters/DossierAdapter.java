package com.example.myapplication.activities.adapters;

import android.graphics.Color;
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

        // MISE À JOUR DE L'ÉTAT DANS LA LISTE
        if (dossier.getStatut() != null) {
            String statutTxt = dossier.getStatut().name().replace("_", " ");
            holder.statut.setText(statutTxt);

            // Petit bonus : changer la couleur selon le statut
            switch (dossier.getStatut()) {
                case A_TRAITER:
                    holder.statut.setTextColor(Color.parseColor("#757575")); // Gris
                    break;
                case EN_COURS:
                    holder.statut.setTextColor(Color.parseColor("#1976D2")); // Bleu
                    break;
                case TERMINE:
                    holder.statut.setTextColor(Color.parseColor("#388E3C")); // Vert
                    break;
            }
        }

        holder.itemView.setOnClickListener(v -> listener.onClick(dossier));
    }

    @Override
    public int getItemCount() {
        return dossiers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ref, client, statut;

        ViewHolder(View itemView) {
            super(itemView);
            ref = itemView.findViewById(R.id.textRef);
            client = itemView.findViewById(R.id.textClient);
            statut = itemView.findViewById(R.id.textStatut);
        }
    }
}