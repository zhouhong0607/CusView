package com.example.macroz.myapplication.customview.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.activity.BaseActivity;
import com.example.macroz.myapplication.utils.Utils;
import com.example.macroz.myapplication.customview.fragment.PagerItemFragment;
import com.example.macroz.myapplication.customview.adapter.MyFragmentPagerAdapter;
import com.example.macroz.myapplication.customview.adapter.MyPagerAdapter;
import com.example.macroz.myapplication.customview.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTestActivity extends BaseActivity {
    private MyViewPager mViewPager;
    private List<View> mViews;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test);
        mViewPager = findViewById(R.id.test_viewpager);
        initViews();
        initFragments();
//        mViewPager.setAdapter(new SubCusTomPagerAdapter<>(mViews));
        mViewPager.setAdapter(new SubCusTomFragmentPagerAdapter(getSupportFragmentManager(), mFragments));
    }

    private void initViews() {
        mViews = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            ImageView imageView = new ImageView(this);

            ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
            lp.width = 200;
            lp.height = 200;

            imageView.setLayoutParams(lp);
            imageView.setImageBitmap(Utils.getBitmap(getResources(), R.drawable.xiaoxiao));
            mViews.add(imageView);
        }
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Fragment fragment = new PagerItemFragment();
            mFragments.add(fragment);
        }

    }


    /**
     * PagerAdapter
     */
    static class SubCusTomPagerAdapter<T extends View> extends MyPagerAdapter {

        List<T> mViewList;

        public SubCusTomPagerAdapter(List<T> viewList) {
            mViewList = viewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViewList.get(position));
        }
    }

    /**
     * FragmentPagerAdapter
     */
    static class SubCusTomFragmentPagerAdapter extends MyFragmentPagerAdapter {
        private List<Fragment> mFragments;

        public SubCusTomFragmentPagerAdapter(FragmentManager fm, List<Fragment> Fragments) {
            super(fm);
            this.mFragments = Fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
//
//    /**
//     * FragmentStatePagerAdapter
//     */
//    static class CustomFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
//        public CustomFragmentStatePagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//    }

}
