package com.krisztianszabo.elementarycellularautomaton.activities.rules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krisztianszabo.elementarycellularautomaton.R;
import com.krisztianszabo.elementarycellularautomaton.model.Rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RuleListAdapter extends RecyclerView.Adapter<RuleViewHolder> {

    private Context context;
    private List<Rule> rules;
    private List<Rule> favourites;
    private List<Rule> nonFavourites;

    public RuleListAdapter(Context context) {
        this.context = context;

        favourites = FavouritesManager.getInstance().loadFavourites(this.context);
        nonFavourites = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            Rule newRule = new Rule(i, false);
            if (!favourites.contains(newRule)) {
                nonFavourites.add(newRule);
            }
        }
        this.rules = new ArrayList<>();
        rules.addAll(favourites);
        rules.addAll(nonFavourites);
    }

    public void makeFavourite(Rule rule) {
        nonFavourites.remove(rule);
        favourites.add(rule);
        rule.toggleFavourite();
        Collections.sort(favourites, new RuleComparator());
        refreshRules();

    }

    public void removeFavourite(Rule rule) {
        favourites.remove(rule);
        nonFavourites.add(rule);
        rule.toggleFavourite();
        Collections.sort(nonFavourites, new RuleComparator());
        refreshRules();
    }

    private void refreshRules() {
        FavouritesManager.getInstance().saveFavourites(this.context, this.favourites);
        rules.clear();
        rules.addAll(favourites);
        rules.addAll(nonFavourites);
        this.notifyDataSetChanged();
    }

    private static class RuleComparator implements Comparator<Rule> {
        @Override
        public int compare(Rule o1, Rule o2) {
            return Integer.compare(o1.getNumber(), o2.getNumber());
        }
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
