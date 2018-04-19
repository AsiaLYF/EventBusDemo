package com.example.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.R;
import com.example.databinding.ActivitySelfBinding;
import com.example.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 功能描述：在当前页面进行事件传递
 * Created by AsiaLYF on 2018/4/18.
 */

public class SelfActivity extends Activity {
    public static final String TAG = "SelfActivity";

    ActivitySelfBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_self);
        mBinding.setPresenter(new Presenter());
        EventBus.getDefault().register(this);

    }

    public class Presenter {
        public void onMainClick() {
            EventBus.getDefault().post(new MessageEvent("这是一条来自主线程的消息事件" +
                    "\nThread Name:" + Thread.currentThread().getName() +
                    "\nThread Id:" + Thread.currentThread().getId()));
        }

        public void onBackgroundClick() {
            EventBus.getDefault().post(new MessageEvent("这是一条来自后台线程的消息事件" +
                    "\nThread Name:" + Thread.currentThread().getName() +
                    "\nThread Id:" + Thread.currentThread().getId()));
        }

        public void onAsyncClick(){
            EventBus.getDefault().post(new MessageEvent("这是一条来自异步线程的消息事件" +
                    "\nThread Name:" + Thread.currentThread().getName() +
                    "\nThread Id:" + Thread.currentThread().getId()));
        }

        public void onPostingClick(){
            EventBus.getDefault().post(new MessageEvent("这是一条来自默认线程的消息事件" +
                    "\nThread Name:" + Thread.currentThread().getName() +
                    "\nThread Id:" + Thread.currentThread().getId()));
        }

        public void onSubThreadClick(){
            new Thread(){
                @Override
                public void run() {
                    EventBus.getDefault().post(new MessageEvent("这是一条来自子线程的消息事件" +
                            "\nThread Name:" + Thread.currentThread().getName() +
                            "\nThread Id:" + Thread.currentThread().getId()));
                }
            }.start();

        }
    }

    /**
     * 主线程中执行
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainThreadEvent(MessageEvent message) {
        Log.d(TAG,"mainThreadEvent:" +Thread.currentThread().getName());
        mBinding.setMainEvent(message);
    }

    /**
     * 后台线程中执行
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void backgroundEvent(MessageEvent message) {
        Log.d(TAG,"backgroundEvent:" +Thread.currentThread().getName());
    }

    /**
     * 异步线程中执行
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void asyncEvent(MessageEvent message){
        Log.d(TAG,"asyncEvent:" +Thread.currentThread().getName());
    }

    /**
     * 默认情况，和发送事件在同一个线程
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void postingEvent(MessageEvent message){
        Log.d(TAG,"postingEvent:" +Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
