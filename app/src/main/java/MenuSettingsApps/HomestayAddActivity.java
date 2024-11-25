package MenuSettingsApps;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.s_gempong.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomestayAddActivity extends AppCompatActivity {

    private EditText namaHomestay, fasilitasHomestay, hargaHomestay;
    private Button addButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_homestay);

        Toolbar toolbar = findViewById(R.id.top_setting);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Inisialisasi EditText dan Button
        namaHomestay = findViewById(R.id.Nama_homestay);
        fasilitasHomestay = findViewById(R.id.Vasilitas);
        hargaHomestay = findViewById(R.id.Harga_homestay);
        addButton = findViewById(R.id.button_add);

        // Inisialisasi DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("homestays");

        // Set onClickListener untuk tombol tambah
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHomestay();
            }
        });
    }

    private void addHomestay() {
        String nama = namaHomestay.getText().toString().trim();
        String fasilitas = fasilitasHomestay.getText().toString().trim();
        String harga = hargaHomestay.getText().toString().trim();

        if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(fasilitas) && !TextUtils.isEmpty(harga)) {
            String id = databaseReference.push().getKey();
            Homestay homestay = new Homestay(id, nama, fasilitas, harga);
            databaseReference.child(id).setValue(homestay);
            Toast.makeText(this, "Homestay added", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
