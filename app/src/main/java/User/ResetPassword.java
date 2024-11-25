package User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.s_gempong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {
    private EditText etNewPassword, etConfirmNewPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmNewPassword = findViewById(R.id.et_confirm_new_password);
        Button btnConfirmReset = findViewById(R.id.btn_confirm_reset);

        mAuth = FirebaseAuth.getInstance();

        btnConfirmReset.setOnClickListener(view -> resetPassword());
    }

    private void resetPassword() {
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("Masukkan password baru");
            etNewPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmNewPassword.setError("Konfirmasi password");
            etConfirmNewPassword.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            etConfirmNewPassword.setError("Password tidak cocok");
            etConfirmNewPassword.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ResetPassword.this, "Password berhasil diubah!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPassword.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ResetPassword.this, "Gagal mengubah password", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ResetPassword.this, "Tidak ada pengguna aktif", Toast.LENGTH_SHORT).show();
        }
    }
}
