package com.tema1.players;

import com.tema1.goods.Goods;

public class GreedyPlayer extends BasicPlayer {

    public GreedyPlayer(final int[] cards, final int budget, final int id, final int nrRound) {
        super(cards, budget, id, nrRound);
    }

    public final Goods illegalCardGreedy() {
        int max1 = 0, max2 = 0;
        int index1 = -1, index2 = -1;
        for (int i = constants.NUMBERLEGALOB; i < constants.NUMBEROB; i++) {
            if (this.getApparence()[i] > 0 && goodsFactory.getGoodsById(i).getProfit() > max1) {
                if (this.getApparence()[i] > 1) {
                    index1 = i;
                    index2 = i;
                    max1 = goodsFactory.getGoodsById(i).getProfit();
                    max2 = max1;
                } else {
                    index2 = index1;
                    index1 = i;
                    max2 = max1;
                    max1 = goodsFactory.getGoodsById(i).getProfit();
                }
            } else if (this.getApparence()[i] > 0
                    && goodsFactory.getGoodsById(i).getProfit() > max2) {
                index2 = i;
                max2 = goodsFactory.getGoodsById(i).getProfit();
            }
        }
        if (index2 != -1) {
            return goodsFactory.getGoodsById(index2);
        }
        return null;
    }

    public final Bag greedyBag() {
        //doar daca in sac nu am deja 8 lucruri
        if (this.getBagCards().size() < constants.BAGCARDSNUMBER) {
            boolean ok = true;
            int profitMax = 0;
            int index = -1;
            int nr = 0;
            //verific daca am un bun ilegal
            for (int i = constants.NUMBERLEGALOB; i < constants.NUMBEROB; i++) {
                if (this.getApparence()[i] != 0) {
                    nr += this.getApparence()[i];
                    Goods goods = this.getGoodsFactory().getGoodsById(i);
                    if (goods.getProfit() > profitMax) {
                        profitMax = goods.getProfit();
                        index = i;
                    }
                    ok = false;
                }
            }
            //daca am un bun ilegal si runda este para
            if (this.getNrRound() % 2 == 0 && !ok) {
                Bag bag = this.basicBag();
                if (nr == constants.CARDSNUMBER)  {
                    bag.getBagCards().add(this.illegalCardGreedy());
                } else {
                    bag.getBagCards().add(goodsFactory.getGoodsById(index));
                }
                return bag;
            }
        }
        return this.basicBag();
    }
}
