package MenuSettingsApps;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.s_gempong.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakananAddActivity  extends AppCompatActivity {

    private EditText namaMakanan, hargaMakanan;
    private Button addButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_makanan);

        namaMakanan = findViewById(R.id.NamaMakanan);
        hargaMakanan = findViewById(R.id.HargaMakanan);
        addButton = findViewById(R.id.button_add_makanan);

        databaseReference = FirebaseDatabase.getInstance().getReference("makanan");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMakanan();
            }
        });
    }

    private void addMakanan() {
        String nama = namaMakanan.getText().toString().trim();
        String harga = hargaMakanan.getText().toString().trim();

        if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(harga) && !TextUtils.isEmpty(harga)) {
            String id = databaseReference.push().getKey();
            Makanan makanan = new Makanan(id, nama, harga);
            databaseReference.child(id).setValue(makanan);
            Toast.makeText(this, "Makanan added", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
