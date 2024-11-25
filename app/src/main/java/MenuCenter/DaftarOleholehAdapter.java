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

import MenuSettingsApps.Oleholeh;

public class DaftarOleholehAdapter extends RecyclerView.Adapter<DaftarOleholehAdapter.OleholehViewHolder> {

    private ArrayList<Oleholeh> oleholehList;

    // Constructor
    public DaftarOleholehAdapter(ArrayList<Oleholeh> oleholehList) {
        this.oleholehList = oleholehList;
    }

    @NonNull
    @Override
    public OleholehViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_daftaroleholeh, parent, false);
        return new OleholehViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OleholehViewHolder holder, int position) {
        // Bind data to the views
        Oleholeh oleholeh = oleholehList.get(position);
        holder.namaOleholeh.setText(oleholeh.getNama());
        holder.hargaOleholeh.setText("Harga Rp. " + oleholeh.getHarga());
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the list
        return oleholehList.size();
    }

    // ViewHolder class to hold the item views
    public static class OleholehViewHolder extends RecyclerView.ViewHolder {
        TextView namaOleholeh, hargaOleholeh;

        public OleholehViewHolder(@NonNull View itemView) {
            super(itemView);
            namaOleholeh = itemView.findViewById(R.id.nama_oleholeh);
            hargaOleholeh = itemView.findViewById(R.id.harga_oleholeh);
        }
    }
}
