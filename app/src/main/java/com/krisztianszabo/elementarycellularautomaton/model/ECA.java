package com.krisztianszabo.elementarycellularautomaton.model;

import android.util.Log;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class ECA {
    BitSet state;
    Map<BitSet, Boolean> ruleSet;

    public ECA(int rule, String state) {
        this.ruleSet = generateRuleSet(rule);
        this.state = parseState(state);

        Log.d("ECA", "len: " + this.state.length());
    }

    public BitSet getState() {
        return (BitSet) this.state.clone();
    }

    public void advance() {
        BitSet result = new BitSet(this.state.length());

        // Left edge is a special case
        BitSet pattern = new BitSet(3);
        // pattern.set(0, false) is not needed, as the whole pattern is initialized to false
        pattern.set(1, this.state.get(0));
        pattern.set(2, this.state.get(1));
        result.set(0, ruleSet.get(pattern));

        // The general case
        for (int i = 1; i < result.size() - 1; i++) {
            pattern.set(0, this.state.get(i - 1));
            pattern.set(1, this.state.get(i));
            pattern.set(2, this.state.get(i + 1));
            result.set(i, ruleSet.get(pattern));
        }

        // Right edge is a special case
        pattern.set(0, this.state.get(this.state.length() - 2));
        pattern.set(1, this.state.get(this.state.length() - 1));
        pattern.set(3, false);
        result.set(result.size() - 1, ruleSet.get(pattern));
    }

    private Map<BitSet, Boolean> generateRuleSet(int rule) {
        final int BITS_IN_KEY = 3;
        Map<BitSet, Boolean> result = new HashMap<>();
        int pattern = 0;

        while (pattern <= 0b111) {
            BitSet key = new BitSet(BITS_IN_KEY);
            for (int i = 0; i < BITS_IN_KEY; i++) {
                if (((1 << i) & pattern) > 0) {
                    key.set(i);
                }
            }
            Boolean value = ((1 << pattern) & rule) > 0;
            result.put(key, value);
            pattern++;
        }

        return result;
    }

    private BitSet parseState(String state) {
        BitSet result = new BitSet(state.length());
        for (int i = 0; i < state.length(); i++) {
            if (state.charAt(i) != ' ') {
                result.set(i);
            }
        }
        return result;
    }
}
