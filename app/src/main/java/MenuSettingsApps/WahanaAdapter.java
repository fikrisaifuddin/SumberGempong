package MenuSettingsApps;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class WahanaAdapter extends RecyclerView.Adapter<WahanaAdapter.WahanaViewHolder> {

    private ArrayList<Wahana> wahanaList;
    private DatabaseReference databaseReference;

    public WahanaAdapter(ArrayList<Wahana> wahanaList, DatabaseReference databaseReference) {
        this.wahanaList = wahanaList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public WahanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_wahana, parent, false);
        return new WahanaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WahanaViewHolder holder, int position) {
        Wahana wahana = wahanaList.get(position);
        holder.namaWahana.setText(wahana.getNama());
        holder.hargaWahana.setText(wahana.getHarga());

        // Handle delete button click
        holder.btnDelete.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Wahana wahanaToDelete = wahanaList.get(adapterPosition);

                // Remove item from Firebase
                databaseReference.child(wahanaToDelete.getId()).removeValue();

                // Remove item from local list
                wahanaList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            }
        });

        // Handle edit button click
        holder.btnEdit.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Wahana wahanaToEdit = wahanaList.get(adapterPosition);

                // Start EditWahanaActivity
                Intent intent = new Intent(v.getContext(), WahanaEdit.class);
                intent.putExtra("wahanaId", wahanaToEdit.getId());
                intent.putExtra("nama", wahanaToEdit.getNama());
                intent.putExtra("harga", wahanaToEdit.getHarga());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wahanaList.size();
    }

    public static class WahanaViewHolder extends RecyclerView.ViewHolder {
        TextView namaWahana, hargaWahana;
        Button btnDelete, btnEdit;

        public WahanaViewHolder(@NonNull View itemView) {
            super(itemView);
            namaWahana = itemView.findViewById(R.id.nama_wahana);
            hargaWahana = itemView.findViewById(R.id.harga_wahana);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
