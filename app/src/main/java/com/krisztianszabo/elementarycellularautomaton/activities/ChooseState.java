package com.krisztianszabo.elementarycellularautomaton.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.krisztianszabo.elementarycellularautomaton.R;

import java.util.Random;

public class ChooseState extends AppCompatActivity {

    private int ruleNumber;
    private int maxLength;
    private TextView lineLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        this.ruleNumber = getIntent().getIntExtra("RULE_NO", -1);

        ((TextView) findViewById(R.id.ruleTitle)).setText("Rule #" + this.ruleNumber);

        this.maxLength = Resources.getSystem().getDisplayMetrics().widthPixels;
        this.maxLength -= this.maxLength / 10;

        this.lineLength = findViewById(R.id.lineLength);
        lineLength.setText(String.valueOf(maxLength));
    }

    public void goToSimulation(View view) {
        Intent intent = new Intent(this, Simulation.class);
        intent.putExtra("RULE_NO", this.ruleNumber);
        intent.putExtra("STATE", generateState());
        startActivity(intent);
    }

    private String generateState() {
        try {
            int len = Integer.parseInt(this.lineLength.getText().toString());
            if (len < 5 || len > maxLength) {
                throw new NumberFormatException();
            }
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            int length = Integer.parseInt(lineLength.getText().toString());
            for (int i = 0; i < length; i++) {
                sb.append(random.nextBoolean() ? ' ' : '*');
            }
            return sb.toString();
        } catch (NumberFormatException e) {
            this.lineLength.setText("");
            this.lineLength.requestFocus();
        }
        return null;
    }
}
