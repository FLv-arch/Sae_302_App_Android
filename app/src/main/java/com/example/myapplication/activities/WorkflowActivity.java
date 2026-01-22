package com.example.myapplication.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activities.models.DossierClient;
import com.example.myapplication.activities.models.StatutDossier;
import com.google.android.material.button.MaterialButton;

public class WorkflowActivity extends AppCompatActivity {

    private DossierClient dossier;

    // UI
    private TextView txtEtatATraiter;
    private TextView txtEtatEnCours;
    private TextView txtEtatTermine;

    private MaterialButton btnATraiter;
    private MaterialButton btnEnCours;
    private MaterialButton btnTermine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workflow);

        // 🔹 Récupération du dossier depuis l'Intent
        dossier = (DossierClient) getIntent().getSerializableExtra("dossier");

        if (dossier == null) {
            Toast.makeText(this, "Erreur : dossier introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialisation des vues
        initViews();

        // Initialisation des actions
        initActions();

        // Affichage de l'état initial
        updateUI();
    }

    private void initViews() {
        txtEtatATraiter = findViewById(R.id.txtEtatATraiter);
        txtEtatEnCours = findViewById(R.id.txtEtatEnCours);
        txtEtatTermine = findViewById(R.id.txtEtatTermine);

        btnATraiter = findViewById(R.id.btnATraiter);
        btnEnCours = findViewById(R.id.btnEnCours);
        btnTermine = findViewById(R.id.btnTermine);
    }

    private void initActions() {
        btnATraiter.setOnClickListener(v -> {
            dossier.setStatut(StatutDossier.A_TRAITER);
            updateUI();
        });

        btnEnCours.setOnClickListener(v -> {
            dossier.setStatut(StatutDossier.EN_COURS);
            updateUI();
        });

        btnTermine.setOnClickListener(v -> {
            dossier.setStatut(StatutDossier.TERMINE);
            updateUI();
        });
    }

    /**
     * Synchronise l'UI avec le statut du dossier
     */
    private void updateUI() {
        resetUI();

        switch (dossier.getStatut()) {
            case A_TRAITER:
                txtEtatATraiter.setBackgroundResource(R.drawable.bg_state_active);
                break;

            case EN_COURS:
                txtEtatEnCours.setBackgroundResource(R.drawable.bg_state_active);
                break;

            case TERMINE:
                txtEtatTermine.setBackgroundResource(R.drawable.bg_state_active);
                break;
        }
    }

    /**
     * Réinitialise l'affichage des états
     */
    private void resetUI() {
        txtEtatATraiter.setBackgroundResource(R.drawable.bg_state_inactive);
        txtEtatEnCours.setBackgroundResource(R.drawable.bg_state_inactive);
        txtEtatTermine.setBackgroundResource(R.drawable.bg_state_inactive);
    }
}
