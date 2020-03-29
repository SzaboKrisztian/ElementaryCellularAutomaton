package com.krisztianszabo.elementarycellularautomaton.activities.simulation;

import android.view.SurfaceHolder;

import com.krisztianszabo.elementarycellularautomaton.model.ECA;

public class DrawThread implements Runnable {

    private SurfaceHolder holder;
    private ECA eca;

    public DrawThread(SurfaceHolder holder, ECA eca) {
        this.holder = holder;
        this.eca = eca;
    }

    @Override
    public void run() {

    }
}
