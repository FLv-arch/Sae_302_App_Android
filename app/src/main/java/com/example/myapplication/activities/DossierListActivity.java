package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.adapters.DossierAdapter;
import com.example.myapplication.activities.data.DossierRepository;
import com.example.myapplication.activities.models.DossierClient;

import java.util.List;

public class DossierListActivity extends AppCompatActivity {

    private DossierAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_list);

        recyclerView = findViewById(R.id.recyclerDossiers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchir les données au retour d'une autre activité
        loadData();
    }

    private void loadData() {
        List<DossierClient> dossiers = DossierRepository.load(this);

        // Si l'adapter existe déjà, on met juste à jour les données
        // Sinon on le crée
        adapter = new DossierAdapter(dossiers, dossier -> {
            Intent intent = new Intent(this, DossierDetailActivity.class);
            intent.putExtra("dossier", dossier);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}