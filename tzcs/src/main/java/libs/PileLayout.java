package libs;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.hsfcompany.tzcs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmuSistone on 2017/5/12.
 */

public class PileLayout extends ViewGroup {

    private List<FrameLayout> itemViewList = new ArrayList<>();
    private OnClickListener onClickListener;

    // 以下三个参数，可通过属性定制
    private int interval = 30; // view之间的间隔
    private float sizeRatio = 1.1f;
    private float scaleStep = 0.36f;

    private int everyWidth;
    private int everyHeight;
    private int scrollDistanceMax; // 滑动参考值
    private List<Integer> originX = new ArrayList<>(); // 存放的是最初的七个View的位置

    // 拖拽相关
    private static final int MODE_IDLE = 0;
    private static final int MODE_HORIZONTAL = 1;
    private static final int MODE_VERTICAL = 2;
    private int scrollMode;
    private int downX, downY;
    private float lastX;
    private final int mTouchSlop; // 判定为滑动的阈值，单位是像素

    private float animateValue;
    private ObjectAnimator animator;
    private Interpolator interpolator = new DecelerateInterpolator();
    private Adapter adapter;
    private boolean hasSetAdapter = false;
    private float displayCount = 1.6f;
    private FrameLayout animatingView;

    public PileLayout(Context context) {
        this(context, null);
    }

    public PileLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PileLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.pile);
        interval = (int) a.getDimension(R.styleable.pile_interval, interval);
        sizeRatio = a.getFloat(R.styleable.pile_sizeRatio, sizeRatio);
        scaleStep = a.getFloat(R.styleable.pile_scaleStep, scaleStep);
        displayCount = a.getFloat(R.styleable.pile_displayCount, displayCount);
        a.recycle();

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();

        onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != adapter) {
                    int position = Integer.parseInt(v.getTag().toString());
                    if (position >= 0 && position < adapter.getItemCount()) {
                        adapter.onItemClick(((FrameLayout) v).getChildAt(0), position);
                    }
                }
            }
        };

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getHeight() > 0 && null != adapter && !hasSetAdapter) {
                    setAdapter(adapter);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        everyWidth = (int) ((width - getPaddingLeft() - getPaddingRight() - interval * 8) / displayCount);
        everyHeight = (int) (everyWidth * sizeRatio);
        setMeasuredDimension(width, (int) (everyHeight * (1 + scaleStep) + getPaddingTop() + getPaddingBottom()));

        // 把每个View的初始位置坐标都计算好
        if (originX.size() == 0) {
            int position0 = 0;
            originX.add(position0);

            int position1 = interval;
            originX.add(position1);

            int position2 = interval * 2;
            originX.add(position2);

            int position3 = interval * 3;
            originX.add(position3);

            int position4 = (int) (position3 + everyWidth * (1 + scaleStep) + interval);
            originX.add(position4);

            int position5 = position4 + everyWidth + interval;
            originX.add(position5);

            int position6 = position5 + everyWidth + interval;
            originX.add(position6);

            int position7 = position6 + everyWidth + interval;
            originX.add(position7);

            int position8 = position7 + everyWidth + interval;
            originX.add(position8);

            scrollDistanceMax = position4 - position3;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int num = getChildCount();
        for (int i = 0; i < num; i++) {
            View itemView = getChildAt(i);
            int left = originX.get(i);
            int top = (getMeasuredHeight() - everyHeight) / 2;
            int right = left + everyWidth;
            int bottom = top + everyHeight;
            itemView.layout(left, top, right, bottom);
            itemView.setPivotX(0);
            itemView.setPivotY(everyHeight / 2);
            adjustScale(itemView);
            adjustAlpha(itemView);
        }
    }

    /**
     * 根据X坐标位置调整ImageView的透明度
     *
     * @param itemView 需要调整的imageView
     */
    private void adjustAlpha(View itemView) {
        int position2 = originX.get(2);
        if (itemView.getLeft() >= position2) {
            itemView.setAlpha(1);
        } else {
            int position0 = originX.get(0);
            float alpha = (float) (itemView.getLeft()) / (position2 - position0);
            itemView.setAlpha(alpha);
        }
    }

    private void adjustScale(View itemView) {
        float scale = 1.0f;
        int position4 = originX.get(4);
        int thisLeft = itemView.getLeft();
        if (thisLeft < position4) {
            int position3 = originX.get(3);
            if (thisLeft > position3) {
                scale = 1 + scaleStep - scaleStep * (thisLeft - position3) / (position4 - position3);
            } else {
                int position2 = originX.get(2);
                scale = 1 + (thisLeft - position2) * scaleStep / interval;
            }
        }
        itemView.setScaleX(scale);
        itemView.setScaleY(scale);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // 决策是否需要拦截
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                lastX = event.getX();
                scrollMode = MODE_IDLE;
                if (null != animator) {
                    animator.cancel();
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if (scrollMode == MODE_IDLE) {
                    float xDistance = Math.abs(downX - event.getX());
                    float yDistance = Math.abs(downY - event.getY());
                    if (xDistance > yDistance && xDistance > mTouchSlop) {
                        // 水平滑动，需要拦截
                        scrollMode = MODE_HORIZONTAL;
                        return true;
                    } else if (yDistance > xDistance && yDistance > mTouchSlop) {
                        // 垂直滑动
                        scrollMode = MODE_VERTICAL;
                    }
                }
                break;
        }
        return false; // 默认都是不拦截的
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 此处说明底层没有子View愿意消费Touch事件
                break;

            case MotionEvent.ACTION_MOVE:
                int currentX = (int) event.getX();
                int dx = (int) (currentX - lastX);
                requireScrollChange(dx);
                lastX = currentX;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                animatingView = itemViewList.get(3);
                lastX = animatingView.getLeft();
                float destX = originX.get(3);
                int tag = Integer.parseInt(animatingView.getTag().toString());
                if (event.getX() > downX) {
                    destX = originX.get(4);
                    tag--;
                }
                if (tag < 0 || tag >= adapter.getItemCount() || Math.abs(lastX - destX) < mTouchSlop) {
                    return true;
                }

                adapter.displaying(tag);
                animator = ObjectAnimator.ofFloat(this, "animateValue", lastX, destX);
                animator.setInterpolator(interpolator);
                animator.setDuration(300).start();

                break;
        }
        return true;
    }

    private void requireScrollChange(int dx) {
        if (dx == 0) {
            return;
        }

        int currentPosition = Integer.parseInt(itemViewList.get(3).getTag().toString());
        if (dx < 0 && currentPosition >= adapter.getItemCount()) {
            return;
        } else if (dx > 0) {
            if (currentPosition <= 0) {
                return;
            } else if (currentPosition == 1) {
                if (itemViewList.get(3).getLeft() + dx >= originX.get(4)) {
                    dx = originX.get(4) - itemViewList.get(3).getLeft();
                }
            }
        }


        int num = itemViewList.size();

        // 1. View循环复用
        FrameLayout firstView = itemViewList.get(0);
        if (dx > 0 && firstView.getLeft() >= originX.get(1)) {
            // 向右滑动，从左边把View补上
            FrameLayout lastView = itemViewList.get(itemViewList.size() - 1);
            itemViewList.remove(lastView);
            itemViewList.add(0, lastView);

            LayoutParams lp = lastView.getLayoutParams();
            removeViewInLayout(lastView);
            addViewInLayout(lastView, 0, lp);

            int tag = Integer.parseInt(lastView.getTag().toString());
            tag -= num;
            lastView.setTag(tag);
            if (tag < 0) {
                lastView.setVisibility(View.INVISIBLE);
            } else {
                lastView.setVisibility(View.VISIBLE);
                adapter.bindView(lastView.getChildAt(0), tag);
            }
        } else if (dx < 0 && firstView.getLeft() <= originX.get(0)) {
            // 向左滑动，从右边把View补上
            itemViewList.remove(firstView);
            itemViewList.add(firstView);

            LayoutParams lp = firstView.getLayoutParams();
            removeViewInLayout(firstView);
            addViewInLayout(firstView, -1, lp);

            int tag = Integer.parseInt(firstView.getTag().toString());
            tag += num;
            firstView.setTag(tag);
            if (tag >= adapter.getItemCount()) {
                firstView.setVisibility(View.INVISIBLE);
            } else {
                firstView.setVisibility(View.VISIBLE);
                adapter.bindView(firstView.getChildAt(0), tag);
            }
        }

        // 2. 位置修正
        View view3 = itemViewList.get(3);
        float rate = (float) ((view3.getLeft() + dx) - originX.get(3)) / scrollDistanceMax;
        if (rate < 0) {
            rate = 0;
        }
        for (int i = 0; i < num; i++) {
            View itemView = itemViewList.get(i);
            int position = (int) (rate * (originX.get(i + 1) - originX.get(i))) + originX.get(i);
            if (position > originX.get(i + 1) && i + 1 < originX.size()) {
                position = originX.get(i + 1);
            }

            itemView.offsetLeftAndRight(position - itemView.getLeft());
            adjustAlpha(itemView); // 调整透明度
            adjustScale(itemView); // 调整缩放
        }
    }

    /**
     * 绑定Adapter
     */
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;

        // ViewdoBindAdapter尚未渲染出来的时候，不做适配
        if (everyWidth > 0 && everyHeight > 0) {
            doBindAdapter();
        }
    }


    /**
     * 真正绑定Adapter
     */
    private void doBindAdapter() {
        if (adapter == null) {
            return;
        }
        if (hasSetAdapter) {
            throw new RuntimeException("PileLayout can only hold one adapter.");
        }
        hasSetAdapter = true;
        if (getChildCount() == 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            for (int i = 0; i < 6; i++) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                View view = inflater.inflate(adapter.getLayoutId(), null);
                FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                lp1.width = everyWidth;
                lp1.height = everyHeight;
                frameLayout.addView(view, lp1);
                LayoutParams lp2 = new LayoutParams(everyWidth, everyHeight);
                lp2.width = everyWidth;
                lp2.height = everyHeight;
                frameLayout.setLayoutParams(lp2);
                frameLayout.setOnClickListener(onClickListener);
                addView(frameLayout);
                frameLayout.setTag(i - 3); // 这个tag主要是对应于在dataList中的数据index
                frameLayout.measure(everyWidth, everyHeight);
                itemViewList.add(frameLayout);
            }
        }

        int num = itemViewList.size();
        for (int i = 0; i < num; i++) {
            if (i < 3) {
                itemViewList.get(i).setVisibility(View.INVISIBLE);
            } else {
                FrameLayout frameLayout = itemViewList.get(i);
                frameLayout.setVisibility(View.VISIBLE);
                adapter.bindView(frameLayout.getChildAt(0), i - 3);
            }
        }
        adapter.displaying(0);
    }

    /**
     * 数据更新通知
     */
    public void notifyDataSetChanged() {
        int num = itemViewList.size();
        for (int i = 0; i < num; i++) {
            FrameLayout frameLayout = itemViewList.get(i);
            int tag = Integer.parseInt(frameLayout.getTag().toString());
            if (tag > 0 && tag < adapter.getItemCount()) {
                frameLayout.setVisibility(View.VISIBLE);
                adapter.bindView(frameLayout.getChildAt(0), tag);
            } else {
                frameLayout.setVisibility(View.INVISIBLE);
            }

            if (i == 3 && tag == 0) {
                adapter.displaying(0);
            }
        }
    }


    /**
     * 属性动画，请勿删除
     */
    public void setAnimateValue(float animateValue) {
        this.animateValue = animateValue; // 当前应该在的位置
        int dx = Math.round(animateValue - animatingView.getLeft());
        requireScrollChange(dx);
        lastX = animateValue;
    }

    public float getAnimateValue() {
        return animateValue;
    }

    /**
     * 适配器
     */
    public static abstract class Adapter {

        /**
         * layout文件ID，调用者必须实现
         */
        public abstract int getLayoutId();

        /**
         * item数量，调用者必须实现
         */
        public abstract int getItemCount();

        /**
         * View与数据绑定回调，可重载
         */
        public void bindView(View view, int index) {
        }

        /**
         * 正在展示的回调，可重载
         */
        public void displaying(int position) {
        }

        /**
         * item点击，可重载
         */
        public void onItemClick(View view, int position) {
        }
    }
}