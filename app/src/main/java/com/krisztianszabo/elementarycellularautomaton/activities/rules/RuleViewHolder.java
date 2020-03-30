package com.krisztianszabo.elementarycellularautomaton.activities.rules;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.krisztianszabo.elementarycellularautomaton.R;
import com.krisztianszabo.elementarycellularautomaton.activities.ChooseState;
import com.krisztianszabo.elementarycellularautomaton.model.Rule;

public class RuleViewHolder extends RecyclerView.ViewHolder {

    private RuleListAdapter adapter;
    private ConstraintLayout layout;

    public RuleViewHolder(@NonNull View itemView, RuleListAdapter adapter) {
        super(itemView);

        this.adapter = adapter;
        this.layout = (ConstraintLayout) itemView;
    }

    public void initHolder(final Rule rule) {
        TextView ruleLabel = itemView.findViewById(R.id.ruleLabel);
        ruleLabel.setText("Rule #" + rule.getNumber());
        Button favBtn = itemView.findViewById(R.id.ruleFavBtn);
        if (rule.isFavourite()) {
            favBtn.setText("Unfavourite");
        } else {
            favBtn.setText("Favourite");
        }

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rule.isFavourite()) {
                    adapter.removeFavourite(rule);
                } else {
                    adapter.makeFavourite(rule);
                }
            }
        });

        this.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(layout.getContext(), ChooseState.class);
                intent.putExtra("RULE_NO", rule.getNumber());
                layout.getContext().startActivity(intent);
            }
        });
    }
}
