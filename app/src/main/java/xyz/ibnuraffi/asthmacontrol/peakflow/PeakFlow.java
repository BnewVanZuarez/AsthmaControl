package xyz.ibnuraffi.asthmacontrol.peakflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObatAdapter;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObatModel;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.EndlessScrollListener;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class PeakFlow extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    private FragmentAdapter fragmentAdapter;
    public BottomNavigationView nav_pager;
    public ViewPager view_pager; // bottom navigation
    public Boolean view_pager_all_create = false;;

    // Fragmen Satu
    public CoordinatorLayout peak_flow_fragment_1_root_layout;
    public ExtendedFloatingActionButton peak_flow_fragment_1_tambah;
    public ListView peak_flow_fragment_1_list_view;
    public PeakFlowAdapter peak_flow_fragment_1_adapter;
    public ArrayList<PeakFlowModel> peak_flow_fragment_1_model = new ArrayList<>();

    // Fragmen Dua
    public CoordinatorLayout peak_flow_fragment_2_root_layout;
    public BarChart peak_flow_fragment_2_chart;
    public BarData peak_flow_fragment_2_chartData = new BarData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peak_flow);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Peak Flow");

        //init menu atas
        nav_pager = findViewById(R.id.nav_pager);
        nav_pager.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_data:
                                view_pager.setCurrentItem(0);
                                return true;
                            case R.id.nav_chart:
                                view_pager.setCurrentItem(1);
                                return true;
                        }
                        return false;
                    }

                }
        );

        //init pager
        view_pager = findViewById(R.id.view_pager);
        view_pager.setOffscreenPageLimit(2);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        view_pager.setAdapter(fragmentAdapter);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                nav_pager.getMenu().getItem(position).setChecked(true);
                if (position == 0) {
                    daftarPeakFlowGetData();
                }else if (position == 1){
                    grafikPeakFlowGetData();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        daftarPeakFlowGetData();
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

        }else if (id == R.id.logout){
            funct.logout();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void daftarPeakFlowGetData(){
        daftarPeakFlow(session.getEmail(), session.getHash(), 0, 50);
    }

    public void daftarPeakFlow(final String email, final String hash, Integer startpoint, final int limit){
        if (startpoint == 0){
            funct.loadingShow();
            JSONObject parram = new JSONObject();
            try {
                parram.put("aksi", "peak_flow");
                parram.put("email", email);
                parram.put("hash", hash);
                parram.put("startpoint", startpoint);
                parram.put("limit", limit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server) + "peak_flow/home.php", parram, new Response.Listener<JSONObject>() {
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

                                    peak_flow_fragment_1_model.clear();
                                    peak_flow_fragment_1_model.addAll(PeakFlowModel.fromJson(data.optJSONArray("peakflow")));
                                    peak_flow_fragment_1_adapter.notifyDataSetChanged();

                                    final int info_num_rows = data.getInt("peakflow_num_rows");
                                    peak_flow_fragment_1_list_view.setOnScrollListener(new EndlessScrollListener(){
                                        @Override
                                        public boolean onLoadMore(int page, int totalItemsCount) {
                                            if (totalItemsCount < info_num_rows) {
                                                daftarPeakFlow(email, hash, (page - 1) * limit, limit);
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
                    funct.notifikasiDismisable(peak_flow_fragment_1_root_layout,"Pastikan internet anda aktif dan coba kembali");
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq);

        }else{

            funct.loadingShow();
            JSONObject parram = new JSONObject();
            try {
                parram.put("aksi", "peak_flow");
                parram.put("email", email);
                parram.put("hash", hash);
                parram.put("startpoint", startpoint);
                parram.put("limit", limit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server) + "peak_flow/home.php", parram, new Response.Listener<JSONObject>() {
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

                                    peak_flow_fragment_1_model.addAll(PeakFlowModel.fromJson(data.optJSONArray("peakflow")));
                                    peak_flow_fragment_1_adapter.notifyDataSetChanged();

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
                    funct.notifikasiDismisable(peak_flow_fragment_1_root_layout,"Pastikan internet anda aktif dan coba kembali");
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq);
        }
    }

    public void grafikPeakFlowGetData(){
        grafikPeakFlow(session.getEmail(), session.getHash());
    }

    public void grafikPeakFlow(final String email, final String hash){
        funct.loadingShow();
        //reset list/data
        peak_flow_fragment_2_chartData.clearValues();
        peak_flow_fragment_2_chart.invalidate();
        peak_flow_fragment_2_chart.clear();
        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi", "peak_flow_chart");
            parram.put("email", email);
            parram.put("hash", hash);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server) + "peak_flow/home.php", parram, new Response.Listener<JSONObject>() {
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

                                JSONArray chart = new JSONArray(data.getString("chart"));
                                if(chart.length() > 0) {
                                    for (int i = 0; i < chart.length(); i++) {

                                        JSONObject row = new JSONObject(chart.getString(i));
                                        String tanggal = row.getString("tanggal").toString().trim();
                                        String warna = row.getString("warna").toString().trim();
                                        final int nilai = row.getInt("nilai");
                                        //System.out.println("Loopingdata " + nama + "-" + total + "-" + warna + "-" + max);

                                        List<BarEntry> entries = new ArrayList<>();
                                        entries.add(new BarEntry(i, nilai));
                                        BarDataSet set = new BarDataSet(entries, tanggal);
                                        if(warna.equalsIgnoreCase("1")) {
                                            set.setColor(Color.RED);
                                        }else if(warna.equalsIgnoreCase("2")) {
                                            set.setColor(Color.YELLOW);
                                        }else if(warna.equalsIgnoreCase("3")) {
                                            set.setColor(Color.GREEN);
                                        }
                                        peak_flow_fragment_2_chartData.addDataSet(set);
                                    }

                                    peak_flow_fragment_2_chart.setData(peak_flow_fragment_2_chartData);
                                    peak_flow_fragment_2_chart.setFitBars(true); // make the x-axis fit exactly all bars
                                    peak_flow_fragment_2_chart.invalidate(); // refresh
                                }

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
                funct.notifikasiDismisable(peak_flow_fragment_1_root_layout,"Pastikan internet anda aktif dan coba kembali");
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}