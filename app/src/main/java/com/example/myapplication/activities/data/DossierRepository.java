package com.example.myapplication.activities.data;

import android.content.Context;

import com.example.myapplication.activities.models.DossierClient;
import com.example.myapplication.activities.models.StatutDossier;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;




public class DossierRepository {

    public static List<DossierClient> load(Context context) {

        List<DossierClient> dossiers = new ArrayList<>();

        try {
            InputStream is = context.getAssets().open("dossiers.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder json = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            JSONArray array = new JSONObject(json.toString()).getJSONArray("dossiers");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                JSONArray logsJson = obj.getJSONArray("logs");
                List<String> logs = new ArrayList<>();

                for (int j = 0; j < logsJson.length(); j++) {
                    logs.add(logsJson.getString(j));
                }

                dossiers.add(new DossierClient(
                        obj.getString("reference"),
                        obj.getString("client"),
                        obj.getString("description"),
                        StatutDossier.valueOf(obj.getString("statut"))
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dossiers;
    }
<<<<<<< HEAD
}

=======
}
>>>>>>> 2fa1fc4 (MAJ)
