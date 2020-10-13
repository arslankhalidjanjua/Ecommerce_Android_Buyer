package com.example.buyer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.buyer.Dashboard.Productseller_Class;
import com.example.buyer.MobDetail.Fragment_MobileDetail_New;
import com.example.buyer.MobDetail.Fragment_MobileDetail_Used;
import com.example.buyer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.android.volley.VolleyLog.TAG;

public class ProductFragment extends Fragment {

    SweetAlertDialog pDialog;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    RecyclerView recyclerView;
    ArrayList<Productseller_Class> arrayList=new ArrayList<>();
    String brandName;

    String model_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_product, container, false);

        recyclerView=view.findViewById(R.id.idrvmenulist);


        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if (args  != null){
            brandName= args.getString("brandname");
        }
        getmoblist_Data(brandName);


    }




    private  void getmoblist_Data(String brandName)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url= Constants.ROOT_URL+"fetch_all_accessories.php";
        final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                      //  Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {
                            arrayList.clear();
                            Productseller_Class approvedProducts_class;
                            JSONArray jsonArray=response.getJSONArray("all_approved_products");

                            for (int i=0 ;i<jsonArray.length();i++)
                            {
                                String id,brandname,color,modelname,storage,used_status,price,is_pta_approved,image,seller_name,seller_id;
                                String model_id,brand_id;
                                String is_access,waranty,accidentalwaranty,sim,shopname,salesmanNum,shopstatus,followers,rating;
                                int pricediscount;
                                JSONObject product= jsonArray.getJSONObject(i);
                                Log.i(TAG, "onResponse: "+product);
                                id=product.getString("id");
                                brandname= product.getString("brand_name");
                                color=product.getString("color_name");
                                modelname= product.getString("model_name");
                                storage= product.getString("storage_name");
                                price= product.getString("price");
                                is_pta_approved=product.getString("pta_approved_status");
                                used_status= product.getString("used_status");
                                rating=product.getString("rating");
                                image=product.getString("image");
                                model_id=product.getString("model_id");
                                seller_id=product.getString("seller_id");
                                seller_name=product.getString("seller_name");
                                brand_id=product.getString("brand_id");
                                pricediscount=product.getInt("discount_percent");
                                waranty=product.getString("warranty_type");
                                accidentalwaranty=product.getString("accidental_warranty");
                                sim=product.getString("sim");
                                shopname=product.getString("shop_name");
                                salesmanNum=product.getString("used_mobile_salesman");
                                shopstatus=product.getString("open_status");
                                followers=product.getString("followers");
                                //is_access=product.getString("isAccessory");


                                approvedProducts_class=new Productseller_Class( id,brandname,  modelname,  storage, color,  price,used_status,is_pta_approved,image,seller_id,seller_name,brand_id,model_id,String.valueOf(pricediscount),waranty,accidentalwaranty,sim,salesmanNum,shopname,shopstatus,followers,rating,"accessory");

                                arrayList.add(approvedProducts_class);

                            }


                            ProductFragment.MoblistAdapter adapter = new ProductFragment.MoblistAdapter(arrayList, getContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            if (arrayList.size()!=0)
                                recyclerView.setAdapter(adapter);
                            else
                            {
                                Toast.makeText(getContext(), "Empty List!", Toast.LENGTH_SHORT).show();
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


    public class MoblistAdapter extends RecyclerView.Adapter<ProductFragment.MoblistAdapter.MyViewHolder>{

        Dialog myDialog;
        Context context;
        ArrayList<Productseller_Class> aList;

        public MoblistAdapter(ArrayList<Productseller_Class> aList, Context context)
        {
            this.aList=aList;
            this.context=context;
        }
        @NonNull
        @Override
        public ProductFragment.MoblistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_rv_mobiles_list,viewGroup,false);
            return new ProductFragment.MoblistAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final  ProductFragment.MoblistAdapter.MyViewHolder myViewHolder, final int i) {


            myViewHolder.tvusedstatus.setText(aList.get(i).getUsed_status());
            if (aList.get(i).getUsed_status().equals("used"))
            {
                myViewHolder.tvshopopen.setText("Shop : "+aList.get(i).getOpen_status());
            }
            myViewHolder.tvbrandname.setText(aList.get(i).getBrandname());
            myViewHolder.tvmodel.setText(aList.get(i).getModelname());
            myViewHolder.tvstorage.setText(aList.get(i).getStorage());
            myViewHolder.tvcolor.setText(aList.get(i).getColor());
            myViewHolder.tvseller.setText(aList.get(i).getShop_name());
            myViewHolder.tvFollower.setText("Followers: "+aList.get(i).getFollowers());
            myViewHolder.ratingBar.setRating(Float.parseFloat(aList.get(i).getRating()));
            myViewHolder.tvdiscount.setText("Discount:"+ aList.get(i).getDiscount()+"%");
            myViewHolder.tvprice.setText("Rs:"+aList.get(i).getPrice());
            myViewHolder.tvRating.setText(aList.get(i).getRating());

//            byte[] decodedString = Base64.decode(aList.get(i).getImage(), Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            RequestOptions requestOptions=new RequestOptions().error(R.drawable.ic_launcher_background);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(Constants.ROOT_URL+aList.get(i).getImage().replaceAll("\\/","/"))
                    .into(myViewHolder.imageView);

            myViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putString("id",aList.get(i).getId());
                    bundle.putString("model_id",aList.get(i).getModel_id());
                    bundle.putString("PTA",aList.get(i).getIs_ptaapproved());
                    bundle.putString("Brand",aList.get(i).getBrandname());
                    bundle.putString("Storage",aList.get(i).getStorage());
                    bundle.putString("Model",aList.get(i).getModelname());
                    bundle.putString("color",aList.get(i).getColor());
                    bundle.putString("isAccess",aList.get(i).getIs_access());
                    bundle.putString("Price",aList.get(i).getPrice());
                    bundle.putString("Seller_id",aList.get(i).getSeller_id());
                    bundle.putString("Brand_id",aList.get(i).getBrand_id());
                    //bundle.putString("Brand_id",aList.get(i).getBrand_id());
//                    bundle.putString("Acc_name",aList.get(i).getAcc_name());
                    bundle.putString("Seller_name",aList.get(i).getSeller_name());
                    bundle.putString("image",aList.get(i).getImage());
                    bundle.putString("shop_name",aList.get(i).getShop_name());
                    bundle.putString("used_mobile_salesman",aList.get(i).getUsed_mobile_salesman());

                    if(aList.get(i).used_status.equals("used"))
                    {
                        Fragment_Accessory_Used obj=new Fragment_Accessory_Used();

                        obj.setArguments(bundle);
                        FragmentManager fragmentManager;
                        FragmentTransaction fragmentTransaction;
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(android.R.id.content,obj).addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                    else
                    {
                        Toast.makeText(context, ""+aList.get(i).getIs_access(), Toast.LENGTH_SHORT).show();
                        Fragment_Accessory_New obj=new Fragment_Accessory_New();
                        obj.setArguments(bundle);
                        FragmentManager fragmentManager;
                        FragmentTransaction fragmentTransaction;
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(android.R.id.content,obj).addToBackStack(null);
                        fragmentTransaction.commit();
                    }

                }
            });

        }
        @Override
        public int getItemCount() {
            return aList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView tvbrandname,tvprice,tvmodel,tvcolor,tvseller,tvusedstatus,tvstorage,tvdiscount,tvshopopen,tvFollower,tvRating;
            RatingBar ratingBar;
            CardView cv;
            ImageView imageView,imgdell,imgupdate;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tvRating=itemView.findViewById(R.id.txtRating);
                ratingBar=itemView.findViewById(R.id.ratingBarL);
                tvFollower=itemView.findViewById(R.id.idFollower);
                tvbrandname=itemView.findViewById(R.id.idtrvbrandname);
                tvmodel=itemView.findViewById(R.id.idrvbrandmodel);
                tvstorage=itemView.findViewById(R.id.idrvstorage);
                tvcolor=itemView.findViewById(R.id.idrvbrandcolor);
                tvdiscount=itemView.findViewById(R.id.idrvdiscount);
                tvprice=itemView.findViewById(R.id.idrvprice);
                tvseller=itemView.findViewById(R.id.idrvseller);
                tvshopopen=itemView.findViewById(R.id.idrvshopopen);
                tvusedstatus=itemView.findViewById(R.id.idtrvusedstatus);
                imageView=itemView.findViewById(R.id.idrvimageviewmoblist);
                cv=itemView.findViewById(R.id.menucardId);
            }
        }
    }

}
