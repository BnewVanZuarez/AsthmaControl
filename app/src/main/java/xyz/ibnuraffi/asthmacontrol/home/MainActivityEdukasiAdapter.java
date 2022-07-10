package xyz.ibnuraffi.asthmacontrol.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import xyz.ibnuraffi.asthmacontrol.R;

public class MainActivityEdukasiAdapter extends RecyclerView.Adapter<MainActivityEdukasiAdapter.ListViewHolder> {

    private ArrayList<MainActivityEdukasi> listEdukasi;
    private OnItemClickCallback onItemClickCallback;

    public MainActivityEdukasiAdapter(ArrayList<MainActivityEdukasi> list) {
        this.listEdukasi = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_edukasi_row, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        final MainActivityEdukasi edukasi = listEdukasi.get(position);

        Glide.with(holder.itemView.getContext())
                .load(edukasi.getGambar())
                .centerCrop()
                .into(holder.imgPhoto);

        holder.tvName.setText(edukasi.getJudul());
        holder.tvtanggal.setText(edukasi.getWriter());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listEdukasi.get(holder.getAdapterPosition()));
                //Intent myIntent = new Intent(v.getContext(), BeritaDetail.class);
                //myIntent.putExtra("id",berita.getId());
                //myIntent.putExtra("detail",hero.getDetail());
                //myIntent.putExtra("gambar",hero.getPhoto());

            }
        });

    }

    @Override
    public int getItemCount() {
        return listEdukasi.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvtanggal;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.nama);
            tvtanggal = itemView.findViewById(R.id.tanggal);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MainActivityEdukasi data);
    }
}
