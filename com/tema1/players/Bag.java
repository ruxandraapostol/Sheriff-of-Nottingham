package com.tema1.players;

import com.tema1.goods.Goods;
import java.util.List;

public class Bag {
    private List<Goods> cardsBag;
    private int id;
    private int bribe;

    public Bag(final List<Goods> bag, final int id, final int bribe) {
        this.cardsBag = bag;
        this.id = id;
        this.bribe = bribe;
    }

    public final List<Goods> getBagCards() {
        return this.cardsBag;
    }

    public final int getId() {
        return this.id;
    }

    public final int getBribe() {
        return this.bribe;
    }
}
