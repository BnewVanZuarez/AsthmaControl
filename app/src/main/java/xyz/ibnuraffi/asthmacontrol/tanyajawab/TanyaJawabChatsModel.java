package xyz.ibnuraffi.asthmacontrol.tanyajawab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TanyaJawabChatsModel {
    public String id;
    public String pesan;
    public String tipe;
    public String tanggal_input;

    public TanyaJawabChatsModel(String id, String pesan, String tipe, String tanggal_input){
        this.id = id;
        this.pesan = pesan;
        this.tipe = tipe;
        this.tanggal_input = tanggal_input;
    }

    public static ArrayList<TanyaJawabChatsModel> fromJson(JSONArray jsonObjects){
        ArrayList<TanyaJawabChatsModel> data = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++){
            try {
                JSONObject row = jsonObjects.getJSONObject(i);
                data.add(
                        new TanyaJawabChatsModel(
                                row.getString("id"),
                                row.getString("pesan"),
                                row.getString("tipe"),
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
