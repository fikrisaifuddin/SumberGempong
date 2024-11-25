package MenuSettingsApps;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.s_gempong.R;

public class SetHargaTiket extends Fragment {

    private EditText HargaTiketMasuk, HargaParkirMotor, HargaParkirMobil, HargaParkirElf;
    private Button btnUpdate;
    private DatabaseReference databaseReference;
    private boolean isUpdate = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout fragment
        View view = inflater.inflate(R.layout.set_harga_tiket, container, false);

        Toolbar toolbar = view.findViewById(R.id.top_setting);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        HargaTiketMasuk = view.findViewById(R.id.harga_tiket_masuk);
        HargaParkirMotor = view.findViewById(R.id.harga_parkir_motor);
        HargaParkirMobil = view.findViewById(R.id.harga_parkir_mobil);
        HargaParkirElf = view.findViewById(R.id.harga_parkir_elf);
        btnUpdate = view.findViewById(R.id.btn_update);

        databaseReference = FirebaseDatabase.getInstance().getReference("tiket");

        checkIfDataExists();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrUpdateTiket();
            }
        });

        return view;
    }

    private void checkIfDataExists() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    isUpdate = true;

                    String HTM = snapshot.child("Tiket").child("htm").getValue(String.class);
                    String HTPmotor = snapshot.child("Parkir").child("motor").getValue(String.class);
                    String HTPmobil = snapshot.child("Parkir").child("mobil").getValue(String.class);
                    String HTPelf = snapshot.child("Parkir").child("elf").getValue(String.class);

                    HargaTiketMasuk.setText(HTM);
                    HargaParkirMotor.setText(HTPmotor);
                    HargaParkirMobil.setText(HTPmobil);
                    HargaParkirElf.setText(HTPelf);
                } else {
                    isUpdate = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveOrUpdateTiket() {
        // Ambil input dari EditText
        String HTM = HargaTiketMasuk.getText().toString();
        String HTPmotor = HargaParkirMotor.getText().toString();
        String HTPmobil = HargaParkirMobil.getText().toString();
        String HTPelf = HargaParkirElf.getText().toString();

        // Validasi input
        if (TextUtils.isEmpty(HTM) || TextUtils.isEmpty(HTPmotor) || TextUtils.isEmpty(HTPmobil) || TextUtils.isEmpty(HTPelf)) {
            Toast.makeText(getActivity(), "Semua harga tiket harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat data tiket dalam bentuk HashMap
        databaseReference.child("Tiket").child("htm").setValue(HTM);
        databaseReference.child("Parkir").child("motor").setValue(HTPmotor);
        databaseReference.child("Parkir").child("mobil").setValue(HTPmobil);
        databaseReference.child("Parkir").child("elf").setValue(HTPelf);

        if (isUpdate) {
            Toast.makeText(getActivity(), "Harga tiket berhasil diperbarui", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Harga tiket berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        }
    }
}
