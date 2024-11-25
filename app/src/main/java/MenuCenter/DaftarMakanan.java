package MenuCenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import MenuSettingsApps.Makanan;
import Navigation.Home;

public class DaftarMakanan extends Fragment {

    private RecyclerView recyclerView;
    private DaftarMakananAdapter makananAdapter;
    private ArrayList<Makanan> makananList;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_makanan, container, false);

        // Inisialisasi RecyclerView dan DatabaseReference
        recyclerView = view.findViewById(R.id.recyler_view_makanan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        makananList = new ArrayList<>();
        makananAdapter = new DaftarMakananAdapter(makananList);
        recyclerView.setAdapter(makananAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("makanan");

        // Ambil data makanan dari Firebase
        loadMakananFromFirebase();

        // Handling UI Buttons (Button Makanan & Oleh-oleh)
        RadioButton buttonMakanan = view.findViewById(R.id.button_makanan);
        RadioButton buttonOlehOleh = view.findViewById(R.id.button_oleholeh);

        buttonMakanan.setOnClickListener(v -> {
            buttonMakanan.setBackgroundResource(R.drawable.rounded_button_selected);
            buttonMakanan.setTextColor(getResources().getColor(R.color.white));
            buttonOlehOleh.setBackgroundResource(R.drawable.rounded_button_unselected);
            buttonOlehOleh.setTextColor(getResources().getColor(R.color.black));
        });

        buttonOlehOleh.setOnClickListener(v -> {
            buttonOlehOleh.setBackgroundResource(R.drawable.rounded_button_selected);
            buttonOlehOleh.setTextColor(getResources().getColor(R.color.white));
            buttonMakanan.setBackgroundResource(R.drawable.rounded_button_unselected);
            buttonMakanan.setTextColor(getResources().getColor(R.color.black));

            // Panggil fragment DaftarOlehOleh
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DaftarOlehOleh())
                    .addToBackStack(null)
                    .commit();
        });
        Toolbar toolbar = view.findViewById(R.id.top_setting);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBack(activity);
            }
        });

        return view;
    }
    private void navigateBack(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Home()); // Pastikan ID container sesuai
        fragmentTransaction.commit();
    }
    // Fungsi untuk memuat data makanan dari Firebase
    private void loadMakananFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                makananList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Makanan makanan = snapshot.getValue(Makanan.class);
                    makananList.add(makanan);
                }
                makananAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
