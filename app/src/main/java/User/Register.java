package User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.s_gempong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import MenuSettingsApps.User; // Menyertakan kelas User

public class Register extends AppCompatActivity {
    private EditText emailET, passwordET, nameET;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, adminRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.green1));


        emailET = findViewById(R.id.email_ET);
        passwordET = findViewById(R.id.password_ET);
        nameET = findViewById(R.id.name_ET);
        Button registerBTN = findViewById(R.id.Register_BTN);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        adminRef = FirebaseDatabase.getInstance().getReference("admin"); // Menyimpan admin pertama

        registerBTN.setOnClickListener(view -> registerUser());


    }



    private void registerUser() {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String name = nameET.getText().toString().trim();

        // Validasi input
        if (name.isEmpty()) {
            nameET.setError("Nama e di isi");
            nameET.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailET.setError("Email e di isi");
            emailET.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Seng tepak email e bro");
            emailET.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordET.setError("Gawe password sek");
            passwordET.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordET.setError("Minimal 6 karakter bro");
            passwordET.requestFocus();
            return;
        }

        // Membuat akun dengan email dan password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        User user = new User(name, email);

                        // Periksa apakah admin sudah ada
                        adminRef.get().addOnCompleteListener(adminTask -> {
                            if (adminTask.isSuccessful()) {
                                DataSnapshot snapshot = adminTask.getResult();

                                if (!snapshot.exists()) {
                                    user.setRole("admin");
                                    adminRef.child("email").setValue(email);
                                } else {
                                    user.setRole("user");
                                }

                                mDatabase.child(userId).setValue(user)
                                        .addOnCompleteListener(saveTask -> {
                                            if (saveTask.isSuccessful()) {
                                                Toast.makeText(Register.this, "Sugeng Rawuh", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Register.this, Login.class));
                                            } else {
                                                Toast.makeText(Register.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } else {
                                Toast.makeText(Register.this, "Gagal memeriksa status admin", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(Register.this, "Gagal bro daftar mu", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
