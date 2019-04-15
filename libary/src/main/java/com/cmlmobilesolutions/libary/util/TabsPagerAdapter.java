package com.cmlmobilesolutions.libary.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


/**
 * Created by addmn.cassio on 08/05/2017.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_TABS = 5;

   /* public TabsPagerAdapter(FragmentManager fm,int nivel) {

        super(fm);
    }*/
    private ArrayList<Fragment> screens;

    public TabsPagerAdapter(FragmentManager fm, ArrayList<Fragment> screens) {
        super(fm);
        this.screens = screens;
    }

    @Override
    public Fragment getItem(int position) {

        return screens.get(position);
    }

    @Override
    public int getCount() {
        return screens.size();
    }
    /*@Override
    public Fragment getItem(int position) {
        FragmentMainActivity fragmentMainActivity =FragmentMainActivity.newInstance(position);

        //fragmentMainActivity.OnTrocaNivel(position);
        return fragmentMainActivity;

      *//*  switch(position){
            case 0:

               // fragmentMainActivity.OnTrocaNivel(position);
                return fragmentMainActivity;
            case 1:
               // fragmentMainActivity.OnTrocaNivel(position);
                return fragmentMainActivity;
            case 2:
              //  fragmentMainActivity.OnTrocaNivel(position);
                return fragmentMainActivity;
            case 3:
               // fragmentMainActivity.OnTrocaNivel(position);
                return fragmentMainActivity;
            case 4:
                // fragmentMainActivity.OnTrocaNivel(position);
                return fragmentMainActivity;
            default:
                //fragmentMainActivity.OnTrocaNivel(position);
                return fragmentMainActivity;
        }*//*
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }*/

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0:
                return "Matéria Prima";
            case 1:
                return "Produto Acabado";
            case 2:
                return "Embalagens";
            case 3:
                return "Sincronizar";
            default:
                return "Graficos";
        }


    }
}