package MenuSettingsApps;

import android.os.Bundle;
import android.util.Log;
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

        View view = inflater.inflate(R.layout.manage_user, container, false);

        usernameTextView = view.findViewById(R.id.nama_user);
        emailTextView = view.findViewById(R.id.email_user);
        phoneNumberTextView = view.findViewById(R.id.No_user);
        addressTextView = view.findViewById(R.id.Alamat_user);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String username = bundle.getString("username");
            String email = bundle.getString("email");
            String phoneNumber = bundle.getString("phoneNumber");
            String address = bundle.getString("address");

            usernameTextView.setText(username != null ? username : "No username available");
            emailTextView.setText(email != null ? email : "No email available");
            phoneNumberTextView.setText(phoneNumber != null && !phoneNumber.isEmpty() ? phoneNumber : "No phone number available");
            addressTextView.setText(address != null && !address.isEmpty() ? address : "No address available");
        }


        fetchFirebaseData();

        return view;
    }

    private void fetchFirebaseData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String firebasephoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                    String firebaseaddress = dataSnapshot.child("address").getValue(String.class);

                    Log.d("FirebaseData", "Phone Number: " + firebasephoneNumber);
                    Log.d("FirebaseData", "Address: " + firebaseaddress);

                    phoneNumberTextView.setText(firebasephoneNumber != null && !firebasephoneNumber.isEmpty() ? firebasephoneNumber : "No phone number available");
                    addressTextView.setText(firebaseaddress != null && !firebaseaddress.isEmpty() ? firebaseaddress : "No address available");
                } else {
                    Toast.makeText(getActivity(), "Data tidak ditemukan di Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal mengambil data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });
    }

}
