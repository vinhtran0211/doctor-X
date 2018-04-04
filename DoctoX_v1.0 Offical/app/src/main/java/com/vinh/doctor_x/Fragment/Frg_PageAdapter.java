package com.vinh.doctor_x.Fragment;

/**
 * Created by nntd290897 on 3/20/18.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinh.doctor_x.R;

import java.util.ArrayList;
import java.util.List;

public class Frg_PageAdapter extends Fragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.frg_viewhistory_withpatient,container, false);
            // Setting ViewPager for each Tabs
            ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
            setupViewPager(viewPager);
            // Set Tabs inside Toolbar
            TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
            tabs.setupWithViewPager(viewPager);


            return view;

        }


        // Add Fragments to Tabs
        private void setupViewPager(ViewPager viewPager) {


            Adapter adapter = new Adapter(getChildFragmentManager());
            adapter.addFragment(new Frg_tabhistory_indoctor(), "History");
            adapter.addFragment(new Frg_tabnext_indoctor(), "Next");
            viewPager.setAdapter(adapter);



        }

        static class Adapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public Adapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        }



    }