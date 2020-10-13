package com.example.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.buyer.Orders.OrdersFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class SidemenuFragment extends Fragment {

    LinearLayout linearLayoutlogout,linearLayoutorders,linearLayoutprofile;
    ImageView imgback;
    PrefManager prefManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sidemenu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        prefManager=new PrefManager(getContext());

        imgback=getView().findViewById(R.id.idbacksidemenu);

        linearLayoutlogout=getView().findViewById(R.id.idlinearlayoutlogout);
        linearLayoutorders=getView().findViewById(R.id.idlinearlayoutlorders);
        linearLayoutprofile=getView().findViewById(R.id.idlinearlayoutprofile);


        linearLayoutprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new ProfileFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        linearLayoutorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new OrdersFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();
            }
        });



        linearLayoutlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setLogin(false);
                FragmentManager fm = getFragmentManager(); // or 'getSupportFragmentManager();'
                int count = fm.getBackStackEntryCount();
                for(int i = 0; i < count; ++i) {
                    fm.popBackStack();
                }

                startActivity(new Intent(getActivity(), MainActivity.class));
//                FragmentManager fragmentManager;
//                FragmentTransaction fragmentTransaction;
//                fragmentManager = getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(android.R.id.content, new LoginFragment());
//                fragmentTransaction.commit();
                  }
        });
    }
}

