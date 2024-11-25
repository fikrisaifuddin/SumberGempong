package MenuSettingsApps;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    // Constructor
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        if (userList != null && position < userList.size()) {
            User user = userList.get(position);
            holder.usernameTextView.setText(user.getUsername());
            holder.emailTextView.setText(user.getEmail());

            // Menangani klik pada item
            holder.itemView.setOnClickListener(v -> {
                // Kirim data ke fragment tujuan
                Fragment fragment = new UserInformation();

                // Menggunakan Bundle untuk mengirimkan data
                Bundle bundle = new Bundle();
                bundle.putString("username", user.getUsername());
                bundle.putString("email", user.getEmail());
                bundle.putString("phoneNumber", user.getPhoneNumber());
                bundle.putString("address", user.getAddress());

                fragment.setArguments(bundle);

                // Mengganti fragment
                if (context instanceof FragmentActivity) {
                    FragmentActivity activity = (FragmentActivity) context;
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (userList != null) ? userList.size() : 0; // Avoid NullPointerException
    }

    // ViewHolder untuk item_user.xml
    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView usernameTextView, emailTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
        }
    }
}
