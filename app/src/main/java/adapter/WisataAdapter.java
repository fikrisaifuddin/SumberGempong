package adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

import java.util.List;

import Navigation.Wisata;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder> {

    private Context context;
    private List<Wisata> wisataList;

    public WisataAdapter(Context context, List<Wisata> wisataList) {
        this.context = context;
        this.wisataList = wisataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wisata wisata = wisataList.get(position);
        holder.caption.setText(wisata.getCaption());
        holder.imageView.setImageResource(wisata.getImageResId());
    }

    @Override
    public int getItemCount() {
        return wisataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView caption;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            caption = itemView.findViewById(R.id.caption);
            imageView = itemView.findViewById(R.id.images);
        }
    }
}
