package User;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s_gempong.DatabaseHelper;
import com.example.s_gempong.R;

public class Register extends AppCompatActivity {
    private DatabaseHelper dataBase;
    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dataBase = new DatabaseHelper(this);
        editTextName = findViewById(R.id.name_ET);
        editTextEmail = findViewById(R.id.email_ET);
        editTextPassword = findViewById(R.id.password_ET);
        buttonRegister = findViewById(R.id.Register_BTN);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (dataBase.addUser(name, email, password)) {
                    Toast.makeText(Register.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                    finish(); // Kembali ke activity sebelumnya
                } else {
                    Toast.makeText(Register.this, "Email sudah terdaftar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

