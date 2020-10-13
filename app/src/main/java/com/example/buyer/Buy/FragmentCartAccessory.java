package com.example.buyer.Buy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.buyer.Buy.Fragment_Buy_new;
import com.example.buyer.Constants;
import com.example.buyer.Dashboard.Dashboard_Buyer_Fragment;
import com.example.buyer.Dashboard.MobileClass;
import com.example.buyer.Dashboard.Productseller_Class;
import com.example.buyer.MobDetail.Fragment_MobileDetail_New;
import com.example.buyer.MobDetail.Fragment_MobileDetail_Used;
import com.example.buyer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FragmentCartAccessory extends Fragment {
    Button btnBuyNow;
    EditText editTextCount;
    ArrayList<CartItemClass> aList;
    String user_id;
    RecyclerView recyclerView;
    ImageView imgbackdetail;
    int cart_total=0;
    String isAccessory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_cart_accessory,container,false);
        btnBuyNow=view.findViewById(R.id.idbtnbuynow);
        imgbackdetail=view.findViewById(R.id.idbackdetail);
    //    isAccessory=getArguments().getString("isAccess");
        //Toast.makeText(getContext(), ""+isAccessory, Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aList=new ArrayList<CartItemClass>();

        recyclerView=getView().findViewById(R.id.idrvcartlist);

        SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        user_id=sharedPreferences.getString("user_id","0");

        loadCart();

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
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
    public void loadCart()
    {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setContentText("PleaseWait..");

        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"fetch_cart_items_access.php?id="+user_id;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            aList.clear();
                            cart_total=0;
                            CartItemClass cartItemClass;
                            JSONArray jsonArray=response.getJSONArray("cart_items");
                            for (int i=0 ;i<jsonArray.length();i++)
                            {
                                String id,brandname,color,modelname,storage,used_status,price,is_pta_approved,image,seller_name,seller_id;
                                String model_id,brand_id,quantity;
                                JSONObject product= jsonArray.getJSONObject(i);
                                id=product.getString("id");
                                brandname= product.getString("brand_name");
                                color=product.getString("color");
                                modelname= product.getString("model_name");
                                storage= product.getString("storage");
                                price= product.getString("price");
                                quantity= product.getString("quantity");
                                is_pta_approved=product.getString("pta_approved_status");
                                image=product.getString("image");
                                //                          model_id=product.getString("model_id");
                                //                            seller_id=product.getString("seller_id");
                                //                              seller_name=product.getString("seller_name");
//                                brand_id=product.getString("brand_id");
                                int quantityI=Integer.parseInt(quantity);
                                cartItemClass=new CartItemClass( id,brandname,  modelname,  color, storage,  price ,image,is_pta_approved,quantityI);

                                aList.add(cartItemClass);

                            }

                            MoblistAdapter adapter = new MoblistAdapter(aList, getContext());
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

    public class MoblistAdapter extends RecyclerView.Adapter<MoblistAdapter.MyViewHolder>{

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
        public MoblistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_rv_cart,viewGroup,false);
            return new MoblistAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final  MoblistAdapter.MyViewHolder myViewHolder, final int i) {

            myViewHolder.tvmenuname.setText(aList.get(i).getBrandname());
            myViewHolder.tvdescription.setText(aList.get(i).getModelname());
            myViewHolder.tvprice.setText("Rs: "+aList.get(i).getPrice());
            myViewHolder.tvColor.setText("Color: "+aList.get(i).getColor());
            myViewHolder.tvQntty.setText("Quantity: "+aList.get(i).getQuantity());

//            byte[] decodedString = Base64.decode(aList.get(i).getImage(), Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            RequestOptions requestOptions=new RequestOptions().error(R.drawable.ic_launcher_background);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(Constants.ROOT_URL+aList.get(i).getImage().replaceAll("\\/","/"))
                    .into(myViewHolder.imageView);
            myViewHolder.imgdell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemRemoved(i);
                    removeCartItem(aList.get(i).getId());

                }
            });

            myViewHolder.btnbuyproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String priceN=aList.get(i).getPrice();
                    int quantityN=aList.get(i).getQuantity();
                    if (quantityN>1)
                    {
                        cart_total=Integer.parseInt(priceN)*quantityN;
                    }
                    else
                    {
                        cart_total=Integer.parseInt(priceN);
                    }
                    //Toast.makeText(context, "total: "+cart_total, Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager;
                    FragmentTransaction fragmentTransaction;
                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    Fragment_Buy_new fragment_buy_new=new Fragment_Buy_new();
                    Bundle bundle=new Bundle();
                    bundle.putString("price",priceN);
                    bundle.putInt("quantity",quantityN);
                    bundle.putString("brandName",aList.get(i).getBrandname());
                    bundle.putString("storage",aList.get(i).getStorage());
                    bundle.putString("modelName",aList.get(i).getModelname());
                    bundle.putString("color",aList.get(i).getColor());
                    bundle.putString("image",aList.get(i).getImage());
                    bundle.putString("isApproved",aList.get(i).getIs_ptaapproved());
                    bundle.putString("id",aList.get(i).getId());
                    bundle.putInt("cart_total",cart_total);
                    bundle.putString("isAccess",isAccessory);
                    fragment_buy_new.setArguments(bundle);
                    fragmentTransaction.replace(android.R.id.content, fragment_buy_new).addToBackStack(null);
                    fragmentTransaction.commit();
                    //Toast.makeText(context, ""+isAccessory, Toast.LENGTH_SHORT).show();
                }
            });
//            myViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            //              @Override
//                public void onClick(View v) {
            //                   Bundle bundle=new Bundle();
//                    bundle.putString("model_id",aList.get(i).getId());
//                    bundle.putString("PTA",aList.get(i).getIs_ptaapproved());
//                    bundle.putString("Brand",aList.get(i).getBrandname());
//                    bundle.putString("Model",aList.get(i).getModelname());
//                    bundle.putString("color",aList.get(i).getColor());
//                    bundle.putString("Price",aList.get(i).getPrice());


            //            }
            //       });

        }
        @Override
        public int getItemCount() {
            return aList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            Button btnbuyproduct;
            TextView tvmenuname,tvprice,tvdescription,tvcat,tvColor,tvQntty;
            CardView cv;
            ImageView imageView,imgdell;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                btnbuyproduct=itemView.findViewById(R.id.id_btnbuy);

                tvQntty=itemView.findViewById(R.id.txtQCart);
                imgdell=itemView.findViewById(R.id.iddelicon);
                tvColor=itemView.findViewById(R.id.idrvmenucolor);
                tvmenuname=itemView.findViewById(R.id.idtrvmenuname);
                tvprice=itemView.findViewById(R.id.idrvmenuprice);

                tvdescription=itemView.findViewById(R.id.idrvmenudescription);
                imageView=itemView.findViewById(R.id.idrvimageviewmenulist);
                cv=itemView.findViewById(R.id.menucardId);
            }
        }
    }

    public void removeCartItem(String id)
    {

        final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setContentText("PleaseWait..");

        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"delete_cart_item_access.php?id="+id;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if(response.contains("success"))
                        {
                            loadCart();

                        }
                        else
                        {
                            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setContentText("There was an Error in deleting item").show();
                            loadCart();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setContentText(error.getMessage()).show();
                        loadCart();
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);

    }


}
