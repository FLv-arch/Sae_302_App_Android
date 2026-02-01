package com.example.myapplication.activities.data;

import com.example.myapplication.activities.models.DossierClient;
import com.example.myapplication.activities.models.StatutDossier;

import java.util.ArrayList;
import java.util.List;

public class FakeDossierRepository {

    public static List<DossierClient> getDossiers() {
        List<DossierClient> dossiers = new ArrayList<>();

        // On ajoute "2026-01-01" pour la date de début et "" pour la date de fin (si non terminé)
        dossiers.add(new DossierClient(
                "20265126",
                "Installation fibre optique",
                "Entreprise ABC",
                StatutDossier.A_TRAITER,
                "2026-01-01",
                ""
        ));

        dossiers.add(new DossierClient(
                "14578956",
                "Audit réseau",
                "Entreprise XYZ",
                StatutDossier.EN_COURS,
                "2026-01-05",
                ""
        ));

        // Pour celui-ci qui est TERMINE, on peut mettre une date de fin
        dossiers.add(new DossierClient(
                "96771100",
                "Maintenance infrastructure",
                "Entreprise DEF",
                StatutDossier.TERMINE,
                "2026-01-10",
                "2026-01-15"
        ));

        return dossiers;
    }
}