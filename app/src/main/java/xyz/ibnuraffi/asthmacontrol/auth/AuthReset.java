package xyz.ibnuraffi.asthmacontrol.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class AuthReset extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private TextInputEditText kode_verif;
    private TextInputEditText password;
    private TextInputEditText konfirmasi;
    private Button btn_ganti_password;
    private Button btn_login;
    String error = "";
    String notifikasi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_reset);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        kode_verif = findViewById(R.id.kode_verif);
        password = findViewById(R.id.password);
        konfirmasi = findViewById(R.id.konfirmasi);
        btn_ganti_password = findViewById(R.id.btn_ganti_password);
        btn_login = findViewById(R.id.btn_login);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            error = bundle.getString("error");
            notifikasi = bundle.getString("notifikasi");
            funct.notifikasiDismisable(root_layout, notifikasi);
        }

        btn_ganti_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kv = kode_verif.getText().toString();
                String pw = password.getText().toString();
                String kf = konfirmasi.getText().toString();

                if(!TextUtils.isEmpty(kv) && !TextUtils.isEmpty(pw) && !TextUtils.isEmpty(kf) ) {
                    resetPassword(kv, pw, kf);
                }else{
                    funct.notifikasiDismisable(root_layout, "Silahkan lengkapi form !");
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthReset.this, AuthLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        funct.closeApp();
    }

    private void resetPassword(String kode, String password, String konfirmasi){

        funct.loadingShow();

        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi","reset_password");
            parram.put("kode",kode);
            parram.put("password",password);
            parram.put("konfirmasi",konfirmasi);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,getResources().getString(R.string.server) + "auth/home.php", parram,
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

                                if(error.equals("2")) {

                                    funct.notifikasiShow(detail, link);

                                }else{

                                    Intent intent = new Intent(AuthReset.this, AuthLogin.class);
                                    intent.putExtra("error", "1");
                                    intent.putExtra("notifikasi", "Berhasil memperbarui Password, silahkan login");
                                    startActivity(intent);
                                    finish();

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
                funct.notifikasiDismisable(root_layout, "Pastikan internet anda aktif dan coba kembali");
            }
        });
//        {
//            @Override
//            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                try {
//                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
//                    Log.d("dasdasdas", jsonString);
//                    System.out.println("Login " + jsonString);
//                    return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
//                } catch (UnsupportedEncodingException e) {
//                    return Response.error(new ParseError(e));
//                } catch (JSONException je) {
//                    return Response.error(new ParseError(je));
//                }
//            }
//        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}