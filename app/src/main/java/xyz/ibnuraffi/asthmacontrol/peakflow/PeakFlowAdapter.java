package xyz.ibnuraffi.asthmacontrol.peakflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObatModel;


public class PeakFlowAdapter extends ArrayAdapter<PeakFlowModel> {

    public PeakFlowAdapter(Context context, ArrayList<PeakFlowModel> list){
        super(context, 0, list);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        PeakFlowModel data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.peak_flow_row, parent, false);
        }

        convertView.setTag(data.id);

        TextView tanggal = convertView.findViewById(R.id.tanggal);
        tanggal.setText(data.tanggal);

        TextView nilai = convertView.findViewById(R.id.nilai);
        nilai.setText(data.nilai+" lpm");

        TextView nilai_warna = convertView.findViewById(R.id.nilai_warna);
        nilai_warna.setText(data.nilai);

        if (data.warna.equals("1")){
            nilai_warna.setBackgroundResource(R.drawable.border_merah);
        }else if (data.warna.equals("2")){
            nilai_warna.setBackgroundResource(R.drawable.border_kuning);
        }else if (data.warna.equals("3")){
            nilai_warna.setBackgroundResource(R.drawable.border_hijau);
        }

        return  convertView;
    }
}
