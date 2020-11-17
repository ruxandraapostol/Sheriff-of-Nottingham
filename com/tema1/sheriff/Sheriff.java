package com.tema1.sheriff;

import com.tema1.common.Constants;

public class Sheriff {
    protected  Constants constants = new Constants();
    private int budget;

    public Sheriff(final int budget) {
        this.budget = budget;
    }

    public final int getBudget() {
        return this.budget;
    }

    public final void setBudget(final int budget) {
        this.budget =  budget;
    }
}
