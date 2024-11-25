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

public class OleholehAddActivity  extends AppCompatActivity {

    private EditText namaOleholeh, hargaOleholeh;
    private Button addButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_oleholeh);

        namaOleholeh = findViewById(R.id.NamaOleholeh);
        hargaOleholeh = findViewById(R.id.HargaOleholeh);
        addButton = findViewById(R.id.button_add_oleholeh);

        databaseReference = FirebaseDatabase.getInstance().getReference("oleholeh");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOleholeh();
            }
        });
    }

    private void addOleholeh() {
        String nama = namaOleholeh.getText().toString().trim();
        String harga = hargaOleholeh.getText().toString().trim();

        if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(harga) && !TextUtils.isEmpty(harga)) {
            String id = databaseReference.push().getKey();
            Oleholeh oleholeh = new Oleholeh(id, nama, harga);
            databaseReference.child(id).setValue(oleholeh);
            Toast.makeText(this, "Oleh-oleh added", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
