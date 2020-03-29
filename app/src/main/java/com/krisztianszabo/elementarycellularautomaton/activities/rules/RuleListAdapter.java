package com.krisztianszabo.elementarycellularautomaton.activities.rules;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krisztianszabo.elementarycellularautomaton.R;
import com.krisztianszabo.elementarycellularautomaton.model.Rule;

import java.util.List;

public class RuleListAdapter extends RecyclerView.Adapter<RuleViewHolder> {

    private List<Rule> rules;

    public RuleListAdapter(List<Rule> rules) {
        this.rules = rules;
    }

    @NonNull
    @Override
    public RuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RuleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rule_item, parent, false), this);
    }

    @Override
    public void onBindViewHolder(@NonNull RuleViewHolder holder, int position) {
        holder.initHolder(this.rules.get(position));
    }

    @Override
    public int getItemCount() {
        return rules.size();
    }
}
