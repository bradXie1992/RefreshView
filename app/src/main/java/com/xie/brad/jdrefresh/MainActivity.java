package com.xie.brad.jdrefresh;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    @InjectView(R.id.myscrollView)
    MyScrollView myscrollView;
    @InjectView(R.id.pulltorefreshview)
    PullToRefreshView pulltorefreshview;
    @InjectView(R.id.homeSearchLeftImage)
    LinearLayout homeSearchLeftImage;
    @InjectView(R.id.home_chat_image)
    LinearLayout homeChatImage;
    @InjectView(R.id.homeSearchInfo)
    TextView homeSearchInfo;
    @InjectView(R.id.home_search)
    RelativeLayout homeSearch;
    @InjectView(R.id.search_bar)
    RelativeLayout searchBar;
    @InjectView(R.id.home_title)
    RelativeLayout homeTitle;
    @InjectView(R.id.activity_main)
    RelativeLayout activityMain;
    @InjectView(R.id.first)
    LinearLayout first;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initScolltoRef(savedInstanceState);
    }


    //设置下拉刷新
    private void initScolltoRef(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("alpha")) {
                progress = savedInstanceState.getInt("alpha", 0);
            } else {
                progress = 0;
            }
        }

        homeTitle.getBackground().setAlpha(progress);
        pulltorefreshview.setOnHeaderRefreshListener(this);
        pulltorefreshview.setOnFooterRefreshListener(this);
        myscrollView.setOnScrollListener(new MyScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int x, int scrollY, int oldxX, int oldY) {
                if (first != null && first.getHeight() > 0) {
                    int lHeight = first.getHeight();
                    if (scrollY < lHeight) {
                        progress = (int) (new Float(scrollY) / new Float(lHeight) * 255);
                        homeTitle.getBackground().setAlpha(progress);
                    } else {
                        progress = 255;
                        homeTitle.getBackground().setAlpha(progress);
                    }
                }
            }

            @Override
            public void isScrollBottom(boolean scrollbottom) {

            }
        });
        //设置头部状态栏消失或者出现的回调
        pulltorefreshview.setOnHeaderShowListener(new PullToRefreshView.OnHeaderShowListener() {
            @Override
            public void onHeadershow(boolean isShow) {
                if (isShow) {
                    homeTitle.clearAnimation();
                    homeTitle.setVisibility(View.GONE);
                } else {
//                    myAnimation = AnimationUtils.loadAnimation(HomeFragment.this.getActivity(), R.anim.alpha_anim);
//                    homeTitle.startAnimation(myAnimation);
                    homeTitle.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("alpha", progress);
    }



    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        pulltorefreshview.postDelayed(refresh, 1000);
    }
    Runnable refresh = new Runnable() {
        @Override
        public void run() {
            pulltorefreshview.onHeaderRefreshComplete();
        }
    };

    @Override
    public void onFooterRefresh(PullToRefreshView view) {

    }
}
