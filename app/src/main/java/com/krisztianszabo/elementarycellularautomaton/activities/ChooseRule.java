package com.krisztianszabo.elementarycellularautomaton.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krisztianszabo.elementarycellularautomaton.R;
import com.krisztianszabo.elementarycellularautomaton.activities.rules.RuleListAdapter;
import com.krisztianszabo.elementarycellularautomaton.model.Rule;

import java.util.ArrayList;
import java.util.List;

public class ChooseRule extends AppCompatActivity {

    private List<Rule> rules;
    private RecyclerView rulesView;
    private RuleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        this.rules = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            rules.add(new Rule(i, false));
        }

        this.rulesView = findViewById(R.id.rules);
        this.adapter = new RuleListAdapter(this.rules);
        this.rulesView.setLayoutManager(new LinearLayoutManager(this));
        this.rulesView.setAdapter(this.adapter);
    }
}
