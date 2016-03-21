package com.lcc.activity.data;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.lcc.activity.R;
import com.lcc.base.BaseActivity;
import com.lcc.utils.CacheHelper;

public class FavContentActivity extends BaseActivity {
    public static final String FAV_KEY="fav_key";
    //当前题目
    private Test mTest;
    //小圆点控件
    private FloatingActionButton mFAB;
    //主要的内容布局
    private ScrollView mMainLayout;
    //答案选项布局
    private LinearLayout mAnswerSelectLayout;

    private TextView mBtShowAnswer;
    //答案 布局
    private RelativeLayout mRyAnswer;
    private TextView mTvQuestion, mTvAnswer, mTvAnswerLong;

    private View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument();
        creatView();
        setButtonListener();
        initData();
        setButtonListener();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_fav_content;
    }

    private void initData() {
        mRyAnswer.setVisibility(View.GONE);
        mTvAnswerLong.setVisibility(View.INVISIBLE);
        mTvQuestion.setText(mTest.getQuestion());
        //回显是否收藏
        showFav();
        //单选类型
        if (mTest.getTestType() == TestActivity.TYPE_SINGLE_ANSWER) {
            mTvAnswer.setText(mTest.getAnswer());
            //生成答案选项
            initSingleChoice();
            //设置答案
            mTvAnswer.setText(mTest.getAnswer());
            //隐藏显示答案的按钮
            mBtShowAnswer.setVisibility(View.GONE);
        }

        //简述类型
        if (mTest.getTestType() == TestActivity.TYPE_QUESTIONS_AND_ANSWERS) {
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

    private void initArgument() {
        if(getIntent()!=null){
            Intent intent = getIntent();
            mTest = (Test) intent.getSerializableExtra(FAV_KEY);
        }
    }

    private void creatView() {
        //小圆点
        mFAB = (FloatingActionButton) findViewById(R.id.fab);

        //布局
        mMainLayout = (ScrollView) findViewById(R.id.sv_main_test);
        mAnswerSelectLayout = (LinearLayout) findViewById(R.id.ly_select_answers);
        mRyAnswer = (RelativeLayout) findViewById(R.id.rl_answer);

        //TextView
        mTvQuestion = (TextView) findViewById(R.id.tv_test_question);
        mTvAnswer = (TextView) findViewById(R.id.tv_answer);
        mTvAnswerLong = (TextView) findViewById(R.id.tv_answer_long);

        //button
        mBtShowAnswer = (TextView) findViewById(R.id.bt_show_answer);

    }

    private void showFav() {
        //回显此题是否被收藏
        if(CacheHelper.getFav(mTest.getTestId() + "")==mTest.getTestId()){
            mFAB.setImageResource(R.mipmap.icon_fav_select);
        }else{
            mFAB.setImageResource(R.mipmap.icon_fav);
        }
    }

    private void setButtonListener() {

        //小圆点的点击事件
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CacheHelper.getFav(mTest.getTestId() + "") == mTest.getTestId()) {
                    mFAB.setImageResource(R.mipmap.icon_fav);
                    CacheHelper.removeToFav(mTest.getTestId() + "");
                    Snackbar.make(mRootView, "取消收藏", Snackbar.LENGTH_SHORT).show();
                } else {
                    mFAB.setImageResource(R.mipmap.icon_fav_select);
                    CacheHelper.putToFav(mTest.getTestId() + "", mTest.getTestId());
                    Snackbar.make(mRootView, "收藏成功", Snackbar.LENGTH_SHORT).show();
                }

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


    private void initSingleChoice() {
        if (mTest.getAnswerA() != null) {
            //新建答案选项
            AnswerItem answerA = new AnswerItem(this);
            answerA.setChoiceText("A");
            answerA.setChoiceContent(mTest.getAnswerA());
            answerA.setTest(mTest);
            answerA.setOnClickListener(new AnswerItemClick(answerA));
            //加入答案选项布局中
            mAnswerSelectLayout.addView(answerA);
        }
        if (mTest.getAnswerB() != null) {
            AnswerItem answerB = new AnswerItem(this);
            answerB.setChoiceText("B");
            answerB.setChoiceContent(mTest.getAnswerB());
            answerB.setTest(mTest);
            answerB.setOnClickListener(new AnswerItemClick(answerB));
            mAnswerSelectLayout.addView(answerB);
        }
        if (mTest.getAnswerC() != null) {
            AnswerItem answerC = new AnswerItem(this);
            answerC.setChoiceText("C");
            answerC.setChoiceContent(mTest.getAnswerC());
            answerC.setTest(mTest);
            answerC.setOnClickListener(new AnswerItemClick(answerC));
            mAnswerSelectLayout.addView(answerC);
        }
        if (mTest.getAnswerD() != null) {
            AnswerItem answerD = new AnswerItem(this);
            answerD.setChoiceText("D");
            answerD.setChoiceContent(mTest.getAnswerD());
            answerD.setTest(mTest);
            answerD.setOnClickListener(new AnswerItemClick(answerD));
            mAnswerSelectLayout.addView(answerD);
        }
        if (mTest.getAnswerE() != null) {
            AnswerItem answerE = new AnswerItem(this);
            answerE.setChoiceText("E");
            answerE.setChoiceContent(mTest.getAnswerE());
            answerE.setTest(mTest);
            answerE.setOnClickListener(new AnswerItemClick(answerE));
            mAnswerSelectLayout.addView(answerE);
        }
        if (mTest.getAnswerF() != null) {
            AnswerItem answerF = new AnswerItem(this);
            answerF.setChoiceText("F");
            answerF.setChoiceContent(mTest.getAnswerF());
            answerF.setTest(mTest);
            answerF.setOnClickListener(new AnswerItemClick(answerF));
            mAnswerSelectLayout.addView(answerF);
        }
        if (mTest.getAnswerG() != null) {
            AnswerItem answerG = new AnswerItem(this);
            answerG.setChoiceText("G");
            answerG.setChoiceContent(mTest.getAnswerG());
            answerG.setTest(mTest);
            answerG.setOnClickListener(new AnswerItemClick(answerG));
            mAnswerSelectLayout.addView(answerG);
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
            scrollToBottom(mMainLayout,mRootView.findViewById(R.id.ly_main));
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

    private void setAnswerItemEnabled(boolean enabled) {
        for (int i = 0; i < mAnswerSelectLayout.getChildCount(); i++) {
            mAnswerSelectLayout.getChildAt(i).setEnabled(enabled);
        }
    }

}
