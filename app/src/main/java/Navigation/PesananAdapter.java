package Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> pesananList;
    private static final int TYPE_TIKET = 0;
    private static final int TYPE_HOMESTAY = 1;
    private static final int TYPE_ACARA = 2;

    public PesananAdapter(List<Object> pesananList) {
        this.pesananList = pesananList;
    }

    @Override
    public int getItemViewType(int position) {
        if (pesananList.get(position) instanceof TiketBooking) {
            return TYPE_TIKET;
        } else if (pesananList.get(position) instanceof HomestayBooking) {
            return TYPE_HOMESTAY;
        } else if (pesananList.get(position) instanceof AcaraBooking) {
            return TYPE_ACARA;
        }
        return -1; // Default case
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TIKET) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tiket_booking, parent, false);
            return new TiketViewHolder(view);
        } else if (viewType == TYPE_HOMESTAY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homestay_booking, parent, false);
            return new HomestayViewHolder(view);
        } else if (viewType == TYPE_ACARA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acara_booking, parent, false);
            return new AcaraViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_TIKET) {
            TiketBooking tiket = (TiketBooking) pesananList.get(position);
            TiketViewHolder tiketViewHolder = (TiketViewHolder) holder;
            tiketViewHolder.tglBooking.setText(tiket.getTanggal());
            tiketViewHolder.hargaBooking.setText(String.valueOf(tiket.getTotalHarga()));
            tiketViewHolder.jmlhPengunjung.setText(String.valueOf(tiket.getJumlahPengunjung()));
            tiketViewHolder.kewarganegaraan.setText(tiket.getKewarganegaraan());
        } else if (getItemViewType(position) == TYPE_HOMESTAY) {
            HomestayBooking homestay = (HomestayBooking) pesananList.get(position);
            HomestayViewHolder homestayViewHolder = (HomestayViewHolder) holder;
            homestayViewHolder.tanggalin.setText(homestay.getTanggalIn());
            homestayViewHolder.tanggalout.setText(homestay.getTanggalOut());
            homestayViewHolder.hargaBooking.setText(String.valueOf(homestay.getTotalPrice()));
            homestayViewHolder.jmlhPengunjung.setText(String.valueOf(homestay.getJmlhPengunjung()));
            homestayViewHolder.homestay.setText(homestay.getHomestay());
        } else if (getItemViewType(position) == TYPE_ACARA) {
            AcaraBooking acara = (AcaraBooking) pesananList.get(position);
            AcaraViewHolder acaraViewHolder = (AcaraViewHolder) holder;
            acaraViewHolder.tanggal.setText(acara.getTanggal());
            acaraViewHolder.penyelenggara.setText(acara.getpenyelenggara());
            acaraViewHolder.namaacara.setText(acara.getnamaacara());
            acaraViewHolder.jumlahPengunjung.setText(String.valueOf(acara.getJumlahPengunjung()));
            acaraViewHolder.totalHarga.setText(String.valueOf(acara.getTotalHarga()));
        }
    }


    @Override
    public int getItemCount() {
        return pesananList.size();
    }

    public static class TiketViewHolder extends RecyclerView.ViewHolder {

        TextView tglBooking, hargaBooking, jmlhPengunjung, kewarganegaraan;

        public TiketViewHolder(View itemView) {
            super(itemView);
            tglBooking = itemView.findViewById(R.id.tglbooking);
            hargaBooking = itemView.findViewById(R.id.hargabooking);
            jmlhPengunjung = itemView.findViewById(R.id.jmlh_pengunjung);
            kewarganegaraan = itemView.findViewById(R.id.kewarganegaraan);
        }
    }

    public static class HomestayViewHolder extends RecyclerView.ViewHolder {

        TextView tanggalin, tanggalout, hargaBooking, jmlhPengunjung, homestay;

        public HomestayViewHolder(View itemView) {
            super(itemView);
            tanggalin = itemView.findViewById(R.id.tglbookingin);
            tanggalout = itemView.findViewById(R.id.tglbookingout);
            hargaBooking = itemView.findViewById(R.id.hargabooking);
            jmlhPengunjung = itemView.findViewById(R.id.jmlh_pengunjung);
            homestay = itemView.findViewById(R.id.Nama_homestay);
        }
    }

    public static class AcaraViewHolder extends RecyclerView.ViewHolder {

        TextView tanggal, penyelenggara, namaacara, jumlahPengunjung, totalHarga;

        public AcaraViewHolder(View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal);
            penyelenggara = itemView.findViewById(R.id.penyelenggara);
            namaacara = itemView.findViewById(R.id.namaacara);
            jumlahPengunjung = itemView.findViewById(R.id.jmlh_pengunjung);
            totalHarga = itemView.findViewById(R.id.total);
        }
    }
}
