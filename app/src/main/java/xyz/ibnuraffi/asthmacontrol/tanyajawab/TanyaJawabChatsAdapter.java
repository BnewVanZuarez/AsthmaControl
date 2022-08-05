package xyz.ibnuraffi.asthmacontrol.tanyajawab;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import xyz.ibnuraffi.asthmacontrol.R;


public class TanyaJawabChatsAdapter extends ArrayAdapter<TanyaJawabChatsModel> {

    public TanyaJawabChatsAdapter(Context context, ArrayList<TanyaJawabChatsModel> list){
        super(context, 0, list);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        TanyaJawabChatsModel data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tanya_jawab_chats_row, parent, false);
        }

        convertView.setTag(data.id);

        LinearLayout chat_layout = convertView.findViewById(R.id.chat_layout);
        CardView chat_card = convertView.findViewById(R.id.chat_card);
        TextView pesan = convertView.findViewById(R.id.pesan);
        TextView tanggal_input = convertView.findViewById(R.id.tanggal_input);

        if (data.tipe.equals("1")){
            chat_layout.setGravity(Gravity.END);
        }else if (data.tipe.equals("2")){
            chat_layout.setGravity(Gravity.START);
            chat_card.setCardBackgroundColor(Color.WHITE);
            tanggal_input.setTextColor(Color.GRAY);
        }
        pesan.setText(data.pesan);
        tanggal_input.setText(data.tanggal_input);

        return  convertView;
    }
}
