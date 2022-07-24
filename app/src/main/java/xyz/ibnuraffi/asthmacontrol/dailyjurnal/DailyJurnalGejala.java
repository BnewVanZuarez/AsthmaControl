package xyz.ibnuraffi.asthmacontrol.dailyjurnal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.button.MaterialButton;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class DailyJurnalGejala extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    private CheckBox gejala1;
    private CheckBox gejala2;
    private CheckBox gejala3;
    private CheckBox gejala4;
    private CheckBox gejala5;
    private CheckBox gejala6;
    private CheckBox gejala7;
    private MaterialButton btn_simpan;

    String gejala = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_jurnal_gejala);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gejala");
        gejala1 = findViewById(R.id.gejala1);
        gejala2 = findViewById(R.id.gejala2);
        gejala3 = findViewById(R.id.gejala3);
        gejala4 = findViewById(R.id.gejala4);
        gejala5 = findViewById(R.id.gejala5);
        gejala6 = findViewById(R.id.gejala6);
        gejala7 = findViewById(R.id.gejala7);
        btn_simpan = findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gejala1.isChecked())
                    gejala = gejala + gejala1.getText().toString() + ", ";
                if (gejala2.isChecked())
                    gejala = gejala + gejala2.getText().toString() + ", ";
                if (gejala3.isChecked())
                    gejala = gejala + gejala3.getText().toString() + ", ";
                if (gejala4.isChecked())
                    gejala = gejala + gejala4.getText().toString() + ", ";
                if (gejala5.isChecked())
                    gejala = gejala + gejala5.getText().toString() + ", ";
                if (gejala6.isChecked())
                    gejala = gejala + gejala6.getText().toString() + ", ";
                if (gejala7.isChecked())
                    gejala = gejala + gejala7.getText().toString();

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                returnIntent.putExtra("gejala", gejala);
                finish();
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