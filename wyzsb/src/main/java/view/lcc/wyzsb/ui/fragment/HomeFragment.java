package view.lcc.wyzsb.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import view.lcc.wyzsb.R;
import view.lcc.wyzsb.adapter.home.TravelingAdapter;
import view.lcc.wyzsb.base.BaseFragment;
import view.lcc.wyzsb.bean.Article;
import view.lcc.wyzsb.bean.Video;
import view.lcc.wyzsb.bean.model.ChannelEntity;
import view.lcc.wyzsb.bean.model.FilterData;
import view.lcc.wyzsb.bean.model.FilterEntity;
import view.lcc.wyzsb.bean.model.FilterTwoEntity;
import view.lcc.wyzsb.bean.model.OperationEntity;
import view.lcc.wyzsb.bean.model.TravelingEntity;
import view.lcc.wyzsb.frame.Frame;
import view.lcc.wyzsb.mvp.param.ArticleParams;
import view.lcc.wyzsb.mvp.param.HomeParams;
import view.lcc.wyzsb.mvp.param.VideoParams;
import view.lcc.wyzsb.mvp.presenter.ArticlePresenter;
import view.lcc.wyzsb.mvp.presenter.HomeFragmentPresenter;
import view.lcc.wyzsb.mvp.presenter.VideoPresenter;
import view.lcc.wyzsb.mvp.presenter.impl.ArticlePresenterImpl;
import view.lcc.wyzsb.mvp.presenter.impl.HomeFragmentPresenterImpl;
import view.lcc.wyzsb.mvp.presenter.impl.VideoPresenterImpl;
import view.lcc.wyzsb.mvp.view.ArticleView;
import view.lcc.wyzsb.mvp.view.HomeFragmentView;
import view.lcc.wyzsb.mvp.view.VideoView;
import view.lcc.wyzsb.ui.activity.article.ArticleActivity;
import view.lcc.wyzsb.ui.activity.article.ArticleDetailsActivity;
import view.lcc.wyzsb.ui.activity.setting.AboutActivity;

import view.lcc.wyzsb.ui.activity.video.VideoDetailsActivity1;
import view.lcc.wyzsb.utils.ColorUtil;
import view.lcc.wyzsb.utils.DensityUtil;
import view.lcc.wyzsb.utils.ModelUtil;
import view.lcc.wyzsb.utils.StatusBarUtil;
import view.lcc.wyzsb.view.home.FilterView;
import view.lcc.wyzsb.view.home.HeaderBannerView;
import view.lcc.wyzsb.view.home.HeaderChannelView;
import view.lcc.wyzsb.view.home.HeaderDividerView;
import view.lcc.wyzsb.view.home.HeaderFilterView;
import view.lcc.wyzsb.view.home.HeaderOperationView;
import view.lcc.wyzsb.view.home.SmoothListView.SmoothListView;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2017年04月08日15:38:09
 * Description:  主页
 */
public class HomeFragment extends BaseFragment implements SmoothListView.ISmoothListViewListener
        ,ArticleView,TravelingAdapter.ItemClickListener,HeaderChannelView.OnChannelClickListener,View.OnClickListener{
    private SmoothListView smoothListView;

    private FilterView realFilterView;

    private RelativeLayout rlBar;

    private TextView tvTitle;

    private View viewTitleBg;

    private View viewActionMoreBg;

    private FrameLayout flActionMore;

    private Context mContext;
    private Activity mActivity;
    // 屏幕高度
    private int mScreenHeight;
    // 广告数据
    private List<String> bannerList = new ArrayList<>();
    // 频道数据
    private List<ChannelEntity> channelList = new ArrayList<>();
    // 运营数据
    private List<OperationEntity> operationList = new ArrayList<>();
    // ListView数据
    private List<Article> travelingList = new ArrayList<>();
    // 广告视图
    private HeaderBannerView headerBannerView;
    // 频道视图
    private HeaderChannelView headerChannelView;
    // 运营视图
    private HeaderOperationView headerOperationView;
    // 分割线占位图
    private HeaderDividerView headerDividerView;
    // 分类筛选视图
    private HeaderFilterView headerFilterView;
    // 筛选数据
    private FilterData filterData;
    private TravelingAdapter mAdapter;
    // 标题栏的高度
    private int titleViewHeight = 65;
    // 从ListView获取的广告子View
    private View itemHeaderBannerView;
    // 广告视图的高度
    private int bannerViewHeight = 180;
    // 广告视图距离顶部的距离
    private int bannerViewTopMargin;
    // 从ListView获取的筛选子View
    private View itemHeaderFilterView;
    // 筛选视图的位置
    private int filterViewPosition = 4;
    // 筛选视图距离顶部的距离
    private int filterViewTopMargin;
    // ListView是否在滑动
    private boolean isScrollIdle = true;
    // 是否吸附在顶部
    private boolean isStickyTop = false;
    // 没有吸附的前提下，是否在滑动
    private boolean isSmooth = false;
    // 点击FilterView的位置：分类(0)、排序(1)、筛选(2)
    private int filterPosition = -1;
    //Presenter
    private ArticlePresenter articlePresenter;

    protected int currentPage = 1;

    @Override
    public int initContentView() {
        return R.layout.home_fragment;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    private ArticleParams params;

    private String a_l = "";
    private String a_c = "";
    private String a_type = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,null);
        smoothListView = (SmoothListView) view.findViewById(R.id.listView);
        realFilterView = (FilterView) view.findViewById(R.id.real_filterView);
        rlBar = (RelativeLayout) view.findViewById(R.id.rl_bar);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        viewTitleBg =  view.findViewById(R.id.view_title_bg);
        viewActionMoreBg =  view.findViewById(R.id.view_action_more_bg);
        flActionMore = (FrameLayout) view.findViewById(R.id.fl_action_more);
        StatusBarUtil.setStatusBarTranslucent(getActivity(), false);
        view.findViewById(R.id.fl_action_more).setOnClickListener(this);
        initData();
        initView();
        initListener();
        return view;
    }

    @Override
    public void initUI(View view) {

    }

    public void initData() {
        articlePresenter = new ArticlePresenterImpl(this);
        mContext = getActivity();
        mActivity = getActivity();
        mScreenHeight = DensityUtil.getWindowHeight(getActivity());
        // 筛选数据
        filterData = new FilterData();
        filterData.setCategory(ModelUtil.getCategoryData());
        filterData.setSorts(ModelUtil.getSortData());
        filterData.setFilters(ModelUtil.getFilterData());
        // 广告数据
        bannerList = ModelUtil.getBannerData();
        // 频道数据
        channelList = ModelUtil.getChannelData();
        // 运营数据
        operationList = ModelUtil.getOperationData();
    }

    private void initView() {
        // 设置广告数据
        headerBannerView = new HeaderBannerView(getActivity());
        headerBannerView.fillView(bannerList, smoothListView);
        // 设置频道数据
        headerChannelView = new HeaderChannelView(getActivity());
        headerChannelView.fillView(channelList, smoothListView);
        headerChannelView.setOnChannelClickListener(this);
        // 设置运营数据
        headerOperationView = new HeaderOperationView(getActivity());
        headerOperationView.fillView(operationList, smoothListView);
        // 设置分割线
        headerDividerView = new HeaderDividerView(getActivity());
        headerDividerView.fillView("", smoothListView);
        // 设置假FilterView数据
        headerFilterView = new HeaderFilterView(getActivity());
        headerFilterView.fillView(new Object(), smoothListView);
        // 设置真FilterView数据
        realFilterView.setFilterData(mActivity, filterData);
        realFilterView.setVisibility(View.GONE);

        mAdapter = new TravelingAdapter(getActivity(), travelingList);
        mAdapter.setOnItemClickListener(this);
        smoothListView.setAdapter(mAdapter);
        filterViewPosition = smoothListView.getHeaderViewsCount() - 1;


        params = new ArticleParams();
        params.setPage(currentPage);
        params.setA_c(a_c);
        params.setA_l(a_l);
        params.setA_type(a_type);
        articlePresenter.getData(params);
    }

    private void initListener() {
        // 关于项目
        flActionMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.startAboutActivity(getActivity());
            }
        });
        // (假的ListView头部展示的)筛选视图点击
        headerFilterView.getFilterView().setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooth = true;
                smoothListView.smoothScrollToPositionFromTop(filterViewPosition,
                        DensityUtil.dip2px(mContext, titleViewHeight));
            }
        });
        // (真正的)筛选视图点击
        realFilterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                realFilterView.show(position);
                smoothListView.smoothScrollToPositionFromTop(filterViewPosition,
                        DensityUtil.dip2px(mContext, titleViewHeight));
            }
        });
        // 分类Item点击
        realFilterView.setOnItemCategoryClickListener(new FilterView.OnItemCategoryClickListener() {
            @Override
            public void onItemCategoryClick(FilterTwoEntity leftEntity, FilterEntity rightEntity) {
                currentPage = 1;
                if (rightEntity.getKey().equals("全部")){
                    a_type = "";
                }else {
                    a_type = rightEntity.getKey();
                }
                params = new ArticleParams();
                params.setPage(currentPage);
                params.setA_c(a_c);
                params.setA_l(a_l);
                params.setA_type(a_type);
                articlePresenter.getData(params);
            }
        });
        // 排序Item点击
        realFilterView.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                currentPage = 1;
                if (entity.getKey().equals("全部")){
                    a_c = "";
                }else {
                    a_c = entity.getKey();
                }
                params = new ArticleParams();
                params.setPage(currentPage);
                params.setA_c(a_c);
                params.setA_l(a_l);
                params.setA_type(a_type);
                articlePresenter.getData(params);
            }
        });
        // 筛选Item点击
        realFilterView.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                currentPage = 1;
                if (entity.getKey().equals("全部")){
                    a_l = "";
                }else {
                    a_l = entity.getKey();
                }
                params = new ArticleParams();
                params.setPage(currentPage);
                params.setA_c(a_c);
                params.setA_l(a_l);
                params.setA_type(a_type);
                articlePresenter.getData(params);
            }
        });
        smoothListView.setRefreshEnable(true);
        smoothListView.setLoadMoreEnable(true);
        smoothListView.setSmoothListViewListener(this);
        smoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
            @Override
            public void onSmoothScrolling(View view) {
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && bannerViewTopMargin < 0) return;
                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderBannerView == null) {
                    itemHeaderBannerView = smoothListView.getChildAt(1);
                }
                if (itemHeaderBannerView != null) {
                    bannerViewTopMargin = DensityUtil.px2dip(mContext, itemHeaderBannerView.getTop());
                    bannerViewHeight = DensityUtil.px2dip(mContext, itemHeaderBannerView.getHeight());
                }
                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopMargin = DensityUtil.px2dip(mContext, itemHeaderFilterView.getTop());
                }
                // 处理筛选是否吸附在顶部
                if (filterViewTopMargin <= titleViewHeight || firstVisibleItem > filterViewPosition) {
                    // 吸附在顶部
                    isStickyTop = true;
                    realFilterView.setVisibility(View.VISIBLE);
                } else {
                    // 没有吸附在顶部
                    isStickyTop = false;
                    realFilterView.setVisibility(View.GONE);
                }

                if (isSmooth && isStickyTop) {
                    isSmooth = false;
                    realFilterView.show(filterPosition);
                }
                // 处理标题栏颜色渐变
                handleTitleBarColorEvaluate();
            }
        });
    }

    /**
     * 处理标题栏颜色渐变
     */
    private void handleTitleBarColorEvaluate() {
        float fraction;
        if (bannerViewTopMargin > 0) {
            fraction = 1f - bannerViewTopMargin * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(bannerViewTopMargin) * 1f;
        fraction = space / (bannerViewHeight - titleViewHeight);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rlBar.setAlpha(1f);

        if (fraction >= 1f || isStickyTop) {
            isStickyTop = true;
            viewTitleBg.setAlpha(0f);
            viewActionMoreBg.setAlpha(0f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.bg_btn_normal_color));
        } else {
            viewTitleBg.setAlpha(1f - fraction);
            viewActionMoreBg.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.bg_btn_normal_color));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        headerBannerView.enqueueBannerLoopMessage();
    }

    @Override
    public void onStop() {
        super.onStop();
        headerBannerView.removeBannerLoopMessage();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                params = new ArticleParams();
                params.setPage(currentPage);
                params.setA_c(a_c);
                params.setA_l(a_l);
                params.setA_type(a_type);
                articlePresenter.refresh(params);
                smoothListView.stopRefresh();
                smoothListView.setRefreshTime("刚刚");
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        params = new ArticleParams();
        params.setPage(currentPage);
        params.setA_c(a_c);
        params.setA_l(a_l);
        params.setA_type(a_type);
        articlePresenter.loadMore(params);
    }

    @Override
    public void getLoading() {

    }

    @Override
    public void getDataEmpty() {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void refreshOrLoadFail(String msg) {

    }

    @Override
    public void refreshDataSuccess(List<Article> list) {
        fillAdapter(list);
    }

    @Override
    public void loadMoreWeekDataSuccess(List<Article> entities) {
        if (entities.isEmpty()) {
            Frame.getInstance().toastPrompt("没有更多数据...");
        } else {
            mAdapter.addALL(entities);
        }
        smoothListView.stopLoadMore();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 填充数据（ListView的数据）
     */
    private void fillAdapter(List<Article> list) {
        if (list == null || list.size() == 0) {
            // 95 = 标题栏高度 ＋ FilterView的高度
            int height = mScreenHeight - DensityUtil.dip2px(mContext, 95);
            mAdapter.setData(ModelUtil.getNoDataEntity(height));
        } else {
            mAdapter.setData(list);
        }
    }

    @Override
    public void click(Article entity) {
        ArticleDetailsActivity.startArticleDetails(getActivity(),entity);
    }

    @Override
    public void Channel(String title) {
        ArticleActivity.startArticleActivity(getActivity(),title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fl_action_more:
                AboutActivity.startAboutActivity(getActivity());
                break;
        }
    }
}
