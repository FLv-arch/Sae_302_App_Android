package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mise à jour de la date et l'heure
        TextView textDateTime = findViewById(R.id.textDateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d MMMM yyyy - HH:mm", Locale.FRANCE);
        String currentDateAndTime = sdf.format(new Date());
        textDateTime.setText(currentDateAndTime);

        Button btn = findViewById(R.id.btnAccessDossier);

        btn.setOnClickListener(v ->
                startActivity(new Intent(this, DossierListActivity.class))
        );
    }
}