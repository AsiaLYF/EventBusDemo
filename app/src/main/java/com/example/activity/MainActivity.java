package com.example.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.R;
import com.example.databinding.ActivityMainBinding;
import com.example.event.MainMessageEvent;
import com.example.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

     ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(new Presenter());
        //接收SecondActivity消息时注册的
        EventBus.getDefault().register(this);

    }

    public class Presenter {
        public void goToSelf() {
            startActivity(new Intent(MainActivity.this, SelfActivity.class));
        }

        public void goToSecond() {

            EventBus.getDefault().postSticky(new MainMessageEvent("这是来自MainActivity的消息事件" +
                    "\nThread Name:" + Thread.currentThread().getName() +
                    "\nThread Id:" + Thread.currentThread().getId()));
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }
        public void goToFragment(){
            startActivity(new Intent(MainActivity.this, MainFragmentAty.class));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MessageEvent msgEvent){
        binding.setMainEvent(msgEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

