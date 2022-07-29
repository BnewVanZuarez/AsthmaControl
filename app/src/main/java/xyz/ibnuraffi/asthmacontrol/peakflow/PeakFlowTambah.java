package xyz.ibnuraffi.asthmacontrol.peakflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.utils.AppController;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class PeakFlowTambah extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    private TextInputEditText input_tanggal;
    private TextInputEditText input_nilai;
    private RadioGroup group_warna;
    private MaterialRadioButton warna_merah;
    private MaterialRadioButton warna_kuning;
    private MaterialRadioButton warna_hijau;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private MaterialButton btn_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peak_flow_tambah);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah");

        Locale id = new Locale("in", "ID");
        String pattern = "yyyy-MM-dd";
        dateFormatter = new SimpleDateFormat(pattern, id);

        input_tanggal = findViewById(R.id.input_tanggal);
        input_nilai = findViewById(R.id.input_nilai);
        group_warna = findViewById(R.id.group_warna);
        warna_merah = findViewById(R.id.warna_merah);
        warna_kuning = findViewById(R.id.warna_kuning);
        warna_hijau = findViewById(R.id.warna_hijau);
        btn_simpan = findViewById(R.id.btn_simpan);

        input_tanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (input_tanggal.hasFocus()){
                    showDateDialog();
                }
            }
        });
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tgl = input_tanggal.getText().toString();
                String nil = input_nilai.getText().toString();
                String wrn = "";
                if (warna_merah.isChecked()){
                    wrn = "1";
                }else if (warna_kuning.isChecked()){
                    wrn = "2";
                }else if (warna_hijau.isChecked()){
                    wrn = "3";
                }
                inputPeakFlow(session.getEmail(), session.getHash(), tgl, nil, wrn);
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

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                input_tanggal.setText(dateFormatter.format(newDate.getTime()));
                input_nilai.requestFocus();
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void inputPeakFlow(final String email, final String hash, String tanggal, String nilai, String warna){
        funct.loadingShow();
        JSONObject parram = new JSONObject();
        try {
            parram.put("aksi", "input_peak_flow");
            parram.put("email", email);
            parram.put("hash", hash);
            parram.put("tanggal", tanggal);
            parram.put("nilai", nilai);
            parram.put("warna", warna);
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

                                if(!TextUtils.isEmpty(detail)) {
                                    funct.notifikasiDismisable(root_layout,detail);
                                }
                                onBackPressed();

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