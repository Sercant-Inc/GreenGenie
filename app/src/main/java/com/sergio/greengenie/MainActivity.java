package com.sergio.greengenie;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sergio.greengenie.Fragments.Page2;
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
        bottom_navigation = findViewById(R.id.bottom_navigation);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);


        // Glide.with(this).load(R.drawable.geniosinfondo).circleCrop().into(frame);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {


                    case R.id.lamp:
                        item.setChecked(true);
                        //Toast.makeText(MainActivity.this, "Add clicked.", Toast.LENGTH_SHORT).show();
                        // removeBadge(mybottomNavView,item.getItemId());
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.user:
                        item.setChecked(true);
                        //Toast.makeText(MainActivity.this, "Browse clicked.", Toast.LENGTH_SHORT).show();
                        //  removeBadge(mybottomNavView,item.getItemId());
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.data:
                        item.setChecked(true);
                        //Toast.makeText(MainActivity.this, "Personal clicked.", Toast.LENGTH_SHORT).show();
                        // removeBadge(mybottomNavView,item.getItemId());
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.form:
                        item.setChecked(true);
                        //Toast.makeText(MainActivity.this, "Likes clicked.", Toast.LENGTH_SHORT).show();
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
        hideBottomNavigationOnKeyboard();

    }

    public void hideBottomNavigationOnKeyboard() {
        View rootView = findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;

                boolean isKeyboardActive = keypadHeight > screenHeight * 0.25;

                if (isKeyboardActive) {
                    bottom_navigation.setVisibility(View.INVISIBLE);
                } else {
                    bottom_navigation.setVisibility(View.VISIBLE);
                }
            }
        });
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.profile_menu, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        if (id == R.id.settings) {
//        }
//        if (id == R.id.profile) {
//            Intent intent = new Intent(this, Page2.class);
//            startActivity(intent);
//        }
//        if (id == R.id.logout) {
//            Intent intent = new Intent(this, LoginPage.class);
//            startActivity(intent);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
