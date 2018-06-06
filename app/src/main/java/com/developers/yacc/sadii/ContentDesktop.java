package com.developers.yacc.sadii;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class ContentDesktop extends Fragment {
    String value;
    RingProgressBar ringProgressBar1, ringProgressBar2;
    int porcent = 0;
    Handler handlerpb = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0)

            {
                if (porcent < 100) {
                    porcent = Integer.parseInt(value);
                    ringProgressBar1.setProgress(38);
                    ringProgressBar2.setProgress(38);
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_desktop, container, false);
        ringProgressBar1 = (RingProgressBar) view.findViewById(R.id.progress_bar_1);
        ringProgressBar2 = (RingProgressBar) view.findViewById(R.id.progress_bar_2);
        ringProgressBar1.setOnProgressListener(new RingProgressBar.OnProgressListener() {
            @Override
            public void progressToComplete() {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(1000);
                        handlerpb.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }
}