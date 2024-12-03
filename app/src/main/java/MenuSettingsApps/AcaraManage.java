package MenuSettingsApps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

public class AcaraManage extends Fragment {

    private RecyclerView recyclerView;
    private AcaraVerificationAdapter adapter;
    private ArrayList<AcaraVerification> verificationList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_acara, container, false);

        Toolbar toolbar = view.findViewById(R.id.top_setting);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        recyclerView = view.findViewById(R.id.all_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        verificationList = new ArrayList<>();
        adapter = new AcaraVerificationAdapter(verificationList);
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


        Button addButton = view.findViewById(R.id.add);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddVerificationAcara.class);
            startActivity(intent);
        });

        loadAllData();


        return view;
    }

    private void searchByKodeBooking(String kodeBooking) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("verifikasi/Acara");

        Query query = reference.orderByChild("kodeBooking").equalTo(kodeBooking);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                verificationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AcaraVerification data = dataSnapshot.getValue(AcaraVerification.class);
                    if (data != null) {
                        verificationList.add(data);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void loadAllData() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("verifikasi/Acara");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                verificationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AcaraVerification data = dataSnapshot.getValue(AcaraVerification.class);
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

    private void navigateBack(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            activity.finish();
        }
    }
}
