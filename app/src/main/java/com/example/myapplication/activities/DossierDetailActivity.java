package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activities.models.DossierClient;
import com.google.android.material.button.MaterialButton;

public class DossierDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_detail);

        DossierClient dossier = (DossierClient) getIntent().getSerializableExtra("dossier");

        ((TextView) findViewById(R.id.textReference)).setText(dossier.getReference());
        ((TextView) findViewById(R.id.textClient)).setText(dossier.getClient());
        ((TextView) findViewById(R.id.textDescription)).setText(dossier.getDescription());
        ((TextView) findViewById(R.id.textStatut)).setText(dossier.getStatut().name());

        MaterialButton btn = findViewById(R.id.btnWorkflow);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WorkflowActivity.class);
            intent.putExtra("dossier", dossier);
            startActivity(intent);
        });
    }
}

