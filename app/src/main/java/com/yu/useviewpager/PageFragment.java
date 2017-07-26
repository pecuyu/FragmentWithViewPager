package com.yu.useviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 显示在ViewPager中的页面
 */
public class PageFragment extends Fragment {
    public View mRootView;
    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance(String title, int color) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("color",color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView tv = (TextView) view.findViewById(R.id.id_page_fragment_text);
        tv.setText("this is "+getArguments().get("title"));
        int color = getArguments().getInt("color");
        view.setBackgroundResource(color);
        mRootView=view;
        return view;
    }



}
