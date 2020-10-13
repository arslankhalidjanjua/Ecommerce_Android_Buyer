package com.example.buyer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefManager {

    Context c;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public PrefManager(Context c)
    {
        this.c = c;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.c);
        this.editor = sharedPreferences.edit();
    }



    public void setDay(String day)
    {
        editor.putString("day" , day);
        editor.commit();

    }

    public String getDay()
    {
        return sharedPreferences.getString("day" , "14");
    }

    public void setImage(String image)
    {
        editor.putString("image" , image);
        editor.commit();

    }

    public String getImage()
    {
        return sharedPreferences.getString("image" , "");
    }

    public void setLogin(boolean firstTime)
    {
        editor.putBoolean("FirstTime" , firstTime);
        editor.commit();

    }


    public boolean isLogin()
    {
        return sharedPreferences.getBoolean("FirstTime" , false);
    }


}

