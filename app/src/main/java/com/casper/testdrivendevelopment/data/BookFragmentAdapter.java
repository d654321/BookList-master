package com.casper.testdrivendevelopment.data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

public class BookFragmentAdapter extends FragmentPagerAdapter {  //管理导航条的标题和Fragment
    ArrayList<Fragment> datas;  //存放要显示的子视图
    ArrayList<String> titles;  //存放要显示的标题

    public BookFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(ArrayList<Fragment> datas) {
        this.datas = datas;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {  //获取对应位置的Fragment
        return datas == null ? null : datas.get(position);
    }

    @Override
    public int getCount() {  //获取子视图Fragment的数目
        return datas == null ? 0 : datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {  //获取对应位置的标题
        return titles == null ? null : titles.get(position);
    }
}
