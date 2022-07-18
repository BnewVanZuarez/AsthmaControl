package xyz.ibnuraffi.asthmacontrol.tanyajawab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TanyaJawabModel {
    public String id;
    public String no_tiket;
    public String perihal;
    public String status;
    public String tanggal_input;

    public TanyaJawabModel(String id, String no_tiket, String perihal, String status, String tanggal_input){
        this.id = id;
        this.no_tiket = no_tiket;
        this.perihal = perihal;
        this.status = status;
        this.tanggal_input = tanggal_input;
    }

    public static ArrayList<TanyaJawabModel> fromJson(JSONArray jsonObjects){
        ArrayList<TanyaJawabModel> data = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++){
            try {
                JSONObject row = jsonObjects.getJSONObject(i);
                data.add(
                        new TanyaJawabModel(
                                row.getString("id"),
                                row.getString("no_tiket"),
                                row.getString("perihal"),
                                row.getString("status"),
                                row.getString("tanggal_input")
                        )
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
