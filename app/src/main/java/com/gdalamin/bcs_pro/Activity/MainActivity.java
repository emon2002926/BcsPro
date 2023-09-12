package com.gdalamin.bcs_pro.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.databinding.ActivityMainBinding;
import com.gdalamin.bcs_pro.fragment.DashBordFragment;
import com.gdalamin.bcs_pro.fragment.HomeFragment;
import com.gdalamin.bcs_pro.fragment.SettingFragment;
import com.google.android.material.navigation.NavigationBarView;

public class
MainActivity extends AppCompatActivity {

//
    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the title text for each menu item
        binding.bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        // Set the title text for each menu item
        binding.bottomNavigationView.getMenu().findItem(R.id.navigation_home).setTitle(getResources().getString(R.string.home));
        binding.bottomNavigationView.getMenu().findItem(R.id.navigation_dashboard).setTitle(getResources().getString(R.string.profile));
//        binding.bottomNavigationView.getMenu().findItem(R.id.navigation_leaderBord).setTitle("Leaderboard");
        binding.bottomNavigationView.getMenu().findItem(R.id.navigation_setting).setTitle(getResources().getString(R.string.more));

        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.navigation_dashboard:
                    replaceFragment(new DashBordFragment());
                    break;
                case R.id.navigation_setting:
                    replaceFragment(new SettingFragment());
                    break;
//                case R.id.navigation_leaderBord:
//                    replaceFragment(new LeaderboardFragment());
//                    break;
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}