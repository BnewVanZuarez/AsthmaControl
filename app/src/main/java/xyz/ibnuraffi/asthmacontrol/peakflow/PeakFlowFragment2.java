package xyz.ibnuraffi.asthmacontrol.peakflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.utils.Funct;
import xyz.ibnuraffi.asthmacontrol.utils.Session;

public class PeakFlowFragment2 extends Fragment {

    private Funct funct;
    private Session session;
    private View view;
    private PeakFlow peakFlow;

    public static PeakFlowFragment2 newInstance() {
        PeakFlowFragment2 fragment = new PeakFlowFragment2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.peak_flow_fragment_2, container, false);

        funct = new Funct(getContext());
        session = new Session(getContext());

        peakFlow = (PeakFlow) getActivity();
        peakFlow.peak_flow_fragment_2_root_layout = view.findViewById(R.id.root_layout);
        peakFlow.peak_flow_fragment_2_chart = view.findViewById(R.id.chart);
        peakFlow.peak_flow_fragment_2_chart.setDragEnabled(false);
        peakFlow.peak_flow_fragment_2_chart.setScaleEnabled(false);
        // if disabled, scaling can be done on x- and y-axis separately
        peakFlow.peak_flow_fragment_2_chart.setPinchZoom(false);
        peakFlow.peak_flow_fragment_2_chart.getAxisRight().setEnabled(false);
        peakFlow.peak_flow_fragment_2_chart.getDescription().setEnabled(false);
        peakFlow.peak_flow_fragment_2_chart.getLegend().setEnabled(false);
        peakFlow.peak_flow_fragment_2_chart.getLegend().setWordWrapEnabled(true);
        peakFlow.peak_flow_fragment_2_chart.getXAxis().setEnabled(false);

        peakFlow.grafikPeakFlowGetData();

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
