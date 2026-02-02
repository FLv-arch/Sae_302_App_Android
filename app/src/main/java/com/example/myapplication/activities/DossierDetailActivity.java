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
    private TextView textStatut, textLogs, textDateDebut, textDateFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_detail);

        dossier = (DossierClient) getIntent().getSerializableExtra("dossier");

        if (dossier == null) {
            finish();
            return;
        }

        initViews();
        displayData();

        MaterialButton btn = findViewById(R.id.btnWorkflow);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WorkflowActivity.class);
            intent.putExtra("dossier", dossier);
            startActivityForResult(intent, REQUEST_WORKFLOW);
        });
    }

    private void initViews() {
        textStatut = findViewById(R.id.textStatut);
        textLogs = findViewById(R.id.textLogs);
        textDateDebut = findViewById(R.id.textDateDebut);
        textDateFin = findViewById(R.id.textDateFin);
    }

    private void displayData() {
        ((TextView) findViewById(R.id.textReference)).setText("Dossier #" + dossier.getReference());
        ((TextView) findViewById(R.id.textClient)).setText(dossier.getClient());
        ((TextView) findViewById(R.id.textDescription)).setText(dossier.getDescription());
        
        textStatut.setText(dossier.getStatut().name());
        textDateDebut.setText("Date de début : " + (dossier.getDateDebut().isEmpty() ? "--" : dossier.getDateDebut()));
        textDateFin.setText("Date de fin : " + (dossier.getDateFin().isEmpty() ? "--" : dossier.getDateFin()));

        // Affichage des logs
        StringBuilder sb = new StringBuilder();
        for (String log : dossier.getLogs()) {
            sb.append("- ").append(log).append("\n");
        }
        textLogs.setText(sb.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_WORKFLOW && resultCode == RESULT_OK && data != null) {
            DossierClient updatedDossier = (DossierClient) data.getSerializableExtra("dossier_mis_a_jour");
            if (updatedDossier != null) {
                this.dossier = updatedDossier;
                displayData(); // Rafraîchir tout l'affichage
            }
        }
    }
}
