package xyz.ibnuraffi.asthmacontrol.daftarobat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DaftarObatModel {
    public String id;
    public String nama;
    public String dosis;
    public String tanggal_input;

    public DaftarObatModel(String id, String nama, String dosis, String tanggal_input){
        this.id = id;
        this.nama = nama;
        this.dosis = dosis;
        this.tanggal_input = tanggal_input;
    }

    public static ArrayList<DaftarObatModel> fromJson(JSONArray jsonObjects){
        ArrayList<DaftarObatModel> data = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++){
            try {
                JSONObject row = jsonObjects.getJSONObject(i);
                data.add(
                        new DaftarObatModel(
                                row.getString("id"),
                                row.getString("nama_obat"),
                                row.getString("dosis"),
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
