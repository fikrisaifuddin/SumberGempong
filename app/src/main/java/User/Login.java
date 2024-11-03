package User;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences; // Tambahkan import ini
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s_gempong.DatabaseHelper;
import com.example.s_gempong.MainActivity;
import com.example.s_gempong.R;

public class Login extends AppCompatActivity {
    private DatabaseHelper dataBase;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dataBase = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.emailET);
        editTextPassword = findViewById(R.id.passwordET);
        buttonLogin = findViewById(R.id.loginBTN);
        buttonRegister = findViewById(R.id.registerBTN);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (dataBase.checkUser(email, password)) {
                    // Simpan status login
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true); // Set status login ke true
                    editor.apply(); // Simpan perubahan

                    Toast.makeText(Login.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Login.this, "Login gagal, silakan periksa email dan password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class)); // Pindah ke RegisterActivity
            }
        });
    }
}
