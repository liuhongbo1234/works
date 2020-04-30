package com.xiaomi.mi_deepclean;


import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xiaomi.mi_deepclean.adapter.CleanAdapter;
import com.xiaomi.mi_deepclean.base.Apps;
import com.xiaomi.mi_deepclean.base.BaseActivity;
import com.xiaomi.mi_deepclean.engine.CacheInfoProvider;
import com.xiaomi.mi_deepclean.model.CacheInfo;
import com.xiaomi.mi_deepclean.model.CacheListItem;
import com.xiaomi.mi_deepclean.swipeback.BaseSwipeBackActivity;
import com.xiaomi.mi_deepclean.utils.L;
import com.xiaomi.mi_deepclean.waveview.WaveView;
import com.xiaomi.mi_deepclean.widget.CounterView;
import com.xiaomi.mi_deepclean.widget.formatters.DecimalFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;

public class DeepCleanActivity extends BaseActivity {
    ActionBar ab;
    protected static final int SCANING = 5;

    protected static final int SCAN_FINIFSH = 6;
    protected static final int PROCESS_MAX = 8;
    protected static final int PROCESS_PROCESS = 9;

    private static final int INITIAL_DELAY_MILLIS = 300;
    Resources res;
    int ptotal = 0;
    int pprocess = 0;


    private boolean mAlreadyScanned = false;
    private boolean mAlreadyCleaned = false;
    private Handler handler;

    ListView mListView;

    TextView mEmptyView;

    RelativeLayout header;

    TextView sufix;

    View mProgressBar;
    TextView mProgressBarText;

    CleanAdapter rublishMemoryAdapter;

    List<CacheListItem> mCacheListItem;


    private CounterView textCounter;
    private Vector<CacheInfo> v;
    private TextView tv_others;
    private Button clean_all;

    //*******************
    private RelativeLayout rl_head;
    private int mLastY = 0;  //最后的点
    private static int mNeedDistance;   // 需要滑动的距离
    private static int mMinHight; //最小高度
    private static int mOrignHight; //原始的高度

    private int mCurrentDistance = 0;  //当前的距离
    private float mRate = 0;  //距离与目标距离的变化率 mRate=mCurrentDistance/mNeedDistance
    private int mPhotoOriginHeight; //图片的原始高度
    private int mPhotoOriginWidth; //图片的原始宽度
    private int mPhotoLeft;  //图片距左边的距离
    private int mPhotoTop;  //图片距离上边的距离
    private int mPhotoNeedMoveDistanceX;  // 图片需要移动的X距离
    private int mPhotoNeedMoveDistanceY;  // 图片需要移动的Y距离
    //需要移动的文字
    private int mTextLeft;  //文字距左边的距离
    private int mTextTop;  //文字距离上边的距离
    private int mTextNeedMoveDistanceX;  // 文字需要移动的X距离
    private int mTextNeedMoveDistanceY;  //文字需要移动的Y距离
    //******************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_clean);
//        初始化各个变量
        initView();
        res = getResources();
        int footerHeight = mContext.getResources().getDimensionPixelSize(R.dimen.footer_height);
        for (int i = 0; i < v.size(); i++) {
            CacheInfo c = v.get(i);
//            mCacheListItem.add(new CacheListItem(c.getPackageName(), c.getName(), c.getIcon(), Long.parseLong(c.getCacheSize())));
            mCacheListItem.add(new CacheListItem(c.getPackageName(), c.getName(), c.getVersionName(), c.getIcon(), 123456, 12345678, 1234567890));
        }
        mListView.setAdapter(rublishMemoryAdapter);
        mListView.setOnItemClickListener(rublishMemoryAdapter);
        refreshTextCounter();
        clean_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = mCacheListItem.size() - 1; i >= 0; i--) {
                    if (mCacheListItem.get(i).getChecked()) {
                        mCacheListItem.remove(mCacheListItem.get(i));
                    }
                }
                rublishMemoryAdapter.notifyDataSetChanged();
                Toast.makeText(DeepCleanActivity.this, "共清理" + "190M" + "垃圾", Toast.LENGTH_LONG).show();
            }
        });
//        initDistance(); 顶部样式缩放动画
    }

    private void refreshTextCounter() {
//        mWaveView.setProgress(20);
        textCounter.setStartValue(0);
        textCounter.setEndValue(193);
        sufix.setText("MB");
//          textCounter.setSuffix("mStorageSize.suffix");
        textCounter.start();
    }


//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mLastY = (int) ev.getY();
//                return super.dispatchTouchEvent(ev);//传递事件，用来子view的点击事件
//            case MotionEvent.ACTION_MOVE:
//                int y = (int) ev.getY();
//                int dy = mLastY - y;
//                if (mCurrentDistance >= mNeedDistance && dy > 0) {
//                    return super.dispatchTouchEvent(ev);//把事件传递进去
//                }
//                //改变布局
//                changeTheLayout(dy);
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_UP:
//                checkTheHeight();
//                return super.dispatchTouchEvent(ev);
//        }
//        return false;
//    }

    /**
     * 通过滑动的偏移量
     */
    private void changeTheLayout(int dy) {
        final ViewGroup.LayoutParams layoutParams = rl_head.getLayoutParams();
        layoutParams.height = layoutParams.height - dy;
        rl_head.setLayoutParams(layoutParams);
        checkTheHeight();
        rl_head.requestLayout();
        //计算当前移动了多少距离
        mCurrentDistance = mOrignHight - rl_head.getLayoutParams().height;
        mRate = (float) (mCurrentDistance * 1.0 / mNeedDistance);
        changeTheAlphaAndPostion(mRate);//获取偏移率后改变某些控件的透明度和位置
    }

    /**
     * 检查上边界和下边界
     */
    private void checkTheHeight() {
        final ViewGroup.LayoutParams layoutParams = rl_head.getLayoutParams();
        if (layoutParams.height < mMinHight) {
            layoutParams.height = mMinHight;
            rl_head.setLayoutParams(layoutParams);
            rl_head.requestLayout();
        }
        if (layoutParams.height > mOrignHight) {
            layoutParams.height = mOrignHight;
            rl_head.setLayoutParams(layoutParams);
            rl_head.requestLayout();
        }
    }

    /**
     * 根据变化率来改变这些这些控件的变化率位置
     *
     * @param rate
     */
    private void changeTheAlphaAndPostion(float rate) {
        //先改变一些控件的透明度
        if (rate >= 1) {
            tv_others.setVisibility(View.GONE);
        } else {
            tv_others.setVisibility(View.VISIBLE);
            tv_others.setAlpha(1 - rate);
            tv_others.setScaleY(1 - rate);
            tv_others.setScaleX(1 - rate);
        }
        //接下来改变控件的大小和位置
        RelativeLayout.LayoutParams sufixParams = (RelativeLayout.LayoutParams) sufix.getLayoutParams();
        sufixParams.leftMargin = (int) (mTextLeft + mTextNeedMoveDistanceX * rate);
        sufixParams.topMargin = (int) (mTextTop - mTextNeedMoveDistanceY * rate);
        sufix.setLayoutParams(sufixParams);

        RelativeLayout.LayoutParams others = (RelativeLayout.LayoutParams) tv_others.getLayoutParams();
        others.leftMargin = (int) (mTextLeft + mTextNeedMoveDistanceX * rate);
        others.topMargin = (int) (mTextTop - mTextNeedMoveDistanceY * rate);
        tv_others.setLayoutParams(others);

        RelativeLayout.LayoutParams text = (RelativeLayout.LayoutParams) textCounter.getLayoutParams();
        text.leftMargin = (int) (mTextLeft + mTextNeedMoveDistanceX * rate);
        text
                .topMargin = (int) (mTextTop - mTextNeedMoveDistanceY * rate);
        textCounter.setLayoutParams(text);
    }

    private void initView() {
        v = Apps.get();
        clean_all = findViewById(R.id.clear_button);
        mListView = findViewById(R.id.listview);
        rl_head = findViewById(R.id.header);
        textCounter = findViewById(R.id.textCounter);
        tv_others = findViewById(R.id.others);
        sufix = findViewById(R.id.sufix);
        mCacheListItem = new ArrayList<>();
        rublishMemoryAdapter = new CleanAdapter(mContext, mCacheListItem);
        textCounter.setAutoFormat(false);
        textCounter.setFormatter(new DecimalFormatter());
        textCounter.setAutoStart(false);
        textCounter.setIncrement(5f); // the amount the number increments at each time interval
        textCounter.setTimeInterval(5);
    }

    // 初始化需要滚动的距离
    private void initDistance() {
        mOrignHight = rl_head.getLayoutParams().height;
        mMinHight = 100;  //设置最小的高度为这么多
        mNeedDistance = mOrignHight - mMinHight;
        /*******************移动的文字初始化***************************/
        RelativeLayout.LayoutParams textParams = (RelativeLayout.LayoutParams) textCounter.getLayoutParams();
        mTextLeft = textParams.leftMargin;
        mTextTop = textParams.topMargin;
        mTextNeedMoveDistanceX = getWindowManager().getDefaultDisplay().getWidth() / 2 - mTextLeft + 10;
        mTextNeedMoveDistanceY = mTextTop - textParams.height / 2;  //这里计算有点误差，正确的应该是剪去获取textview高度的一半
    }


}
