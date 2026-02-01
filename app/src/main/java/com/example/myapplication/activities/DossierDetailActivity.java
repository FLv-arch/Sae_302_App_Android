package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activities.models.DossierClient;
import com.google.android.material.button.MaterialButton;

public class DossierDetailActivity extends AppCompatActivity {

    private static final int REQUEST_WORKFLOW = 1001;
    private DossierClient dossier;
    private TextView textStatut;
    private TextView textDateDebut;
    private TextView textDateFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_detail);

        dossier = (DossierClient) getIntent().getSerializableExtra("dossier");

        if (dossier == null) {
            finish();
            return;
        }

        ((TextView) findViewById(R.id.textReference)).setText(dossier.getReference());
        ((TextView) findViewById(R.id.textClient)).setText(dossier.getClient());
        ((TextView) findViewById(R.id.textDescription)).setText(dossier.getDescription());

        textStatut = findViewById(R.id.textStatut);
        textStatut.setText(dossier.getStatut().name());

        textDateDebut = findViewById(R.id.textDateDebut);
        textDateFin = findViewById(R.id.textDateFin);

        textDateDebut.setText("Début : " + dossier.getDateDebut());

        if (dossier.getDateFin() == null || dossier.getDateFin().isEmpty()) {
            textDateFin.setText("Fin : En cours");
        } else {
            textDateFin.setText("Fin : " + dossier.getDateFin());
        }

        MaterialButton btn = findViewById(R.id.btnWorkflow);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WorkflowActivity.class);
            intent.putExtra("dossier", dossier);
            startActivityForResult(intent, REQUEST_WORKFLOW);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_WORKFLOW && resultCode == RESULT_OK && data != null) {
            DossierClient updatedDossier = (DossierClient) data.getSerializableExtra("dossier_mis_a_jour");
            if (updatedDossier != null) {
                this.dossier = updatedDossier;
                textStatut.setText(dossier.getStatut().name());
                textDateDebut.setText("Début : " + dossier.getDateDebut());
                textDateFin.setText(dossier.getDateFin() == null || dossier.getDateFin().isEmpty() ? "Fin : En cours" : "Fin : " + dossier.getDateFin());
            }
        }
    }
}