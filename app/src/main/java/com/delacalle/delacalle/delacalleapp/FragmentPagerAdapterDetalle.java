package com.delacalle.delacalle.delacalleapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FragmentPagerAdapterDetalle extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Información", "Carta", "Comentarios" };

    public FragmentPagerAdapterDetalle(FragmentManager fm) {
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
                return PageFragmentInformacionDetalle.newInstance(0);
            case 1:
                return PageFragmentCartaDetalle.newInstance(1);
            case 2:
                return FragmentPageComentariosDetalle.newInstance(2);

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
