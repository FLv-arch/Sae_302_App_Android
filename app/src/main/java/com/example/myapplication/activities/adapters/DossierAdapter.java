package com.example.myapplication.activities.adapters;

import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import com.example.myapplication.R;
import com.example.myapplication.activities.models.DossierClient;
import com.example.myapplication.activities.DossierDetailActivity;

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

        holder.textDateDebut.setText(dossier.getDateDebut());

        if (dossier.getDateFin() == null || dossier.getDateFin().isEmpty()) {
            holder.textDateFin.setText("En cours");
        } else {
            holder.textDateFin.setText(dossier.getDateFin());
        }

        if (dossier.getStatut() != null) {
            String statutTxt = dossier.getStatut().name().replace("_", " ");
            holder.statut.setText(statutTxt);

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

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DossierDetailActivity.class);
            intent.putExtra("dossier", dossier); // La clé doit être "dossier"
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dossiers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ref, client, statut, textDateDebut, textDateFin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ref = itemView.findViewById(R.id.textRef);
            client = itemView.findViewById(R.id.textClient);
            statut = itemView.findViewById(R.id.textStatut);

            textDateDebut = itemView.findViewById(R.id.textDateDebut);
            textDateFin = itemView.findViewById(R.id.textDateFin);
        }
    }
}