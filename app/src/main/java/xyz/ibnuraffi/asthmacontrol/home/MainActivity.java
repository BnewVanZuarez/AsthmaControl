package xyz.ibnuraffi.asthmacontrol.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObat;
import xyz.ibnuraffi.asthmacontrol.dailyjurnal.DailyJurnal;
import xyz.ibnuraffi.asthmacontrol.edukasi.Edukasi;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;
import xyz.ibnuraffi.asthmacontrol.webview.WebView;

public class MainActivity extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;

    private ImageView btn_profile;
    private ImageView btn_logout;
    private TextView nama_lengkap;
    private TextView greeting;

    private LinearLayout btn_daily_jurnal;
    private LinearLayout btn_asthma_control;
    private LinearLayout btn_peak_flow;
    private LinearLayout btn_rencana_aksi_asthma;
    private LinearLayout btn_tanya_jawab;
    private LinearLayout btn_daftar_obat;

    private TextView semua_edukasi;
    public RecyclerView rv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        btn_profile = findViewById(R.id.btn_profile);
        btn_logout = findViewById(R.id.btn_logout);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        greeting = findViewById(R.id.greeting);

        btn_daily_jurnal = findViewById(R.id.btn_daily_jurnal);
        btn_asthma_control = findViewById(R.id.btn_asthma_control);
        btn_peak_flow = findViewById(R.id.btn_peak_flow);
        btn_rencana_aksi_asthma = findViewById(R.id.btn_rencana_aksi_asthma);
        btn_tanya_jawab = findViewById(R.id.btn_tanya_jawab);
        btn_daftar_obat = findViewById(R.id.btn_daftar_obat);

        semua_edukasi = findViewById(R.id.semua_edukasi);

        rv_view = findViewById(R.id.rv_view);
        rv_view.setHasFixedSize(false);
        rv_view.setNestedScrollingEnabled(true);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funct.notifikasiDismisable(root_layout, "Profile");
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funct.logout();
            }
        });
        btn_daily_jurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //funct.notifikasiDismisable(root_layout, "Daily Jurnal");
                Intent intent = new Intent(MainActivity.this, DailyJurnal.class);
                startActivity(intent);
            }
        });
        btn_asthma_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funct.notifikasiDismisable(root_layout, "Asthma Control");
            }
        });
        btn_peak_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funct.notifikasiDismisable(root_layout, "Peak Flow");
            }
        });
        btn_rencana_aksi_asthma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funct.notifikasiDismisable(root_layout, "Rencana Aksi Asthma");
            }
        });
        btn_tanya_jawab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funct.notifikasiDismisable(root_layout, "Tanya Jawab");
            }
        });
        btn_daftar_obat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DaftarObat.class);
                startActivity(intent);
            }
        });

        semua_edukasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Edukasi.class);
                startActivity(intent);
            }
        });

        cekLogin(session.getEmail(), session.getHash());
    }

    @Override
    public void onBackPressed() {
        funct.closeApp();
    }

    private void cekLogin(String email, String hash){
        funct.loadingShow();
        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi","cek_login");
            parram.put("email",email);
            parram.put("hash",hash);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,getResources().getString(R.string.server) + "home/home.php", parram,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean("status");

                            if(status) {
                                JSONObject data = new JSONObject(response.getString("data"));
                                JSONObject info = new JSONObject(data.getString("info"));

                                String error = info.getString("error");
                                String detail = info.getString("detail");
                                String link = info.getString("link");
                                Boolean login = data.getBoolean("login");

                                if(error.equals("1")) {
                                    if (login){

                                        JSONObject login_data = new JSONObject(data.getString("login_data"));
                                        JSONArray edukasi_content = new JSONArray(data.getString("edukasi"));

                                        nama_lengkap.setText("Hi, "+login_data.getString("nama_lengkap"));
                                        greeting.setText(data.getString("greeting"));

                                        //ArrayList<BeritaModel> array = BeritaModel.fromJson(data.optJSONArray("slider"));
                                        ArrayList<MainActivityEdukasi> list = new ArrayList<>();
                                        list.addAll(MainActivityEdukasiModel.fromJson(edukasi_content));
                                        rv_view.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false));
                                        MainActivityEdukasiAdapter homeBeritaAdapter = new MainActivityEdukasiAdapter(list);
                                        rv_view.setAdapter(homeBeritaAdapter);
                                        homeBeritaAdapter.setOnItemClickCallback(new MainActivityEdukasiAdapter.OnItemClickCallback() {
                                            @Override
                                            public void onItemClicked(MainActivityEdukasi data) {
                                                String slug = data.getSlug();
                                                Intent intent = new Intent(MainActivity.this, WebView.class);
                                                intent.putExtra("url", getResources().getString(R.string.webview) + "index.php?pages=edukasi&sub_page=depan&slug="+slug);
                                                intent.putExtra("judul", "Edukasi");
                                                startActivity(intent);
                                            }
                                        });

                                    }else{
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
//                    Log.d("Home ", jsonString);
//                    System.out.println("Home "+jsonString);
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