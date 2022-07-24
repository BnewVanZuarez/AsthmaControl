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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;
import xyz.ibnuraffi.asthmacontrol.utils.SpinnerModel;

public class DailyJurnalTambah extends AppCompatActivity {

    private Funct funct;
    private Session session;
    private CoordinatorLayout root_layout;
    private Toolbar toolbar;

    private TextView date_now;
    private SeekBar seekBar_today;
    private TextView today_rate;
    private SeekBar seekBar_pain;
    private TextView pain_rate;
    private Spinner mood;
    private RadioGroup radio_gejala;
    private RadioButton gejala_ya;
    private RadioButton gejala_tidak;
    private RadioButton radioGejala;
    private LinearLayout gejala;
    private String gejala_value = "";
    private TextView gejala_text;
    private RadioGroup radio_paparan;
    private RadioButton paparan_ya;
    private RadioButton paparan_tidak;
    private RadioButton radioPaparan;
    private EditText paparan_text;

    private SeekBar seekBar_nafsu_makan;
    private TextView nafsu_makan_rate;
    private SeekBar seekBar_kelelahan;
    private TextView kelelahan_rate;

    private EditText aktivitas_text;
    private EditText durasi_text;
    private RadioGroup radio_intensitas;
    private RadioButton intensitas_ringan;
    private RadioButton intensitas_sedang;
    private RadioButton intensitas_berat;
    private RadioButton radioIntensitas;

    private EditText notes_comment_text;

    private MaterialButton btn_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_jurnal_tambah);

        funct = new Funct(this);
        session = new Session(this);

        root_layout = findViewById(R.id.root_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daily Jurnal");

        // Today
        date_now = findViewById(R.id.date_now);
        seekBar_today = findViewById(R.id.seekBar_today);
        today_rate = findViewById(R.id.today_rate);
        seekBar_pain = findViewById(R.id.seekBar_pain);
        pain_rate = findViewById(R.id.pain_rate);
        mood = findViewById(R.id.mood);
        radio_gejala = findViewById(R.id.radio_gejala);
        gejala_ya = findViewById(R.id.gejala_ya);
        gejala_tidak = findViewById(R.id.gejala_tidak);
        gejala = findViewById(R.id.gejala);
        gejala_text = findViewById(R.id.gejala_text);
        radio_paparan = findViewById(R.id.radio_paparan);
        paparan_ya = findViewById(R.id.paparan_ya);
        paparan_tidak = findViewById(R.id.paparan_tidak);
        paparan_text = findViewById(R.id.paparan_text);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        date_now.setText(formattedDate);

        seekBar_today.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                today_rate.setText(String.valueOf(progressChangedValue));
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekBar_pain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                pain_rate.setText(String.valueOf(progressChangedValue));
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        funct.inputDropdownSpiner(mood, new ArrayList<SpinnerModel>(){{
            add(new SpinnerModel("", "Pilih Mood"));
            add(new SpinnerModel("1", "Good"));
            add(new SpinnerModel("2", "Happy"));
            add(new SpinnerModel("3", "Joyful"));
            add(new SpinnerModel("4", "Bad"));
            add(new SpinnerModel("5", "Depressed"));
        }});
        radio_gejala.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.gejala_ya){
                    //Toast.makeText(Form.this, "Gejala Ya", Toast.LENGTH_SHORT).show();
                }else if (i == R.id.gejala_tidak){
                    //Toast.makeText(Form.this, "Gejala Tidak", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Form.this, "Gejala", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DailyJurnalTambah.this, DailyJurnalGejala.class);
                startActivityForResult(intent, 1);
            }
        });
        radio_paparan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.paparan_ya){
                    paparan_text.setVisibility(View.VISIBLE);
                }else if (i == R.id.paparan_tidak){
                    paparan_text.setVisibility(View.GONE);
                }
            }
        });

        // Kualitas Hidup
        seekBar_nafsu_makan = findViewById(R.id.seekBar_nafsu_makan);
        nafsu_makan_rate = findViewById(R.id.nafsu_makan_rate);
        seekBar_kelelahan = findViewById(R.id.seekBar_kelelahan);
        kelelahan_rate = findViewById(R.id.kelelahan_rate);

        seekBar_nafsu_makan.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                nafsu_makan_rate.setText(String.valueOf(progressChangedValue));
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekBar_kelelahan.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                kelelahan_rate.setText(String.valueOf(progressChangedValue));
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Aktivitas
        aktivitas_text = findViewById(R.id.aktivitas_text);
        durasi_text = findViewById(R.id.durasi_text);
        radio_intensitas = findViewById(R.id.radio_intensitas);
        intensitas_ringan = findViewById(R.id.intensitas_ringan);
        intensitas_sedang = findViewById(R.id.intensitas_sedang);
        intensitas_berat = findViewById(R.id.intensitas_berat);

        // Notes
        notes_comment_text = findViewById(R.id.notes_comment_text);
        btn_simpan = findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dn = date_now.getText().toString();
                String tr = today_rate.getText().toString();
                String pr = pain_rate.getText().toString();
                String md = ((SpinnerModel) mood.getSelectedItem()).getId();
                int selectedGejala = radio_gejala.getCheckedRadioButtonId();
                radioGejala = findViewById(selectedGejala);
                String rg = radioGejala.getText().toString();
                int selectedPaparan = radio_paparan.getCheckedRadioButtonId();
                radioPaparan = findViewById(selectedPaparan);
                String rp = radioPaparan.getText().toString();
                String pt = paparan_text.getText().toString();

                String nm = nafsu_makan_rate.getText().toString();
                String ll = kelelahan_rate.getText().toString();

                String at = aktivitas_text.getText().toString();
                String da = durasi_text.getText().toString();
                int slectedIntens = radio_intensitas.getCheckedRadioButtonId();
                radioIntensitas = findViewById(slectedIntens);
                String ri = radioIntensitas.getText().toString();

                String nc = notes_comment_text.getText().toString();

                funct.notifikasiToastShow(dn+"\n"+tr+"\n"+pr+"\n"+md+"\n"+rg+"\n"+rp+"\n"+pt+"\n"+"\n"+nm+"\n"+ll+"\n"+at+"\n"+da+"\n"+ri+"\n"+"\n"+nc+"\n");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                gejala_value = data.getStringExtra("gejala");
                //System.out.println("Gejala Text " + gejala_value);
                gejala_text.setText(gejala_value);
                gejala_text.setVisibility(View.VISIBLE);
            }
        }
    }
}