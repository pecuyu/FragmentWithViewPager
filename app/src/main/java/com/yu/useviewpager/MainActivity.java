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
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 *  使用Fragment+ViewPager打造通讯录
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<PageFragment> mViews;
    private String[] mTitles = {"拨号","联系人","信息"};
    private int[] mColors = {R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark};
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
        pager.setPageTransformer(true, new RotateDownPageTransformer());
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
//        PageFragment fragment;
        for (int i=0;i<mTitles.length;i++) {
            PageFragment fragment = PageFragment.newInstance(mTitles[i],mColors[i]);
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

    /**
     * ViewPager切换动画设置原理
     *  根据view切换时的position（旋转百分比），设置view的各种属性值
     *  position记录的是当前view和即将显示的view的旋转占比值
     *  以向左滑威为例：可以划分position区间
     *   position < -1 旋转到左边，完全不可见
     *   -1 <= position <= 0 将要向左旋转出去的view
     *   0 < position < 1 将要从右显示的view
     *   position >1 完全不可见
     */

    /**
     * ViewPager切换动画
     */
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            Log.e("DepthPageTransformer ->", "view:"+view+"position:" + position);
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public class RotateDownPageTransformer implements ViewPager.PageTransformer
    {

        private static final float ROT_MAX = 20.0f;
        private float mRot;

        public void transformPage(View view, float position)
        {

            Log.e("TAG", view + " , " + position + "");

            if (position < -1)
            { // [-Infinity,-1)
                // This page is way off-screen to the left.
                ViewHelper.setRotation(view, 0);

            } else if (position <= 1) // a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                if (position < 0) {

                    mRot = (ROT_MAX * position);
                    ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(view, view.getMeasuredHeight());
                    ViewHelper.setRotation(view, mRot);
                } else {

                    mRot = (ROT_MAX * position);
                    ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(view, view.getMeasuredHeight());
                    ViewHelper.setRotation(view, mRot);
                }

                // Scale the page down (between MIN_SCALE and 1)

                // Fade the page relative to its size.

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                ViewHelper.setRotation(view, 0);
            }
        }
    }
}
