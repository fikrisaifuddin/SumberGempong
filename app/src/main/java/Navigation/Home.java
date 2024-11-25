package Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.s_gempong.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import MenuCenter.Booking_Homestay;
import MenuCenter.Booking_Tiket;
import MenuCenter.Booking_For_Acara;
import MenuCenter.DaftarMakanan;
import MenuCenter.DaftarWahana;
import adapter.ImageAdapter;
import adapter.WisataAdapter;

public class Home extends Fragment {

    private RecyclerView recyclerView;
    private List<Wisata> wisataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_home, container, false);

        recyclerView = view.findViewById(R.id.all_wisata);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        ImageAdapter imageAdapter = new ImageAdapter(getContext());
        viewPager.setAdapter(imageAdapter);

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

        wisataList = new ArrayList<>();
        wisataList.add(new Wisata("View Sawah Dari atas", R.drawable.sawah));
        wisataList.add(new Wisata("Gapuro Sumber Gempong", R.drawable.gapuro));
        wisataList.add(new Wisata("Rumah Makan", R.drawable.rumahmakan));
        wisataList.add(new Wisata("Kereta Sawah", R.drawable.kereta));
        wisataList.add(new Wisata("Ayunan Jantra", R.drawable.ayunanjantra));
        wisataList.add(new Wisata("Sepedah Layang & Becak Terbang", R.drawable.sepeda_layang));
        wisataList.add(new Wisata("Bebek Air", R.drawable.bebek_air));
        wisataList.add(new Wisata("Kolam Renang & Terapi Ikan", R.drawable.kolam_renang));



        // Set adapter
        WisataAdapter adapter = new WisataAdapter(getContext(), wisataList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void showBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());

        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_set_dialog, null);

        TextView tvInformasi = bottomSheetView.findViewById(R.id.tInformasi);
        TextView tvHargaTiket = bottomSheetView.findViewById(R.id.tHargaTiket);
        TextView tvHargaHomestay = bottomSheetView.findViewById(R.id.tHargaHomestay);

        tvInformasi.setText("Informasi Harga :");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tiket");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String HTM = snapshot.child("Tiket").child("htm").getValue(String.class);
                    String HTPmotor = snapshot.child("Parkir").child("motor").getValue(String.class);
                    String HTPmobil = snapshot.child("Parkir").child("mobil").getValue(String.class);
                    String HTPelf = snapshot.child("Parkir").child("elf").getValue(String.class);


                    String hargaTiketText =
                            "Harga Tiket Masuk : Rp" + HTM + "\n" +
                            "Harga Parkir Motor: Rp" + HTPmotor + "\n" +
                            "Harga Parkir Mobil: Rp" + HTPmobil + "\n" +
                            "Harga Parkir Elf: Rp" + HTPelf;
                    tvHargaTiket.setText(hargaTiketText);

                } else {

                    tvHargaTiket.setText("Harga tiket belum diset.");
                    tvHargaHomestay.setText("Harga homestay belum tersedia.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });

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
