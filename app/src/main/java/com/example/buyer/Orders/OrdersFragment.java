package com.example.buyer.Orders;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buyer.Constants;
import com.example.buyer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class OrdersFragment extends Fragment {

    Dialog dialog;
    ArrayList<OrderClass> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    String user_id;
    ImageView imgback;
    TextView tvshortdetail,price,tvstorage;
    TextView tvsetpta,tvsetbrand,tvsetmodel,tvsetcolor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_orders, container, false);
        recyclerView=view.findViewById(R.id.idrvorders);
        imgback=view.findViewById(R.id.idbackpoints);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        user_id= getActivity().getPreferences(Context.MODE_PRIVATE).getString("user_id","0");

        getorders();
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();
            }
        });
    }

    private  void getorders()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String urlget = getString(R.string.url)+"fetch_orders_user.php?id="+user_id;
        //Toast.makeText(getContext(), ""+user_id, Toast.LENGTH_SHORT).show();
        final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, urlget, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();
                        try {
                            arrayList.clear();
                            JSONArray jsonArray=response.getJSONArray("orders");
                            for (int i=0 ;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                Log.i(TAG, "onResponse: "+jsonObject);
                                String orderid=jsonObject.getString("order_id");
                                String totalamount=jsonObject.getString("price");
                                String  deliverycharges=jsonObject.getString("delivery_charges");
                                String brandname= jsonObject.getString("brand_name");
                                String color=jsonObject.getString("color");
                                String modelname= jsonObject.getString("model_name");
                                String storage= jsonObject.getString("storage");
                                String price= jsonObject.getString("price");
                                String is_pta_approved=jsonObject.getString("pta_approved_status");
                                String order_status=jsonObject.getString("order_status");

                                arrayList.add(new OrderClass(orderid,totalamount,deliverycharges,price,brandname,modelname,storage,color,is_pta_approved,order_status));


                            }
                          OrderslistAdapter adapter=new OrderslistAdapter(arrayList,getContext());
                           recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

                        progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);

    }
    public class OrderslistAdapter extends RecyclerView.Adapter<OrderslistAdapter.MyViewHolder>{


        Dialog myDialog;
        Context context;
        ArrayList<OrderClass> aList;

        public OrderslistAdapter(ArrayList<OrderClass> aList, Context context)
        {
            this.aList=aList;
            this.context=context;
        }
        @NonNull
        @Override
        public OrderslistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_layout_user_orders,viewGroup,false);
            return new OrderslistAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final  OrderslistAdapter.MyViewHolder myViewHolder, final int i) {


           // myViewHolder.tvtotalamount.setText(String.valueOf(Integer.parseInt(aList.get(i).getDeliverycharges())+Integer.parseInt(aList.get(i).getTotalamount())));

            myViewHolder.tvtotalamount.setText(aList.get(i).getTotalamount());
            myViewHolder.tvdelivereycharges.setText(aList.get(i).getDeliverycharges());
            myViewHolder.tvorderid.setText("Order ID = "+aList.get(i).getOrderid());


            if (aList.get(i).getOrder_status().equals("Pending"))
            {

                myViewHolder.imgapproved.setColorFilter(ContextCompat.getColor(getContext(), R.color.lightgrey));
                myViewHolder.imgdispatch.setColorFilter(ContextCompat.getColor(getContext(), R.color.lightgrey));

            }else if(aList.get(i).getOrder_status().equals("Approved"))
            {
                myViewHolder.imageViewdell.setVisibility(View.GONE);
                myViewHolder.imgapproved.setColorFilter(ContextCompat.getColor(getContext(), R.color.main_green_color));
                myViewHolder.imgdispatch.setColorFilter(ContextCompat.getColor(getContext(), R.color.lightgrey));

            }
            else  if(aList.get(i).getOrder_status().equals("Dispatched")){
                myViewHolder.imageViewdell.setVisibility(View.GONE);
                myViewHolder.imgapproved.setColorFilter(ContextCompat.getColor(getContext(), R.color.main_green_color));
                myViewHolder.imgdispatch.setColorFilter(ContextCompat.getColor(getContext(), R.color.main_green_color));

            }
            myViewHolder.btndetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.popup_detail);
                    ImageView imageView=dialog.findViewById(R.id.idimgclosepopup);

                    price=dialog.findViewById(R.id.idtvprice);
                    tvshortdetail=dialog.findViewById(R.id.idtvshortdetail);
                    tvsetpta=dialog.findViewById(R.id.idtvsetpta);
                    tvsetbrand=dialog.findViewById(R.id.idtvsetbrand);
                    tvsetmodel=dialog.findViewById(R.id.idtvsetmodel);
                    tvstorage=dialog.findViewById(R.id.idtvstorage);
                    tvsetcolor=dialog.findViewById(R.id.idtvsetcolor);

                    price.setText("Price : "+aList.get(i).getPrice());
                    tvshortdetail.setText(aList.get(i).getBrandname() +" "+aList.get(i).getModelname()+" "+aList.get(i).getStorage());
                    tvsetbrand.setText(aList.get(i).getBrandname() );
                    tvsetmodel.setText(aList.get(i).getModelname());
                    tvsetcolor.setText(aList.get(i).getColor());
                    tvstorage.setText(aList.get(i).getStorage());
                    tvsetpta.setText(aList.get(i).getPtaapproved());
//

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            });

            myViewHolder.imageViewdell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Delete Order")
                            .setContentText("Do you want to Delete?")
                            .setCancelText("No")
                            .setConfirmText("Yes")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            }) .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.cancel();
                            final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setContentText("PleaseWait..");
                            progressDialog.show();
                            String url= Constants.ROOT_URL+"delete_order_user.php";
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                   // Toast.makeText(context, ""+aList.get(i).getOrderid()+aList.get(i).getOrder_status(), Toast.LENGTH_SHORT).show();
                                   // Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                                    if (response.equals("success"))
                                    {
                                        removeItemFromView(i);
                                    }



                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    //  Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();

                                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setContentText(error.getMessage())
                                            .setTitleText("Some Error Occured").show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams()  {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("id",aList.get(i).getOrderid());
                                    return params;

                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            requestQueue.add(request);

                        }
                    }).show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return aList.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView tvtotalamount,tvdelivereycharges,tvorderid;
            Button btndetail;
            ImageView imgapproved,imgdispatch;
            Button imageViewdell;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);


                tvorderid=itemView.findViewById(R.id.idtvorderid);
                tvtotalamount=itemView.findViewById(R.id.idtvtotal);
                tvdelivereycharges=itemView.findViewById(R.id.idtvdc);
                btndetail=itemView.findViewById(R.id.idbtndetail);
                imgapproved=itemView.findViewById(R.id.idimgapproved);
                imgdispatch=itemView.findViewById(R.id.idimgdispatch);
                imageViewdell=itemView.findViewById(R.id.iddellicon);


            }
        }

        public void removeItemFromView(int position) {
            this.aList.remove(position);
            notifyItemRemoved(position);
        }

    }

}
