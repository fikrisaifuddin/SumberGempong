package MenuCenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.s_gempong.R;
import java.util.ArrayList;

import MenuSettingsApps.Wahana;

public class DaftarWahanaAdapter extends RecyclerView.Adapter<DaftarWahanaAdapter.WahanaViewHolder> {

    private ArrayList<Wahana> wahanaList;

    // Constructor
    public DaftarWahanaAdapter(ArrayList<Wahana> wahanaList) {
        this.wahanaList = wahanaList;
    }

    @NonNull
    @Override
    public WahanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_daftar_wahana, parent, false);
        return new WahanaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WahanaViewHolder holder, int position) {
        // Bind data to the views
        Wahana wahana = wahanaList.get(position);
        holder.namaWahana.setText(wahana.getNama());
        holder.hargaWahana.setText("Harga Rp. " + wahana.getHarga());
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the list
        return wahanaList.size();
    }

    // ViewHolder class to hold the item views
    public static class WahanaViewHolder extends RecyclerView.ViewHolder {
        TextView namaWahana, hargaWahana;

        public WahanaViewHolder(@NonNull View itemView) {
            super(itemView);
            namaWahana = itemView.findViewById(R.id.nama_wahana);
            hargaWahana = itemView.findViewById(R.id.harga_wahana);
        }
    }
}
