package com.example.buyer;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.buyer.Dashboard.Dashboard_Buyer_Fragment;
import com.example.buyer.Dashboard.Productseller_Class;
import com.example.buyer.R;
import com.example.buyer.model.ColorItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.android.volley.VolleyLog.TAG;

public class Fragment_Accessory_Used extends Fragment {
    String seller_number;
    TextView tvshortdetail,tvpta,tvbrand,tvmodel,tvcolor,tvos,
            tvweight,tvbattery,tvstorage,tvresolution,
            tvselfiecam,tvsim,tvsensors,tvfeature,tvdisplay_technology;

    TextView tvsetpta,tvsetbrand,tvsetmodel,tvsetcolor,
            tvsetos,tvsetweight,tvsetbattery,tvsetstorage,
            tvsetresolution,tvsetselfiecam,tvsetsim,
            tvsetsensors,tvsetfeature,tvsetdisplay_technology;
    TextView tvvdisplay_technology,seller_name;
    ImageView imgbackdetail,imageMobile;
    String modelid,colorName,shopName;
    LinearLayout linearLayouthome,linearLayoutbuy;
    TextView price;
    String  seller_id;
    SweetAlertDialog pDialog;
    int chkFollowers;
    String isUnfollow="false";
    RatingBar ratingBar;
    LinearLayout linearLayoutcall,linearLayoutmsg;
    String salemannumb,sellerName,user_id;
    RecyclerView recyclerView;
    ArrayList<ColorItem> arrayList=new ArrayList<>();
    Button btnFollow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment__accessory__used, container, false);
        ratingBar=view.findViewById(R.id.ratingBarU);
        seller_name=view.findViewById(R.id.sellerName);
        btnFollow=view.findViewById(R.id.btnFollowU);
        recyclerView=view.findViewById(R.id.recyclerColor);
        imgbackdetail=view.findViewById(R.id.idbackdetail);
        linearLayouthome=view.findViewById(R.id.idllhome);
        linearLayoutcall= view.findViewById(R.id.idllbuy);
        linearLayoutmsg= view.findViewById(R.id.idllmsg);
        imageMobile=view.findViewById(R.id.modelImgU);
        init(view);


        return view;
    }

    private void init(View view) {
        price=view.findViewById(R.id.idtvprice);
        tvshortdetail=view.findViewById(R.id.idtvshortdetail);
        tvpta=view.findViewById(R.id.idtvpta);
        tvbrand=view.findViewById(R.id.idtvbrand);
        tvmodel=view.findViewById(R.id.idtvmodel);
        tvcolor=view.findViewById(R.id.idtvcolor);
        tvos=view.findViewById(R.id.idtvos);
        tvweight=view.findViewById(R.id.idtvweight);
        tvbattery=view.findViewById(R.id.idtvbattery);
        tvstorage=view.findViewById(R.id.idtvstorage);
        tvresolution=view.findViewById(R.id.idtvresolution);
        tvselfiecam=view.findViewById(R.id.idtvstelfiecam);
        tvsim=view.findViewById(R.id.idtvSim);
        tvsensors=view.findViewById(R.id.idtvsenosrs);
        tvfeature=view.findViewById(R.id.idtvfeatures);
        tvdisplay_technology=view.findViewById(R.id.idtvdisplaytype);

        tvsetpta=view.findViewById(R.id.idtvsetpta);
        tvsetbrand=view.findViewById(R.id.idtvsetbrand);
        tvsetmodel=view.findViewById(R.id.idtvsetmodel);
        tvsetcolor=view.findViewById(R.id.idtvsetcolor);
        tvsetos=view.findViewById(R.id.idtvsetos);
        tvsetweight=view.findViewById(R.id.idtvsetweight);
        tvsetbattery=view.findViewById(R.id.idtvsetbattery);
        tvsetstorage=view.findViewById(R.id.idtvsetstorage);
        tvsetresolution=view.findViewById(R.id.idtvsetresoltuion);
        tvsetselfiecam=view.findViewById(R.id.idtvsetselfie);
        tvsetsim=view.findViewById(R.id.idtvsetsim);
        tvsetsensors=view.findViewById(R.id.idtvsetsensors);
        tvsetfeature=view.findViewById(R.id.idtvsetfeatures);;
        tvsetdisplay_technology=view.findViewById(R.id.idtvsetdisplaytech);
        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Medium.ttf"); //use this.getAssets if you are calling from an Activity
        tvshortdetail.setTypeface(roboto);
        tvpta.setTypeface(roboto);
        tvbrand.setTypeface(roboto);
        tvmodel.setTypeface(roboto);
        tvcolor.setTypeface(roboto);

        tvos.setTypeface(roboto);
        tvweight.setTypeface(roboto);
        tvbattery.setTypeface(roboto);
        tvstorage.setTypeface(roboto);

        tvresolution.setTypeface(roboto);
        tvselfiecam.setTypeface(roboto);
        tvsim.setTypeface(roboto);
        tvsensors.setTypeface(roboto);
        tvfeature.setTypeface(roboto);
        tvdisplay_technology.setTypeface(roboto);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        user_id=sharedPreferences.getString("user_id","0");

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnFollow.getText().toString().equals("Follow"))
                {
                    add_follower();
                    getSellerN();
                    btnFollow.setText("Following");
                }
                else
                {
                    btnFollow.setText("Follow");
                    getSellerN();
                }

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                add_rating(rating);
            }
        });

        Bundle args = getArguments();
        if (args  != null){
            shopName=args.getString("shop_name");
            colorName=args.getString("color");
            modelid  = args.getString("model_id");
            tvshortdetail.setText(args.getString("Brand") +" "+args.getString("Model"));
            price.setText("Price : "+args.getString("Price")+"Rs");
            tvsetpta.setText(args.getString("PTA"));
            tvsetbrand.setText(args.getString("Brand"));
            tvsetmodel.setText(args.getString("Model"));
            tvsetcolor.setText(args.getString("color"));
            seller_id=args.getString("Seller_id");
            salemannumb=args.getString("used_mobile_salesman");
            sellerName=args.getString("Seller_name");
            //Toast.makeText(getContext(), "sellerid"+seller_id, Toast.LENGTH_SHORT).show();
        }
        getRating();
        fetch_following();
        getmodel_detail();
        getColors();
        getSeller();



        linearLayoutcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                call.setData(Uri.parse("tel:" + salemannumb));
                getActivity().startActivity(call);

            }

        });
        linearLayoutmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = salemannumb;  // The number on which you want to send SMS
                Log.i(TAG, "onClick: "+salemannumb);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });
        linearLayouthome.setOnClickListener(new View.OnClickListener() {
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
    private void getSeller() {

        final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Please Wait");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"fetch_seller.php?id="+seller_id;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray=response.getJSONArray("seller");

                            JSONObject seller= jsonArray.getJSONObject(0);

                            seller_number=seller.getString("phone");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);
    }
    private  void getmodel_detail()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"fetch_description_access.php?id="+modelid;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {

                            Productseller_Class approvedProducts_class;
                            JSONArray jsonArray=response.getJSONArray("description");
                            for (int i=0 ;i<jsonArray.length();i++)
                            {
                                JSONObject product= jsonArray.getJSONObject(i);

                                tvsetos.setText(product.getString("OS"));
                                tvsetweight.setText(product.getString("weight"));
                                tvsetbattery.setText(product.getString("battery_capacity"));
                                tvsetstorage.setText(product.getString("memory_built_in"));
                                tvsetresolution.setText(product.getString("display_resolution"));
                                tvsetselfiecam.setText(product.getString("camera_main"));
                                tvsetsim.setText(product.getString("sim"));
                                tvsetsensors.setText(product.getString("sensors"));
                                tvsetfeature.setText(product.getString("OS"));
                                tvsetdisplay_technology.setText(product.getString("display_technology"));
                                seller_name.setText(shopName);
                                RequestOptions requestOptions=new RequestOptions().error(R.drawable.bg_overlay);
                                Glide.with(getContext()).setDefaultRequestOptions(requestOptions).load(Constants.ROOT_URL+product.getString("image").replaceAll("\\/","/"))
                                        .into(imageMobile);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);

    }

    public class ColorAdapter extends RecyclerView.Adapter<Fragment_Accessory_Used.ColorAdapter.MyViewHolder>{

        int selected_position = 0; // You have to set this globally in the Adapter class
        Dialog myDialog;
        Context context;
        ArrayList<ColorItem> aList;

        public ColorAdapter(ArrayList<ColorItem> aList, Context context)
        {
            this.aList=aList;
            this.context=context;
        }
        @NonNull
        @Override
        public Fragment_Accessory_Used.ColorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.color_item,viewGroup,false);
            return new Fragment_Accessory_Used.ColorAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final  Fragment_Accessory_Used.ColorAdapter.MyViewHolder myViewHolder, final int i) {

            myViewHolder.tvColor.setText(aList.get(i).getColorName());
            final RequestOptions requestOptions=new RequestOptions().error(R.drawable.bg_overlay);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(Constants.ROOT_URL+aList.get(i).getImageUrl().replaceAll("\\/","/"))
                    .into(myViewHolder.imageView);
            // Here I am just highlighting the background

            myViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Glide.with(context).setDefaultRequestOptions(requestOptions).load(Constants.ROOT_URL+aList.get(i).getImageUrl().replaceAll("\\/","/"))
                            .into(imageMobile);
                    colorName=aList.get(i).getColorName();

                }
            });

        }
        @Override
        public int getItemCount() {
            return aList.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView tvColor;
            CardView cv;
            ImageView imageView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);


                tvColor=itemView.findViewById(R.id.txtColor);
                imageView=itemView.findViewById(R.id.colorImg);
                cv=itemView.findViewById(R.id.cardColor);
            }

            @Override
            public void onClick(View view) {

                // Below line is just like a safety check, because sometimes holder could be null,
                // in that case, getAdapterPosition() will return RecyclerView.NO_POSITION
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                // Updating old as well as new positions
                notifyItemChanged(selected_position);
                selected_position = getAdapterPosition();
                notifyItemChanged(selected_position);

            }
        }
    }

    private  void getColors()
    {
//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"fetch_color_model_access.php?getData=";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url+modelid, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
//                        progressDialog.dismiss();
                        //Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                        try {
                            arrayList.clear();
                            ColorItem colorItem;
                            JSONArray jsonArray=response.getJSONArray("MyData");

                            for (int i=0 ;i<jsonArray.length();i++)
                            {
                                String colorname,colorImg;
                                JSONObject product= jsonArray.getJSONObject(i);
                                Log.i(TAG, "onResponse: "+product);
                                colorname=product.getString("name");
                                colorImg=product.getString("color_image");
                                colorItem=new ColorItem(colorname,colorImg);


                                arrayList.add(colorItem);

                            }


                            Fragment_Accessory_Used.ColorAdapter adapter = new Fragment_Accessory_Used.ColorAdapter(arrayList, getContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);

    }

    public void add_follower()
    {    //Adding buyers
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Following Seller");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = getString(R.string.url) + "add_follower.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismissWithAnimation();
                if (response.equals("success")) {
                    pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("Followed!");
                    pDialog.setContentText("You are now following "+ sellerName);

                    pDialog.show();
//                    FragmentManager fragmentManager;
//                    FragmentTransaction fragmentTransaction;
//                    fragmentManager = getFragmentManager();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(android.R.id.content, new Fragment_Signin());
//                    fragmentTransaction.commit();

                } else {
                    pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Some Error Occured while following this seller");
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
                params.put("buyer_id",user_id);
                params.put("following_seller_id",seller_id);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    public void fetch_following()
    {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url= getString(R.string.url)+"fetch_following_buyer.php?getData="+seller_id+"&getDataB="+user_id;
        final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getContext(), "response: "+url, Toast.LENGTH_SHORT).show();
                        try {
                            String seller_id_r,buyer_id_r;
                            JSONArray jsonArray=response.getJSONArray("MyData");
                            for (int i=0 ;i<jsonArray.length();i++) {
                                seller_id_r=jsonArray.getJSONObject(i).getString("following_seller_id");
                                buyer_id_r=jsonArray.getJSONObject(i).getString("buyer_id");
                                btnFollow.setText("Following");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);



    }
    void update_follower(final String followers, final String btnText) {
        //  Toast.makeText(getContext(), "followerGet: "+followers, Toast.LENGTH_SHORT).show();
        if (btnText.equals("Follow"))
        {
            isUnfollow="true";
            if (Integer.parseInt(followers)!=0 )
                chkFollowers=Integer.parseInt(followers)-1;
        }
        else if (btnText.equals("Following"))
        {
            isUnfollow="false";
            chkFollowers=Integer.parseInt(followers)+1;
        }
        String url = getString(R.string.url) + "update_followers.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                } else {

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("seller_id",seller_id);
                params.put("follower",String.valueOf(chkFollowers));
                params.put("buyer_id",user_id);
                params.put("isUnfollow",isUnfollow);


                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

        //Toast.makeText(getContext(), " "+opening_time+" "+closing_time+" "+off_day+" "+seller_id, Toast.LENGTH_SHORT).show();

    }
    private void getSellerN() {

        final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Please Wait");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"fetch_seller.php?id="+seller_id;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            String followers;
                            JSONArray jsonArray=response.getJSONArray("seller");

                            JSONObject seller= jsonArray.getJSONObject(0);

                            followers=seller.getString("followers");
                            update_follower(followers,btnFollow.getText().toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);
    }
    public void add_rating(final float rating)
    {    //Adding buyers
//        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Creating Account");
//        pDialog.setCancelable(false);
//        pDialog.show();

        String url = getString(R.string.url) + "add_rating.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                pDialog.dismissWithAnimation();
                //Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                if (response.equals("success")) {
//                    pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
//                    pDialog.setTitleText("Success");
//                    pDialog.setContentText("You have given "+rating+" rating.");
//
//                    pDialog.show();
//                    FragmentManager fragmentManager;
//                    FragmentTransaction fragmentTransaction;
//                    fragmentManager = getFragmentManager();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(android.R.id.content, new Fragment_Signin());
//                    fragmentTransaction.commit();

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
                params.put("seller_id",seller_id);
                params.put("buyer_id",user_id);
                params.put("stars",String.valueOf(rating));

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
    private void getRating() {

//        final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE)
//                .setTitleText("Please Wait");
//        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"fetch_seller.php?id="+seller_id;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
//                        progressDialog.dismiss();
                        try {
                            String rating;
                            JSONArray jsonArray=response.getJSONArray("seller");

                            JSONObject seller= jsonArray.getJSONObject(0);

                            rating=seller.getString("rating");
                            ratingBar.setRating(Float.parseFloat(rating));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);
    }

}

