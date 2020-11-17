package com.tema1.players;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;

import java.util.ArrayList;
import java.util.List;

public class Player {
    protected Constants constants = new Constants();
    private int id;
    private int budget;
    private List<Goods> bag = new ArrayList<>();
    //un vector de aparitie al cartilor din sac
    private int[] apparence = new int[constants.NUMBEROB];
    private Bag bagCreated;
    protected GoodsFactory goodsFactory = GoodsFactory.getInstance();
    private int nrRound;

    public Player(final int[] cards, final int budget, final int id, final int nrRound) {
        this.nrRound = nrRound;
        this.id = id;
        this.budget = budget;
        for (int i = 0; i < constants.CARDSNUMBER; i++) {
            this.apparence[cards[i]]++;
        }
        if (this instanceof GreedyPlayer) {
            this.bagCreated = ((GreedyPlayer) this).greedyBag();
        } else if (this instanceof BribePlayer) {
            this.bagCreated = ((BribePlayer) this).bribeBag();
        } else {
            this.bagCreated = ((BasicPlayer) this).basicBag();
        }
    }

    public final int getNrRound() {
        return  this.nrRound;
    }

    public final int getBudget() {

        return this.budget;
    }

    public final void setBudget(final int budget) {

        this.budget = budget;
    }

    public final List<Goods> getBagCards() {

        return this.bag;
    }

    public final int[] getApparence() {

        return this.apparence;
    }

    public final void setApparence(final int number, final int index) {

        this.apparence[index] = number;
    }

    public final GoodsFactory getGoodsFactory() {

        return this.goodsFactory;
    }

    public final Bag getBagCreated() {

        return this.bagCreated;
    }

    public final void setBagCreated(final Bag newBag) {

        this.bagCreated = newBag;
    }

    public final int getId() {

        return this.id;
    }
}
