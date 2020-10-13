package com.example.buyer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.buyer.Dashboard.Dashboard_Buyer_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_verifyOTP extends Fragment {
    EditText otp;
SweetAlertDialog pDialog;
    String name,email,password,number,address;
    Button btnVerify;
    String verificationID;
    PhoneAuthProvider.ForceResendingToken resendToken;
    PhoneAuthProvider phoneAuthProvider;
    FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    CountDownTimer countDownTimer;
    TextView tvResend;
    private String phoneVerificationId;


    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otp_verification, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        otp=getView().findViewById(R.id.otp);
        btnVerify=getView().findViewById(R.id.idbtnverify);
        mAuth=FirebaseAuth.getInstance();
        Bundle args = getArguments();
        if (args  != null) {

            name=args.getString("name");
            email=args.getString("email");
            password=args.getString("password");
            number=args.getString("number");
            address=args.getString("address");
        }

        phoneAuthProvider=PhoneAuthProvider.getInstance();
        tvResend=getView().findViewById(R.id.tvResend);

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number+"",        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                verificationCallbacks);

btnVerify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (!otp.getText().toString().equals(""))
        {
            try {
                PhoneAuthCredential credential =
                        PhoneAuthProvider.getCredential(phoneVerificationId,otp.getText().toString() );
                signInWithPhoneAuthCredential(credential);
            }catch (Exception e){
                e.printStackTrace();
                Toast toast = Toast.makeText(getContext(), "Verification Code is wrong", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }

        }else {
            Toast.makeText(getContext(), "Enter the Correct verification Code", Toast.LENGTH_SHORT).show();
        }

    }
});

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpVerificatonCallbacks();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        number,
                        60,
                        TimeUnit.SECONDS,
                        getActivity(),
                        verificationCallbacks,
                        resendToken);
            }
        });

    }


    private void setUpVerificatonCallbacks() {
        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {

                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {


                        Log.d("responce",e.toString());
                        Toast.makeText(getContext(), e.getMessage()+" ", Toast.LENGTH_SHORT).show();
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            e.printStackTrace();
                            // Invalid request
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            e.printStackTrace();
                            // SMS quota exceeded
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        phoneVerificationId = verificationId;
                        resendToken = token;
                        Toast.makeText(getContext(), "Code Sent to ( "+number+" )", Toast.LENGTH_SHORT).show();
                        startTimer(1);

                    }
                };
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            countDownTimer.cancel();

                            register_buyer();

                            Intent returnIntent = new Intent();
                            getActivity().setResult(Activity.RESULT_OK,returnIntent);


                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });
    }


    private void startTimer(int noOfMinutes) {

        countDownTimer=new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvResend.setEnabled(false);
                tvResend.setText("Resend in "+ millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {

                //Dialogs.errorDialog(OTPVerify.this,getString(R.string.oops),"Couldn't Verify, Check your mobile number again!",true,false,"",getString(R.string.ok2),null);
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pDialog.setTitleText("Couldn't Verify! Check your mobile number again.");
                pDialog.show();
                tvResend.setEnabled(true);
                tvResend.setText("Resend");
            }

        }.start();

    }

    private void verifyVerificationCode(String otp) {

        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, otp);
        //String code= credential.getSmsCode();
        //Toast.makeText(getActivity().getApplicationContext(), ""+code, Toast.LENGTH_SHORT).show();
        //register_buyer();
        //Toast.makeText(getContext(), credential.getSmsCode(), Toast.LENGTH_SHORT).show();
        mAuth.signInWithCredential(credential).addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    register_buyer();
                }
                else
                {
                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Error")
                            .setContentText("Unable to Verify").show();
                }
            }
        });

    }
    public void register_buyer()
    {    //Adding buyers
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Creating Account");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = getString(R.string.url) + "register_buyer.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismissWithAnimation();
                if (response.equals("success")) {
                    pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("Success");
                    pDialog.setContentText("Account created Successfully");

                    pDialog.show();
                    FragmentManager fragmentManager;
                    FragmentTransaction fragmentTransaction;
                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(android.R.id.content, new Fragment_Signin());
                    fragmentTransaction.commit();

                } else {
                    pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Some Error Occured");
                    pDialog.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",name);
                params.put("password",password);
                params.put("email",email);
                params.put("phone",number);
                params.put("address",address);


                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }



}
