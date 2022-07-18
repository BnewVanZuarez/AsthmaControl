package xyz.ibnuraffi.asthmacontrol.tanyajawab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObatModel;


public class TanyaJawabAdapter extends ArrayAdapter<TanyaJawabModel> {

    public TanyaJawabAdapter(Context context, ArrayList<TanyaJawabModel> list){
        super(context, 0, list);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        TanyaJawabModel data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tanya_jawab_row, parent, false);
        }

        convertView.setTag(data.id);

        TextView no_tiket = convertView.findViewById(R.id.no_tiket);
        no_tiket.setText("#"+data.no_tiket);

        TextView perihal = convertView.findViewById(R.id.perihal);
        perihal.setText(data.perihal);

        TextView tanggal = convertView.findViewById(R.id.tanggal);
        tanggal.setText(data.tanggal_input);

        return  convertView;
    }
}
