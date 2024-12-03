package MenuSettingsApps;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.s_gempong.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddVerificationAcara extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_verification_acara);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.green1));


        EditText kodeBookingEditText = findViewById(R.id.kodebooking);
        EditText tanggalEditText = findViewById(R.id.tanggal);
        EditText tempatEditText = findViewById(R.id.tempat);
        Button verifikasiButton = findViewById(R.id.button_add_makanan);

        databaseReference = FirebaseDatabase.getInstance().getReference("verifikasi");


        verifikasiButton.setOnClickListener(v -> {

            String kodeBooking = kodeBookingEditText.getText().toString();
            String tanggal = tanggalEditText.getText().toString();
            String tempat = tempatEditText.getText().toString();


            if (!kodeBooking.isEmpty() && !tanggal.isEmpty()) {
                // Menyimpan data verifikasi ke Firebase
                saveVerificationData(kodeBooking, tanggal, tempat);
            } else {
                // Menampilkan pesan jika ada data yang belum diisi
                Toast.makeText(AddVerificationAcara.this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            }
        });

        tanggalEditText.setOnClickListener(v -> showDatePicker(tanggalEditText));
    }

    private void showDatePicker(EditText tanggalEditText) {
        // Mendapatkan tanggal saat ini
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Membuat DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddVerificationAcara.this,
                (view, year1, month1, dayOfMonth1) -> {
                    // Format tanggal yang dipilih
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year1, month1, dayOfMonth1);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedDate.getTime());

                    tanggalEditText.setText(formattedDate);
                },
                year, month, dayOfMonth);

        // Menampilkan DatePickerDialog
        datePickerDialog.show();
    }

    private void saveVerificationData(String kodeBooking, String tanggal,String tempat) {
        String id = databaseReference.push().getKey();

        AcaraVerification verification = new AcaraVerification(id, kodeBooking, tanggal, tempat);

        if (id != null) {
            databaseReference.child("Acara")
                    .child(id)  // ID unik untuk setiap acara
                    .setValue(verification)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AddVerificationAcara.this, "Verifikasi berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddVerificationAcara.this, "Gagal menyimpan verifikasi", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
