package xyz.ibnuraffi.asthmacontrol.rencanaaksiasma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class RencanaAksiAsmaBuatZona extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    private String rencana_id = "";

    // Zona Hijau
    private TextInputEditText hijau_peakflow_dari;
    private TextInputEditText hijau_peakflow_ke;
    private LinearLayout obat_hijau;
    private ArrayList<View> obat_hijau_form = new ArrayList<>();
    private ArrayList<Map<String, String>> obat_hijau_array = new ArrayList<>();
    private MaterialButton add_obat_hijau;
    private Gson obat_hijau_gson = new Gson();

    // Zona Kuning
    private TextInputEditText kuning_peakflow_dari;
    private TextInputEditText kuning_peakflow_ke;
    private LinearLayout obat_kuning;
    private ArrayList<View> obat_kuning_form = new ArrayList<>();
    private ArrayList<Map<String, String>> obat_kuning_array = new ArrayList<>();
    private MaterialButton add_obat_kuning;
    private Gson obat_kuning_gson = new Gson();

    // Zona Merah
    private TextInputEditText merah_peakflow_dari;
    private TextInputEditText merah_peakflow_ke;
    private LinearLayout obat_merah;
    private ArrayList<View> obat_merah_form = new ArrayList<>();
    private ArrayList<Map<String, String>> obat_merah_array = new ArrayList<>();
    private MaterialButton add_obat_merah;
    private Gson obat_merah_gson = new Gson();

    private MaterialButton btn_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rencana_aksi_asma_buat_zona);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rencana Aksi Asma");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            rencana_id = bundle.getString("rencana_id");
        }

        // Zona Hijau
        hijau_peakflow_dari = findViewById(R.id.hijau_peakflow_dari);
        hijau_peakflow_ke = findViewById(R.id.hijau_peakflow_ke);
        obat_hijau = findViewById(R.id.obat_hijau);
        add_obat_hijau = findViewById(R.id.add_obat_hijau);
        add_obat_hijau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View vw = LayoutInflater.from(RencanaAksiAsmaBuatZona.this).inflate(R.layout.rencana_aksi_asma_buat_zona_obat, null);
                obat_hijau.addView(vw);
                obat_hijau_form.add(vw);
            }
        });

        // Zona Kuning
        kuning_peakflow_dari = findViewById(R.id.kuning_peakflow_dari);
        kuning_peakflow_ke = findViewById(R.id.kuning_peakflow_ke);
        obat_kuning = findViewById(R.id.obat_kuning);
        add_obat_kuning = findViewById(R.id.add_obat_kuning);
        add_obat_kuning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View vw = LayoutInflater.from(RencanaAksiAsmaBuatZona.this).inflate(R.layout.rencana_aksi_asma_buat_zona_obat, null);
                obat_kuning.addView(vw);
                obat_kuning_form.add(vw);
            }
        });

        // Zona Merah
        merah_peakflow_dari = findViewById(R.id.merah_peakflow_dari);
        merah_peakflow_ke = findViewById(R.id.merah_peakflow_ke);
        obat_merah = findViewById(R.id.obat_merah);
        add_obat_merah = findViewById(R.id.add_obat_merah);
        add_obat_merah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View vw = LayoutInflater.from(RencanaAksiAsmaBuatZona.this).inflate(R.layout.rencana_aksi_asma_buat_zona_obat, null);
                obat_merah.addView(vw);
                obat_merah_form.add(vw);
            }
        });

        btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hpd = hijau_peakflow_dari.getText().toString();
                String hpk = hijau_peakflow_ke.getText().toString();
                String data_obat_hijau = "[]";
                obat_hijau_array.clear();
                if (obat_hijau_form.size() > 0){
                    for (View i : obat_hijau_form){
                        EditText jenis_obat = i.findViewById(R.id.obat_jenis);
                        EditText dosis = i.findViewById(R.id.obat_dosis);
                        EditText waktu = i.findViewById(R.id.obat_waktu);

                        Map<String, String> i_map = new HashMap<>();
                        i_map.put("data_jenis", jenis_obat.getText().toString());
                        i_map.put("data_dosis", dosis.getText().toString());
                        i_map.put("data_waktu", waktu.getText().toString());
                        if (!TextUtils.isEmpty(jenis_obat.getText().toString()) || !TextUtils.isEmpty(dosis.getText().toString()) || !TextUtils.isEmpty(waktu.getText().toString()) ){
                            obat_hijau_array.add(i_map);
                        }
                    }
                    data_obat_hijau = obat_hijau_gson.toJson(obat_hijau_array);
                }

                String kpd = kuning_peakflow_dari.getText().toString();
                String kpk = kuning_peakflow_ke.getText().toString();
                String data_obat_kuning = "[]";
                obat_kuning_array.clear();
                if (obat_kuning_form.size() > 0){
                    for (View i : obat_kuning_form){
                        EditText jenis_obat = i.findViewById(R.id.obat_jenis);
                        EditText dosis = i.findViewById(R.id.obat_dosis);
                        EditText waktu = i.findViewById(R.id.obat_waktu);

                        Map<String, String> i_map = new HashMap<>();
                        i_map.put("data_jenis", jenis_obat.getText().toString());
                        i_map.put("data_dosis", dosis.getText().toString());
                        i_map.put("data_waktu", waktu.getText().toString());
                        if (!TextUtils.isEmpty(jenis_obat.getText().toString()) || !TextUtils.isEmpty(dosis.getText().toString()) || !TextUtils.isEmpty(waktu.getText().toString()) ){
                            obat_kuning_array.add(i_map);
                        }
                    }
                    data_obat_kuning = obat_kuning_gson.toJson(obat_kuning_array);
                }

                String mpd = merah_peakflow_dari.getText().toString();
                String mpk = merah_peakflow_ke.getText().toString();
                String data_obat_merah = "[]";
                obat_merah_array.clear();
                if (obat_merah_form.size() > 0){
                    for (View i : obat_merah_form){
                        EditText jenis_obat = i.findViewById(R.id.obat_jenis);
                        EditText dosis = i.findViewById(R.id.obat_dosis);
                        EditText waktu = i.findViewById(R.id.obat_waktu);

                        Map<String, String> i_map = new HashMap<>();
                        i_map.put("data_jenis", jenis_obat.getText().toString());
                        i_map.put("data_dosis", dosis.getText().toString());
                        i_map.put("data_waktu", waktu.getText().toString());
                        if (!TextUtils.isEmpty(jenis_obat.getText().toString()) || !TextUtils.isEmpty(dosis.getText().toString()) || !TextUtils.isEmpty(waktu.getText().toString()) ){
                            obat_merah_array.add(i_map);
                        }
                    }
                    data_obat_merah = obat_merah_gson.toJson(obat_merah_array);
                }

                //funct.notifikasiShow(data_obat_hijau+" "+data_obat_kuning+" "+data_obat_merah, "");

                updateRencana(session.getEmail(), session.getHash(), rencana_id, hpd, hpk, kpd, kpk, mpd, mpk, data_obat_hijau, data_obat_kuning, data_obat_merah);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        menu.findItem(R.id.tambah).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
            return true;

        }else if (id == R.id.logout){
            funct.logout();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void updateRencana(
            final String email,
            final String hash,
            String rencana_id,
            String hijau_peakflow_dari,
            String hijau_peakflow_ke,
            String kuning_peakflow_dari,
            String kuning_peakflow_ke,
            String merah_peakflow_dari,
            String merah_peakflow_ke,
            String data_obat_hijau,
            String data_obat_kuning,
            String data_obat_merah
    ){
        funct.loadingShow();
        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi", "update_rencana");
            parram.put("email", email);
            parram.put("hash", hash);
            parram.put("rencana_id", rencana_id);
            parram.put("hijau_peakflow_dari", hijau_peakflow_dari);
            parram.put("hijau_peakflow_ke", hijau_peakflow_ke);
            parram.put("kuning_peakflow_dari", kuning_peakflow_dari);
            parram.put("kuning_peakflow_ke", kuning_peakflow_ke);
            parram.put("merah_peakflow_dari", merah_peakflow_dari);
            parram.put("merah_peakflow_ke", merah_peakflow_ke);
            parram.put("data_obat_hijau", data_obat_hijau);
            parram.put("data_obat_kuning", data_obat_kuning);
            parram.put("data_obat_merah", data_obat_merah);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server) + "rencana_aksi_asma/home.php", parram, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Boolean status = response.getBoolean("status");

                    if (status){
                        JSONObject data = new JSONObject(response.getString("data"));
                        JSONObject info = new JSONObject(data.getString("info"));

                        String error = info.getString("error");
                        String detail = info.getString("detail");
                        String link = info.getString("link");
                        Boolean login = data.getBoolean("login");
                        String rencana_id = data.getString("rencana_id");

                        if(error.equals("1")) {

                            if (login){

                                if(!TextUtils.isEmpty(detail)) {
                                    funct.notifikasiDismisable(root_layout,detail);
                                }
                                Intent intent = new Intent(RencanaAksiAsmaBuatZona.this, RencanaAksiAsma.class);
                                startActivity(intent);
                                finish();

                            }else {
                                funct.logout();
                            }

                        }else if(error.equals("2")) {
                            funct.notifikasiShow(detail, link);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                funct.loadingHide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                funct.loadingHide();
                funct.notifikasiDismisable(root_layout,"Pastikan internet anda aktif dan coba kembali");
            }
        });
//        {
//            @Override
//            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                try {
//                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
//                    Log.d("Rencana ", jsonString);
//                    System.out.println("Rencana " + jsonString);
//                    return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
//                } catch (UnsupportedEncodingException e) {
//                    return Response.error(new ParseError(e));
//                } catch (JSONException je) {
//                    return Response.error(new ParseError(je));
//                }
//            }
//        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}