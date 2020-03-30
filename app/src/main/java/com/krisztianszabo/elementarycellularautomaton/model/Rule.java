package com.krisztianszabo.elementarycellularautomaton.model;

import org.jetbrains.annotations.NotNull;

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

    public void toggleFavourite() {
        this.isFavourite = !this.isFavourite;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Rule) {
            return this.number == ((Rule) other).number;
        } else {
            return false;
        }
    }
}
