package edu.njupt.liuq.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import edu.njupt.liuq.zhbj.utils.PrefUtils;

/**
 * Created by Administrator on 2017/1/4.
 */

public class GuideActivity extends Activity {
    private static final String TAG = "GuideActivity";
    private ViewPager mViewPager;
    //引导页图片id
    private int[] mImageIds = new int[]{R.mipmap.guide_1,
            R.mipmap.guide_2, R.mipmap.guide_3};
    private ArrayList<ImageView> mImageViewList;//ImageView集合
    private LinearLayout llContainer;
    private ImageView ivRedPoint;//小红点
    private int mPointDis;//小红点移动距离
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_guide);

        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        ivRedPoint = (ImageView) findViewById(R.id.iv_red_point);
        btnStart  = (Button) findViewById(R.id.btn_start);

        initData();
        mViewPager.setAdapter(new GuideAdapter());//设置数据


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当页面滑动过程中的回调
                Log.d(TAG, "当前位置:" + position + ";移动偏移:" + positionOffset + ";移动偏移百分比:" +
                        positionOffsetPixels);
                //更新小红点距离
                int leftMargin = (int) (mPointDis*(positionOffset+position));//计算小红点当前的左边距
             RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                params.leftMargin = leftMargin;//修改左边距

                ivRedPoint.setLayoutParams(params);//重新设置边距，小红点移到滑动位置
            }

            @Override
            public void onPageSelected(int position) {
                //某个页面被选中
                //当选中页面的位置是最后一个时，显示按钮其他情况不显示
                if (position==mImageViewList.size()-1){
                    btnStart.setVisibility(View.VISIBLE);
                }else {
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //页面状态发生变化的回调
            }
        });
        //计算两个原点距离
        //移动距离 = 第二个原点left值-第一个原点left值
        //measure->layout(确定位置)此时不能测量，因为只有在onCreate创建结束后才可以测量，
        // 而此方法在onCreate内所以显示的原点距离为0
       /* mPointDis = llContainer.getChildAt(1).getLeft() - llContainer.getChildAt(0).getLeft();
        Log.d(TAG, "原点距离：" + mPointDis);*/
        //视图树
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //layout方法执行结束的回调，执行完onCreate后调用此方法进行测量
                        ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        //移除监听避免重复
                        mPointDis = llContainer.getChildAt(1).getLeft() -
                                llContainer.getChildAt(0).getLeft();
                        Log.d(TAG, "原点距离：" + mPointDis);
                    }
                }
        );


        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //跳转到主页面，并且设置不是第一次进入了
                PrefUtils.SetBoolean(getApplicationContext(),"is_first_enter",false);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    //初始化数据
    private void initData() {
        mImageViewList = new ArrayList<ImageView>();

        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);//通过设置背景，可以让宽高填充布局
            mImageViewList.add(view);

            //初始化小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);//设置图片（shape形状）
            llContainer.addView(point);//给容器添加圆点

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                //从第二个点开始设置左边距
                params.leftMargin = 90;
            }
            point.setLayoutParams(params);//设置布局参数
        }
    }

    class GuideAdapter extends PagerAdapter {

        //item的个数
        @Override
        public int getCount() {
            return mImageViewList.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;//判断是否是view对象
        }

        //初始化item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);

            /*关于异常“The specified child already has a parent. You must call removeView"的解决（举例说明，附源码）*/
            mViewPager.removeView(view);


            container.addView(view);
            return view;
        }

        //销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) container);
        }
    }

}
