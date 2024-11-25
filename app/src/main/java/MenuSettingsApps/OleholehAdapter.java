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

public class OleholehAdapter  extends RecyclerView.Adapter<OleholehAdapter.OleholehViewHolder> {

    private ArrayList<Oleholeh> oleholehList;
    private DatabaseReference databaseReference;

    public OleholehAdapter(ArrayList<Oleholeh> oleholehList, DatabaseReference databaseReference) {
        this.oleholehList = oleholehList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public OleholehViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_oleholeh, parent, false);
        return new OleholehViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OleholehViewHolder holder, int position) {
        Oleholeh oleholeh = oleholehList.get(position);
        holder.namaOleholeh.setText(oleholeh.getNama());
        holder.hargaOleholeh.setText(oleholeh.getHarga());

        // Handle delete button click
        holder.btnDelete.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Oleholeh oleholehToDelete = oleholehList.get(adapterPosition);

                // Remove item from Firebase
                databaseReference.child(oleholehToDelete.getId()).removeValue();

                // Remove item from local list
                oleholehList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            }
        });

        // Handle edit button click
        holder.btnEdit.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Oleholeh oleholehToEdit = oleholehList.get(adapterPosition);

                // Start EditMakananActivity
                Intent intent = new Intent(v.getContext(), OleholehEdit.class);
                intent.putExtra("oleholehId", oleholehToEdit.getId());
                intent.putExtra("nama", oleholehToEdit.getNama());
                intent.putExtra("harga", oleholehToEdit.getHarga());
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return oleholehList.size();
    }

    public static class OleholehViewHolder extends RecyclerView.ViewHolder {
        TextView namaOleholeh,hargaOleholeh;
        Button btnDelete, btnEdit;

        public OleholehViewHolder(@NonNull View itemView) {
            super(itemView);
            namaOleholeh = itemView.findViewById(R.id.nama_makanan);
            hargaOleholeh = itemView.findViewById(R.id.harga_makanan);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
