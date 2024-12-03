package MenuCenter;

import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.s_gempong.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

public class Booking_Tiket extends Fragment {

    private EditText etJumlahPengunjung, etTotalHarga;
    private DatabaseReference databaseReference;
    private int hargaTiketMasuk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_booking_tiket, container, false);

        etJumlahPengunjung = view.findViewById(R.id.jmlh_pengunjung);
        etTotalHarga = view.findViewById(R.id.total);

        databaseReference = FirebaseDatabase.getInstance().getReference("tiket");

        fetchTicketPrice();

        loadTransactionData();

        etJumlahPengunjung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                calculateTotalPrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        Button okeButton = view.findViewById(R.id.btn_ok);
        okeButton.setOnClickListener(v -> {
            String jumlahPengunjungStr = etJumlahPengunjung.getText().toString();

            if (jumlahPengunjungStr.isEmpty()) {
                if (getView() != null) {
                    Snackbar.make(v, "Jumlah pengunjung harus diisi!", Snackbar.LENGTH_LONG).show();
                }
                return;
            }

            int jumlahPengunjung;
            try {
                jumlahPengunjung = Integer.parseInt(jumlahPengunjungStr);
            } catch (NumberFormatException e) {
                if (getView() != null) {
                    Snackbar.make(v, "Jumlah pengunjung tidak valid.", Snackbar.LENGTH_LONG).show();
                }
                return;
            }

            int totalHarga;
            try {
                totalHarga = Integer.parseInt(etTotalHarga.getText().toString());
            } catch (NumberFormatException e) {
                if (getView() != null) {
                    Snackbar.make(v, "Total harga tidak valid.", Snackbar.LENGTH_LONG).show();
                }
                return;
            }

            if (jumlahPengunjung <= 0 || totalHarga <= 0) {
                if (getView() != null) {
                    Snackbar.make(v, "Jumlah pengunjung dan total harga harus valid!", Snackbar.LENGTH_LONG).show();
                }
                return;
            }

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference userTransactionRef = FirebaseDatabase.getInstance().getReference("transaksi").child(userId);

            userTransactionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String transaksiId = snapshot.getChildren().iterator().next().getKey();
                        if (transaksiId != null) {
                            TiketBooking updatedBooking = new TiketBooking(jumlahPengunjung, totalHarga);
                            userTransactionRef.child(transaksiId).setValue(updatedBooking)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            if (getView() != null) {
                                                Snackbar.make(v, "Data berhasil diperbarui!", Snackbar.LENGTH_LONG).show();
                                            }
                                        } else {
                                            if (getView() != null) {
                                                Snackbar.make(v, "Gagal memperbarui data.", Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    } else {
                        String transaksiId = userTransactionRef.push().getKey(); // Generate transaksi ID baru
                        if (transaksiId != null) {
                            TiketBooking newBooking = new TiketBooking(jumlahPengunjung, totalHarga);
                            userTransactionRef.child(transaksiId).setValue(newBooking)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            if (getView() != null) {
                                                Snackbar.make(v, "Tunjukan ke loket dan lakukan pembayaran", Snackbar.LENGTH_LONG).show();
                                            }
                                        } else {
                                            if (getView() != null) {
                                                Snackbar.make(v, "Gagal menambahkan data.", Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (getView() != null) {
                        Snackbar.make(getView(), "Gagal memproses permintaan.", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        });

        return view;
    }

    private void fetchTicketPrice() {
        databaseReference.child("Tiket").child("htm").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String harga = snapshot.getValue(String.class);
                    if (harga != null) {
                        try {
                            hargaTiketMasuk = Integer.parseInt(harga);
                        } catch (NumberFormatException e) {
                            if (getView() != null) {
                                Snackbar.make(getView(), "Harga tiket tidak valid.", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        if (getView() != null) {
                            Snackbar.make(getView(), "Harga tiket belum diatur.", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (getView() != null) {
                    Snackbar.make(getView(), "Gagal memuat harga tiket.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void calculateTotalPrice() {
        String jumlahPengunjungStr = etJumlahPengunjung.getText().toString();

        if (jumlahPengunjungStr.isEmpty() || hargaTiketMasuk <= 0) {
            return;
        }

        int jumlahPengunjung;
        try {
            jumlahPengunjung = Integer.parseInt(jumlahPengunjungStr);
        } catch (NumberFormatException e) {
            return; // Return if input is invalid
        }

        int totalHarga = hargaTiketMasuk * jumlahPengunjung;
        etTotalHarga.setText(String.valueOf(totalHarga));
    }

    private void loadTransactionData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userTransactionRef = FirebaseDatabase.getInstance().getReference("transaksi").child(userId);

        userTransactionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String transaksiId = snapshot.getChildren().iterator().next().getKey(); // Ambil transaksi pertama
                    if (transaksiId != null) {
                        TiketBooking tiketBooking = snapshot.child(transaksiId).getValue(TiketBooking.class);
                        if (tiketBooking != null) {
                            etJumlahPengunjung.setText(String.valueOf(tiketBooking.getJumlahPengunjung()));
                            etTotalHarga.setText(String.valueOf(tiketBooking.getTotalHarga()));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (getView() != null) {
                    Snackbar.make(getView(), "Gagal memuat data transaksi.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
