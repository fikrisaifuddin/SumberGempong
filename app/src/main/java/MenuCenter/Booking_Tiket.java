package MenuCenter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.s_gempong.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class Booking_Tiket extends Fragment {

    private EditText etTanggal;
    private Calendar checkInDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_booking_tiket, container, false);

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
