package com.yu.useviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *  使用Fragment+ViewPager打造通讯录
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<PageFragment> mViews;
    private String[] mTitles = {"拨号","联系人","信息"};
    private TextView tvDialer,tvContacts,tvMsg;
    PagerAdapter adapter;
    ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();
        pager = (ViewPager) findViewById(R.id.id_view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        FragmentPageChangerListener listener = new FragmentPageChangerListener();
        pager.addOnPageChangeListener(listener);
        pager.setAdapter(adapter);

    }

    private void initViews() {
        tvDialer = (TextView) findViewById(R.id.id_tv_dialer);
        tvContacts = (TextView) findViewById(R.id.id_tv_contacts);
        tvMsg = (TextView) findViewById(R.id.id_tv_message);

        tvDialer.setOnClickListener(this);
        tvContacts.setOnClickListener(this);
        tvMsg.setOnClickListener(this);
    }

    private void initDatas() {
        mViews = new ArrayList<>();
        PageFragment fragment;
        for (int i=0;i<mTitles.length;i++) {
            fragment = PageFragment.newInstance(mTitles[i]);
            mViews.add(fragment);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_dialer:
                pager.setCurrentItem(0);
                break;
            case R.id.id_tv_contacts:
                pager.setCurrentItem(1);
                break;
            case R.id.id_tv_message:
                pager.setCurrentItem(2);
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mViews.size();
        }


        @Override
        public Fragment getItem(int position) {
            return mViews.get(position);
        }
    }

    class FragmentPageChangerListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            updateTitleStyle(position);
        }



        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 更新标题样式
     * @param position
     */
    private void updateTitleStyle(int position) {
        tvDialer.setTextColor(Color.GRAY);
        tvContacts.setTextColor(Color.GRAY);
        tvMsg.setTextColor(Color.GRAY);
        switch (position) {
            case 0:
                tvDialer.setTextColor(Color.parseColor("#6677ff"));
                break;
            case 1:
                tvContacts.setTextColor(Color.parseColor("#6677ff"));
                break;
            case 2:
                tvMsg.setTextColor(Color.parseColor("#6677ff"));
                break;
        }
    }
}
