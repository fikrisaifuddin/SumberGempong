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

import MenuSettingsApps.Makanan;

public class DaftarMakananAdapter extends RecyclerView.Adapter<DaftarMakananAdapter.MakananViewHolder> {

    private ArrayList<Makanan> makananList;

    // Constructor
    public DaftarMakananAdapter(ArrayList<Makanan> makananList) {
        this.makananList = makananList;
    }

    @NonNull
    @Override
    public MakananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_daftarmakanan, parent, false);
        return new MakananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MakananViewHolder holder, int position) {
        // Bind data to the views
        Makanan makanan = makananList.get(position);
        holder.namaMakanan.setText(makanan.getNama());
        holder.hargaMakanan.setText("Harga Rp. " + makanan.getHarga());
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the list
        return makananList.size();
    }

    // ViewHolder class to hold the item views
    public static class MakananViewHolder extends RecyclerView.ViewHolder {
        TextView namaMakanan, hargaMakanan;

        public MakananViewHolder(@NonNull View itemView) {
            super(itemView);
            namaMakanan = itemView.findViewById(R.id.nama_makanan);
            hargaMakanan = itemView.findViewById(R.id.harga_makanan);
        }
    }
}
