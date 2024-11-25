package AkunSetClass;

import android.os.Bundle;
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

public class AkunSet extends Fragment {

    private EditText oldPasswordET, newPasswordET;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_setakun, container, false);

        Toolbar toolbar = view.findViewById(R.id.top_setting);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());


        oldPasswordET = view.findViewById(R.id.OldPassword);
        newPasswordET = view.findViewById(R.id.NewPassword);
        Button resetButton = view.findViewById(R.id.reset);

        mAuth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(v -> resetPassword());

        return view;
    }

    private void resetPassword() {
        String oldPassword = oldPasswordET.getText().toString().trim();
        String newPassword = newPasswordET.getText().toString().trim();

        // Validasi input
        if (oldPassword.isEmpty()) {
            oldPasswordET.setError("Password lama diperlukan");
            oldPasswordET.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            newPasswordET.setError("Password baru diperlukan");
            newPasswordET.requestFocus();
            return;
        }

        if (newPassword.length() < 6) {
            newPasswordET.setError("Password harus lebih dari 6 karakter");
            newPasswordET.requestFocus();
            return;
        }

        // Ambil email pengguna saat ini
        String userEmail = mAuth.getCurrentUser().getEmail();

        // Menggunakan Firebase untuk mengautentikasi dan mereset password
        mAuth.signInWithEmailAndPassword(userEmail, oldPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Jika login berhasil, reset password
                        mAuth.getCurrentUser().updatePassword(newPassword)
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Password berhasil direset", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Gagal mereset password", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Jika password lama tidak cocok
                        Toast.makeText(getActivity(), "Password lama salah", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
