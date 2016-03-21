package com.lcc.activity.data;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.lcc.activity.R;
import com.lcc.utils.CacheHelper;
import com.lcc.utils.NetWorkUtils;
import com.lcc.view.LoadingLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestActivity extends Activity {

    //toolbar 已做题目数目统计
    private static int sTestCount = 0;
    //单选题
    public static final int TYPE_SINGLE_ANSWER = 1;
    //简答题
    public static final int TYPE_QUESTIONS_AND_ANSWERS = 2;
    //根布局
    private View mRootView;
    //小圆点控件
    private FloatingActionButton mFAB;
    //主要的内容布局
    private ScrollView mMainLayout;
    //答案选项布局
    private LinearLayout mAnswerSelectLayout;
    //加载布局
    private LoadingLayout mLoadingLayout;
    //全部题目加载完后显示的布局
    private RelativeLayout mTestEmpty;
    //当前题目
    private Test mTest;
    private TextView mTvQuestion, mTvAnswer, mTvAnswerLong;
    //答案 布局
    private RelativeLayout mRyAnswer;
    //显示答案、下一题按钮、重新加载
    private TextView mBtShowAnswer, mBtNext, mBtReset;
    //题库题量
    private int mTestCount = 0;
    //随机list 每次根据服务器总体数量生成 随机顺序的list 每次请求题目的时候取出里面的值。（做到随机而不重复题目）
    private List<Integer> mRandomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //创建UI
        creatViews();
        //设置按钮点击事件
        setButtonListener();
        //读取网上题库并加载题目
        loadTestCountAndData();
        
    }
    private void creatViews() {
        // TODO: 16/3/20
        //设置已做题目统计数
        //mParentActivity.setTextCount(0);
        //数据加载loading 布局
        mLoadingLayout = (LoadingLayout) findViewById(R.id.ly_loading);
        //小圆点
        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        //布局
        mMainLayout = (ScrollView) findViewById(R.id.sv_main_test);
        mAnswerSelectLayout = (LinearLayout) findViewById(R.id.ly_select_answers);
        mRyAnswer = (RelativeLayout) findViewById(R.id.rl_answer);
        mTestEmpty = (RelativeLayout) findViewById(R.id.ry_test_empty);
        //TextView
        mTvQuestion = (TextView) findViewById(R.id.tv_test_question);
        mTvAnswer = (TextView) findViewById(R.id.tv_answer);
        mTvAnswerLong = (TextView) findViewById(R.id.tv_answer_long);
        //button
        mBtShowAnswer = (TextView) findViewById(R.id.bt_show_answer);
        mBtNext = (TextView) findViewById(R.id.bt_next);
        mBtReset = (TextView)findViewById(R.id.bt_reset);

    }

    private void setButtonListener() {
        //网络出错时布局的点击事件
        mLoadingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataByNet();
            }
        });
        //小圆点的点击事件
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CacheHelper.getFav(mTest.getTestId() + "") == mTest.getTestId()) {
                    mFAB.setImageResource(R.mipmap.icon_fav);
                    CacheHelper.removeToFav(mTest.getTestId() + "");
                    //Snackbar.make(mParentActivity.getRootView(), "取消收藏", Snackbar.LENGTH_SHORT).show();
                } else {
                    mFAB.setImageResource(R.mipmap.icon_fav_select);
                    CacheHelper.putToFav(mTest.getTestId() + "", mTest.getTestId());
                    //Snackbar.make(mParentActivity.getRootView(), "收藏成功", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        //题库做完后提示重新加载题目的按钮
        mBtReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRandomList.clear();
                loadTestCountAndData();
            }
        });

        //下一题
        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //移除随机题库ID List 的第一项，达到随机而不重复的效果
                mRandomList.remove(0);
                //重置答案选择布局中的 答案选项
                mAnswerSelectLayout.removeAllViews();
                //如果随机题库ID List =0 证明网络题库已经全部答完，那么需要提醒用户重新做一次题目之类的。
                if (mRandomList.size() != 0) {
                    loadData();
                } else {
                    resetLayout();
                }
                //顶部toolbar做题总数统计
                // TODO: 16/3/20 此处
                //mParentActivity.setTextCount(++sTestCount);
            }
        });
        //显示答案
        mBtShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTvAnswerLong.getVisibility() == View.INVISIBLE) {
                    mTvAnswerLong.setVisibility(View.VISIBLE);
                    mBtShowAnswer.setText("隐藏");
                } else {
                    mTvAnswerLong.setVisibility(View.INVISIBLE);
                    mBtShowAnswer.setText("显示");
                }

            }
        });
    }

    private void initRandom() {
        for (int i = 0; i < mTestCount; i++) {
            mRandomList.add(i);
        }
        //打乱list
        Collections.shuffle(mRandomList);
    }

    private void loadDataByNet() {
        //加载数据前的布局操作
        preLoadLayout();
        //题库为0证明前面的读取服务器题库数目操作失败，需要重新调用，以保证mTestCount一定有值，并且这个值是服务器上题库的数量
        if (mTestCount == 0) {
            loadTestCountAndData();
        } else {
            // TODO: 16/3/20 从网上获取数据 "testId", mRandomList.get(0)
            preLoadLayout();
            SystemClock.sleep(2000);
            List<Test> list=new ArrayList<>();
            Test test=new Test();
            test.setAnswer("C");
            test.setTestId(1);
            test.setAnswerA("你猜我是谁啊a");
            test.setAnswerB("你猜我是谁啊b");
            test.setAnswerC("你猜我是谁啊c");
            test.setAnswerD("你猜我是谁啊d");
            test.setTestType(1);
            list.add(test);
            mTest = list.get(0);
            //把数据缓存到本地
            SaveCacheAsyncTask savecaheTask = new SaveCacheAsyncTask(TestActivity.this, mTest, CacheHelper.TEST + mRandomList.get(0));
            savecaheTask.execute();
            //显示题目
            showTest();
            //加载完数据后的布局操作
            postLoadLayout();
            //errorLoadLayout();
        }
    }

    private void showTest() {
        //回显此题是否被收藏
        showFav();
        //设置题目
        mTvQuestion.setText(mTest.getQuestion());
        //隐藏答案/重置文本控件中的文字内容
        mRyAnswer.setVisibility(View.GONE);
        mTvAnswerLong.setVisibility(View.INVISIBLE);
        mTvAnswerLong.setText("");
        //答案复位
        mTvAnswer.setText("");

        //单选类型
        if (mTest.getTestType() == TYPE_SINGLE_ANSWER) {
            //生成答案选项
            initSingleChoice();
            //设置答案
            mTvAnswer.setText(mTest.getAnswer());
            //隐藏显示答案的按钮
            mBtShowAnswer.setVisibility(View.GONE);
        }

        //简述类型
        if (mTest.getTestType() == TYPE_QUESTIONS_AND_ANSWERS) {
            //设置答案--简答题答案
            mTvAnswerLong.setText(Html.fromHtml(mTest.getAnswer()));
            //显示 显示答案布局
            mRyAnswer.setVisibility(View.VISIBLE);
            //显示 显示答案的按钮
            mBtShowAnswer.setVisibility(View.VISIBLE);
            //显示答案按钮文字复位
            mBtShowAnswer.setText("答案");
        }
    }

    private void loadTestCountAndData() {
        //准备网络请求前的布局变动
        preLoadLayout();
        //请求服务器中题库数目，为了客户端能更有效同步服务器题库。因此这里不做默认值，只能从网络获取题库数目。
        // TODO: 16/3/20 次数需要获取一个count
        mTestCount = 1;
        //根据题量生成随机数字集合
        initRandom();
        //读取数据，获取缓存或者网络数据
        loadData();
    }

    private void loadData() {
        //是否有缓存
        boolean hasCache = CacheHelper.isExistDataCache(TestActivity.this, CacheHelper.TEST + mRandomList.get(0));
        //缓存是否过期 这里设置了WIFI环境下5分钟过期，普通网络下48小时过期，已达到能尽可能和服务器数据同步
        boolean isCacheOverTiem = CacheHelper.isCacheDataFailure(TestActivity.this, CacheHelper.TEST + mRandomList.get(0));
        //有网络并且有没缓存||有网络且缓存过期  就请求网络数据 否则 读取缓存
        if ((NetWorkUtils.hasInternet() && !hasCache) || (NetWorkUtils.hasInternet() && isCacheOverTiem)) {
            //从网络上读取数据
            loadDataByNet();
        } else {
            //用AsyncTask读取缓存
            readCache();
        }
    }

    private void showFav() {
        //回显此题是否被收藏
        if (CacheHelper.getFav(mTest.getTestId() + "") == mTest.getTestId()) {
            mFAB.setImageResource(R.mipmap.icon_fav_select);
        } else {
            mFAB.setImageResource(R.mipmap.icon_fav);
        }
    }

    /**
     * 读取缓存
     */
    private void readCache() {
        ReadCacheAsyncTask<Test> readCacheAsyncTask = new ReadCacheAsyncTask<>(TestActivity.this);
        //设置读取缓存的回调函数，
        readCacheAsyncTask.setOnReadCacheToDo(new ReadCacheAsyncTask.OnReadCacheToDo<Test>() {
            @Override
            public void preExecute() {
                //读取缓存操作前 布局的准备操作
                preLoadLayout();
            }

            @Override
            public void postExectue(Test data) {
                //读取缓存的操作。生成选择项改变布局等操作
                mTest = data;
                showTest();
                postLoadLayout();
            }
        });
        readCacheAsyncTask.execute(CacheHelper.TEST + mRandomList.get(0));
    }

    private void initSingleChoice() {
        if (mTest.getAnswerA() != null&& !TextUtils.isEmpty(mTest.getAnswerA())) {
            AnswerItem answerA = new AnswerItem(TestActivity.this);
            answerA.setChoiceText("A");
            answerA.setChoiceContent(mTest.getAnswerA());
            answerA.setTest(mTest);
            answerA.setOnClickListener(new AnswerItemClick(answerA));
            mAnswerSelectLayout.addView(answerA);
        }
        if (mTest.getAnswerB() != null && !TextUtils.isEmpty(mTest.getAnswerB())) {
            AnswerItem answerB = new AnswerItem(TestActivity.this);
            answerB.setChoiceText("B");
            answerB.setChoiceContent(mTest.getAnswerB());
            answerB.setTest(mTest);
            answerB.setOnClickListener(new AnswerItemClick(answerB));
            mAnswerSelectLayout.addView(answerB);
        }
        if (mTest.getAnswerC() != null&& !TextUtils.isEmpty(mTest.getAnswerC())) {
            AnswerItem answerC = new AnswerItem(TestActivity.this);
            answerC.setChoiceText("C");
            answerC.setChoiceContent(mTest.getAnswerC());
            answerC.setTest(mTest);
            answerC.setOnClickListener(new AnswerItemClick(answerC));
            mAnswerSelectLayout.addView(answerC);
        }
        if (mTest.getAnswerD() != null&& !TextUtils.isEmpty(mTest.getAnswerD())) {
            AnswerItem answerD = new AnswerItem(TestActivity.this);
            answerD.setChoiceText("D");
            answerD.setChoiceContent(mTest.getAnswerD());
            answerD.setTest(mTest);
            answerD.setOnClickListener(new AnswerItemClick(answerD));
            mAnswerSelectLayout.addView(answerD);
        }
        if (mTest.getAnswerE() != null&& !TextUtils.isEmpty(mTest.getAnswerE())) {
            AnswerItem answerE = new AnswerItem(TestActivity.this);
            answerE.setChoiceText("E");
            answerE.setChoiceContent(mTest.getAnswerE());
            answerE.setTest(mTest);
            answerE.setOnClickListener(new AnswerItemClick(answerE));
            mAnswerSelectLayout.addView(answerE);
        }
        if (mTest.getAnswerF() != null&& !TextUtils.isEmpty(mTest.getAnswerF())) {
            AnswerItem answerF = new AnswerItem(TestActivity.this);
            answerF.setChoiceText("F");
            answerF.setChoiceContent(mTest.getAnswerF());
            answerF.setTest(mTest);
            answerF.setOnClickListener(new AnswerItemClick(answerF));
            mAnswerSelectLayout.addView(answerF);
        }
        if (mTest.getAnswerG() != null&& !TextUtils.isEmpty(mTest.getAnswerG())) {
            AnswerItem answerG = new AnswerItem(TestActivity.this);
            answerG.setChoiceText("G");
            answerG.setChoiceContent(mTest.getAnswerG());
            answerG.setTest(mTest);
            answerG.setOnClickListener(new AnswerItemClick(answerG));
            mAnswerSelectLayout.addView(answerG);
        }
    }

    private void preLoadLayout() {
        mMainLayout.setVisibility(View.GONE);
        mFAB.setVisibility(View.GONE);
        mTestEmpty.setVisibility(View.GONE);
        mLoadingLayout.setLoadingLayout(LoadingLayout.NETWORK_LOADING);
    }

    private void postLoadLayout() {
        mMainLayout.setVisibility(View.VISIBLE);
        mFAB.setVisibility(View.VISIBLE);
        mTestEmpty.setVisibility(View.GONE);
        mLoadingLayout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
    }

    private void errorLoadLayout() {
        mMainLayout.setVisibility(View.GONE);
        mFAB.setVisibility(View.GONE);
        mTestEmpty.setVisibility(View.GONE);
        mLoadingLayout.setLoadingLayout(LoadingLayout.NETWORK_ERROR);
    }

    private void resetLayout() {
        mMainLayout.setVisibility(View.GONE);
        mFAB.setVisibility(View.GONE);
        mTestEmpty.setVisibility(View.VISIBLE);
    }

    private void setAnswerItemEnabled(boolean enabled) {
        for (int i = 0; i < mAnswerSelectLayout.getChildCount(); i++) {
            mAnswerSelectLayout.getChildAt(i).setEnabled(enabled);
        }
    }

    private class AnswerItemClick implements View.OnClickListener {

        private AnswerItem answerItem;

        public AnswerItemClick(AnswerItem answerItem) {
            this.answerItem = answerItem;
        }

        @Override
        public void onClick(View v) {
            answerItem.click();
            setAnswerItemEnabled(false);
            mRyAnswer.setVisibility(View.VISIBLE);
            scrollToBottom(mMainLayout, findViewById(R.id.ly_main));
        }
    }

    public static void scrollToBottom(final ScrollView scroll, final View inner) {

        Handler mHandler = new Handler();

        mHandler.post(new Runnable() {
            public void run() {
                if (scroll == null || inner == null) {
                    return;
                }

                int offset = inner.getMeasuredHeight() - scroll.getHeight();
                if (offset < 0) {
                    offset = 0;
                }

                scroll.smoothScrollTo(0, offset);
            }
        });
    }
}
