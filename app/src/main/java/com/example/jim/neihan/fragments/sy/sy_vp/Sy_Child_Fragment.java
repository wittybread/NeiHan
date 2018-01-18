package com.example.jim.neihan.fragments.sy.sy_vp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jim.neihan.R;
import com.example.jim.neihan.fragments.sy.SyAPI;
import com.example.jim.neihan.fragments.sy.sybean.SyChildBean;
import com.example.jim.neihan.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jim on 2018/1/10.
 */

public class Sy_Child_Fragment extends Fragment implements SyVpViewAPI {

    private String url;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.tab_view, null);
        TextView viewById = inflate.findViewById(R.id.tv_tab_url);
        mRecyclerView = inflate.findViewById(R.id.my_recycler_view);


        Bundle arguments = getArguments();
        url = (String) arguments.get("Tab_url");
        int tab_type = arguments.getInt("Tab_type");
        viewById.setText(url);

        SyVpPresenter syVpPresenter = new SyVpPresenter(this, getContext());

        syVpPresenter.getTabData("http://lf.snssdk.com", tab_type + "");
        return inflate;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSuccess(SyChildBean syTabData) {
        if (syTabData.getData().getData().size() != 0) {
            List<SyChildBean.DataBeanX.DataBean> data = syTabData.getData().getData();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            //创建并设置Adapter
            mRecyclerView.setAdapter(new SyAdapter(data, getContext()));
        }

    }

    @Override
    public void onFailed(String err) {
        Log.d("WXW", err);
    }
}
