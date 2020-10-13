package com.example.buyer.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.buyer.Buy.Fragment_Cart;
import com.example.buyer.MainCartFragment;
import com.example.buyer.PrefManager;
import com.example.buyer.ProductFragment;
import com.example.buyer.R;
import com.example.buyer.SidemenuFragment;
import com.example.buyer.model.SliderItem;
import com.example.buyer.slider.SliderAdapterBlur;
import com.example.buyer.slider.SliderAdapterExample;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Dashboard_Buyer_Fragment extends Fragment {

    ImageView cart;
    ImageView imgmenu;
    SliderView sliderView,sliderViewBG;
    private SliderAdapterExample adapter;
    private  SliderAdapterBlur adapterBlur;
    ArrayList<SliderItem>  arrayList=new ArrayList<>();
    PrefManager prefManager;
    String user_id;
    ViewPager vpPager;
    MyPagerAdapter adapterViewPager;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.buyerdashboard, container, false);
        //loadPage(new Fragment_Mobile_List());
        tabLayout=view.findViewById(R.id.tabLayout);
        vpPager = view.findViewById(R.id.pager_header);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabLayout.addTab(tabLayout.newTab().setText("Mobiles"));
        tabLayout.addTab(tabLayout.newTab().setText("Accessories"));

        adapterViewPager = new MyPagerAdapter(getFragmentManager(),tabLayout.getTabCount());
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

        SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        user_id=sharedPreferences.getString("user_id","0");

        //loadLogs();

        prefManager=new PrefManager(getContext());
        sliderView = getView().findViewById(R.id.imageSlider);
        sliderViewBG = getView().findViewById(R.id.imageSliderBG);
        adapter = new SliderAdapterExample(arrayList,getContext());
        adapterBlur=new SliderAdapterBlur(arrayList,getContext());
        sliderViewBG.setSliderAdapter(adapterBlur);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderViewBG.setSliderAdapter(adapterBlur);

//        sliderViewBG.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderViewBG.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderViewBG.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
//        sliderViewBG.setIndicatorSelectedColor(Color.WHITE);
//        sliderViewBG.setIndicatorUnselectedColor(Color.GRAY);
        sliderViewBG.setScrollTimeInSec(3);
        sliderViewBG.setAutoCycle(true);
        sliderViewBG.startAutoCycle();

        String url=getString(R.string.url)+"fetch_banners.php";
        new JsonTask().execute(url);


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
                Toast.makeText(getContext(), "item Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        cart=getView().findViewById(R.id.idivcart);
        imgmenu=getView().findViewById(R.id.idimgmenudashboard);
        cart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, new MainCartFragment());
        fragmentTransaction.commit();
            }});
        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new SidemenuFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }});

    }

    private boolean loadPage(Fragment fragment) {
        if (fragment != null) {
            Toast.makeText(getContext(), "If", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.idframebuyer, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                //connection.setDoInput(true);




                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            try {
                //Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();

                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.has("banners")) {


                    JSONArray banner_array = jsonObject.getJSONArray("banners");
                    SliderItem bannerClass;

                    for(int i=0;i<banner_array.length();i++)
                    { String brandName,bannerName,bannerImage;
                        JSONObject banner= banner_array.getJSONObject(i);
                        brandName=banner.getString("brand_name");
                        bannerName= banner.getString("banner_desc");
                        bannerImage= banner.getString("image_url");

                        bannerClass=new SliderItem(bannerName,bannerImage,brandName);

                        arrayList.add(bannerClass);
                        adapter.renewItems(arrayList);
                        adapterBlur.renewItems(arrayList);

                    }

                }




            }catch (Exception ex)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        // .setContentText(ex.getLocalizedMessage())
                        .setContentText("Some Error Occured")
                        .show();

            }
        }


    }

   static public class MyPagerAdapter extends FragmentStatePagerAdapter {
        int NUM_ITEMS = 2;
        FragmentManager fragmentManager;
        Dashboard_Buyer_Fragment dashboard_buyer_fragment=new Dashboard_Buyer_Fragment();
        public MyPagerAdapter(FragmentManager fragmentManager,int NUM_ITEMS) {
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

                    Fragment_Mobile_List fragment_mobile_list=new Fragment_Mobile_List();
////                  //dashboard_buyer_fragment.loadPage(new Fragment_Mobile_List());
////                    //FragmentManager fragmentManager;
////                    FragmentTransaction fragmentTransaction;
////                    //fragmentManager = dashboard_buyer_fragment.activity.getSupportFragmentManager();
////                    fragmentTransaction = fragmentManager.beginTransaction();
////                    fragmentTransaction.replace(R.id.idframebuyer,fragment_mobile_list).addToBackStack(null);
////                    fragmentTransaction.commit();
                    return fragment_mobile_list;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    ProductFragment productFragment=new ProductFragment();
//                    dashboard_buyer_fragment.loadPage(new ProductFragment());
                    return productFragment;
                default:
                    return null;
            }
        }

//    // Returns the page title for the top indicator
//    @Override
//    public CharSequence getPageTitle(int position) {
//        //return "Page " + position;
//        if (position==0)
//        {
//            return "Mobiles";
//        }
//        else if (position==1)
//        {
//            return "Accessories";
//        }
//    }

    }




}