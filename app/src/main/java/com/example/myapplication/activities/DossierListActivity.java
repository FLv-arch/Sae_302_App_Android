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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerDossiers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<DossierClient> dossiers = DossierRepository.load(this);

        DossierAdapter adapter = new DossierAdapter(dossiers, dossier -> {
            Intent intent = new Intent(this, DossierDetailActivity.class);
            intent.putExtra("dossier", dossier); // UN SEUL dossier
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}


