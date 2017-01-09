package com.example.matan.intellignet;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.ArrayList;
import java.util.List;

public class SudokuMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_menu);
        TextView disconnect = (TextView)findViewById(R.id.disconnect);
        TextView connectedName = (TextView) findViewById(R.id.connectedName);

        PublisherAdView mPublisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);


        /*Four possible options:
        * 1- this is a guest and no user connected - GOOD
        * 2- this is a guest and we have user that's connected - BAD
        * 3- this is not a guest and no user connected - BAD
        * 4- this is not a guest and we have user that's connected - GOOD*/
        if(LoginActivity.isGuest && MainActivity.user == null)
        {
            disconnect.setVisibility(View.INVISIBLE);
            connectedName.setVisibility(View.INVISIBLE);
        }
        else if(LoginActivity.isGuest && MainActivity.user != null)
        {
            MainActivity.user=null; //TODO make possible to be connected and still go to guest mode. nowadays need to disconnect for guest.
            disconnect.setVisibility(View.INVISIBLE);
            connectedName.setVisibility(View.INVISIBLE);
        }
        else if(!LoginActivity.isGuest && MainActivity.user == null)
        {
            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if(!LoginActivity.isGuest && MainActivity.user != null)
        {
            connectedName.setText(MainActivity.user.getFirstName() + " " + MainActivity.user.getLastName());
            disconnect.setVisibility(View.VISIBLE);
            connectedName.setVisibility(View.VISIBLE);
        }



        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                sharedpreferences.edit().clear().commit();

                Intent disconnectIntent = new Intent(getApplicationContext(), LoginActivity.class);
                disconnectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(disconnectIntent);
                finish();
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //set the default tab when open
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SudokuHardMenu(), "קשה");
        adapter.addFragment(new SudokuMedMenu(), "בינוני");
        adapter.addFragment(new SudokuEasyMenu(), "קל");


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter
    {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }
    }
}

