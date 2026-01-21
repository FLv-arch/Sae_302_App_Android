package com.example.myapplication.activities.models;

public class DossierClient {

    private String reference;
    private String description;
    private String client;
    private StatutDossier statut;

    public DossierClient(String reference, String description, String client, StatutDossier statut) {
        this.reference = reference;
        this.description = description;
        this.client = client;
        this.statut = statut;
    }

    public String getReference() {
        return reference;
    }

    public String getDescription() {
        return description;
    }

    public String getClient() {
        return client;
    }

    public StatutDossier getStatut() {
        return statut;
    }

    public void setStatut(StatutDossier statut) {
        this.statut = statut;
    }
}
