package MenuSettingsApps;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.s_gempong.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddVerificationHomestay extends AppCompatActivity {

    private String namaHomestay;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_verification_homestay);

        // Menangani status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.green1));

        // Mengambil nama homestay yang dikirimkan dari aktivitas sebelumnya
        if (getIntent() != null) {
            namaHomestay = getIntent().getStringExtra("namaHomestay");
        }

        // Menampilkan nama homestay di UI jika ada
        TextView homestayNameTextView = findViewById(R.id.homestayName);
        if (homestayNameTextView != null && namaHomestay != null) {
            homestayNameTextView.setText(namaHomestay);
        }

        EditText kodeBookingEditText = findViewById(R.id.kodebooking);
        EditText tanggalEditText = findViewById(R.id.tanggal);
        Button verifikasiButton = findViewById(R.id.button_add_makanan);

        databaseReference = FirebaseDatabase.getInstance().getReference("verifikasi");

        verifikasiButton.setOnClickListener(v -> {
            String kodeBooking = kodeBookingEditText.getText().toString();
            String tanggal = tanggalEditText.getText().toString();

            if (!kodeBooking.isEmpty() && !tanggal.isEmpty() && namaHomestay != null) {
                saveVerificationData(kodeBooking, tanggal, namaHomestay);
            } else {
                Toast.makeText(AddVerificationHomestay.this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
        tanggalEditText.setOnClickListener(v -> showDatePicker(tanggalEditText));
    }

    private void showDatePicker(EditText tanggalEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddVerificationHomestay.this,
                (view, year1, month1, dayOfMonth1) -> {
                    // Format tanggal yang dipilih
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year1, month1, dayOfMonth1);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedDate.getTime());

                    // Menampilkan tanggal yang dipilih ke EditText
                    tanggalEditText.setText(formattedDate);
                },
                year, month, dayOfMonth);

        // Menampilkan DatePickerDialog
        datePickerDialog.show();
    }

    // Fungsi untuk menyimpan data verifikasi ke Firebase
    private void saveVerificationData(String kodeBooking, String tanggal, String namaHomestay) {
        // Membuat ID unik untuk setiap data verifikasi
        String id = databaseReference.push().getKey();

        // Membuat objek untuk data verifikasi
        HomestayVerification verification = new HomestayVerification(id, kodeBooking, tanggal);

        // Menyimpan data verifikasi ke Firebase
        if (id != null) {
            databaseReference.child("homestay")
                    .child(namaHomestay)
                    .child(id)
                    .setValue(verification)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AddVerificationHomestay.this, "Verifikasi berhasil ditambahkan untuk " + namaHomestay, Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddVerificationHomestay.this, "Gagal menyimpan verifikasi", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // Kelas untuk data verifikasi

}
