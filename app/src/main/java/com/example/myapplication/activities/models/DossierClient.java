package com.example.myapplication.activities.models;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class DossierClient implements Serializable {

    private String reference;
    private String client;
    private String description;
    private StatutDossier statut;
    private List<String> logs;

    public DossierClient(String reference, String client, String description, StatutDossier statut) {
        this.reference = reference;
        this.client = client;
        this.description = description;
        this.statut = statut;
        this.logs = new ArrayList<>();
        this.logs.add("Dossier créé - Statut initial : " + statut.name());
    }

    public String getReference() { return reference; }
    public String getClient() { return client; }
    public String getDescription() { return description; }
    public StatutDossier getStatut() { return statut; }
    public List<String> getLogs() { return logs; }

    public void setStatut(StatutDossier statut) {
        this.statut = statut;
        if (logs == null) logs = new ArrayList<>();
        logs.add("Statut modifié → " + statut.name());
    }
}