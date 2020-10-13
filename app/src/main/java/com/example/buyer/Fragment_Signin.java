package com.example.buyer;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buyer.Dashboard.Dashboard_Buyer_Fragment;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_Signin extends Fragment {
SweetAlertDialog pDialog;
    TextView tvsignup;
    EditText ed_name,ed_pass;
    SharedPreferences sharedPreferences;
    String name,password;
    Button btnSignin;
    PrefManager prefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefManager=new PrefManager(getContext());
        ed_name=getView().findViewById(R.id.idedtloginusername);
        ed_pass=getView().findViewById(R.id.idedtloginpass);
        tvsignup=getView().findViewById(R.id.idsignuptv);
        btnSignin=getView().findViewById(R.id.idbtnlogin);
        sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new Fragment_Signup());
                fragmentTransaction.commit();



            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=ed_name.getText().toString();
                password=ed_pass.getText().toString();
// name is number
                if (name.charAt(0)!='+')
                {
                    name="+"+name;
                }

                signIn();


            }
        });
    }



    public void signIn()
    {

            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Logging in");
            pDialog.setCancelable(false);
            pDialog.show();

            String url = getString(R.string.url) + "check_login_buyer.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismissWithAnimation();
                    if (response.contains("success")) {
                        prefManager.setLogin(true);
                        String[] strings=response.split(" ");
                                sharedPreferences.edit().putString("user_id",strings[1]).commit();
                                FragmentManager fragmentManager;
                                FragmentTransaction fragmentTransaction;
                                fragmentManager = getFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(android.R.id.content, new Dashboard_Buyer_Fragment());
                                fragmentTransaction.commit();

                    }
                    else
                    {
                        new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Invalid Credentials")
                                .show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                  //  Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setContentText(error.getMessage())
                            .setTitleText("Some Error Occured").show();
                }
            }) {
                @Override
                protected Map<String, String> getParams()  {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username",name);
                    params.put("password",password);


                    return params;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(request);
        }
}

