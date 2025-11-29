package com.example.tiktokdemo.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tiktokdemo.R;
import com.google.android.material.tabs.TabLayout;
import com.example.tiktokdemo.fragment.ExperienceFragment;
import com.example.tiktokdemo.fragment.TodoFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // setupGestureDetector();
        if (savedInstanceState == null) {
            loadFragment(new ExperienceFragment());
        }
        setupTabs();
    }

    // private void setupGestureDetector() {
    //     gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
    //         @Override
    //         public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    //             float deltaX = e2.getX() - e1.getX();
    //             if (Math.abs(deltaX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
    //                 if (deltaX > 0) {
    //                     Toast.makeText(MainActivity.this, "Swipe Right Detected", Toast.LENGTH_SHORT).show();
    //                 } else {
    //                     Toast.makeText(MainActivity.this, "Swipe Left Detected", Toast.LENGTH_SHORT).show();
    //                 }
    //                 return true;
    //             }
    //             return false;
    //         }
    //     });
    // }
    // 设置顶部tab栏
    private void setupTabs(){
        TabLayout tabLayout = findViewById(R.id.top_tabs);
        String[] tabTitles = {"直播", "精选", "经验", "同城", "关注", "热点", "团购", "商城", "推荐"};
        for (String title: tabTitles){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        // 默认打开经验tab
        TabLayout.Tab defaultTab = tabLayout.getTabAt(2);
        if (defaultTab != null) {
            defaultTab.select();
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragmentToLoad;
                switch (position){
                    case 2:
                        fragmentToLoad = new ExperienceFragment();
                        break;
                    default:
                        fragmentToLoad = new TodoFragment();
                        break;
                }
                loadFragment(fragmentToLoad);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null && (currentFragment == null || !currentFragment.getClass().equals(fragment.getClass()))) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            currentFragment = fragment;
        }
    }
}