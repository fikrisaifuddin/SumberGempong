package MenuSettingsApps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SetHomestay extends Fragment {
    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;
    private ArrayList<Homestay> homestayList;
    private HomestayAdapter homestayAdapter;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_homestay, container, false);

        Toolbar toolbar = view.findViewById(R.id.top_setting);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        fabAdd = view.findViewById(R.id.fab_add);
        recyclerView = view.findViewById(R.id.all_homestay);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference("homestays");
        homestayList = new ArrayList<>();
        homestayAdapter = new HomestayAdapter(homestayList, databaseReference, getParentFragmentManager());
        recyclerView.setAdapter(homestayAdapter);
        recyclerView.setAdapter(homestayAdapter);

        loadHomestaysFromFirebase();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomestayAddActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadHomestaysFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                homestayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Homestay homestay = snapshot.getValue(Homestay.class);
                    homestayList.add(homestay);
                }
                homestayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
