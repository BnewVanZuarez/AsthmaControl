package xyz.ibnuraffi.asthmacontrol.tanyajawab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObatTambah;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.EndlessScrollListener;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class TanyaJawabChats extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    //list data
    private ListView list_view;
    private TanyaJawabChatsAdapter adapter;
    private ArrayList<TanyaJawabChatsModel> model = new ArrayList<>();

    private String tiket_id = "";

    private EditText text_content;
    private ImageView btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tanya_jawab_chats);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kode");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            tiket_id = bundle.getString("tiket_id");
            chats(session.getEmail(), session.getHash(), tiket_id);
        }

        list_view = findViewById(R.id.list_view);
        adapter = new TanyaJawabChatsAdapter(TanyaJawabChats.this, model);
        list_view.setAdapter(adapter);

        text_content = findViewById(R.id.text_content);
        btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tc = text_content.getText().toString();
                kirimChat(session.getEmail(), session.getHash(), tiket_id, tc);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        chats(session.getEmail(), session.getHash(), tiket_id);
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

    public void chats(final String email, final String hash, String tiket_id){
        funct.loadingShow();
        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi", "daftar_pesan");
            parram.put("email", email);
            parram.put("hash", hash);
            parram.put("tiket_id", tiket_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server) + "tanya_jawab/home.php", parram, new Response.Listener<JSONObject>() {
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

                        if(error.equals("1")) {
                            if (login){

                                JSONObject detail_chat = new JSONObject(data.getString("detail"));
                                getSupportActionBar().setTitle("#"+detail_chat.getString("no_tiket"));

                                model.clear();
                                model.addAll(TanyaJawabChatsModel.fromJson(data.optJSONArray("riwayat")));
                                adapter.notifyDataSetChanged();

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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void kirimChat(final String email, final String hash, String tiket_id, String pesan){
        funct.loadingShow();
        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi", "kirim_pesan");
            parram.put("email", email);
            parram.put("hash", hash);
            parram.put("tiket_id", tiket_id);
            parram.put("pesan", pesan);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server) + "tanya_jawab/home.php", parram, new Response.Listener<JSONObject>() {
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

                        if(error.equals("1")) {

                            if (login){

                                text_content.setText("");
                                chats(session.getEmail(), session.getHash(), tiket_id);

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