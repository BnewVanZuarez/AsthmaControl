package xyz.ibnuraffi.asthmacontrol.rencanaaksiasma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.tanyajawab.TanyaJawabChats;
import xyz.ibnuraffi.asthmacontrol.tanyajawab.TanyaJawabTambah;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class RencanaAksiAsmaBuat extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    private TextInputEditText input_nama;
    private TextInputEditText input_telp;
    private TextInputEditText input_kontak_darurat;
    private TextInputEditText input_telp_darurat;

    private TextInputEditText input_obat;
    private TextInputEditText input_dosis;
    private TextInputEditText input_digunakan;
    private TextInputEditText input_instruksi;

    private CheckBox pemicu1;
    private CheckBox pemicu2;
    private CheckBox pemicu3;
    private CheckBox pemicu4;
    private CheckBox pemicu5;
    private CheckBox pemicu6;
    private CheckBox pemicu7;
    private CheckBox pemicu8;
    private CheckBox pemicu9;
    private CheckBox pemicu10;
    private CheckBox pemicu11;

    private MaterialButton btn_selanjutnya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rencana_aksi_asma_buat);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rencana Aksi Asma");

        input_nama = findViewById(R.id.input_nama);
        input_telp = findViewById(R.id.input_telp);
        input_kontak_darurat = findViewById(R.id.input_kontak_darurat);
        input_telp_darurat = findViewById(R.id.input_telp_darurat);

        input_obat = findViewById(R.id.input_obat);
        input_dosis = findViewById(R.id.input_dosis);
        input_digunakan = findViewById(R.id.input_digunakan);
        input_instruksi = findViewById(R.id.input_instruksi);

        pemicu1 = findViewById(R.id.pemicu1);
        pemicu2 = findViewById(R.id.pemicu2);
        pemicu3 = findViewById(R.id.pemicu3);
        pemicu4 = findViewById(R.id.pemicu4);
        pemicu5 = findViewById(R.id.pemicu5);
        pemicu6 = findViewById(R.id.pemicu6);
        pemicu7 = findViewById(R.id.pemicu7);
        pemicu8 = findViewById(R.id.pemicu8);
        pemicu9 = findViewById(R.id.pemicu9);
        pemicu10 = findViewById(R.id.pemicu10);
        pemicu11 = findViewById(R.id.pemicu11);

        btn_selanjutnya = findViewById(R.id.btn_selanjutnya);
        btn_selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm = input_nama.getText().toString();
                String tp = input_telp.getText().toString();
                String kd = input_kontak_darurat.getText().toString();
                String td = input_telp_darurat.getText().toString();
                String ob = input_obat.getText().toString();
                String ds = input_dosis.getText().toString();
                String dg = input_digunakan.getText().toString();
                String it = input_instruksi.getText().toString();
                String pc = "";
                if (pemicu1.isChecked())
                    pc = pc+ pemicu1.getText().toString() + ", ";
                if (pemicu2.isChecked())
                    pc = pc+ pemicu2.getText().toString() + ", ";
                if (pemicu3.isChecked())
                    pc = pc+ pemicu3.getText().toString() + ", ";
                if (pemicu4.isChecked())
                    pc = pc+ pemicu4.getText().toString() + ", ";
                if (pemicu5.isChecked())
                    pc = pc+ pemicu5.getText().toString() + ", ";
                if (pemicu6.isChecked())
                    pc = pc+ pemicu6.getText().toString() + ", ";
                if (pemicu7.isChecked())
                    pc = pc+ pemicu7.getText().toString() + ", ";
                if (pemicu8.isChecked())
                    pc = pc+ pemicu8.getText().toString() + ", ";
                if (pemicu9.isChecked())
                    pc = pc+ pemicu9.getText().toString() + ", ";
                if (pemicu10.isChecked())
                    pc = pc+ pemicu10.getText().toString() + ", ";
                if (pemicu11.isChecked())
                    pc = pc+ pemicu11.getText().toString() + ".";

                inputRencana(session.getEmail(), session.getHash(), nm, tp, kd, td, ob, ds, dg, it, pc);
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

    public void inputRencana(
            final String email,
            final String hash,
            String nama_dokter,
            String telp_dokter,
            String kontak_darurat,
            String telp_darurat,
            String nama_obat,
            String dosis_obat,
            String digunakan_saat,
            String instruksi_tambahan,
            String pemicu
    ){
        funct.loadingShow();
        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi", "buat_rencana");
            parram.put("email", email);
            parram.put("hash", hash);
            parram.put("nama_dokter", nama_dokter);
            parram.put("telp_dokter", telp_dokter);
            parram.put("kontak_darurat", kontak_darurat);
            parram.put("telp_darurat", telp_darurat);
            parram.put("nama_obat", nama_obat);
            parram.put("dosis_obat", dosis_obat);
            parram.put("digunakan_saat", digunakan_saat);
            parram.put("instruksi_tambahan", instruksi_tambahan);
            parram.put("pemicu", pemicu);
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
                                Intent intent = new Intent(RencanaAksiAsmaBuat.this, RencanaAksiAsmaBuatZona.class);
                                intent.putExtra("rencana_id", rencana_id);
                                startActivity(intent);

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
//                    Log.d("Input Obat ", jsonString);
//                    System.out.println("Input Obat " + jsonString);
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