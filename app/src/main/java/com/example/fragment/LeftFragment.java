package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * 功能描述：
 * Created by AsiaLYF on 2018/4/18.
 */

public class LeftFragment extends ListFragment {



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] items = {"item1", "item2", "item3"};
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                EventBus.getDefault().post(new MessageEvent("主线程发送的消息1\n" + Thread.currentThread().getName()));
                break;
            case 1:
                new Thread(){
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new MessageEvent("子线程发送的消息\n" + Thread.currentThread().getName()));
                    }
                }.start();

                break;
            case 2:
                EventBus.getDefault().post(new MessageEvent("主线程发送的消息2\n" + Thread.currentThread().getName()));
                break;
        }
    }


}
