package com.krisztianszabo.elementarycellularautomaton.model;

public class Rule {
    private int number;
    private boolean isFavourite;

    public Rule(int number, boolean isFavourite) {
        this.number = number;
        this.isFavourite = isFavourite;
    }

    public int getNumber() {
        return number;
    }

    public boolean isFavourite() {
        return isFavourite;
    }
}
