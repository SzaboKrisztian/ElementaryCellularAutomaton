package com.krisztianszabo.elementarycellularautomaton.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.krisztianszabo.elementarycellularautomaton.R;
import com.krisztianszabo.elementarycellularautomaton.activities.simulation.SimulationView;
import com.krisztianszabo.elementarycellularautomaton.model.ECA;

import java.util.BitSet;

public class Simulation extends AppCompatActivity {

    private Button runButton;
    private SimulationView simulation;
    private ECA eca;
    private boolean running = false;
    private long delay = 10;
    private Handler handler = new Handler();
    private int emptyForNGens = 0;
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            eca.advance();
            BitSet state = eca.getState();
            if (state.isEmpty()) {
                emptyForNGens++;
            } else {
                emptyForNGens = 0;
            }
            simulation.addLine(state);
            if (running && emptyForNGens < 2) {
                handler.postDelayed(run, delay);
            } else {
                running = false;
                runButton.setText("Run");
            }
        }
    };
    private int backgroundColor;
    private int foregroundColor;

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public int getForegroundColor() {
        return this.foregroundColor;
    }

    public int getLineLength() {
        return eca.getLength();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        int ruleNo = getIntent().getIntExtra("RULE_NO", -1);
        String state = getIntent().getStringExtra("STATE");
        this.backgroundColor = getIntent().getIntExtra("BACK", -1);
        this.foregroundColor = getIntent().getIntExtra("FORE", -1);

        this.eca = new ECA(ruleNo, state);

        this.simulation = findViewById(R.id.simulation);
        this.simulation.setSimulation(this);

        this.runButton = findViewById(R.id.toggleRun);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.running = false;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void toggleRun(View view) {
        Button btn = (Button) view;
        if (this.running) {
            this.running = false;
            runButton.setText("Run");
        } else {
            this.running = true;
            runButton.setText("Stop");
            handler.post(run);
        }
    }
}
