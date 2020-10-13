package com.example.buyer.slider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.buyer.Dashboard.Fragment_Mobile_List;
import com.example.buyer.PrefManager;
import com.example.buyer.R;
import com.example.buyer.model.SliderItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();
    ArrayList<SliderItem> aList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    PrefManager prefManager;

    public SliderAdapterExample(ArrayList<SliderItem> aList,Context context) {
        this.aList=aList;
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(final SliderAdapterVH viewHolder, final int position) {

        prefManager=new PrefManager(context);
        SliderItem sliderItem = mSliderItems.get(position);


        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        viewHolder.textBrandName.setText(sliderItem.getBrandName());
//        Glide.with(viewHolder.itemView)
//                .load(sliderItem.getImageUrl())
//                .fitCenter()
//                .into(viewHolder.imageViewBackground);
        RequestOptions requestOptions= new RequestOptions().error(R.drawable.bg_overlay);
        Glide.with(context).setDefaultRequestOptions(requestOptions)
                .load(context.getString(R.string.url)+aList.get(position).getImageUrl().replaceAll("\\/","/"))
                .into(viewHolder.imageViewBackground);
        prefManager.setImage(context.getString(R.string.url)+aList.get(position).getImageUrl().replaceAll("\\/","/"));
        //Toast.makeText(context, "url:"+aList.get(position).getImageUrl().replaceAll("\\/","/"), Toast.LENGTH_SHORT).show();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
                String brandN=viewHolder.textBrandName.getText().toString();
                fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("brandname",brandN);
                Fragment_Mobile_List obj=new Fragment_Mobile_List();
                obj.setArguments(bundle);
                fragmentTransaction.replace(R.id.idframebuyer, obj);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription,textBrandName;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            textBrandName=itemView.findViewById(R.id.txtBrandName);
            this.itemView = itemView;
        }
    }

}