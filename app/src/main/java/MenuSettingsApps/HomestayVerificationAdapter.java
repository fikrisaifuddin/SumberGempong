package MenuSettingsApps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.s_gempong.R;
import java.util.ArrayList;

public class HomestayVerificationAdapter extends RecyclerView.Adapter<HomestayVerificationAdapter.VerificationViewHolder> {
    private ArrayList<HomestayVerification> verificationList;

    public HomestayVerificationAdapter(ArrayList<HomestayVerification> verificationList) {
        this.verificationList = verificationList;
    }

    @NonNull
    @Override
    public VerificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verification_homestay, parent, false);
        return new VerificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerificationViewHolder holder, int position) {
        HomestayVerification data = verificationList.get(position);
        holder.kodeBooking.setText("Kode Booking: " + data.getKodeBooking());
        holder.tanggal.setText("Tanggal: " + data.getTanggal());
        holder.id.setText("ID: " + data.getId());
    }

    @Override
    public int getItemCount() {
        return verificationList.size();
    }

    static class VerificationViewHolder extends RecyclerView.ViewHolder {
        TextView kodeBooking, tanggal, id;

        public VerificationViewHolder(@NonNull View itemView) {
            super(itemView);
            kodeBooking = itemView.findViewById(R.id.kodeBooking);
            tanggal = itemView.findViewById(R.id.tanggal);
            id = itemView.findViewById(R.id.id);
        }
    }
}
