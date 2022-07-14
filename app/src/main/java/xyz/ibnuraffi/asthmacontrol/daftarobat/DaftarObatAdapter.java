package xyz.ibnuraffi.asthmacontrol.daftarobat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.edukasi.EdukasiModel;


public class DaftarObatAdapter extends ArrayAdapter<DaftarObatModel> {

    public DaftarObatAdapter(Context context, ArrayList<DaftarObatModel> list){
        super(context, 0, list);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        DaftarObatModel data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.daftar_obat_row, parent, false);
        }

        convertView.setTag(data.id);

        TextView nama = convertView.findViewById(R.id.nama);
        nama.setText(data.nama);

        TextView dosis = convertView.findViewById(R.id.dosis);
        dosis.setText(data.dosis);

        return  convertView;
    }
}
