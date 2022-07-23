package xyz.ibnuraffi.asthmacontrol.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.home.MainActivity;
import xyz.ibnuraffi.asthmacontrol.home.MainActivityEdukasi;
import xyz.ibnuraffi.asthmacontrol.home.MainActivityEdukasiAdapter;
import xyz.ibnuraffi.asthmacontrol.home.MainActivityEdukasiModel;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;
import xyz.ibnuraffi.asthmacontrol.webview.WebView;

public class Profile extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    private TextInputEditText input_email;
    private TextInputEditText input_password;
    private TextInputEditText input_nama;
    private TextInputEditText input_notelp;
    private MaterialButton btn_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil");

        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_nama = findViewById(R.id.input_nama);
        input_notelp = findViewById(R.id.input_notelp);
        btn_simpan = findViewById(R.id.btn_simpan);

        cekLogin(session.getEmail(), session.getHash());
    }

    @Override
    protected void onResume() {
        super.onResume();
        cekLogin(session.getEmail(), session.getHash());
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
                                        input_email.setText(login_data.getString("email"));
                                        input_nama.setText(login_data.getString("nama_lengkap"));
                                        input_notelp.setText(login_data.getString("no_telp"));

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