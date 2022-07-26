package xyz.ibnuraffi.asthmacontrol.dailyjurnal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;
import xyz.ibnuraffi.asthmacontrol.daftarobat.DaftarObatModel;


public class DailyJurnalAdapter extends ArrayAdapter<DailyJurnalModel> {

    public DailyJurnalAdapter(Context context, ArrayList<DailyJurnalModel> list){
        super(context, 0, list);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        DailyJurnalModel data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.daily_jurnal_row, parent, false);
        }

        convertView.setTag(data.id);

        TextView mood = convertView.findViewById(R.id.mood);
        if (data.mood_today.equals("1")){
            mood.setText("Mood : Good");
        }else if (data.mood_today.equals("2")){
            mood.setText("Mood : Happy");
        }else if (data.mood_today.equals("3")){
            mood.setText("Mood : Joyful");
        }else if (data.mood_today.equals("4")){
            mood.setText("Mood : Bad");
        }else if (data.mood_today.equals("5")){
            mood.setText("Mood : Depressed");
        }

        TextView tanggal = convertView.findViewById(R.id.tanggal);
        tanggal.setText(data.tanggal);

        return  convertView;
    }
}
