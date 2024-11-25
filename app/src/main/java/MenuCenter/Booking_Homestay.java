package MenuCenter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import com.example.s_gempong.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import MenuSettingsApps.Homestay;

public class Booking_Homestay extends Fragment {

    private EditText etTanggal, elTanggal, etHarga, etJumlahPengunjung;
    private String selectedHomestay;
    private Calendar checkInDate;
    private ArrayList<Homestay> homestayList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_booking_homestay, container, false);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = view.findViewById(R.id.top_setting);
        Button okeButton = view.findViewById(R.id.btn_oke);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        Spinner spinner = view.findViewById(R.id.spinner_Bookinghomestay);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        loadHomestaysFromFirebase(adapter, spinner);
        etTanggal = view.findViewById(R.id.etTanggalin);
        etTanggal.setOnClickListener(v -> showDatePicker(v, etTanggal, null)); // Check-in date
        elTanggal = view.findViewById(R.id.etTanggalout);
        elTanggal.setOnClickListener(v -> showDatePicker(v, elTanggal, checkInDate)); // Check-out date
        etHarga = view.findViewById(R.id.TotalHarga);
        etJumlahPengunjung = view.findViewById(R.id.pengunjung);
        okeButton.setOnClickListener(v -> {
            if (validateForm()) {
                saveBookingToFirebase();
                sendWhatsAppMessage();
                etTanggal.setText("");
                elTanggal.setText("");
                etHarga.setText("");
                etJumlahPengunjung.setText("");

                Snackbar.make(getView(), "Booking berhasil.", Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void sendWhatsAppMessage() {
        String adminNumber = "6282140258470";

        String bookingCode = generateUniqueCode();

        String message = "Halo Admin,\n" +
                "Saya ingin melakukan booking homestay dengan detail sebagai berikut:\n" +
                "Homestay: " + selectedHomestay + "\n" +
                "Tanggal Masuk: " + etTanggal.getText().toString() + "\n" +
                "Tanggal Keluar: " + elTanggal.getText().toString() + "\n" +
                "Jumlah Pengunjung: " + etJumlahPengunjung.getText().toString() + "\n" +
                "Total Harga: " + etHarga.getText().toString() + "\n" +
                "Kode Booking: " + bookingCode + "\n" +
                "Mohon informasi untuk pembayaran DP. Terima kasih.";

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + adminNumber + "&text=" + Uri.encode(message);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Snackbar.make(getView(), "Gagal membuka WhatsApp. Pastikan WhatsApp terpasang.", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker(View v, EditText dateEditText, Calendar minDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);

                    if (minDate != null && selectedDate.before(minDate)) {
                        Snackbar.make(v, "Tanggal keluar harus lebih dari tanggal masuk!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    String formattedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    dateEditText.setText(formattedDate);

                    if (dateEditText == etTanggal) {
                        checkInDate = selectedDate;
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        if (minDate != null) {
            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis() + 86400000); // 1 day after check-in
        }

        datePickerDialog.show();
    }

    private void loadHomestaysFromFirebase(final ArrayAdapter<String> adapter, Spinner spinner) {
        databaseReference = FirebaseDatabase.getInstance().getReference("homestays");
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                homestayList.clear();
                ArrayList<String> names = new ArrayList<>();
                for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Homestay homestay = snapshot.getValue(Homestay.class);
                    if (homestay != null && homestay.getNama() != null) {
                        homestayList.add(homestay);
                        names.add(homestay.getNama());
                    }
                }
                adapter.clear();
                adapter.addAll(names);
                adapter.notifyDataSetChanged();

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        if (position < homestayList.size()) {
                            Homestay selected = homestayList.get(position);
                            etHarga.setText(selected.getHarga());
                            selectedHomestay = selected.getNama();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {
                Snackbar.make(getView(), "Gagal memuat data homestay", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private String generateUniqueCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(36); // Menghasilkan angka antara 0-35
            if (digit < 10) {
                code.append(digit);  // Menambahkan angka (0-9)
            } else {
                code.append((char) ('A' + digit - 10));  // Menambahkan huruf (A-Z)
            }
        }
        return code.toString();
    }


    private boolean validateForm() {
        String checkIn = etTanggal.getText().toString();
        String checkOut = elTanggal.getText().toString();
        String totalPrice = etHarga.getText().toString();
        String jumlahPengunjung = etJumlahPengunjung.getText().toString();

        if (checkIn.isEmpty() || checkOut.isEmpty() || totalPrice.isEmpty() || jumlahPengunjung.isEmpty()) {
            Snackbar.make(getView(), "Harap lengkapi semua kolom", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveBookingToFirebase() {
        String checkIn = etTanggal.getText().toString();
        String checkOut = elTanggal.getText().toString();
        String totalPrice = etHarga.getText().toString();
        String jumlahPengunjung = etJumlahPengunjung.getText().toString();
        String userId = mAuth.getCurrentUser().getUid();

        String bookingCode = generateUniqueCode();

        HomestayBooking booking = new HomestayBooking(checkIn, checkOut, selectedHomestay, Integer.parseInt(jumlahPengunjung), Double.parseDouble(totalPrice), userId, bookingCode);

        DatabaseReference bookingReference = FirebaseDatabase.getInstance().getReference("bookinghomestay").child(userId);
        String bookingId = bookingReference.push().getKey();

        if (bookingId != null) {
            bookingReference.child(bookingId).setValue(booking)
                    .addOnSuccessListener(aVoid -> Snackbar.make(getView(), "Booking berhasil disimpan", Snackbar.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Snackbar.make(getView(), "Gagal menyimpan booking", Snackbar.LENGTH_SHORT).show());
        }
    }
}
