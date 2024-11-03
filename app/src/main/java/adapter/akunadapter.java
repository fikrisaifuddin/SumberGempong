package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

import java.util.List;

import Navigation.AkunItem;

public class akunadapter extends RecyclerView.Adapter<akunadapter.ViewHolder> {

    private final List<AkunItem> akunItemList;
    private final OnAkunItemClickListener listener;

    // Interface untuk menangani klik pada item
    public interface OnAkunItemClickListener {
        void onItemClick(AkunItem akunItem);
    }

    // Konstruktor menerima daftar item dan listener
    public akunadapter(List<AkunItem> akunItemList, OnAkunItemClickListener listener) {
        this.akunItemList = akunItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_akun, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AkunItem currentItem = akunItemList.get(position);

        if (currentItem.getIconResId() != 0) {
            holder.icon.setImageResource(currentItem.getIconResId());
            holder.icon.setVisibility(View.VISIBLE);
        } else {
            holder.icon.setVisibility(View.GONE);
        }

        holder.title.setText(currentItem.getTitle());

        if (currentItem.getDescription() != null) {
            holder.description.setText(currentItem.getDescription());
            holder.description.setVisibility(View.VISIBLE);
        } else {
            holder.description.setVisibility(View.GONE);
        }

        // Menambahkan aksi klik ke item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(currentItem); // Memicu aksi dari listener saat item diklik
            }
        });
    }

    @Override
    public int getItemCount() {
        return akunItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }
}
