package Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

import java.util.ArrayList;
import java.util.List;

import AkunSetClass.AkunSet;
import AkunSetClass.SettingsApps;
import AkunSetClass.TentangAnda;
import User.Login;
import adapter.akunadapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Akun extends Fragment implements akunadapter.OnAkunItemClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_akun, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<AkunItem> akunItems = new ArrayList<>();
        akunItems.add(new AkunItem(R.drawable.akun, "Tentang Anda", "Update informasi pribadi anda"));
        akunItems.add(new AkunItem(R.drawable.akun, "Akun", "Ubah password"));
        akunItems.add(new AkunItem(0, "Log Out", null));

        // Mengecek role user dan menambah item jika admin
        checkUserRoleAndAddSettingsApp(akunItems, recyclerView);

        return view;
    }

    private void checkUserRoleAndAddSettingsApp(List<AkunItem> akunItems, RecyclerView recyclerView) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);

                    if ("admin".equalsIgnoreCase(role)) {
                        akunItems.add(new AkunItem(0, "Settings Apps", null)); // Menambahkan item "Settings Apps" hanya untuk admin
                    }
                }
            } else {
                Toast.makeText(getContext(), "Gagal mengambil data pengguna", Toast.LENGTH_SHORT).show();
            }

            // Mengupdate adapter setelah data berhasil diambil
            akunadapter adapter = new akunadapter(akunItems, Akun.this);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onItemClick(AkunItem akunItem) {
        if (akunItem.getTitle().equals("Tentang Anda")) {
            moveToFragment(new TentangAnda());
        } else if (akunItem.getTitle().equals("Akun")) {
            moveToFragment(new AkunSet());
        } else if (akunItem.getTitle().equals("Settings Apps")) {
            moveToFragment(new SettingsApps());
        } else if (akunItem.getTitle().equals("Log Out")) {
            logout();
        }
    }

    private void moveToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void logout() {
        Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
