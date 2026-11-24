package com.example2.mid_exam_sameryounes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example2.mid_exam_sameryounes.R;
import com.example2.mid_exam_sameryounes.databinding.ActivityModeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ModeActivity extends AppCompatActivity {
private ActivityModeBinding modeBinding;
    private TabLayout Tabslayoutss;
    private PagerAdapter pagerAdapterss;
    private ViewPager2 viewPager2ss;
    private Button buttonSetMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modeBinding=ActivityModeBinding.inflate(getLayoutInflater());
        setContentView(modeBinding.getRoot());

        if (getSupportActionBar() != null) {  // لتشغيل السهم في action bar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Tabslayoutss = findViewById(R.id.tabLayout_id);
        viewPager2ss= findViewById(R.id.viewPager2_id);

        pagerAdapterss= new PagerAdapter(this);
        viewPager2ss.setAdapter(pagerAdapterss);

        buttonSetMode=findViewById(R.id.button_set_mode);


        ArrayList<String> tabs = new ArrayList<>();
        tabs.add(getString(R.string.first));
        tabs.add(getString(R.string.second));
        tabs.add(getString(R.string.third));




        new TabLayoutMediator(Tabslayoutss,viewPager2ss,((tab, position) -> {   //عشان نربط tablayout مع  pagerAdapter  ونضع عنوان لكل tab

            tab.setText(tabs.get(position));
        })).attach();






        buttonSetMode.setOnClickListener(new View.OnClickListener() { // ايش بدو يصير لما نضغط على الزر
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(ModeActivity.this,buttonSetMode); // ناخذ نسخة من  PopupMenu
                popupMenu.getMenuInflater().inflate(R.menu.menupopup , popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {   //هان ايش بدو يصير لما نظغط على  العنصر في القائمة

                        int id = menuItem.getItemId();

                        if (id == R.id.mode_light) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            return true;
                        } else if (id == R.id.mode_night) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            return true;
                        } else if (id == R.id.mode_system) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                            return true;
                        }

                        return false;
                    }
                });

                popupMenu.show();

            }
        });
    }



    public class PagerAdapter extends FragmentStateAdapter {     //هاد كلاس جديد عملناه ل  pager adapter

        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {

            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {     // هان بنحدد ايش الفراغمنت(tab) الي بدنا ياها تظهر ع الشاشة

            if (position==0) {
                return new FirstFragment();
            }

            else if (position==1) {
                return  new SecondFragment();
            }

            else if (position==2) {
                return new ThirdFragment();
            }


            else return  null;
        }

        @Override
        public int getItemCount() {

            return 3;     //كم Tab  نريد وضعه
        }
    }

    @Override
    public void onBackPressed() {
                                    //هان عشان لما نضغط على زر الرجوع سواء كنا في اي   tab   يرجعنا الى  tab الاولى

        if(viewPager2ss.getCurrentItem()!=0){
            viewPager2ss.setCurrentItem(0);
        }

        else {
            super.onBackPressed();
        }
    }



}