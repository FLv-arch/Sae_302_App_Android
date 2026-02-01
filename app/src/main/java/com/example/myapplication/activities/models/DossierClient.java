package com.example.myapplication.activities.models;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

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
        this.logs.add("Dossier créé - Statut initial : " + statut.name());
    }


    public String getReference() { return reference; }
    public String getClient() { return client; }
    public String getDescription() { return description; }
    public StatutDossier getStatut() { return statut; }
    public List<String> getLogs() { return logs; }


    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }

    public void setStatut(StatutDossier statut) {
        this.statut = statut;
        if (logs == null) logs = new ArrayList<>();
        logs.add("Statut modifié → " + statut.name());
    }


    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}