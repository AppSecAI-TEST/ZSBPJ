package view.lcc.wyzsb.view.home;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import view.lcc.wyzsb.R;
import view.lcc.wyzsb.adapter.home.FilterLeftAdapter;
import view.lcc.wyzsb.adapter.home.FilterOneAdapter;
import view.lcc.wyzsb.adapter.home.FilterRightAdapter;
import view.lcc.wyzsb.bean.model.FilterData;
import view.lcc.wyzsb.bean.model.FilterEntity;
import view.lcc.wyzsb.bean.model.FilterTwoEntity;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class FilterView extends LinearLayout implements View.OnClickListener {

    TextView tvCategoryTitle;
    ImageView ivCategoryArrow;
    TextView tvSortTitle;
    ImageView ivSortArrow;
    TextView tvFilterTitle;
    ImageView ivFilterArrow;
    LinearLayout llCategory;
    LinearLayout llSort;
    LinearLayout llFilter;
    ListView lvLeft;
    ListView lvRight;
    LinearLayout llHeadLayout;
    LinearLayout llContentListView;
    View viewMaskBg;

    private Context mContext;
    private Activity mActivity;

    private int filterPosition = -1;
    private int lastFilterPosition = -1;
    // 分类的位置
    public static final int POSITION_CATEGORY = 0;
    // 排序的位置
    public static final int POSITION_SORT = 1;
    // 筛选的位置
    public static final int POSITION_FILTER = 2;

    private boolean isShowing = false;
    private int panelHeight;
    private FilterData filterData;

    private FilterLeftAdapter leftAdapter;
    private FilterRightAdapter rightAdapter;
    private FilterOneAdapter sortAdapter;
    private FilterOneAdapter filterAdapter;
    // 被选择的分类项左侧数据
    private FilterTwoEntity leftSelectedCategoryEntity;
    // 被选择的分类项右侧数据
    private FilterEntity rightSelectedCategoryEntity;
    // 被选择的排序项
    private FilterEntity selectedSortEntity;
    // 被选择的筛选项
    private FilterEntity selectedFilterEntity;

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_layout, this);


        tvCategoryTitle = (TextView) view.findViewById(R.id.tv_category_title);
        ivCategoryArrow = (ImageView) view.findViewById(R.id.iv_category_arrow);
        tvSortTitle = (TextView) view.findViewById(R.id.tv_sort_title);
        ivSortArrow = (ImageView) view.findViewById(R.id.iv_sort_arrow);
        tvFilterTitle = (TextView) view.findViewById(R.id.tv_filter_title);
        ivFilterArrow = (ImageView) view.findViewById(R.id.iv_filter_arrow);
        llCategory = (LinearLayout) view.findViewById(R.id.ll_category);

        llSort = (LinearLayout) view.findViewById(R.id.ll_sort);
        llFilter = (LinearLayout) view.findViewById(R.id.ll_filter);
        lvLeft = (ListView) view.findViewById(R.id.lv_left);
        lvRight = (ListView) view.findViewById(R.id.lv_right);
        llHeadLayout = (LinearLayout) view.findViewById(R.id.ll_head_layout);
        llContentListView = (LinearLayout) view.findViewById(R.id.ll_content_list_view);
        viewMaskBg =  view.findViewById(R.id.view_mask_bg);
        initView();
        initListener();
    }

    private void initView() {
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    private void initListener() {
        llCategory.setOnClickListener(this);
        llSort.setOnClickListener(this);
        llFilter.setOnClickListener(this);
        viewMaskBg.setOnClickListener(this);
        llContentListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_category:
                filterPosition = 0;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_sort:
                filterPosition = 1;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_filter:
                filterPosition = 2;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.view_mask_bg:
                hide();
                break;
        }
    }

    /**
     * 复位筛选的显示状态
     */
    public void resetViewStatus() {
        tvCategoryTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivCategoryArrow.setImageResource(R.drawable.home_down_arrow);

        tvSortTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivSortArrow.setImageResource(R.drawable.home_down_arrow);

        tvFilterTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivFilterArrow.setImageResource(R.drawable.home_down_arrow);
    }

    /**
     * 复位所有的状态
     */
    public void resetAllStatus() {
        resetViewStatus();
        hide();
    }

    /**
     * 设置分类数据
     */
    private void setCategoryAdapter() {
        lvLeft.setVisibility(VISIBLE);
        lvRight.setVisibility(VISIBLE);

        // 左边列表视图
        leftAdapter = new FilterLeftAdapter(mContext, filterData.getCategory());
        lvLeft.setAdapter(leftAdapter);
        if (leftSelectedCategoryEntity == null) {
            leftSelectedCategoryEntity = filterData.getCategory().get(0);
        }
        leftAdapter.setSelectedEntity(leftSelectedCategoryEntity);

        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leftSelectedCategoryEntity = filterData.getCategory().get(position);
                leftAdapter.setSelectedEntity(leftSelectedCategoryEntity);

                // 右边列表视图
                rightAdapter = new FilterRightAdapter(mContext, leftSelectedCategoryEntity.getList());
                lvRight.setAdapter(rightAdapter);
                rightAdapter.setSelectedEntity(rightSelectedCategoryEntity);
            }
        });

        // 右边列表视图
        rightAdapter = new FilterRightAdapter(mContext, leftSelectedCategoryEntity.getList());
        lvRight.setAdapter(rightAdapter);
        rightAdapter.setSelectedEntity(rightSelectedCategoryEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rightSelectedCategoryEntity = leftSelectedCategoryEntity.getList().get(position);
                rightAdapter.setSelectedEntity(rightSelectedCategoryEntity);
                if (onItemCategoryClickListener != null) {
                    onItemCategoryClickListener.onItemCategoryClick(leftSelectedCategoryEntity, rightSelectedCategoryEntity);
                }
                hide();
            }
        });
    }

    /**
     * 设置排序数据
     */
    private void setSortAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        sortAdapter = new FilterOneAdapter(mContext, filterData.getSorts());
        lvRight.setAdapter(sortAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSortEntity = filterData.getSorts().get(position);
                sortAdapter.setSelectedEntity(selectedSortEntity);
                if (onItemSortClickListener != null) {
                    onItemSortClickListener.onItemSortClick(selectedSortEntity);
                }
                hide();
            }
        });
    }

    /**
     * 设置筛选数据
     */
    private void setFilterAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        filterAdapter = new FilterOneAdapter(mContext, filterData.getFilters());
        lvRight.setAdapter(filterAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFilterEntity = filterData.getFilters().get(position);
                filterAdapter.setSelectedEntity(selectedFilterEntity);
                if (onItemFilterClickListener != null) {
                    onItemFilterClickListener.onItemFilterClick(selectedFilterEntity);
                }
                hide();
            }
        });
    }

    /**
     * 动画显示
     */
    public void show(int position) {
        if (isShowing && lastFilterPosition == position) {
            hide();
            return;
        } else if (!isShowing) {
            viewMaskBg.setVisibility(VISIBLE);
            llContentListView.setVisibility(VISIBLE);
            llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    llContentListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    panelHeight = llContentListView.getHeight();
                    ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
                }
            });
        }
        isShowing = true;
        resetViewStatus();
        rotateArrowUp(position);
        rotateArrowDown(lastFilterPosition);
        lastFilterPosition = position;

        switch (position) {
            case POSITION_CATEGORY:
                tvCategoryTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivCategoryArrow.setImageResource(R.drawable.home_down_arrow_red);
                setCategoryAdapter();
                break;
            case POSITION_SORT:
                tvSortTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivSortArrow.setImageResource(R.drawable.home_down_arrow_red);
                setSortAdapter();
                break;
            case POSITION_FILTER:
                tvFilterTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivFilterArrow.setImageResource(R.drawable.home_down_arrow_red);
                setFilterAdapter();
                break;
        }
    }

    /**
     * 隐藏动画
     */
    public void hide() {
        isShowing = false;
        resetViewStatus();
        rotateArrowDown(filterPosition);
        rotateArrowDown(lastFilterPosition);
        filterPosition = -1;
        lastFilterPosition = -1;
        viewMaskBg.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(llContentListView, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    /**
     * 设置筛选数据
     */
    public void setFilterData(Activity activity, FilterData filterData) {
        this.mActivity = activity;
        this.filterData = filterData;
    }

    /**
     * 是否显示
     */
    public boolean isShowing() {
        return isShowing;
    }

    public int getFilterPosition() {
        return filterPosition;
    }

    /**
     * 旋转箭头向上
     */
    private void rotateArrowUp(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowUpAnimation(ivCategoryArrow);
                break;
            case POSITION_SORT:
                rotateArrowUpAnimation(ivSortArrow);
                break;
            case POSITION_FILTER:
                rotateArrowUpAnimation(ivFilterArrow);
                break;
        }
    }

    /**
     * 旋转箭头向下
     */
    private void rotateArrowDown(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowDownAnimation(ivCategoryArrow);
                break;
            case POSITION_SORT:
                rotateArrowDownAnimation(ivSortArrow);
                break;
            case POSITION_FILTER:
                rotateArrowDownAnimation(ivFilterArrow);
                break;
        }
    }

    /**
     * 旋转箭头向上
     */
    public static void rotateArrowUpAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        iv.startAnimation(animation);
    }

    /**
     * 旋转箭头向下
     */
    public static void rotateArrowDownAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        iv.startAnimation(animation);
    }

    /**
     * 筛选视图点击
     */
    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }

    /**
     * 分类Item点击
     */
    private OnItemCategoryClickListener onItemCategoryClickListener;

    public void setOnItemCategoryClickListener(OnItemCategoryClickListener onItemCategoryClickListener) {
        this.onItemCategoryClickListener = onItemCategoryClickListener;
    }

    public interface OnItemCategoryClickListener {
        void onItemCategoryClick(FilterTwoEntity leftEntity, FilterEntity rightEntity);
    }

    /**
     * 排序Item点击
     */
    private OnItemSortClickListener onItemSortClickListener;

    public void setOnItemSortClickListener(OnItemSortClickListener onItemSortClickListener) {
        this.onItemSortClickListener = onItemSortClickListener;
    }

    public interface OnItemSortClickListener {
        void onItemSortClick(FilterEntity entity);
    }

    /**
     * 筛选Item点击
     */
    private OnItemFilterClickListener onItemFilterClickListener;

    public void setOnItemFilterClickListener(OnItemFilterClickListener onItemFilterClickListener) {
        this.onItemFilterClickListener = onItemFilterClickListener;
    }

    public interface OnItemFilterClickListener {
        void onItemFilterClick(FilterEntity entity);
    }

}
