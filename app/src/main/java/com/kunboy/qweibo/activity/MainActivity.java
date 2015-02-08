package com.kunboy.qweibo.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kunboy.qweibo.R;
import com.kunboy.qweibo.adapter.MyAdapter;
import com.kunboy.qweibo.ui.CustomSwipeRefreshLayout;
import com.kunboy.qweibo.ui.FloatingActionButton;

/**
 * Created by sunhongkun on 2015/2/8.
 */
public class MainActivity extends Activity {


    private RecyclerView mRecyclerView;
    private CustomSwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    private String[] myDataset;
    private String[][] datas = new String[2][];
    private int index = 0;
    private int addedIndex = 0;
    private TextView mTitleBar;
    private View mBlank;
    private boolean isShown = true;
    private FloatingActionButton editButton;
    private RelativeLayout mTitleBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datas[0] = new String[]{"1111", "2222", "3333", "4444", "5555",
                "6666", "7777", "8888", "9999", "0000", "1000", "2000"};
        datas[1] = new String[]{"aaa", "bbb", "ccc", "ddd", "eee", "fff",
                "ggg", "hhh", "jjj", "kkk", "lll", "ooo"};
        mSwipeRefreshLayout = (CustomSwipeRefreshLayout) findViewById(R.id.swiperefresh);

        mTitleBar = (TextView) findViewById(R.id.title);
        mBlank = findViewById(R.id.blank);
        editButton = (FloatingActionButton) findViewById(R.id.edit_button);
        mTitleBarLayout = (RelativeLayout) findViewById(R.id.title_bar);

        mSwipeRefreshLayout
                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // initiateRefresh();
                        titleBarAnimator(false);
                        swipViewAnimator(false);
                        editButton.show();
                        addOne();
                    }

                });
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        myDataset = datas[index];

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                // TODO Auto-generated method stub
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // TODO Auto-generated method stub
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView
                        .getChildCount() == 0) ? 0 : recyclerView.getChildAt(0)
                        .getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }

        });
        // ItemAnimator itemAnimator = new SlideInDownAnimator();
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator() {

            @Override
            public boolean animateAdd(RecyclerView.ViewHolder holder) {
                // TODO Auto-generated method stub
                return super.animateAdd(holder);
            }

        };
        itemAnimator.setAddDuration(200);
        mRecyclerView.setItemAnimator(itemAnimator);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "点击：" + view.getTag(), Toast.LENGTH_SHORT).show();

            }
        });
        mSwipeRefreshLayout.setCustomScrollListener(new CustomSwipeRefreshLayout.CustomScrollListener() {

            @Override
            public void onScroll(boolean scrollUp) {
                // Toast.makeText(getApplicationContext(), "上滑："+scrollUp,
                // Toast.LENGTH_SHORT).show();
                titleBarAnimator(scrollUp);
                swipViewAnimator(scrollUp);
                if(scrollUp){
                    editButton.hide();
                }else{
                    editButton.show();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void titleBarAnimator(boolean scrollUp) {
        // TODO Auto-generated method stub

        if (scrollUp) {
            if (!isShown)
                return;
            ValueAnimator mAnimator = ValueAnimator.ofFloat(0,
                    +mTitleBarLayout.getHeight());
            mAnimator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // TODO Auto-generated method stub
                    // view5.setX(endValue);
                    isShown = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // TODO Auto-generated method stub
                }
            });
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // TODO Auto-generated method stub
                    Float animationValue = (Float) animation.getAnimatedValue();
                    mTitleBarLayout.setTranslationY(-animationValue);
                }
            });
            // 4.设置动画的持续时间、是否重复及重复次数等属性
            mAnimator.setDuration(500);
            mAnimator.setTarget(mTitleBarLayout);
            mAnimator.start();
        } else {
            if (isShown)
                return;
            ValueAnimator mAnimator = ValueAnimator.ofFloat(
                    0 + mTitleBarLayout.getHeight(), 0);
            mAnimator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // TODO Auto-generated method stub
                    // view5.setX(endValue);
                    isShown = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // TODO Auto-generated method stub
                }
            });
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // TODO Auto-generated method stub
                    Float animationValue = (Float) animation.getAnimatedValue();
                    mTitleBarLayout.setTranslationY(-animationValue);
                }
            });
            // 4.设置动画的持续时间、是否重复及重复次数等属性
            mAnimator.setDuration(500);
            mAnimator.setTarget(mTitleBarLayout);
            mAnimator.start();
        }

    }

    protected void swipViewAnimator(boolean scrollUp) {
        // TODO Auto-generated method stub

        if (scrollUp) {
            if (!isShown)
                return;
            final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mBlank
                    .getLayoutParams();
            ValueAnimator mAnimator = ValueAnimator.ofInt(
                    +mTitleBarLayout.getHeight(), 0);
            mAnimator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // TODO Auto-generated method stub
                    // view5.setX(endValue);
                    isShown = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // TODO Auto-generated method stub
                }
            });
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // TODO Auto-generated method stub
                    int animationValue = (int) animation.getAnimatedValue();
                    lp.height = animationValue;
                    mBlank.setLayoutParams(lp);
                    // mBlank.requestLayout();
                }
            });
            // 4.设置动画的持续时间、是否重复及重复次数等属性
            mAnimator.setDuration(500);
            mAnimator.setTarget(mBlank);
            mAnimator.start();
        } else {
            if (isShown)
                return;
            final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mBlank
                    .getLayoutParams();
            ValueAnimator mAnimator = ValueAnimator.ofInt(0,
                    0 + mTitleBarLayout.getHeight());
            mAnimator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // TODO Auto-generated method stub
                    // view5.setX(endValue);
                    isShown = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // TODO Auto-generated method stub
                }
            });
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // TODO Auto-generated method stub
                    int animationValue = (int) animation.getAnimatedValue();
                    lp.height = animationValue;
                    mBlank.setLayoutParams(lp);
                    // mBlank.requestLayout();
                }
            });
            // 4.设置动画的持续时间、是否重复及重复次数等属性
            mAnimator.setDuration(500);
            mAnimator.setTarget(mBlank);
            mAnimator.start();
        }

    }

    protected void addOne() {
        // TODO Auto-generated method stub
        for (int x = 0; x < 2; x++) {
            mAdapter.getData().add(0, "" + (addedIndex++));
            mAdapter.notifyItemInserted(0);
        }
//		mAdapter.notifyItemRangeInserted(0, 2);
        mRecyclerView.scrollToPosition(0);
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private void initiateRefresh() {
        // myDataset = datas[0] = new
        // String[]{"1111","qqqq","2222","3333","4444","5555","6666","7777","8888","9999","0000","1000","2000"};
        myDataset = datas[(++index) % 2];
        mAdapter.setData(myDataset);
        mAdapter.notifyDataSetChanged();
        // mAdapter.notifyItemMoved(1, 0);
        // mAdapter.notifyItemRemoved(0);
        // mAdapter.notifyItemInserted(0);
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
