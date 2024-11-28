package Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PesananSaya extends Fragment {

    private RecyclerView recyclerView;
    private PesananAdapter pesananAdapter;
    private List<Object> pesananList;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_acara, container, false);

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.all_pesanan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pesananList = new ArrayList<>();
        pesananAdapter = new PesananAdapter(pesananList);
        recyclerView.setAdapter(pesananAdapter);

        // Get current user
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference homestayRef = FirebaseDatabase.getInstance().getReference("bookinghomestay").child(userId);
            DatabaseReference acaraRef = FirebaseDatabase.getInstance().getReference("acara").child(userId);

            fetchHomestayData(homestayRef);
            fetchAcaraData(acaraRef);
        }

        return view;
    }

    private void fetchHomestayData(DatabaseReference reference) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    HomestayBooking homestayData = data.getValue(HomestayBooking.class);
                    pesananList.add(homestayData);
                }
                pesananAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    private void fetchAcaraData(DatabaseReference reference) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    AcaraBooking acaraData = data.getValue(AcaraBooking.class);
                    pesananList.add(acaraData);
                }
                pesananAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }
}
