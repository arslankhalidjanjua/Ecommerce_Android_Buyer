package com.example.buyer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.buyer.Buy.CartPagerAdapter;
import com.example.buyer.Dashboard.Dashboard_Buyer_Fragment;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainCartFragment extends Fragment {
    ViewPager vpPager;
    CartPagerAdapter adapterViewPager;
    TabLayout tabLayout;
    ImageView imgbackdetail;
    public MainCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_cart, container, false);
        // Inflate the layout for this fragment
        imgbackdetail=view.findViewById(R.id.idbackdetail);
        tabLayout=view.findViewById(R.id.tabLayout);
        vpPager = view.findViewById(R.id.pager_header);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        tabLayout.addTab(tabLayout.newTab().setText("Mobiles"));
        tabLayout.addTab(tabLayout.newTab().setText("Accessories"));

        adapterViewPager = new CartPagerAdapter(getFragmentManager(),tabLayout.getTabCount());
        vpPager.setAdapter(adapterViewPager);
        //Toast.makeText(getContext(), ""+vpPager, Toast.LENGTH_SHORT).show();
        vpPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        imgbackdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new Dashboard_Buyer_Fragment());
                fragmentTransaction.commit();
            }
        });
    }
}
