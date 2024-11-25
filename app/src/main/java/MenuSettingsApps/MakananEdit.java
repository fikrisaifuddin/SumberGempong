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

public class MakananEdit extends AppCompatActivity {

    private EditText editNamaMakanan, editHargaMakanan;
    private Button btnSave;
    private DatabaseReference databaseReference;
    private String makananId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_makanan);

        // Inisialisasi Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("makanan");

        // Inisialisasi komponen UI
        editNamaMakanan = findViewById(R.id.edit_nama_makanan);
        editHargaMakanan = findViewById(R.id.edit_harga_makanan);
        btnSave = findViewById(R.id.btn_save);

        // Ambil data yang dikirim melalui Intent
        if (getIntent() != null) {
            makananId = getIntent().getStringExtra("makananId");
            editNamaMakanan.setText(getIntent().getStringExtra("nama"));
            editHargaMakanan.setText(getIntent().getStringExtra("harga"));
        }

        // Simpan perubahan
        btnSave.setOnClickListener(v -> {
            String nama = editNamaMakanan.getText().toString();
            String harga = editHargaMakanan.getText().toString();

            if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(harga)) {
                Toast.makeText(MakananEdit.this, "Nama dan harga tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update data di Firebase
            databaseReference.child(makananId).child("nama").setValue(nama);
            databaseReference.child(makananId).child("harga").setValue(harga);

            Toast.makeText(MakananEdit.this, "Makanan berhasil diupdate", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
