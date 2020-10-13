package com.example.buyer.Buy;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.buyer.Buy.CartItemClass;
import com.example.buyer.Constants;
import com.example.buyer.Dashboard.Dashboard_Buyer_Fragment;
import com.example.buyer.Dashboard.Productseller_Class;
import com.example.buyer.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SuccessTickView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.android.volley.VolleyLog.TAG;

public class FragmentBuyNewAccess  extends Fragment implements AdapterView.OnItemSelectedListener {

    String user_id,price,modelName,color,image,isApproved,storage,brandName,id;

    EditText edAddress;

    Spinner spCity;

    Button btnOrder;

    int cart_total,quantity,grandTotal;
    String isAccessory;
    ArrayList<String> aListS;

    ImageView imageViewback;
    TextView txtCharges,TotalTxt;
    RecyclerView recyclerView;
    ArrayList<CartItemClass> aList;
    CartItemClass cartItemClass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_buy_new_access, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView=getView().findViewById(R.id.idrvmenulist);
        imageViewback=getView().findViewById(R.id.idbackdetail);
        txtCharges=getView().findViewById(R.id.chargesTxt);
        TotalTxt=getView().findViewById(R.id.TotalTxt);
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            isAccessory=bundle.getString("isAccess");
            cart_total=bundle.getInt("cart_total");
            storage=bundle.getString("storage");
            brandName=bundle.getString("brandName");
            color=bundle.getString("color");
            image=bundle.getString("image");
            modelName=bundle.getString("modelName");
            isApproved=bundle.getString("isApproved");
            id=bundle.getString("id");
            price=bundle.getString("price");
            quantity=bundle.getInt("quantity");

        }

        //Toast.makeText(getContext(), ""+isAccessory, Toast.LENGTH_SHORT).show();

        aListS= new ArrayList<>();
        edAddress=getView().findViewById(R.id.idedaddress);
        spCity=getView().findViewById(R.id.idspcity);
        aList=new ArrayList<CartItemClass>();

        cartItemClass=new CartItemClass( id,brandName,  modelName,  color, storage,  price ,image,isApproved,quantity);
        aList.add(cartItemClass);

        spCity.setOnItemSelectedListener(this);
        load_cities();

        FragmentBuyNewAccess.MoblistAdapter adapter = new FragmentBuyNewAccess.MoblistAdapter(aList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnOrder=getView().findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "charges: "+txtCharges.getText().toString(), Toast.LENGTH_SHORT).show();

                    buyNowAccessory();

            }
        });

        SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        user_id=sharedPreferences.getString("user_id","0");

        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        getprofile_data();
    }


    public void buyNowAccessory()
    {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setContentText("PleaseWait..");

        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"add_order_access.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()

                {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                .setContentText("Order Placed Successfully").show();

                        FragmentManager fragmentManager;
                        FragmentTransaction fragmentTransaction;
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(android.R.id.content, new Dashboard_Buyer_Fragment());
                        fragmentTransaction.commit();

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
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",user_id);
                params.put("address",edAddress.getText().toString());
                params.put("city",spCity.getSelectedItem().toString());
                params.put("amount",String.valueOf(grandTotal));
                params.put("charges",txtCharges.getText().toString());

                return params;
            }
        };
        // add it to the RequestQueue
        queue.add(request);

    }

    public void load_cities()
    {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setContentText("PleaseWait..");

        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"fetch_city_province.php";
        final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            aListS.clear();
                            String t_city,t_province;
                            JSONArray jsonArray=response.getJSONArray("cities");
                            for (int i=0 ;i<jsonArray.length();i++) {
                                t_city=jsonArray.getJSONObject(i).getString("city_name");
                                t_province=jsonArray.getJSONObject(i).getString("province");
                                aListS.add(t_city+","+t_province);

                            }
                            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, aListS);
                            arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                            spCity.setAdapter(arrayAdapter);
                            String provinceFromSpinner=spCity.getSelectedItem().toString().trim();
                            load_charges(provinceFromSpinner);
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

    public void load_charges(String spinnerItem)
    {

        //Toast.makeText(getContext(), "province: "+province, Toast.LENGTH_SHORT).show();
        final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setContentText("PleaseWait..");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= getString(R.string.url)+"fetch_delivery_charges.php?getData=";
        final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url+spinnerItem, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getContext(), "response: "+response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {

                            String t_city,t_province;
                            JSONArray jsonArray=response.getJSONArray("MyData");
                            for (int i=0 ;i<jsonArray.length();i++) {
                                t_city=jsonArray.getJSONObject(i).getString("charges");
                                txtCharges.setText("Charges: "+t_city);
                                grandTotal=cart_total+Integer.parseInt(t_city);
                                TotalTxt.setText("Total RS: "+String.valueOf(grandTotal));

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String label = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(getContext(), "label: "+label, Toast.LENGTH_SHORT).show();
        load_charges(label);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class MoblistAdapter extends RecyclerView.Adapter<FragmentBuyNewAccess.MoblistAdapter.MyViewHolder>{

        Dialog myDialog;
        Context context;
        ArrayList<CartItemClass> aList;

        public MoblistAdapter(ArrayList<CartItemClass> aList, Context context)
        {
            this.aList=aList;
            this.context=context;
        }
        @NonNull
        @Override
        public FragmentBuyNewAccess.MoblistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order,viewGroup,false);
            return new FragmentBuyNewAccess.MoblistAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final  FragmentBuyNewAccess.MoblistAdapter.MyViewHolder myViewHolder, final int i) {

            myViewHolder.tvmenuname.setText(aList.get(i).getBrandname());
            myViewHolder.tvdescription.setText(aList.get(i).getModelname());
            myViewHolder.tvprice.setText("Rs: "+aList.get(i).getPrice());
            myViewHolder.tvColor.setText("Color: "+aList.get(i).getColor());
            myViewHolder.tvQntty.setText("Quantity: "+aList.get(i).getQuantity());

//            byte[] decodedString = Base64.decode(aList.get(i).getImage(), Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            RequestOptions requestOptions=new RequestOptions().error(R.drawable.ic_launcher_background);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(Constants.ROOT_URL+""+aList.get(i).getImage().replaceAll("\\/","/"))
                    .into(myViewHolder.imageView);


        }
        @Override
        public int getItemCount() {
            return aList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView tvmenuname,tvprice,tvdescription,tvcat,tvColor,tvQntty;
            CardView cv;
            ImageView imageView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvQntty=itemView.findViewById(R.id.txtQCart);
                tvColor=itemView.findViewById(R.id.idrvmenucolor);
                tvmenuname=itemView.findViewById(R.id.idtrvmenuname);
                tvprice=itemView.findViewById(R.id.idrvmenuprice);

                tvdescription=itemView.findViewById(R.id.idrvmenudescription);
                imageView=itemView.findViewById(R.id.idrvimageviewmenulist);
                cv=itemView.findViewById(R.id.menucardId);
            }
        }
    }

    public void getprofile_data()
    {

        final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setContentText("PleaseWait..");

        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"fetch_buyer.php?id="+user_id;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getContext(), ""+edAddress.getText().toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {

                            JSONArray jsonArray=response.getJSONArray("buyer");
                            for (int i=0 ;i<jsonArray.length();i++)
                            {
                                JSONObject profileobj= jsonArray.getJSONObject(i);
                                Log.i(TAG, "onResponse: "+profileobj);
                                edAddress.setText(profileobj.getString("address"));
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
}
