package com.krisztianszabo.elementarycellularautomaton.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.krisztianszabo.elementarycellularautomaton.R;
import com.krisztianszabo.elementarycellularautomaton.activities.simulation.SimulationView;
import com.krisztianszabo.elementarycellularautomaton.model.ECA;

public class Simulation extends AppCompatActivity {

    private ConstraintLayout simLayout;
    private SimulationView simulation;
    private ECA eca;
    private boolean running = false;
    private long delay = 500;
    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            eca.advance();
            simulation.addLine(eca.getState());
            if (running) {
                handler.postDelayed(run, delay);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        int ruleNo = getIntent().getIntExtra("RULE_NO", -1);
        String state = getIntent().getStringExtra("STATE");


        this.simLayout = findViewById(R.id.simLayout);
        this.eca = new ECA(ruleNo, state);
        this.simLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                simLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                initSimulation();
            }
        });
    }

    private void initSimulation() {
        Log.d("INITIALIZING", simLayout.getWidth() + ", " + simLayout.getHeight());
        this.simulation = new SimulationView(getApplicationContext(), simLayout.getWidth(), simLayout.getHeight(), eca.getState().length());
        simLayout.addView(this.simulation);
    }

    public void toggleRun(View view) {
        Button btn = (Button) view;
        if (this.running) {
            this.running = false;
            btn.setText("Run");
        } else {
            this.running = true;
            btn.setText("Stop");
            handler.post(run);
        }
    }
}
