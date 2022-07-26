package xyz.ibnuraffi.asthmacontrol.dailyjurnal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DailyJurnalModel {
    public String id;
    public String tanggal;
    public String rate_today;
    public String rate_pain;
    public String mood_today;

    public DailyJurnalModel(String id, String tanggal, String rate_today, String rate_pain, String mood_today){
        this.id = id;
        this.tanggal = tanggal;
        this.rate_today = rate_today;
        this.rate_pain = rate_pain;
        this.mood_today = mood_today;
    }

    public static ArrayList<DailyJurnalModel> fromJson(JSONArray jsonObjects){
        ArrayList<DailyJurnalModel> data = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++){
            try {
                JSONObject row = jsonObjects.getJSONObject(i);
                data.add(
                        new DailyJurnalModel(
                                row.getString("id"),
                                row.getString("tanggal"),
                                row.getString("rate_today"),
                                row.getString("rate_pain"),
                                row.getString("mood_today")
                        )
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
