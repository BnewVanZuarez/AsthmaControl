package xyz.ibnuraffi.asthmacontrol.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import xyz.ibnuraffi.asthmacontrol.home.MainActivity;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class AuthLogin extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private TextInputEditText email_txt;
    private TextInputEditText paswd_txt;
    private Button btn_masuk;
    private Button btn_daftar;
    private Button btn_reset;
    String error = "";
    String notifikasi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        email_txt = findViewById(R.id.email);
        paswd_txt = findViewById(R.id.password);
        btn_masuk = findViewById(R.id.btn_masuk);
        btn_daftar = findViewById(R.id.btn_daftar);
        btn_reset = findViewById(R.id.btn_reset);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            error = bundle.getString("error");
            notifikasi = bundle.getString("notifikasi");
            funct.notifikasiDismisable(root_layout, notifikasi);
        }

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email_txt.getText().toString().trim();
                String pw = paswd_txt.getText().toString().trim();
                login(em, pw);
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthLogin.this, AuthRegister.class);
                startActivity(intent);
                finish();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthLogin.this, AuthLupa.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        funct.closeApp();
    }

    private void login(String email, String password){

        funct.loadingShow();

        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi","login");
            parram.put("email",email);
            parram.put("password",password);
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

                                String error  = info.getString("error");
                                String detail = info.getString("detail");
                                String link   = info.getString("link");

                                if(error.equals("2")) {

                                    funct.notifikasiShow(detail, link);

                                }else{
                                    String hash = data.getString("hash");

                                    session.setEmail(email);
                                    session.setHash(hash);

                                    Intent intent = new Intent(AuthLogin.this, MainActivity.class);
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