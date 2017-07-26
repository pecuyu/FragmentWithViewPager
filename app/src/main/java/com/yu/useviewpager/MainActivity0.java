package com.yu.useviewpager;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用图片填充viewpager
 */
public class MainActivity0 extends AppCompatActivity {
    private List<ImageView> mViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        ViewPager pager = (ViewPager) findViewById(R.id.id_view_pager);
        PagerAdapter adapter=new ViewPagerAdapter();
        pager.setAdapter(adapter);
    }

    private void initDatas() {
        mViews = new ArrayList<>();
        ImageView imageView;
        for (int i=0;i<5;i++) {
            imageView = new ImageView(this);
            switch (i) {
                case 0:
                    imageView.setBackgroundColor(Color.RED);
                    break;
                case 1:
                    imageView.setBackgroundColor(Color.BLUE);
                    break;
                case 2:
                    imageView.setBackgroundColor(Color.GREEN);
                    break;
                case 3:
                    imageView.setBackgroundColor(Color.GRAY);
                    break;
                case 4:
                    imageView.setBackgroundColor(Color.BLACK);
                    break;
            }
            mViews.add(imageView);
        }

    }

    class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView)object);
        }
    }

}
