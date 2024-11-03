package MenuCenter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.s_gempong.R;

import Navigation.Home;

public class DaftarMakanan extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_makanan, container, false);

        RadioButton buttonMakanan = view.findViewById(R.id.button_makanan);
        RadioButton buttonOlehOleh = view.findViewById(R.id.button_oleholeh);

        // Handle click event for button Makanan
        buttonMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMakanan.setBackgroundResource(R.drawable.rounded_button_selected);
                buttonMakanan.setTextColor(Color.WHITE);

                buttonOlehOleh.setBackgroundResource(R.drawable.rounded_button_unselected);
                buttonOlehOleh.setTextColor(Color.BLACK);

                // Jika butuh tambahan logika untuk fragment makanan bisa ditambahkan disini
            }
        });

        // Handle click event for button Oleh Oleh
        buttonOlehOleh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOlehOleh.setBackgroundResource(R.drawable.rounded_button_selected);
                buttonOlehOleh.setTextColor(Color.WHITE);

                buttonMakanan.setBackgroundResource(R.drawable.rounded_button_unselected);
                buttonMakanan.setTextColor(Color.BLACK);

                // Panggil fragment DaftarOlehOleh
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new DaftarOlehOleh()); // Ganti fragment_container dengan ID container yang sesuai
                fragmentTransaction.addToBackStack(null);  // Agar bisa kembali ke fragment sebelumnya
                fragmentTransaction.commit();
            }
        });

        // Set up toolbar
        Toolbar toolbar = view.findViewById(R.id.top_setting);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Handle back navigation
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBack(activity);
            }
        });

        return view;
    }

    // Handle back navigation logic
    private void navigateBack(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        // Abaikan backstack dan langsung ganti dengan HomeFragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Home()); // Pastikan ID container sesuai
        fragmentTransaction.commit();
    }

}
