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

public class MakananAdapter  extends RecyclerView.Adapter<MakananAdapter.MakananViewHolder> {

    private ArrayList<Makanan> makananList;
    private DatabaseReference databaseReference;

    public MakananAdapter(ArrayList<Makanan> makananList, DatabaseReference databaseReference) {
        this.makananList = makananList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public MakananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_makanan, parent, false);
        return new MakananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MakananViewHolder holder, int position) {
        Makanan makanan = makananList.get(position);
        holder.namaMakanan.setText(makanan.getNama());
        holder.hargaMakanan.setText(makanan.getHarga());

        // Handle delete button click
        holder.btnDelete.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Makanan makananToDelete = makananList.get(adapterPosition);

                // Remove item from Firebase
                databaseReference.child(makananToDelete.getId()).removeValue();

                // Remove item from local list
                makananList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            }
        });

        // Handle edit button click
        holder.btnEdit.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Makanan makananToEdit = makananList.get(adapterPosition);

                // Start EditMakananActivity
                Intent intent = new Intent(v.getContext(), MakananEdit.class);
                intent.putExtra("makananId", makananToEdit.getId());
                intent.putExtra("nama", makananToEdit.getNama());
                intent.putExtra("harga", makananToEdit.getHarga());
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return makananList.size();
    }

    public static class MakananViewHolder extends RecyclerView.ViewHolder {
        TextView namaMakanan,hargaMakanan;
        Button btnDelete, btnEdit;

        public MakananViewHolder(@NonNull View itemView) {
            super(itemView);
            namaMakanan = itemView.findViewById(R.id.nama_makanan);
            hargaMakanan = itemView.findViewById(R.id.harga_makanan);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
