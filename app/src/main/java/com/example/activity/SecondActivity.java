package com.example.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.R;
import com.example.databinding.ActivitySecondBinding;
import com.example.event.MainMessageEvent;
import com.example.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 功能描述：与MainActivity之间进行事件传递
 * Created by AsiaLYF on 2018/4/18.
 */

public class SecondActivity extends Activity {
    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding =  DataBindingUtil.setContentView(this, R.layout.activity_second);
       binding.setPresenter(new Presenter());
       //接收MainActivity黏性事件时注册
       EventBus.getDefault().register(this);

    }
    public class Presenter {
        public void goToMain() {
            EventBus.getDefault().post(new MessageEvent("这是来自SecondActivity的消息" +
                    "\nThread Name:" + Thread.currentThread().getName() +
                    "\nThread Id:" + Thread.currentThread().getId()));
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMainThread(MainMessageEvent msgEvent){
        binding.setMainEvent(msgEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
