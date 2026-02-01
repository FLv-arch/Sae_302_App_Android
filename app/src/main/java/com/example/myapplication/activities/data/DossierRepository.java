package com.example.myapplication.activities.data;

import android.content.Context;
import android.util.Log;
import com.example.myapplication.activities.models.DossierClient;
import com.example.myapplication.activities.models.StatutDossier;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class DossierRepository {

    private static final String FILE_NAME = "dossiers_data.json";
    private static List<DossierClient> cachedDossiers = null;

    public static List<DossierClient> load(Context context) {
        if (cachedDossiers != null) return cachedDossiers;

        List<DossierClient> dossiers = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_NAME);

        try {
            InputStream is;
            if (file.exists()) {
                is = new FileInputStream(file);
            } else {
                // Si le fichier n'existe pas encore, on tente de lire l'original dans assets
                try {
                    is = context.getAssets().open("dossiers.json");
                } catch (Exception e) {
                    // Si pas de fichier dans assets non plus, on part sur une liste vide
                    cachedDossiers = new ArrayList<>();
                    return cachedDossiers;
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            is.close();

            JSONObject root = new JSONObject(json.toString());
            JSONArray array = root.getJSONArray("dossiers");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                // Correction ici : On récupère aussi les dates pour le constructeur
                DossierClient dossier = new DossierClient(
                        obj.getString("reference"),
                        obj.getString("client"),
                        obj.optString("description", ""),
                        StatutDossier.valueOf(obj.getString("statut")),
                        obj.optString("dateDebut", ""),
                        obj.optString("dateFin", "")
                );
                dossiers.add(dossier);
            }
            cachedDossiers = dossiers;
        } catch (Exception e) {
            Log.e("DossierRepository", "Error loading dossiers", e);
            cachedDossiers = new ArrayList<>();
        }
        return cachedDossiers;
    }

    // AJOUT DE LA MÉTHODE MANQUANTE POUR CreateDossierActivity
    public static void addDossier(Context context, DossierClient newDossier) {
        if (cachedDossiers == null) {
            load(context);
        }
        cachedDossiers.add(0, newDossier); // Ajouter en haut de liste
        saveAll(context); // Sauvegarder immédiatement dans le JSON
    }

    public static void updateDossier(Context context, DossierClient updatedDossier) {
        if (cachedDossiers == null) load(context);

        for (int i = 0; i < cachedDossiers.size(); i++) {
            if (cachedDossiers.get(i).getReference().equals(updatedDossier.getReference())) {
                cachedDossiers.set(i, updatedDossier);
                break;
            }
        }
        saveAll(context);
    }

    private static void saveAll(Context context) {
        if (cachedDossiers == null) return;

        try {
            JSONObject root = new JSONObject();
            JSONArray array = new JSONArray();

            for (DossierClient d : cachedDossiers) {
                JSONObject obj = new JSONObject();
                obj.put("reference", d.getReference());
                obj.put("client", d.getClient());
                obj.put("description", d.getDescription());
                obj.put("statut", d.getStatut().name());
                obj.put("dateDebut", d.getDateDebut());
                obj.put("dateFin", d.getDateFin());
                array.put(obj);
            }

            root.put("dossiers", array);

            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(root.toString());
            writer.close();
            fos.close();
        } catch (Exception e) {
            Log.e("DossierRepository", "Error saving dossiers", e);
        }
    }
}