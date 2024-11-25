package Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

import java.util.List;

public class TiketAdapter extends RecyclerView.Adapter<TiketAdapter.TiketViewHolder> {

    private final List<TiketBooking> tiketList;

    public TiketAdapter(List<TiketBooking> tiketList) {
        this.tiketList = tiketList;
    }

    @NonNull
    @Override
    public TiketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tiket_booking, parent, false);
        return new TiketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiketViewHolder holder, int position) {
        TiketBooking tiket = tiketList.get(position);

        holder.tglBooking.setText(tiket.getTanggal());
        holder.hargaBooking.setText(String.valueOf(tiket.getTotalHarga()));
        holder.jmlhPengunjung.setText(String.valueOf(tiket.getJumlahPengunjung()));
        holder.kewarganegaraan.setText(tiket.getKewarganegaraan());
    }

    @Override
    public int getItemCount() {
        return tiketList.size();
    }

    static class TiketViewHolder extends RecyclerView.ViewHolder {

        TextView tglBooking, hargaBooking, jmlhPengunjung, kewarganegaraan;

        public TiketViewHolder(@NonNull View itemView) {
            super(itemView);
            tglBooking = itemView.findViewById(R.id.tglbooking);
            hargaBooking = itemView.findViewById(R.id.hargabooking);
            jmlhPengunjung = itemView.findViewById(R.id.jmlh_pengunjung);
            kewarganegaraan = itemView.findViewById(R.id.kewarganegaraan);
        }
    }
}
