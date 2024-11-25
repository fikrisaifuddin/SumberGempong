package MenuSettingsApps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.s_gempong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInformation extends Fragment {

    private TextView usernameTextView, emailTextView, phoneNumberTextView, addressTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Menginflate layout fragment
        View view = inflater.inflate(R.layout.manage_user, container, false);

        // Inisialisasi Views
        usernameTextView = view.findViewById(R.id.nama_user);
        emailTextView = view.findViewById(R.id.email_user);
        phoneNumberTextView = view.findViewById(R.id.No_user);
        addressTextView = view.findViewById(R.id.Alamat_user);

        // Mendapatkan data dari Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String username = bundle.getString("username");
            String email = bundle.getString("email");
            String phoneNumber = bundle.getString("phoneNumber");
            String address = bundle.getString("address");

            // Tampilkan data ke TextViews
            usernameTextView.setText(username);
            emailTextView.setText(email);

            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                phoneNumberTextView.setText(phoneNumber);
            } else {
                phoneNumberTextView.setText("No phone number available");
            }

            if (address != null && !address.isEmpty()) {
                addressTextView.setText(address);
            } else {
                addressTextView.setText("No address available");
            }

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Mengambil data yang mungkin belum dikirim di Bundle
                        String firebasePhoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                        String firebaseAddress = dataSnapshot.child("address").getValue(String.class);

                        // Hanya update jika data Firebase tidak null
                        if (firebasePhoneNumber != null && !firebasePhoneNumber.isEmpty()) {
                            phoneNumberTextView.setText(firebasePhoneNumber);
                        }

                        if (firebaseAddress != null && !firebaseAddress.isEmpty()) {
                            addressTextView.setText(firebaseAddress);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
