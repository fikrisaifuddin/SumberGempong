package Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

import java.util.List;

public class HomestayAdapter extends RecyclerView.Adapter<HomestayAdapter.HomestayViewHolder> {

    private final List<HomestayBooking> homestayList;

    public HomestayAdapter(List<HomestayBooking> homestayList) {
        this.homestayList = homestayList;
    }

    @NonNull
    @Override
    public HomestayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homestay_booking, parent, false);
        return new HomestayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomestayViewHolder holder, int position) {
        HomestayBooking homestay = homestayList.get(position);
        holder.tanggalin.setText(homestay.getTanggalIn());
        holder.tanggalout.setText(homestay.getTanggalOut());
        holder.hargaBooking.setText(String.valueOf(homestay.getTotalPrice()));
        holder.jmlhPengunjung.setText(String.valueOf(homestay.getJmlhPengunjung()));
        holder.homestay.setText(homestay.getHomestay());
    }

    @Override
    public int getItemCount() {
        return homestayList.size();
    }

    static class HomestayViewHolder extends RecyclerView.ViewHolder {

        TextView tanggalin, tanggalout, hargaBooking, jmlhPengunjung, homestay;

        public HomestayViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggalin = itemView.findViewById(R.id.tglbookingin);
            tanggalout = itemView.findViewById(R.id.tglbookingout);
            hargaBooking = itemView.findViewById(R.id.hargabooking);
            jmlhPengunjung = itemView.findViewById(R.id.jmlh_pengunjung);
            homestay = itemView.findViewById(R.id.Nama_homestay);
        }
    }
}

