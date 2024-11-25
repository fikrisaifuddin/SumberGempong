package AkunSetClass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.s_gempong.R;

import MenuCenter.Booking_Tiket;
import MenuCenter.DaftarMakanan;
import MenuSettingsApps.SetHargaTiket;
import MenuSettingsApps.SetHomestay;
import MenuSettingsApps.SetMakanan;
import MenuSettingsApps.SetOleholeh;
import MenuSettingsApps.SetPendopo;
import MenuSettingsApps.SetWahana;
import MenuSettingsApps.UserActivity;

public class SettingsApps extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_settingapps, container, false);

        Toolbar toolbar = view.findViewById(R.id.top_setting);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        Button homestayButton = view.findViewById(R.id.btn_homestay);
        Button pendopoButton = view.findViewById(R.id.btn_Pendopo);
        Button tiketButton = view.findViewById(R.id.btn_Tiket);
        Button makananButton = view.findViewById(R.id.btn_makanan);
        Button oleholehButton = view.findViewById(R.id.btn_Oleholeh);
        Button wahanaButton = view.findViewById(R.id.btn_Wahana);
        Button userButton = view.findViewById(R.id.btn_User);

        homestayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new SetHomestay());
            }
        });

        tiketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { switchFragment(new SetHargaTiket()); }
        });

        pendopoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { switchFragment(new SetPendopo()); }
        });

        makananButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { switchFragment(new SetMakanan()); }
        });

        oleholehButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { switchFragment(new SetOleholeh()); }
        });

        wahanaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { switchFragment(new SetWahana()); }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { switchFragment(new UserActivity()); }
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

