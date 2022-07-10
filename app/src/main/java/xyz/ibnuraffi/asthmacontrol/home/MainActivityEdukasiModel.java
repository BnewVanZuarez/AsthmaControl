package xyz.ibnuraffi.asthmacontrol.home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivityEdukasiModel {

    public String id;
    public String slug;
    public String writer;
    public String judul;
    public String gambar;
    public String tanggal_input;

    public MainActivityEdukasiModel(JSONObject object){
        try {
            this.id = object.getString("id");
            this.slug = object.getString("slug");
            this.gambar = object.getString("gambar");
            this.writer = object.getString("writer");
            this.judul = object.getString("judul");
            this.tanggal_input = object.getString("tanggal_input");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MainActivityEdukasi> fromJson(JSONArray jsonObjects) {
        ArrayList<MainActivityEdukasi> data = new ArrayList<>();
        for (int position = 0; position < jsonObjects.length(); position++){
            MainActivityEdukasi berita = new MainActivityEdukasi();
            try {
                JSONObject obj = jsonObjects.getJSONObject(position);
                //JsonObject obj = (JsonObject) jsonObjects.get(position);
                berita.setId(obj.get("id").toString());
                berita.setSlug(obj.get("slug").toString());
                berita.setWriter(obj.get("writer").toString());
                berita.setJudul(obj.get("judul").toString());
                berita.setGambar(obj.get("gambar").toString());
                berita.setTanggal_input(obj.get("tanggal_input").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            data.add(berita);
        }
        return data;
    }

}
