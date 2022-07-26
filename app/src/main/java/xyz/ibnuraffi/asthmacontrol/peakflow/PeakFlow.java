package xyz.ibnuraffi.asthmacontrol.peakflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import xyz.ibnuraffi.asthmacontrol.R;
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

    // Fragmen Dua
    public CoordinatorLayout peak_flow_fragment_2_root_layout;

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
                }else if (position == 1){
                }
            }
        });
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
}