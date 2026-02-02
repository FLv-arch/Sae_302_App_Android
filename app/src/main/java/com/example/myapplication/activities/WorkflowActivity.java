package com.example.myapplication.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
    private FloatingActionButton btnHome;

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
        btnHome = findViewById(R.id.btnHome);
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
        
        int colorActive = Color.parseColor("#FFCC99");
        int colorDarkBlue = Color.parseColor("#1A237E");

        switch (dossier.getStatut()) {
            case A_TRAITER:
                highlightState(stateATraiter, btnATraiter, colorActive, colorDarkBlue);
                break;

            case EN_COURS:
                highlightState(stateEnCours, btnEnCours, colorActive, colorDarkBlue);
                break;

            case TERMINE:
                highlightState(stateTermine, btnTermine, colorActive, colorDarkBlue);
                break;
        }
    }

    private void highlightState(View dot, MaterialButton button, int colorActive, int colorText) {
        dot.setBackgroundTintList(ColorStateList.valueOf(colorActive));
        dot.setAlpha(1.0f);

        button.setBackgroundTintList(ColorStateList.valueOf(colorActive));
        button.setTextColor(colorText);
        button.setStrokeWidth(0);
    }

    private void resetUI() {
        int colorInactiveDot = Color.parseColor("#80FFFFFF");
        int colorWhite = Color.WHITE;
        
        stateATraiter.setBackgroundTintList(ColorStateList.valueOf(colorInactiveDot));
        stateATraiter.setAlpha(0.5f);
        stateEnCours.setBackgroundTintList(ColorStateList.valueOf(colorInactiveDot));
        stateEnCours.setAlpha(0.5f);
        stateTermine.setBackgroundTintList(ColorStateList.valueOf(colorInactiveDot));
        stateTermine.setAlpha(0.5f);

        resetButtonStyle(btnATraiter, colorWhite);
        resetButtonStyle(btnEnCours, colorWhite);
        resetButtonStyle(btnTermine, colorWhite);
    }

    private void resetButtonStyle(MaterialButton button, int color) {
        button.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
        button.setStrokeColor(ColorStateList.valueOf(color));
        button.setStrokeWidth(3);
        button.setTextColor(color);
    }
}
