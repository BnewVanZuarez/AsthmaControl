package xyz.ibnuraffi.asthmacontrol.rumahsakit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.edukasi.EdukasiModel;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.EndlessScrollListener;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class RumahSakit extends AppCompatActivity implements OnMapReadyCallback {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    private GoogleMap googleMap;
    private SupportMapFragment map_fragment;
    private LatLng latLng;
    private LocationRequest locationRequest;
    private Boolean zoom = true;
    private LocationManager locationManager;
    private FusedLocationProviderClient mFusedLocationClient;

    BitmapDescriptor bitmapMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rumah_sakit);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rumah Sakit");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        map_fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        map_fragment.getMapAsync(this);
        daftarRumahSakit(session.getEmail(), session.getHash());
        funct.setPermission(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        daftarRumahSakit(session.getEmail(), session.getHash());
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

    public void daftarRumahSakit(final String email, final String hash){
        funct.loadingShow();
        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi", "rumah_sakit");
            parram.put("email", email);
            parram.put("hash", hash);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server) + "rumah_sakit/home.php", parram, new Response.Listener<JSONObject>() {
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

                                JSONArray rumahsakit = new JSONArray(data.getString("rumahsakit"));
                                googleMap.clear();
                                if(rumahsakit.length() > 0) {
                                    for (int i = 0; i < rumahsakit.length(); i++) {
                                        JSONObject row = new JSONObject(rumahsakit.getString(i));
                                        final String id = row.getString("id").toString().trim();
                                        final String nama = row.getString("nama").toString().trim();
                                        final String alamat = row.getString("alamat").toString().trim();
                                        final double latitude = row.getDouble("latitude");
                                        final double longitude = row.getDouble("longitude");

                                        bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                                        googleMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(latitude,longitude))
                                                .anchor(0.5f, 0.5f)
                                                .title(nama)
                                                .snippet(alamat)
                                                .icon(bitmapMarker));

                                    }
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
                funct.notifikasiDismisable(root_layout,"Pastikan internet anda aktif dan coba kembali");
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap gm) {
        googleMap = gm;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.8108235,110.3218609), 15.0f));
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                //get latlng at the center by calling
                latLng = googleMap.getCameraPosition().target;
            }
        });
        try {
            googleMap.setMyLocationEnabled(true);
        }catch (SecurityException e){
            e.printStackTrace();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }
}