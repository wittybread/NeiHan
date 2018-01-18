package com.example.jim.neihan.fragments.sy.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jim.neihan.R;
import com.example.jim.neihan.fragments.sy.sy_vp.Sy_Child_Fragment;
import com.example.jim.neihan.fragments.sy.sybean.SyTabData;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * http://lf.snssdk.com/neihan/service/tabs/
 * <p>
 * Created by jim on 2018/1/10.
 */

public class SyFragment extends Fragment implements SyTabViewAPI {
    private List<String> tab_list;
    private List<Fragment> fr_list;
    private TabLayout tab_layout;
    private ViewPager vp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.sy_view, null);
        tab_layout = (TabLayout) inflate.findViewById(R.id.tab_layout);
        vp = (ViewPager) inflate.findViewById(R.id.vp);

        //设置tab的值
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SyTabPresenter syTabPresenter = new SyTabPresenter(this, getContext());
        syTabPresenter.getTabData("http://lf.snssdk.com/");

    }

    @Override
    public void onSuccess(SyTabData syTabData) {
        tab_list = new ArrayList<>();
        tab_list.clear();
        List<SyTabData.DataBean> data = syTabData.getData();
        for (int i = 0; i < data.size(); i++) {
            tab_list.add(data.get(i).getName());
        }

        //设置适配器
        vp.setAdapter(new TabAdapter(getChildFragmentManager(), data));
        //进行关联
        tab_layout.setupWithViewPager(vp);
    }

    @Override
    public void onFailed(String err) {

    }

    //适配器
    class TabAdapter extends FragmentPagerAdapter {
        private List<SyTabData.DataBean> list;

        public TabAdapter(FragmentManager fm, List<SyTabData.DataBean> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tab_list.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("Tab_url", list.get(position).getUrl());
            bundle.putInt("Tab_type", list.get(position).getList_id());


            Sy_Child_Fragment sy_child_fragment = new Sy_Child_Fragment();
            sy_child_fragment.setArguments(bundle);
            return sy_child_fragment;
        }

        @Override
        public int getCount() {


            return tab_list.size();
        }
    }


}
