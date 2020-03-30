package com.krisztianszabo.elementarycellularautomaton.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.krisztianszabo.elementarycellularautomaton.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChooseState extends AppCompatActivity {

    private int ruleNumber;
    private int maxLength;
    private TextView lineLength;
    private TextView pattern;
    private Switch random;
    private Switch repeatPattern;
    private Spinner foregroundColor;
    private Spinner backgroundColor;
    private int foreColor = -1;
    private int backColor = -1;
    private final Map<String, Integer> colors = new HashMap<>();
    private final List<String> colorKeys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        initColors();

        this.ruleNumber = getIntent().getIntExtra("RULE_NO", -1);

        this.maxLength = Resources.getSystem().getDisplayMetrics().widthPixels;
        this.maxLength /= 2;

        initViews();
    }

    private void initColors() {
        colors.put("Black", Color.BLACK);
        colors.put("White", Color.WHITE);
        colors.put("Red", Color.RED);
        colors.put("Green", Color.GREEN);
        colors.put("Blue", Color.BLUE);
        colors.put("Magenta", Color.MAGENTA);
        colors.put("Yellow", Color.YELLOW);
        colors.put("Cyan", Color.CYAN);
        colors.put("Gray", Color.GRAY);

        colorKeys.add("Black");
        colorKeys.add("White");
        colorKeys.add("Red");
        colorKeys.add("Green");
        colorKeys.add("Blue");
        colorKeys.add("Magenta");
        colorKeys.add("Yellow");
        colorKeys.add("Cyan");
        colorKeys.add("Gray");
    }

    private void initViews() {
        this.lineLength = findViewById(R.id.lineLength);
        lineLength.setText(String.valueOf(maxLength));

        ((TextView) findViewById(R.id.ruleTitle)).setText("Rule #" + this.ruleNumber);
        this.pattern = findViewById(R.id.pattern);
        this.random = findViewById(R.id.randomState);
        this.repeatPattern = findViewById(R.id.repeatPattern);

        this.foregroundColor = findViewById(R.id.foregroundColor);
        this.backgroundColor = findViewById(R.id.backgroundColor);

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, colorKeys);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foregroundColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                foreColor = colors.get(colorKeys.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                foreColor = -1;
            }
        });
        backgroundColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                backColor = colors.get(colorKeys.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                backColor = -1;
            }
        });

        foregroundColor.setAdapter(colorAdapter);
        backgroundColor.setAdapter(colorAdapter);
        backgroundColor.setSelection(colorAdapter.getPosition("White"));
    }

    public void toggleRandom(View view) {
        pattern.setEnabled(!random.isChecked());
        repeatPattern.setEnabled(!random.isChecked());
    }

    public void goToSimulation(View view) {
        String state = "";
        if (random.isChecked()) {
            state = generateState();
        } else {
            String ptrn = this.pattern.getText().toString().replaceAll("o", " ");
            if (repeatPattern.isChecked()) {
                StringBuilder sb = new StringBuilder();
                try {
                    int length = Integer.parseInt(lineLength.getText().toString());
                    while (sb.length() < length) {
                        sb.append(ptrn);
                    }
                    state = sb.substring(0, length);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                if (ptrn.length() < 5) {
                    Toast.makeText(this, "Enter minimum 5 characters for the state", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    state = ptrn;
                }
            }
        }
        Log.d("STATE", state);
        Intent intent = new Intent(this, Simulation.class);
        intent.putExtra("RULE_NO", this.ruleNumber);
        intent.putExtra("STATE", state);
        intent.putExtra("FORE", foreColor);
        intent.putExtra("BACK", backColor);
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
