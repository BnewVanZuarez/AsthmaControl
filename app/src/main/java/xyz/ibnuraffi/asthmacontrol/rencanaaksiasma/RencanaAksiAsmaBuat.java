package xyz.ibnuraffi.asthmacontrol.rencanaaksiasma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import xyz.ibnuraffi.asthmacontrol.R;
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
                Intent intent = new Intent(RencanaAksiAsmaBuat.this, RencanaAksiAsmaBuatZona.class);
                startActivity(intent);
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
}