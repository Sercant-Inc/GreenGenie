package com.sergio.greengenie;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sergio.greengenie.UI.Main.SectionsPagerAdapter;
import com.sergio.greengenie.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ImageView frame;
    private BottomNavigationView bottom_navigation;

    private MenuItem prevMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // frame=findViewById(R.id.frame);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        bottom_navigation=findViewById(R.id.bottom_navigation);
       // TabLayout tabs = binding.tabs;
       // tabs.setupWithViewPager(viewPager);
       // Glide.with(this).load(R.drawable.geniosinfondo).circleCrop().into(frame);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {


                    case R.id.add:
                        item.setChecked(true);
                        Toast.makeText(MainActivity.this, "Add clicked.", Toast.LENGTH_SHORT).show();
                       // removeBadge(mybottomNavView,item.getItemId());
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.browse:
                        item.setChecked(true);
                        Toast.makeText(MainActivity.this, "Browse clicked.", Toast.LENGTH_SHORT).show();
                      //  removeBadge(mybottomNavView,item.getItemId());
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.personal:
                        item.setChecked(true);
                        Toast.makeText(MainActivity.this, "Personal clicked.", Toast.LENGTH_SHORT).show();
                       // removeBadge(mybottomNavView,item.getItemId());
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.form:
                        item.setChecked(true);
                        Toast.makeText(MainActivity.this, "Likes clicked.", Toast.LENGTH_SHORT).show();
                        //removeBadge(mybottomNavView,item.getItemId());
                        viewPager.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });


//        here we listen to viewpager moves and set navigations items checked or not

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottom_navigation.getMenu().getItem(0).setChecked(false);
                    bottom_navigation.getMenu().getItem(position).setChecked(true);
                   // removeBadge(bottom_navigation, bottom_navigation.getMenu().getItem(position).getItemId());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    }
