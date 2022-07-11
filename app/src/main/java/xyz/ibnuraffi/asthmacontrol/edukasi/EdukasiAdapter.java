package xyz.ibnuraffi.asthmacontrol.edukasi;

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


public class EdukasiAdapter extends ArrayAdapter<EdukasiModel> {

    public EdukasiAdapter(Context context, ArrayList<EdukasiModel> list){
        super(context, 0, list);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        EdukasiModel data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_edukasi_row, parent, false);
        }

        convertView.setTag(data.slug);

        TextView nama = convertView.findViewById(R.id.nama);
        nama.setText(data.judul);

        TextView tanggal = convertView.findViewById(R.id.tanggal);
        tanggal.setText(data.writer);

        ImageView image = convertView.findViewById(R.id.image);
        Glide.with(convertView)
                .load(data.gambar)
                .centerCrop()
                .into(image);

        return  convertView;
    }
}
