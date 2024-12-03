package MenuSettingsApps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomestayManage extends Fragment {

    private String namaHomestay;
    private RecyclerView recyclerView;
    private HomestayVerificationAdapter adapter;
    private ArrayList<HomestayVerification> verificationList;

    public static HomestayManage newInstance(String namaHomestay) {
        HomestayManage fragment = new HomestayManage();
        Bundle args = new Bundle();
        args.putString("nama_homestay", namaHomestay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            namaHomestay = getArguments().getString("nama_homestay");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_homestay, container, false);

        // Inisialisasi Toolbar
        Toolbar toolbar = view.findViewById(R.id.top_setting);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        // Tampilkan Nama Homestay
        TextView textNamaHomestay = view.findViewById(R.id.NamaHomestay);
        if (namaHomestay != null) {
            textNamaHomestay.setText(namaHomestay);
        }

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.all_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        verificationList = new ArrayList<>();
        adapter = new HomestayVerificationAdapter(verificationList);
        recyclerView.setAdapter(adapter);

        // Tombol untuk mencari berdasarkan kodeBooking
        EditText kodeBookingEditText = view.findViewById(R.id.kodeBooking);
        Button searchButton = view.findViewById(R.id.search);

        searchButton.setOnClickListener(v -> {
            String kodeBooking = kodeBookingEditText.getText().toString().trim();
            if (!kodeBooking.isEmpty()) {
                searchByKodeBooking(kodeBooking);
            }
        });

        // Tombol untuk menambah data
        Button addButton = view.findViewById(R.id.add);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddVerificationHomestay.class);
            intent.putExtra("namaHomestay", namaHomestay);
            startActivity(intent);
        });


        loadAllData();

        return view;
    }

    private void loadAllData() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("verifikasi/homestay")
                .child(namaHomestay);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                verificationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HomestayVerification data = dataSnapshot.getValue(HomestayVerification.class);
                    if (data != null) {
                        verificationList.add(data);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Tangani error jika diperlukan
            }
        });
    }

    private void searchByKodeBooking(String kodeBooking) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("verifikasi/homestay")
                .child(namaHomestay);

        // Query untuk mencari berdasarkan kodeBooking
        Query query = reference.orderByChild("kodeBooking").equalTo(kodeBooking);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                verificationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HomestayVerification data = dataSnapshot.getValue(HomestayVerification.class);
                    if (data != null) {
                        verificationList.add(data);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Tangani error jika diperlukan
            }
        });
    }
}
