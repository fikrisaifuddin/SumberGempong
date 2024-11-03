package Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.s_gempong.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import MenuCenter.Booking_Homestay;
import MenuCenter.Booking_Tiket;
import MenuCenter.Booking_For_Acara;
import MenuCenter.DaftarMakanan;
import MenuCenter.DaftarWahana;

public class Home extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_home, container, false);

        ImageButton tiketButton = view.findViewById(R.id.Tiket);
        ImageButton acaraButton = view.findViewById(R.id.Acara);
        ImageButton homestayButton = view.findViewById(R.id.homestay);
        ImageButton UmkmButton = view.findViewById(R.id.Umkm);
        ImageButton wahanaButton = view.findViewById(R.id.Wahana);
        Button cekButton = view.findViewById(R.id.btn_cek);

        tiketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new Booking_Tiket());
            }
        });

        acaraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new Booking_For_Acara());
            }
        });

        UmkmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new DaftarMakanan());
            }
        });


        wahanaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new DaftarWahana());
            }
        });

        homestayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new Booking_Homestay());
            }
        });

        cekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });



        return view;
    }


    private void showBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());

        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_set_dialog, null);
        TextView tvInformasi = bottomSheetView.findViewById(R.id.tInformasi);
        TextView tvHargaTiket = bottomSheetView.findViewById(R.id.tHargaTiket);
        TextView tvHargaHomestay = bottomSheetView.findViewById(R.id.tHargaHomestay);

        tvInformasi.setText("Informasi Harga :");
        tvHargaTiket.setText("Harga tiket per orang: Rp50.000");
        tvHargaHomestay.setText("Harga booking homestay: Rp300.000 per malam");

        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetDialog.show();
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
