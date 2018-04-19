package com.example.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.R;
import com.example.databinding.ActivityMainFragmentBinding;
import com.example.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 功能描述：
 * Created by AsiaLYF on 2018/4/18.
 */

public class MainFragmentAty extends FragmentActivity {

    ActivityMainFragmentBinding mFragmentBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_fragment);
        EventBus.getDefault().register(this);
        mFragmentBinding.setPresenter(new Presenter());


    }

    public class Presenter {
        public void buttonClick() {
            EventBus.getDefault().post(new MessageEvent("这是来自MainFragmentAty的消息"));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataFromFragment(MessageEvent message) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
