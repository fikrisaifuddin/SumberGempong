package adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private int[] imageList = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    private Handler handler = new Handler();
    private int currentImageIndex = 0;

    public ImageAdapter(Context context) {
        this.context = context;
        startImageSlideshow();
    }

    private void startImageSlideshow() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentImageIndex = (currentImageIndex + 1) % imageList.length;
                notifyDataSetChanged();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(imageList[currentImageIndex]);
    }

    @Override
    public int getItemCount() {
        return 1; // Kita hanya menampilkan satu gambar pada waktu tertentu
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);  // Pastikan R.id.imageView ada di item_image.xml
        }
    }
}
