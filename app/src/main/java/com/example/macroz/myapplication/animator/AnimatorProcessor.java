package com.example.macroz.myapplication.animator;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/12/4 上午11:14
 * 修改人:   macroz
 * 修改时间: 2018/12/4 上午11:14
 * 修改备注:
 */
public class AnimatorProcessor extends Thread {

    private LinkedBlockingQueue<RecyclerAnimatorManager.Action> mQueue;

    private volatile boolean stop = false;

    private Handler mHandler;

    private static volatile AnimatorProcessor mIns;

    public static AnimatorProcessor getIns() {
        if (mIns == null) {
            synchronized (AnimatorProcessor.class) {
                if (mIns == null) {
                    mIns = new AnimatorProcessor();
                }
            }
        }
        return mIns;
    }


    private AnimatorProcessor() {
        mQueue = new LinkedBlockingQueue<>();
        mHandler = new Handler(Looper.getMainLooper());
        start();
    }

    @Override
    public void run() {
        if (mQueue == null || mHandler == null) {
            return;
        }
        try {
            while (!stop && !isInterrupted()) {
                RecyclerAnimatorManager.Action action = mQueue.take();


                long delay = action.getRunTime() - System.currentTimeMillis();
                delay = delay > 0 ? delay : 0;
                mHandler.postDelayed(action.getCallback(), delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isStop() {
        return stop;
    }


    public void stopWork() {
        setStop(true);
        interrupt();
        mIns = null;
    }

    public void enqueue(RecyclerAnimatorManager.Action action) {
        if (action == null) {
            return;
        }
        mQueue.add(action);
    }
}
