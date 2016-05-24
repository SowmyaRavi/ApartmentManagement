package com.example.piyushravi.finalapartment.Adapters;

/**
 * Created by Piyush Ravi on 5/6/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.piyushravi.finalapartment.Fragments.ManageTenantList;

public class TenantDetails extends FragmentPagerAdapter {
    public TenantDetails(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {

            case 0:
                return new ManageTenantList();

            default:
                break;
        }
        return null;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 1;
    }
}