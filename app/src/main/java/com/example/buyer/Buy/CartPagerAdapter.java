package com.example.buyer.Buy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.buyer.Dashboard.Dashboard_Buyer_Fragment;
import com.example.buyer.Dashboard.Fragment_Mobile_List;
import com.example.buyer.ProductFragment;

public class CartPagerAdapter extends FragmentStatePagerAdapter {
    int NUM_ITEMS = 2;
    FragmentManager fragmentManager;
    Dashboard_Buyer_Fragment dashboard_buyer_fragment=new Dashboard_Buyer_Fragment();
    public CartPagerAdapter(FragmentManager fragmentManager,int NUM_ITEMS) {
        super(fragmentManager);
        this.NUM_ITEMS=NUM_ITEMS;
        this.fragmentManager=fragmentManager;
    }


    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @NonNull
    @Override
    public Fragment getItem(int position) {
//            Context context=dashboard_buyer_fragment.activity;
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                Fragment_Cart fragment_cart=new Fragment_Cart();
                return fragment_cart;
            case 1: // Fragment # 0 - This will show FirstFragment different title
                FragmentCartAccessory fragmentCartAccessory=new FragmentCartAccessory();
                return fragmentCartAccessory;
            default:
                return null;
        }
    }


}
