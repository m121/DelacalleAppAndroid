package com.delacalle.delacalle.delacalleapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;


/**
 * Created by pc on 18/11/2015.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    final int PAGE_COUNT = 2;
    private int tabIcons[] = {R.mipmap.ic_caliente, R.drawable.ic_lista};
    private String tabTitles[] = new String[] { "Tab1", "Tab2" };

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PageFragment.newInstance(0);
            case 1:
                return PageFragmentTwo.newInstance(1);

            default:
                return null;
        }
    }

    @Override
    public int getPageIconResId(int position) {
        // Generate title based on item position
        return tabIcons[position];
    }




}