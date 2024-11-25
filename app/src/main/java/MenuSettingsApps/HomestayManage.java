package MenuSettingsApps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.s_gempong.R;

import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomestayManage extends Fragment {
    private static final String ARG_NAMA_HOMESTAY = "nama_homestay";
    private String namaHomestay;

    public static HomestayManage newInstance(String namaHomestay) {
        HomestayManage fragment = new HomestayManage();
        Bundle args = new Bundle();
        args.putString(ARG_NAMA_HOMESTAY, namaHomestay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            namaHomestay = getArguments().getString(ARG_NAMA_HOMESTAY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_homestay, container, false);
        Toolbar toolbar = view.findViewById(R.id.top_setting);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        TextView textNamaHomestay = view.findViewById(R.id.NamaHomestay);
        if (namaHomestay != null) {
            textNamaHomestay.setText(namaHomestay);
        }

        // Set up the DatePickerDialog for Cal_Homestay EditText
        EditText calHomestayEditText = view.findViewById(R.id.Cal_Homestay);
        calHomestayEditText.setOnClickListener(v -> showDatePickerDialog());

        return view;
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Set the selected date to the EditText
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = dateFormat.format(selectedDate.getTime());

                    // Set the selected date into the EditText
                    EditText calHomestayEditText = getView().findViewById(R.id.Cal_Homestay);
                    calHomestayEditText.setText(formattedDate);
                }, year, month, dayOfMonth);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}
