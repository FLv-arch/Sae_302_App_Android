package com.example.myapplication.activities.models;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DossierClient implements Serializable {

    private String reference;
    private String client;
    private String description;
    private StatutDossier statut;
    private String dateDebut;
    private String dateFin;
    private List<String> logs;

    public DossierClient(String reference, String client, String description, StatutDossier statut, String dateDebut, String dateFin) {
        this.reference = reference;
        this.client = client;
        this.description = description;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.logs = new ArrayList<>();
        addLog("Dossier créé - Statut initial : " + statut.name());
    }

    public String getReference() { return reference; }
    public String getClient() { return client; }
    public String getDescription() { return description; }
    public StatutDossier getStatut() { return statut; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
    public List<String> getLogs() { 
        if (logs == null) logs = new ArrayList<>();
        return logs; 
    }

    public void setStatut(StatutDossier statut) {
        this.statut = statut;
        addLog("Statut modifié → " + statut.name());
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void addLog(String message) {
        if (logs == null) logs = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String timestamp = sdf.format(new Date());
        logs.add("[" + timestamp + "] " + message);
    }
    
    public void setLogs(List<String> logs) {
        this.logs = logs;
    }
}
