package xyz.ibnuraffi.asthmacontrol.edukasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EdukasiModel {
    public String id;
    public String slug;
    public String writer;
    public String judul;
    public String gambar;
    public String video;
    public String tanggal_input;

    public EdukasiModel(String id, String slug, String writer, String judul, String gambar, String video, String tanggal_input){
        this.id = id;
        this.slug = slug;
        this.writer = writer;
        this.judul = judul;
        this.gambar = gambar;
        this.video = video;
        this.tanggal_input = tanggal_input;
    }

    public static ArrayList<EdukasiModel> fromJson(JSONArray jsonObjects){
        ArrayList<EdukasiModel> data = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++){
            try {
                JSONObject row = jsonObjects.getJSONObject(i);
                data.add(
                        new EdukasiModel(
                                row.getString("id"),
                                row.getString("slug"),
                                row.getString("writer"),
                                row.getString("judul"),
                                row.getString("gambar"),
                                row.getString("video"),
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
