package xyz.ibnuraffi.asthmacontrol.dailyjurnal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObat;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObatAdapter;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObatModel;
import xyz.ibnuraffi.asthmacontrol.edukasi.Edukasi;
import xyz.ibnuraffi.asthmacontrol.home.MainActivity;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.EndlessScrollListener;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class DailyJurnal extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    //list data
    private ListView list_view;
    private DailyJurnalAdapter adapter;
    private ArrayList<DailyJurnalModel> model = new ArrayList<>();

    private ExtendedFloatingActionButton tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_jurnal);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daily Jurnal");

        list_view = findViewById(R.id.list_view);
        adapter = new DailyJurnalAdapter(DailyJurnal.this, model);
        list_view.setAdapter(adapter);

        tambah = findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyJurnal.this, DailyJurnalTambah.class);
                startActivity(intent);
            }
        });

        dailyJurnal(session.getEmail(), session.getHash(), 0, 50);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dailyJurnal(session.getEmail(), session.getHash(), 0, 50);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
            return true;

        }else if (id == R.id.tambah){
            funct.notifikasiDismisable(root_layout, "Tembah");
            return true;

        }else if (id == R.id.logout){
            funct.logout();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void dailyJurnal(final String email, final String hash, Integer startpoint, final int limit){
        if (startpoint == 0){
            funct.loadingShow();
            JSONObject parram = new JSONObject();
            try {
                parram.put("aksi", "daily_jurnal");
                parram.put("email", email);
                parram.put("hash", hash);
                parram.put("startpoint", startpoint);
                parram.put("limit", limit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server) + "daily_jurnal/home.php", parram, new Response.Listener<JSONObject>() {
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

                                    model.clear();
                                    model.addAll(DailyJurnalModel.fromJson(data.optJSONArray("daily")));
                                    adapter.notifyDataSetChanged();

                                    final int daily_num_rows = data.getInt("daily_num_rows");
                                    list_view.setOnScrollListener(new EndlessScrollListener(){
                                        @Override
                                        public boolean onLoadMore(int page, int totalItemsCount) {
                                            if (totalItemsCount < daily_num_rows) {
                                                dailyJurnal(email, hash, (page - 1) * limit, limit);
                                                return true;
                                            }
                                            return false;
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

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq);

        }else{
            funct.loadingShow();
            JSONObject parram = new JSONObject();
            try {
                parram.put("aksi", "daily_jurnal");
                parram.put("email", email);
                parram.put("hash", hash);
                parram.put("startpoint", startpoint);
                parram.put("limit", limit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server) + "daily_jurnal/home.php", parram, new Response.Listener<JSONObject>() {
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

                                    model.addAll(DailyJurnalModel.fromJson(data.optJSONArray("daily")));
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
    }

}