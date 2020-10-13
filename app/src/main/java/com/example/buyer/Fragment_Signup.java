package com.example.buyer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_Signup extends Fragment {
    SweetAlertDialog pDialog;

    TextView tvsignin,tvname,tvemail,tvpassword,tvnumber,tvconfirmpass,address;
    String name,email,password,number,addressS;
    Button btnSignUp;
    String verificationID;
    FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        tvsignin=getView().findViewById(R.id.idsignintv);

                tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new Fragment_Signin());
                fragmentTransaction.commit();
            }
        });
                address=getView().findViewById(R.id.idedtaddressB);
        tvname=getView().findViewById(R.id.idedtname);
        tvemail= getView().findViewById(R.id.idedtemail);
        tvpassword=getView().findViewById(R.id.idedtloginpass);
        tvnumber=getView().findViewById(R.id.idedtphonenumb);
        tvconfirmpass=getView().findViewById(R.id.idedtloginpass2);
        btnSignUp=getView().findViewById(R.id.idbtnlogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=tvname.getText().toString();
                email=tvemail.getText().toString();
                password=tvpassword.getText().toString();
                number=tvnumber.getText().toString();
                addressS=address.getText().toString();
                if(number.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() || name.trim().isEmpty() || addressS.trim().isEmpty())
                {
                    new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE).
                            setTitleText("Fields Empty").setContentText("Please fill all fields").
                            show();


                    return;
                }

                if(number.length() < 13){
                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).
                            setTitleText("Invalid Phone Number").setContentText("Check Length and Format of Phone Number").
                            show();


                    return;
                }
                if (!password.equals(tvconfirmpass.getText().toString().trim()))
                {
                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).
                            setTitleText("Passwords dont match").setContentText("Password and Confirm Password do not match").
                            show();
                    return;
                }

                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_verifyOTP fragment_verifyOTP= new Fragment_verifyOTP();
                Bundle bundle= new Bundle();
                bundle.putString("name",name);
                bundle.putString("email",email);
                bundle.putString("password",password);
                bundle.putString("number",number);
                bundle.putString("address",addressS);


                fragment_verifyOTP.setArguments(bundle);
                fragmentTransaction.replace(android.R.id.content,fragment_verifyOTP);
                fragmentTransaction.commit();



            }

    });


}
}
