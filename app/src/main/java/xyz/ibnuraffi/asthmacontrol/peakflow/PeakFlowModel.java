package xyz.ibnuraffi.asthmacontrol.peakflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PeakFlowModel {
    public String id;
    public String tanggal;
    public String nilai;
    public String warna;

    public PeakFlowModel(String id, String tanggal, String nilai, String warna){
        this.id = id;
        this.tanggal = tanggal;
        this.nilai = nilai;
        this.warna = warna;
    }

    public static ArrayList<PeakFlowModel> fromJson(JSONArray jsonObjects){
        ArrayList<PeakFlowModel> data = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++){
            try {
                JSONObject row = jsonObjects.getJSONObject(i);
                data.add(
                        new PeakFlowModel(
                                row.getString("id"),
                                row.getString("tanggal"),
                                row.getString("nilai"),
                                row.getString("warna")
                        )
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
