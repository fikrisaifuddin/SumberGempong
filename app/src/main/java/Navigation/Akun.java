package Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_gempong.R;

import java.util.ArrayList;
import java.util.List;

import AkunSetClass.AkunSet;
import AkunSetClass.SettingsApps;
import AkunSetClass.TentangAnda;
import User.Login;
import adapter.akunadapter;

public class Akun extends Fragment implements akunadapter.OnAkunItemClickListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_akun, container, false);

        // Set up toolbar
        Toolbar toolbar = view.findViewById(R.id.top_setting); // Ganti dengan ID toolbar Anda
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

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<AkunItem> akunItems = new ArrayList<>();
        akunItems.add(new AkunItem(R.drawable.akun, "Tentang Anda", "Update informasi pribadi anda"));
        akunItems.add(new AkunItem(R.drawable.akun, "Akun", "Ubah password"));
        akunItems.add(new AkunItem(R.drawable.akun, "Login", "Just for admin"));
        akunItems.add(new AkunItem(0, "Log Out", null));
        akunItems.add(new AkunItem(0, "Settings Apps", null));

        akunadapter adapter = new akunadapter(akunItems, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(AkunItem akunItem) {
        if (akunItem.getTitle().equals("Tentang Anda")) {
            moveToFragment(new TentangAnda());
        } else if (akunItem.getTitle().equals("Akun")) {
            moveToFragment(new AkunSet());
        } else if (akunItem.getTitle().equals("Settings Apps")) {
            moveToFragment(new SettingsApps());
        } else if (akunItem.getTitle().equals("Log Out")) {
            logout();
        }
    }

    private void moveToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void logout() {
        Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
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
