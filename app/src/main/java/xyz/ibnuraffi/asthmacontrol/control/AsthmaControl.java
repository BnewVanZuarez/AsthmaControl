package xyz.ibnuraffi.asthmacontrol.control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.radiobutton.MaterialRadioButton;

import cn.pedant.SweetAlert.SweetAlertDialog;
import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class AsthmaControl extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    private MaterialRadioButton pertanyaan_1_a;
    private MaterialRadioButton pertanyaan_1_b;
    private MaterialRadioButton pertanyaan_1_c;
    private MaterialRadioButton pertanyaan_1_d;
    private MaterialRadioButton pertanyaan_1_e;

    private MaterialRadioButton pertanyaan_2_a;
    private MaterialRadioButton pertanyaan_2_b;
    private MaterialRadioButton pertanyaan_2_c;
    private MaterialRadioButton pertanyaan_2_d;
    private MaterialRadioButton pertanyaan_2_e;

    private MaterialRadioButton pertanyaan_3_a;
    private MaterialRadioButton pertanyaan_3_b;
    private MaterialRadioButton pertanyaan_3_c;
    private MaterialRadioButton pertanyaan_3_d;
    private MaterialRadioButton pertanyaan_3_e;

    private MaterialRadioButton pertanyaan_4_a;
    private MaterialRadioButton pertanyaan_4_b;
    private MaterialRadioButton pertanyaan_4_c;
    private MaterialRadioButton pertanyaan_4_d;
    private MaterialRadioButton pertanyaan_4_e;

    private MaterialRadioButton pertanyaan_5_a;
    private MaterialRadioButton pertanyaan_5_b;
    private MaterialRadioButton pertanyaan_5_c;
    private MaterialRadioButton pertanyaan_5_d;
    private MaterialRadioButton pertanyaan_5_e;

    private Button btn_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asthma_control);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Asthma Control");

        pertanyaan_1_a = findViewById(R.id.pertanyaan_1_a);
        pertanyaan_1_b = findViewById(R.id.pertanyaan_1_b);
        pertanyaan_1_c = findViewById(R.id.pertanyaan_1_c);
        pertanyaan_1_d = findViewById(R.id.pertanyaan_1_d);
        pertanyaan_1_e = findViewById(R.id.pertanyaan_1_e);

        pertanyaan_2_a = findViewById(R.id.pertanyaan_2_a);
        pertanyaan_2_b = findViewById(R.id.pertanyaan_2_b);
        pertanyaan_2_c = findViewById(R.id.pertanyaan_2_c);
        pertanyaan_2_d = findViewById(R.id.pertanyaan_2_d);
        pertanyaan_2_e = findViewById(R.id.pertanyaan_2_e);

        pertanyaan_3_a = findViewById(R.id.pertanyaan_3_a);
        pertanyaan_3_b = findViewById(R.id.pertanyaan_3_b);
        pertanyaan_3_c = findViewById(R.id.pertanyaan_3_c);
        pertanyaan_3_d = findViewById(R.id.pertanyaan_3_d);
        pertanyaan_3_e = findViewById(R.id.pertanyaan_3_e);

        pertanyaan_4_a = findViewById(R.id.pertanyaan_4_a);
        pertanyaan_4_b = findViewById(R.id.pertanyaan_4_b);
        pertanyaan_4_c = findViewById(R.id.pertanyaan_4_c);
        pertanyaan_4_d = findViewById(R.id.pertanyaan_4_d);
        pertanyaan_4_e = findViewById(R.id.pertanyaan_4_e);

        pertanyaan_5_a = findViewById(R.id.pertanyaan_5_a);
        pertanyaan_5_b = findViewById(R.id.pertanyaan_5_b);
        pertanyaan_5_c = findViewById(R.id.pertanyaan_5_c);
        pertanyaan_5_d = findViewById(R.id.pertanyaan_5_d);
        pertanyaan_5_e = findViewById(R.id.pertanyaan_5_e);

        btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nilai_1 = 0;
                int nilai_2 = 0;
                int nilai_3 = 0;
                int nilai_4 = 0;
                int nilai_5 = 0;
                int total = 0;
                if (pertanyaan_1_a.isChecked()){
                    nilai_1 = 1;
                }else if (pertanyaan_1_b.isChecked()){
                    nilai_1 = 2;
                }else if (pertanyaan_1_c.isChecked()){
                    nilai_1 = 3;
                }else if (pertanyaan_1_d.isChecked()){
                    nilai_1 = 4;
                }else if (pertanyaan_1_e.isChecked()){
                    nilai_1 = 5;
                }
                if (pertanyaan_2_a.isChecked()){
                    nilai_2 = 1;
                }else if (pertanyaan_2_b.isChecked()){
                    nilai_2 = 2;
                }else if (pertanyaan_2_c.isChecked()){
                    nilai_2 = 3;
                }else if (pertanyaan_2_d.isChecked()){
                    nilai_2 = 4;
                }else if (pertanyaan_2_e.isChecked()){
                    nilai_2 = 5;
                }
                if (pertanyaan_3_a.isChecked()){
                    nilai_3 = 1;
                }else if (pertanyaan_3_b.isChecked()){
                    nilai_3 = 2;
                }else if (pertanyaan_3_c.isChecked()){
                    nilai_3 = 3;
                }else if (pertanyaan_3_d.isChecked()){
                    nilai_3 = 4;
                }else if (pertanyaan_3_e.isChecked()){
                    nilai_3 = 5;
                }
                if (pertanyaan_4_a.isChecked()){
                    nilai_4 = 1;
                }else if (pertanyaan_4_b.isChecked()){
                    nilai_4 = 2;
                }else if (pertanyaan_4_c.isChecked()){
                    nilai_4 = 3;
                }else if (pertanyaan_4_d.isChecked()){
                    nilai_4 = 4;
                }else if (pertanyaan_4_e.isChecked()){
                    nilai_4 = 5;
                }
                if (pertanyaan_5_a.isChecked()){
                    nilai_5 = 1;
                }else if (pertanyaan_5_b.isChecked()){
                    nilai_5 = 2;
                }else if (pertanyaan_5_c.isChecked()){
                    nilai_5 = 3;
                }else if (pertanyaan_5_d.isChecked()){
                    nilai_5 = 4;
                }else if (pertanyaan_5_e.isChecked()){
                    nilai_5 = 5;
                }
                total = nilai_1 + nilai_2 + nilai_3 + nilai_4 + nilai_5;

                if (total == 25){
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(AsthmaControl.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Selamat !")
                            .setContentText("Anda sudah mencapai TOTAL KONTROL Anda tidak lagi mengalami gejala asma dan tidak ada keterbatasan aktivitas karena asma.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            });
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.show();

                }else if (total >= 20 && total <= 24){
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(AsthmaControl.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("On Target !")
                            .setContentText("Asma Anda Terkontrol Dengan Baik tetapi belum mencapai TOTAL KONTROL.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            });
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.show();

                }else if (total <= 19){
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(AsthmaControl.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Off Target !")
                            .setContentText("Asma anda Belum Terkontrol. Dokter & perawat Anda akan merekomendasikan program penatalaksanaan asma untuk meningkatkan kontrol asma anda")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            });
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.show();

                }
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