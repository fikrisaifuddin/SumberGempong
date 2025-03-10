package MenuSettingsApps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Menginflate layout fragment
        View view = inflater.inflate(R.layout.set_user, container, false);

        // Toolbar setup
        Toolbar toolbar = view.findViewById(R.id.top_setting);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        }

        // RecyclerView setup
        recyclerView = view.findViewById(R.id.all_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(getActivity(), userList);
        recyclerView.setAdapter(userAdapter);

        // Firebase authentication
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users");

            // Check if the user is admin
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String role = dataSnapshot.child("role").getValue(String.class);

                        // If role is admin, fetch all users with role "user"
                        if ("admin".equals(role)) {
                            databaseReference.orderByChild("role").equalTo("user")
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            userList.clear(); // Clear previous data
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                String username = snapshot.child("username").getValue(String.class);
                                                String email = snapshot.child("email").getValue(String.class);

                                                // Ensure username and email are not null
                                                if (username != null && email != null) {
                                                    userList.add(new User(username, email));
                                                }
                                            }
                                            userAdapter.notifyDataSetChanged(); // Update the adapter
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity(), "Hanya admin yang dapat melihat daftar pengguna", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Data pengguna tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Pengguna belum login", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}
