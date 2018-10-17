package bobLearn.viewpagerOptimization;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import bobLearn.util.DensityUtil;
import bobLearn.viewpagerOptimization.indicator.CommonNavigator;
import bobLearn.viewpagerOptimization.indicator.CommonNavigatorAdapter;
import bobLearn.viewpagerOptimization.indicator.IPagerIndicator;
import bobLearn.viewpagerOptimization.indicator.IPagerTitleView;
import bobLearn.viewpagerOptimization.indicator.LinePagerIndicator;
import bobLearn.viewpagerOptimization.indicator.MagicIndicator;
import bobLearn.viewpagerOptimization.indicator.ScaleTransitionPagerTitleView;
import bobLearn.viewpagerOptimization.indicator.SimplePagerTitleView;
import bobLearn.viewpagerOptimization.indicator.ViewPagerHelper;
import learn.bob.com.androidlearn.R;

public class FragmentVpActivity extends AppCompatActivity {
    private static final String[] TITLES = {
            "1", "2", "3", "4"
    };

    private MagicIndicator mIndicator;
    private CommonNavigatorAdapter mNavigatorAdapter;

    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private FragmentVpAdapter mVpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        findView();
        initView();
    }

    private void initView() {
        mFragments = new ArrayList<>();
        OneFragment oneFragment = new OneFragment();
        TwoFragment twoFragment = new TwoFragment();
        ThreeFragment threeFragment = new ThreeFragment();
        FourFragment fourFragment = new FourFragment();
        mFragments.add(oneFragment);
        mFragments.add(twoFragment);
        mFragments.add(threeFragment);
        mFragments.add(fourFragment);
        mVpAdapter = new FragmentVpAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mVpAdapter);

        mIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setScrollPivotX(0.8f);
        mNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                return FragmentVpActivity.this.getTitleView(context, index);
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return FragmentVpActivity.this.getIndicator(context);
            }
        };
        commonNavigator.setAdapter(mNavigatorAdapter);
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }

    private IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setText(TITLES[index]);
        simplePagerTitleView.setTextSize(17);
        simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.blackalpha50));
        simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.black));
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });
        return simplePagerTitleView;
    }

    private IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
        indicator.setYOffset(DensityUtil.dip2px(0));
        indicator.setLineHeight(DensityUtil.dip2px(1));
        indicator.setColors(ContextCompat.getColor(context, R.color.colorAccent));
        return indicator;
    }

    private void findView() {
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.vp);
    }
}
