package com.example.recreatequize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.recreatequize.databinding.ActivityMainBinding;
import com.example.recreatequize.fragment.DashBordFragment;
import com.example.recreatequize.fragment.HomeFragment;
import com.example.recreatequize.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

//
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragement(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.navigation_home:
                    replaceFragement(new HomeFragment());
                    break;

                case R.id.navigation_dashboard:
                    replaceFragement(new DashBordFragment());
                    break;

                case R.id.navigation_setting:
                    replaceFragement(new SettingFragment());
                    break;

            }


            return true;
        });

    }
//////////////////////////////////////



    public void replaceFragement(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }



//
}