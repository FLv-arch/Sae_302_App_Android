package com.example.myapplication.activities.data;

import com.example.myapplication.activities.models.DossierClient;
import com.example.myapplication.activities.models.StatutDossier;

import java.util.ArrayList;
import java.util.List;

public class FakeDossierRepository {

    public static List<DossierClient> getDossiers() {
        List<DossierClient> dossiers = new ArrayList<>();

        dossiers.add(new DossierClient(
                "20265126",
                "Installation fibre optique",
                "Entreprise ABC",
                StatutDossier.A_TRAITER
        ));

        dossiers.add(new DossierClient(
                "14578956",
                "Audit réseau",
                "Entreprise XYZ",
                StatutDossier.EN_COURS
        ));

        dossiers.add(new DossierClient(
                "96771100",
                "Maintenance infrastructure",
                "Entreprise DEF",
                StatutDossier.TERMINE
        ));

        return dossiers;
    }
}

