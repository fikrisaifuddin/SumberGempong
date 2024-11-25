package User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s_gempong.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText etEmailForgotPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmailForgotPassword = findViewById(R.id.et_email_forgot_password);
        Button btnResetPassword = findViewById(R.id.btn_reset_password);
        Button btnBackToLogin = findViewById(R.id.btn_back_to_login);

        mAuth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(view -> resetPassword());

        btnBackToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(ForgotPassword.this, Login.class);
            startActivity(intent);
            finish();
        });
    }

    private void resetPassword() {
        String email = etEmailForgotPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmailForgotPassword.setError("Masukkan email Anda");
            etEmailForgotPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailForgotPassword.setError("Email tidak valid");
            etEmailForgotPassword.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ForgotPassword.this, "Email reset password telah dikirim!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPassword.this, ResetPassword.class);
                intent.putExtra("email", email); // Kirim email ke halaman reset
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(ForgotPassword.this, "Gagal mengirim email reset password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
