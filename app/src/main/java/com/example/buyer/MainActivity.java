package com.example.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.buyer.Dashboard.Dashboard_Buyer_Fragment;

public class MainActivity extends AppCompatActivity {
PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       prefManager=new PrefManager(MainActivity.this);

       if (prefManager.isLogin())
       {
           FragmentManager fragmentManager;
           FragmentTransaction fragmentTransaction;
           fragmentManager = getSupportFragmentManager();
           fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(android.R.id.content, new Dashboard_Buyer_Fragment());
           fragmentTransaction.commit();
       }
       else
       {
           FragmentManager fragmentManager;
           FragmentTransaction fragmentTransaction;
           fragmentManager = getSupportFragmentManager();
           fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(android.R.id.content, new Fragment_Signin());
           fragmentTransaction.commit();
       }


    }
}
