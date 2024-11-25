package AkunSetClass;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.s_gempong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import MenuSettingsApps.User;

public class TentangAnda extends Fragment {

    private EditText namaUserET, noHpET, alamatET;
    private Button perbaruiBTN;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frg_tentanganda, container, false);

        Toolbar toolbar = view.findViewById(R.id.top_setting);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());


        namaUserET = view.findViewById(R.id.namauser);
        noHpET = view.findViewById(R.id.Nohp);
        alamatET = view.findViewById(R.id.alamat);
        perbaruiBTN = view.findViewById(R.id.perbarui);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        // Pastikan ada pengguna yang sedang login
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Mengambil data pengguna dari Firebase dan menampilkan di EditText
            userRef.child(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            namaUserET.setText(user.getUsername()); // Menampilkan username di EditText
                            noHpET.setText(user.getPhoneNumber() != null ? user.getPhoneNumber() : "");
                            alamatET.setText(user.getAddress() != null ? user.getAddress() : "");
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Gagal memuat data pengguna", Toast.LENGTH_SHORT).show();
                }
            });

            // Perbarui data pengguna ketika tombol perbarui ditekan
            perbaruiBTN.setOnClickListener(v -> updateUserData(userId));

        } else {
            Toast.makeText(getContext(), "User tidak ditemukan, harap login terlebih dahulu", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void updateUserData(String userId) {
        String namaUser = namaUserET.getText().toString().trim();
        String noHp = noHpET.getText().toString().trim();
        String alamat = alamatET.getText().toString().trim();

        // Validasi input
        if (TextUtils.isEmpty(namaUser) || TextUtils.isEmpty(noHp) || TextUtils.isEmpty(alamat)) {
            Toast.makeText(getContext(), "Isi data yang kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perbarui data di Firebase
        userRef.child(userId).child("username").setValue(namaUser);  // Menambahkan perbarui username
        userRef.child(userId).child("phoneNumber").setValue(noHp);
        userRef.child(userId).child("address").setValue(alamat)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
