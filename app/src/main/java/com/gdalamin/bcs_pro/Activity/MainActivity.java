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