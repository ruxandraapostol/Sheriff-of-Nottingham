package com.tema1.result;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private Constants constants = new Constants();
    private int budget;
    private String type;
    private int id;
    private List<Goods> goodsOnTable;

    public Result(final int id, final String type) {
        budget = constants.BUGET;
        this.id = id;
        this.type = type;
        this.goodsOnTable = new ArrayList<>();
    }

    public final void setBudget(final int budget) {
        this.budget = budget;
    }

    public final void  setId(final int id) {
        this.id = id;
    }

    public final int getBudget() {
        return this.budget;
    }

    public final int getId() {
        return  this.id;
    }

    public final void addGoods(final Goods actual) {
        this.goodsOnTable.add(actual);
    }

    public final List<Goods> getGoodsOnTable() {
        return this.goodsOnTable;
    }


    @Override
    public final String toString() {
        return this.id + " " + this.type.toUpperCase() + " " + this.budget;
    }


}
