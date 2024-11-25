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

public class WahanaAddActivity extends AppCompatActivity {

    private EditText namaWahana, hargaWahana;
    private Button addButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wahana);

        namaWahana = findViewById(R.id.NamaWahana);
        hargaWahana = findViewById(R.id.HargaWahana);
        addButton = findViewById(R.id.button_add_wahana);

        databaseReference = FirebaseDatabase.getInstance().getReference("wahana");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWahana();
            }
        });
    }

    private void addWahana() {
        String nama = namaWahana.getText().toString().trim();
        String harga = hargaWahana.getText().toString().trim();

        if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(harga)) {
            String id = databaseReference.push().getKey();
            Wahana wahana = new Wahana(id, nama, harga);
            databaseReference.child(id).setValue(wahana);
            Toast.makeText(this, "Wahana added", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
