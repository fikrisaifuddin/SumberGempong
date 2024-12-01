package MenuCenter;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.s_gempong.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Booking_Tiket extends Fragment {

    private EditText etTanggal, etJumlahPengunjung, etTotalHarga;
    private Calendar checkInDate;
    private DatabaseReference databaseReference;
    private int hargaTiketMasuk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_booking_tiket, container, false);

        etTanggal = view.findViewById(R.id.etTanggalin);
        etJumlahPengunjung = view.findViewById(R.id.jmlh_pengunjung);
        etTotalHarga = view.findViewById(R.id.total);

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

        Button okeButton = view.findViewById(R.id.btn_ok);
        okeButton.setOnClickListener(v -> {
            String tanggal = etTanggal.getText().toString();
            String jumlahPengunjungStr = etJumlahPengunjung.getText().toString();

            if (tanggal.isEmpty() || jumlahPengunjungStr.isEmpty()) {
                Snackbar.make(v, "Semua data harus diisi!", Snackbar.LENGTH_LONG).show();
                return;
            }

            int jumlahPengunjung = Integer.parseInt(jumlahPengunjungStr);
            int totalHarga = Integer.parseInt(etTotalHarga.getText().toString());

            // Simpan ke Firebase
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference userTransactionRef = FirebaseDatabase.getInstance().getReference("transaksi").child(userId);
            String transaksiId = userTransactionRef.push().getKey();

            if (transaksiId != null) {
                TiketBooking bookingData = new TiketBooking(tanggal, jumlahPengunjung, totalHarga);
                userTransactionRef.child(transaksiId).setValue(bookingData)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Snackbar.make(v, "Tiket berhasil dipesan!", Snackbar.LENGTH_LONG).show();
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
    }
}

