package com.gdalamin.bcs_pro.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;

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

    ActivityMainBinding binding;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



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




    public void replaceFragment(Fragment fragment, int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);

        // Only add non-default fragments to the back stack
        if (!(fragment instanceof HomeFragment)) {
            transaction.addToBackStack(null);
        }

        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    // Overload the method with a version that uses default animations
    public void replaceFragment(Fragment fragment) {
        // Call the method with default animations (you can change these defaults)
        replaceFragment(fragment, R.anim.default_enter_anim, R.anim.default_exit_anim, R.anim.default_pop_enter_anim, R.anim.default_pop_exit_anim);
    }



    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed(); // This could be finish() to close the activity
        }
    }


}