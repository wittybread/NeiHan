package com.example.jim.neihan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jim.neihan.fragments.fx.FxFragment;
import com.example.jim.neihan.fragments.sy.tab.SyFragment;
import com.example.jim.neihan.fragments.wd.WdFragment;
import com.example.jim.neihan.fragments.xx.XxFragment;
import com.hjm.bottomtabbar.BottomTabBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //配置底部按钮
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(70, 70)
                .setFontSize(12)
                .setTabPadding(4, 6, 10)
                .setChangeColor(Color.RED, Color.DKGRAY)
                .addTabItem("首页", R.drawable.sy_n, SyFragment.class)
                .addTabItem("发现", R.drawable.fl_n, FxFragment.class)
                .addTabItem("消息", R.drawable.dpj, XxFragment.class)
                .addTabItem("我的", R.drawable.wd_n, WdFragment.class)
                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name, View view) {

                    }

                });

    }

}
