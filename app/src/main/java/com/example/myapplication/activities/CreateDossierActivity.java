package com.example.myapplication.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Locale;

import com.example.myapplication.activities.models.DossierClient;
import com.example.myapplication.activities.models.StatutDossier;
import com.example.myapplication.activities.data.DossierRepository;
import com.example.myapplication.R; // Remplace par ton vrai nom de package

public class CreateDossierActivity extends AppCompatActivity {

    private TextInputEditText inputRef, inputClient, inputDesc, inputDateDebut, inputDateFin;
    private Spinner spinnerStatut;
    private MaterialButton btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dossier);

        inputRef = findViewById(R.id.inputReference);
        inputClient = findViewById(R.id.inputClient);
        inputDesc = findViewById(R.id.inputDescription);
        inputDateDebut = findViewById(R.id.inputDateDebut);
        inputDateFin = findViewById(R.id.inputDateFin);
        spinnerStatut = findViewById(R.id.spinnerStatut);
        btnCreate = findViewById(R.id.btnCreateDossier);

        spinnerStatut.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, StatutDossier.values()));

        inputDateDebut.setOnClickListener(v -> showDatePicker(inputDateDebut));
        inputDateFin.setOnClickListener(v -> showDatePicker(inputDateFin));

        btnCreate.setOnClickListener(v -> saveDossier());
    }

    private void showDatePicker(TextInputEditText editText) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.FRANCE, "%02d/%02d/%d", dayOfMonth, month + 1, year);
            editText.setText(date);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveDossier() {
        String ref = inputRef.getText().toString().trim();
        String client = inputClient.getText().toString().trim();
        String desc = inputDesc.getText().toString().trim();
        String dateD = inputDateDebut.getText().toString().trim();
        String dateF = inputDateFin.getText().toString().trim();
        StatutDossier statut = (StatutDossier) spinnerStatut.getSelectedItem();


        if (ref.isEmpty() || client.isEmpty() || dateD.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir la référence, le client et la date de début", Toast.LENGTH_SHORT).show();
            return;
        }

        DossierClient nouveauDossier = new DossierClient(ref, client, desc, statut, dateD, dateF);

        DossierRepository.addDossier(this, nouveauDossier);

        Toast.makeText(this, "Dossier créé avec succès !", Toast.LENGTH_SHORT).show();
        finish();
    }
}