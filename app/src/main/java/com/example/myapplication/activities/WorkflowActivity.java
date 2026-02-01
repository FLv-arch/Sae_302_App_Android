package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.activities.data.DossierRepository;
import com.example.myapplication.activities.models.DossierClient;
import com.example.myapplication.activities.models.StatutDossier;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WorkflowActivity extends AppCompatActivity {

    private DossierClient dossier;
    private View stateATraiter, stateEnCours, stateTermine;
    private MaterialButton btnATraiter, btnEnCours, btnTermine;
    private FloatingActionButton btnHome; // Ajout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workflow);

        dossier = (DossierClient) getIntent().getSerializableExtra("dossier");

        if (dossier == null) {
            Toast.makeText(this, "Erreur : dossier introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        initActions();
        updateUI();
    }

    private void initViews() {
        stateATraiter = findViewById(R.id.stateATraiter);
        stateEnCours = findViewById(R.id.stateEnCours);
        stateTermine = findViewById(R.id.stateTermine);
        btnATraiter = findViewById(R.id.btnATraiter);
        btnEnCours = findViewById(R.id.btnEnCours);
        btnTermine = findViewById(R.id.btnTermine);
        btnHome = findViewById(R.id.btnHome); // Ajout
    }

    private void initActions() {
        btnATraiter.setOnClickListener(v -> changeStatut(StatutDossier.A_TRAITER));
        btnEnCours.setOnClickListener(v -> changeStatut(StatutDossier.EN_COURS));
        btnTermine.setOnClickListener(v -> changeStatut(StatutDossier.TERMINE));

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(WorkflowActivity.this, DossierListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void changeStatut(StatutDossier nouveauStatut) {
        dossier.setStatut(nouveauStatut);
        DossierRepository.updateDossier(this, dossier);
        updateUI();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("dossier_mis_a_jour", dossier);
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, "Statut enregistré : " + nouveauStatut.name(), Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        resetUI();

        if (dossier.getStatut() == null) return;
        switch (dossier.getStatut()) {
            case A_TRAITER:
                stateATraiter.setBackgroundResource(R.drawable.bg_state_active);
                stateATraiter.setAlpha(1.0f); // Bien visible
                break;

            case EN_COURS:
                stateEnCours.setBackgroundResource(R.drawable.bg_state_active);
                stateEnCours.setAlpha(1.0f);
                break;

            case TERMINE:
                stateTermine.setBackgroundResource(R.drawable.bg_state_active);
                stateTermine.setAlpha(1.0f);
                break;
        }
    }

    private void resetUI() {
        stateATraiter.setBackgroundResource(R.drawable.bg_state_inactive);
        stateATraiter.setAlpha(0.5f);

        stateEnCours.setBackgroundResource(R.drawable.bg_state_inactive);
        stateEnCours.setAlpha(0.5f);

        stateTermine.setBackgroundResource(R.drawable.bg_state_inactive);
        stateTermine.setAlpha(0.5f);
    }
}