package fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import cn.liu.mynewsapp.MainActivity;
import cn.liu.mynewsapp.R;

/**
 * Created by liu on 2017-03-03.
 */

public class NewsFragment extends BaseFragment {
    private ViewPager newsPager;
    private  String [] typesEn;
    private  String [] types;
    private ImageButton menu;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_newsdata, null);
        newsPager = (ViewPager) view.findViewById(R.id.news_viewpager);
        menu= (ImageButton) view.findViewById(R.id.ib_menu);
        types=getResources().getStringArray(R.array.news_type_cn);
        typesEn=getResources().getStringArray(R.array.news_type_en);
        NewsPagerAdapter newsPagerAdapter=new NewsPagerAdapter(getActivity().getSupportFragmentManager());
        newsPager.setAdapter(newsPagerAdapter);

        //设置新闻指示器
        MagicIndicator magicIndicator= (MagicIndicator) view.findViewById(R.id.news_magicindicator);
        CommonNavigator commonNavigator=new CommonNavigator(mActivity);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
               return types == null ? 0:  types.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.BLACK);
                colorTransitionPagerTitleView.setSelectedColor(Color.RED);
                colorTransitionPagerTitleView.setText(types[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newsPager.setCurrentItem(index);
                    }
                });

                return colorTransitionPagerTitleView;
            }

            //设置指示器类型
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator=new LinePagerIndicator(mActivity);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return  indicator;

            }
        });
        magicIndicator.setNavigator(commonNavigator);
        //viewpager绑定指示器
        ViewPagerHelper.bind(magicIndicator,newsPager);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity MainUi= (MainActivity) mActivity;
                MainUi.OpenCloseDrawerLayout(true);
            }
        });

        return view;
    }

    @Override
    public void initData() {

    }

    class NewsPagerAdapter extends FragmentStatePagerAdapter{

        public NewsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new NewsDetailFragment(typesEn[position]);
        }

        @Override
        public int getCount() {
            return types.length;
        }
    }



}
