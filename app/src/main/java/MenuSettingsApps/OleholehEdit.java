package MenuSettingsApps;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s_gempong.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OleholehEdit extends AppCompatActivity {

    private EditText editNamaOleholeh, editHargaOleholeh;
    private Button btnSave;
    private DatabaseReference databaseReference;
    private String oleholehId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_oleholeh);

        // Inisialisasi Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("oleholeh");

        // Inisialisasi komponen UI
        editNamaOleholeh = findViewById(R.id.edit_nama_oleholeh);
        editHargaOleholeh = findViewById(R.id.edit_harga_oleholeh);
        btnSave = findViewById(R.id.btn_save);

        // Ambil data yang dikirim melalui Intent
        if (getIntent() != null) {
            oleholehId = getIntent().getStringExtra("oleholehId");
            editNamaOleholeh.setText(getIntent().getStringExtra("nama"));
            editHargaOleholeh.setText(getIntent().getStringExtra("harga"));
        }

        // Simpan perubahan
        btnSave.setOnClickListener(v -> {
            String nama = editNamaOleholeh.getText().toString();
            String harga = editHargaOleholeh.getText().toString();

            if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(harga)) {
                Toast.makeText(OleholehEdit.this, "Nama dan harga tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update data di Firebase
            databaseReference.child(oleholehId).child("nama").setValue(nama);
            databaseReference.child(oleholehId).child("harga").setValue(harga);

            Toast.makeText(OleholehEdit.this, "Makanan berhasil diupdate", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
