package xyz.ibnuraffi.asthmacontrol.rencanaaksiasma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.dailyjurnal.DailyJurnalModel;


public class RencanaAksiAsmaAdapter extends ArrayAdapter<RencanaAksiAsmaModel> {

    public RencanaAksiAsmaAdapter(Context context, ArrayList<RencanaAksiAsmaModel> list){
        super(context, 0, list);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        RencanaAksiAsmaModel data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rencana_aksi_asma_row, parent, false);
        }

        convertView.setTag(data.id);

        TextView nama = convertView.findViewById(R.id.nama);
        nama.setText(data.nama_dokter);

        TextView tanggal = convertView.findViewById(R.id.tanggal);
        tanggal.setText(data.tanggal_input);

        return  convertView;
    }
}
