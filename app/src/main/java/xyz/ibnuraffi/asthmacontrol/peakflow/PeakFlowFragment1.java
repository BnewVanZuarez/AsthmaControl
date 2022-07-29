package xyz.ibnuraffi.asthmacontrol.peakflow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObat;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObatAdapter;
import xyz.ibnuraffi.asthmacontrol.home.MainActivity;
import xyz.ibnuraffi.asthmacontrol.profile.Profile;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class PeakFlowFragment1 extends Fragment {

    private Funct funct;
    private Session session;
    private View view;
    private PeakFlow peakFlow;

    public static PeakFlowFragment1 newInstance() {
        PeakFlowFragment1 fragment = new PeakFlowFragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.peak_flow_fragment_1, container, false);

        funct = new Funct(getContext());
        session = new Session(getContext());

        peakFlow = (PeakFlow) getActivity();
        peakFlow.peak_flow_fragment_1_root_layout = view.findViewById(R.id.root_layout);
        peakFlow.peak_flow_fragment_1_tambah = view.findViewById(R.id.tambah);
        peakFlow.peak_flow_fragment_1_list_view = view.findViewById(R.id.list_view);
        peakFlow.peak_flow_fragment_1_adapter = new PeakFlowAdapter(getContext(), peakFlow.peak_flow_fragment_1_model);
        peakFlow.peak_flow_fragment_1_list_view.setAdapter(peakFlow.peak_flow_fragment_1_adapter);

        peakFlow.peak_flow_fragment_1_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PeakFlowTambah.class);
                startActivity(intent);
            }
        });

        peakFlow.daftarPeakFlowGetData();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.tambah){
            Intent intent = new Intent(getContext(), PeakFlowTambah.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
