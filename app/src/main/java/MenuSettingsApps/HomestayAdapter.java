package MenuSettingsApps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class HomestayAdapter extends RecyclerView.Adapter<HomestayAdapter.HomestayViewHolder> {

    private ArrayList<Homestay> homestayList;
    private DatabaseReference databaseReference;

    public HomestayAdapter(ArrayList<Homestay> homestayList, DatabaseReference databaseReference) {
        this.homestayList = homestayList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public HomestayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_homestay, parent, false);
        return new HomestayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomestayViewHolder holder, int position) {
        Homestay homestay = homestayList.get(position);
        holder.namaHomestay.setText(homestay.getNama());

        holder.btnDelete.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Homestay selectedHomestay = homestayList.get(adapterPosition);
                databaseReference.child(selectedHomestay.getId()).removeValue();
                homestayList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            }
        });

        holder.btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Homestay homestay = homestayList.get(adapterPosition);

                    // Navigate to ManageHomestayFragment
                    HomestayManage fragment = HomestayManage.newInstance(homestay.getNama());
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });



        holder.btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Homestay homestay = homestayList.get(adapterPosition);

                    // Navigate to ManageHomestayFragment
                    HomestayManage fragment = HomestayManage.newInstance(homestay.getNama());
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

    }

    private FragmentManager fragmentManager;

    public HomestayAdapter(ArrayList<Homestay> homestayList, DatabaseReference databaseReference, FragmentManager fragmentManager) {
        this.homestayList = homestayList;
        this.databaseReference = databaseReference;
        this.fragmentManager = fragmentManager;
    }


    @Override
    public int getItemCount() {
        return homestayList.size();
    }

    public static class HomestayViewHolder extends RecyclerView.ViewHolder {
        TextView namaHomestay;
        Button btnDelete, btnLihat;

        public HomestayViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHomestay = itemView.findViewById(R.id.namahomestay);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnLihat = itemView.findViewById(R.id.btn_lihat);
        }
    }
}
