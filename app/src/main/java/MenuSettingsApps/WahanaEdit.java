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

public class WahanaEdit extends AppCompatActivity {

    private EditText editNamaWahana, editHargaWahana;
    private Button btnSave;
    private DatabaseReference databaseReference;
    private String wahanaId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_wahana);

        // Inisialisasi Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("wahana");

        // Inisialisasi komponen UI
        editNamaWahana = findViewById(R.id.edit_nama_wahana);
        editHargaWahana = findViewById(R.id.edit_harga_wahana);
        btnSave = findViewById(R.id.btn_save);

        // Ambil data yang dikirim melalui Intent
        if (getIntent() != null) {
            wahanaId = getIntent().getStringExtra("wahanaId");
            editNamaWahana.setText(getIntent().getStringExtra("nama"));
            editHargaWahana.setText(getIntent().getStringExtra("harga"));
        }

        // Simpan perubahan
        btnSave.setOnClickListener(v -> {
            String nama = editNamaWahana.getText().toString();
            String harga = editHargaWahana.getText().toString();

            if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(harga)) {
                Toast.makeText(WahanaEdit.this, "Nama dan harga tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update data di Firebase
            databaseReference.child(wahanaId).child("nama").setValue(nama);
            databaseReference.child(wahanaId).child("harga").setValue(harga);

            Toast.makeText(WahanaEdit.this, "Wahana berhasil diupdate", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
