package xyz.ibnuraffi.asthmacontrol.rencanaaksiasma;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RencanaAksiAsmaModel {
    public String id;
    public String nama_dokter;
    public String telp_dokter;
    public String tanggal_input;

    public RencanaAksiAsmaModel(String id, String nama_dokter, String telp_dokter, String tanggal_input){
        this.id = id;
        this.nama_dokter = nama_dokter;
        this.telp_dokter = telp_dokter;
        this.tanggal_input = tanggal_input;
    }

    public static ArrayList<RencanaAksiAsmaModel> fromJson(JSONArray jsonObjects){
        ArrayList<RencanaAksiAsmaModel> data = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++){
            try {
                JSONObject row = jsonObjects.getJSONObject(i);
                data.add(
                        new RencanaAksiAsmaModel(
                                row.getString("id"),
                                row.getString("nama_dokter"),
                                row.getString("telp_dokter"),
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
