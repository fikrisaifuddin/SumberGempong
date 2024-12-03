package MenuSettingsApps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    // Constructor
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList != null ? userList : new ArrayList<>(); // Ensure list is not null
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        if (position < userList.size()) {
            User user = userList.get(position);

            holder.usernameTextView.setText(user.getUsername() != null ? user.getUsername() : "No username");
            holder.emailTextView.setText(user.getEmail() != null ? user.getEmail() : "No email");
        }
    }

    @Override
    public int getItemCount() {
        return userList.size(); // Avoid NullPointerException
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView usernameTextView, emailTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
        }
    }
}
