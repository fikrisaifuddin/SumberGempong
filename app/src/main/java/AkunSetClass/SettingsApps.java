package AkunSetClass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.s_gempong.R;

import MenuCenter.Booking_Tiket;
import MenuCenter.DaftarMakanan;
import MenuSettingsApps.SetHomestay;

public class SettingsApps extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_settingapps, container, false);

        Button homestayButton = view.findViewById(R.id.btn_homestay);

        homestayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new SetHomestay());
            }
        });
        return view;

    }
    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

