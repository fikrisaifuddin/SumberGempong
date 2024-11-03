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

public class Booking_For_Acara extends Fragment {

    private EditText etTanggal;
    private EditText elTanggal;
    private String selectedHomestay;
    private Calendar checkInDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_booking_acara, container, false);

        Button okeButton = view.findViewById(R.id.btn_ok);

        Toolbar toolbar = view.findViewById(R.id.top_setting);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        String[] homestays = {"Homestay A", "Homestay B", "Homestay C", "Tanpa Homestay"};
        Spinner spinner = view.findViewById(R.id.spinner_homestay);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, homestays);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedHomestay = homestays[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        etTanggal = view.findViewById(R.id.etTanggalin);
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Simpan tanggal check-in
                                checkInDate = Calendar.getInstance();
                                checkInDate.set(year, month, dayOfMonth);

                                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                etTanggal.setText(selectedDate);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        elTanggal = view.findViewById(R.id.etTanggalout);
        elTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInDate == null) {
                    // Jika belum memilih tanggal check-in, tampilkan pesan error
                    Snackbar.make(v, "Silakan pilih tanggal check-in terlebih dahulu", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Simpan tanggal check-out
                                Calendar checkOutDate = Calendar.getInstance();
                                checkOutDate.set(year, month, dayOfMonth);

                                // Validasi tanggal keluar harus lebih dari tanggal masuk
                                if (checkOutDate.after(checkInDate)) {
                                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                    elTanggal.setText(selectedDate);
                                } else {
                                    Snackbar.make(v, "Tanggal keluar harus lebih dari tanggal masuk!", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        okeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oke = "Cek pesanan anda di menu tiket";

                Snackbar snackbar = Snackbar.make(v, oke, Snackbar.LENGTH_LONG);

                snackbar.setAnchorView(v);
                snackbar.show();
            }
        });

        return view;
    }
}
