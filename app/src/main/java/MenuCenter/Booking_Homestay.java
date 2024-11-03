package MenuCenter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.s_gempong.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class Booking_Homestay extends Fragment {

    private EditText etTanggal;
    private EditText elTanggal;
    private String selectedHomestay;
    private Calendar checkInDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_booking_homestay, container, false);

        // Inisialisasi Toolbar dan tombol "Oke"
        Toolbar toolbar = view.findViewById(R.id.top_setting);
        Button okeButton = view.findViewById(R.id.btn_oke);

        // Set up Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        // Spinner untuk memilih homestay
        String[] homestays = {"Homestay A", "Homestay B", "Homestay C", "Tanpa Homestay"};
        Spinner spinner = view.findViewById(R.id.spinner_Bookinghomestay);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, homestays);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedHomestay = homestays[position];  // Simpan pilihan homestay
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Tidak ada aksi jika tidak ada yang dipilih
            }
        });

        // Input tanggal check-in
        etTanggal = view.findViewById(R.id.etTanggalin);
        etTanggal.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view1, year1, month1, dayOfMonth) -> {
                        // Simpan tanggal check-in
                        checkInDate = Calendar.getInstance();
                        checkInDate.set(year1, month1, dayOfMonth);

                        // Tampilkan tanggal yang dipilih
                        String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        etTanggal.setText(selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Input tanggal check-out
        elTanggal = view.findViewById(R.id.etTanggalout);
        elTanggal.setOnClickListener(v -> {
            if (checkInDate == null) {
                // Jika tanggal check-in belum dipilih, tampilkan pesan
                Snackbar.make(v, "Silakan pilih tanggal check-in terlebih dahulu", Snackbar.LENGTH_SHORT).show();
                return;
            }

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view12, year12, month12, dayOfMonth) -> {
                        // Simpan tanggal check-out
                        Calendar checkOutDate = Calendar.getInstance();
                        checkOutDate.set(year12, month12, dayOfMonth);

                        // Validasi bahwa tanggal check-out harus lebih dari tanggal check-in
                        if (checkOutDate.after(checkInDate)) {
                            String selectedDate = dayOfMonth + "/" + (month12 + 1) + "/" + year12;
                            elTanggal.setText(selectedDate);
                        } else {
                            Snackbar.make(v, "Tanggal keluar harus lebih dari tanggal masuk!", Snackbar.LENGTH_SHORT).show();
                        }
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Tombol Oke untuk memverifikasi pesanan
        okeButton.setOnClickListener(v -> {
            String pesan = "Cek pesanan anda di menu tiket";
            Snackbar snackbar = Snackbar.make(v, pesan, Snackbar.LENGTH_LONG);
            snackbar.setAnchorView(v);
            snackbar.show();
        });

        return view;
    }
}
