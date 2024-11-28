package MenuCenter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.s_gempong.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class Booking_For_Acara extends Fragment {

    private EditText Penyelenggara, NamaAcara, etTanggal, etJumlahPengunjung, etTotalHarga;
    private Calendar checkInDate;
    private DatabaseReference databaseReference;
    private int hargaTiketMasuk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_booking_acara, container, false);

        etTanggal = view.findViewById(R.id.etTanggal);
        etJumlahPengunjung = view.findViewById(R.id.jmlh_pengunjung);
        etTotalHarga = view.findViewById(R.id.total);
        Penyelenggara = view.findViewById(R.id.penyelenggara);
        NamaAcara = view.findViewById(R.id.namaacara);

        databaseReference = FirebaseDatabase.getInstance().getReference("tiket");

        // Ambil harga HTM dari database
        fetchTicketPrice();

        etTanggal.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view1, year1, month1, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year1, month1, dayOfMonth);

                        Calendar today = Calendar.getInstance();
                        today.set(Calendar.HOUR_OF_DAY, 0);
                        today.set(Calendar.MINUTE, 0);
                        today.set(Calendar.SECOND, 0);
                        today.set(Calendar.MILLISECOND, 0);

                        if (selectedDate.before(today)) {
                            Snackbar.make(v, "Tanggal tidak boleh kurang dari hari ini!", Snackbar.LENGTH_LONG).show();
                        } else {
                            checkInDate = selectedDate;

                            String selectedDateStr = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                            etTanggal.setText(selectedDateStr);

                            calculateTotalPrice();
                        }
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        etJumlahPengunjung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                calculateTotalPrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        Button okeButton = view.findViewById(R.id.btn_oke);
        okeButton.setOnClickListener(v -> {
            String tanggal = etTanggal.getText().toString();
            String jumlahPengunjungStr = etJumlahPengunjung.getText().toString();
            String penyelenggara = Penyelenggara.getText().toString();
            String namaacara = NamaAcara.getText().toString();

            if (tanggal.isEmpty() || jumlahPengunjungStr.isEmpty()) {
                Snackbar.make(v, "Semua data harus diisi!", Snackbar.LENGTH_LONG).show();
                return;
            }

            if (penyelenggara.isEmpty() || namaacara.isEmpty()) {
                Snackbar.make(v, "Penyelenggara dan Nama Acara harus diisi!", Snackbar.LENGTH_LONG).show();
                return;
            }

            int jumlahPengunjung = Integer.parseInt(jumlahPengunjungStr);
            int totalHarga = Integer.parseInt(etTotalHarga.getText().toString());
            String bookingCode = generateUniqueCode();

            // Simpan ke Firebase
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference userTransactionRef = FirebaseDatabase.getInstance().getReference("acara").child(userId);
            String transaksiId = userTransactionRef.push().getKey();

            if (transaksiId != null) {
                AcaraBooking bookingData = new AcaraBooking(bookingCode, tanggal, penyelenggara, namaacara, jumlahPengunjung, totalHarga);
                userTransactionRef.child(transaksiId).setValue(bookingData)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Snackbar.make(v, "Tiket berhasil dipesan!", Snackbar.LENGTH_LONG).show();
                                sendWhatsAppMessage(bookingCode, penyelenggara, namaacara, tanggal, jumlahPengunjung, totalHarga);
                                clearFields();
                            } else {
                                Snackbar.make(v, "Gagal menyimpan transaksi.", Snackbar.LENGTH_LONG).show();
                            }
                        });
            }
        });

        return view;
    }

    private void fetchTicketPrice() {
        databaseReference.child("Tiket").child("htm").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    hargaTiketMasuk = Integer.parseInt(snapshot.getValue(String.class));
                } else {
                    Snackbar.make(getView(), "Harga tiket belum diatur.", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(getView(), "Gagal memuat harga tiket.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void calculateTotalPrice() {
        String jumlahPengunjungStr = etJumlahPengunjung.getText().toString();

        if (jumlahPengunjungStr.isEmpty() || checkInDate == null) {
            return;
        }

        int jumlahPengunjung = Integer.parseInt(jumlahPengunjungStr);
        int totalHarga = hargaTiketMasuk * jumlahPengunjung;
        etTotalHarga.setText(String.valueOf(totalHarga));
    }

    private void clearFields() {
        etTanggal.setText("");
        etJumlahPengunjung.setText("");
        etTotalHarga.setText("");
        Penyelenggara.setText("");
        NamaAcara.setText("");
    }

    private String generateUniqueCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(36);
            if (digit < 10) {
                code.append(digit);
            } else {
                code.append((char) ('A' + digit - 10));
            }
        }
        return code.toString();
    }

    private void sendWhatsAppMessage(String bookingCode, String penyelenggara, String namaAcara, String tanggal, int jumlahPengunjung, int totalHarga) {
        String message = "Halo,\n" +
                "Berikut adalah detail booking Anda:\n" +
                "- Kode Booking: " + bookingCode + "\n" +
                "- Penyelenggara: " + penyelenggara + "\n" +
                "- Nama Acara: " + namaAcara + "\n" +
                "- Tanggal: " + tanggal + "\n" +
                "- Jumlah Pengunjung: " + jumlahPengunjung + "\n" +
                "- Total Harga: Rp " + totalHarga + "\n\n" +
                "Terima kasih telah menggunakan layanan kami!";

        String phoneNumber = "6282140258470";
        String whatsappUrl = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(whatsappUrl));
        startActivity(intent);
    }
}
