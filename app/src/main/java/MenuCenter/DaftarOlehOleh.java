package MenuCenter;

import android.graphics.Color;
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
import MenuSettingsApps.Oleholeh;
import Navigation.Home;

public class DaftarOlehOleh extends Fragment {

    private RecyclerView recyclerView;
    private DaftarOleholehAdapter oleholehAdapter;
    private ArrayList<Oleholeh> oleholehList;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_oleholeh, container, false);

        recyclerView = view.findViewById(R.id.recyler_view_oleholeh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        oleholehList = new ArrayList<>();
        oleholehAdapter = new DaftarOleholehAdapter(oleholehList);
        recyclerView.setAdapter(oleholehAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("oleholeh");

        // Ambil data makanan dari Firebase
        loadOleholehFromFirebase();

        RadioButton buttonMakanan = view.findViewById(R.id.button_makanan);
        RadioButton buttonOlehOleh = view.findViewById(R.id.button_oleholeh);

        buttonOlehOleh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMakanan.setBackgroundResource(R.drawable.rounded_button_selected);
                buttonMakanan.setTextColor(Color.WHITE);

                buttonOlehOleh.setBackgroundResource(R.drawable.rounded_button_unselected);
                buttonOlehOleh.setTextColor(Color.BLACK);
            }
        });

        buttonMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOlehOleh.setBackgroundResource(R.drawable.rounded_button_selected);
                buttonOlehOleh.setTextColor(Color.WHITE);

                buttonMakanan.setBackgroundResource(R.drawable.rounded_button_unselected);
                buttonMakanan.setTextColor(Color.BLACK);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new DaftarMakanan());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
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
    private void loadOleholehFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                oleholehList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Oleholeh oleholeh = snapshot.getValue(Oleholeh.class);
                    oleholehList.add(oleholeh);
                }
                oleholehAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
