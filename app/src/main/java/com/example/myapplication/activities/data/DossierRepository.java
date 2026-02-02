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
                try {
                    is = context.getAssets().open("dossiers.json");
                } catch (Exception e) {
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

                DossierClient dossier = new DossierClient(
                        obj.getString("reference"),
                        obj.getString("client"),
                        obj.optString("description", ""),
                        StatutDossier.valueOf(obj.getString("statut")),
                        obj.optString("date_debut", obj.optString("dateDebut", "")),
                        obj.optString("date_fin", obj.optString("dateFin", ""))
                );
                
                // Chargement des LOGS
                if (obj.has("logs")) {
                    JSONArray logsArray = obj.getJSONArray("logs");
                    List<String> logsList = new ArrayList<>();
                    for (int j = 0; j < logsArray.length(); j++) {
                        logsList.add(logsArray.getString(j));
                    }
                    dossier.setLogs(logsList);
                }
                
                dossiers.add(dossier);
            }
            cachedDossiers = dossiers;
        } catch (Exception e) {
            Log.e("DossierRepository", "Error loading dossiers", e);
            cachedDossiers = new ArrayList<>();
        }
        return cachedDossiers;
    }

    public static void addDossier(Context context, DossierClient newDossier) {
        if (cachedDossiers == null) load(context);
        cachedDossiers.add(0, newDossier);
        saveAll(context);
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
                obj.put("date_debut", d.getDateDebut());
                obj.put("date_fin", d.getDateFin());
                
                // Sauvegarde des LOGS
                JSONArray logsArray = new JSONArray();
                for (String log : d.getLogs()) {
                    logsArray.put(log);
                }
                obj.put("logs", logsArray);
                
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
