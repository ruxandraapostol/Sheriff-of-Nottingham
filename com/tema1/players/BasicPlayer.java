package com.tema1.players;

import com.tema1.goods.Goods;

public class BasicPlayer extends Player {

    public BasicPlayer(final int[] cards, final int budget, final int id, final int nrRound) {
        super(cards, budget, id, nrRound);
    }

    public final int frequentCard() {
        int i, max = 0, index = -1;
        /*aleg cartea legala cea mai frecvent intalnita
        si o returnez daca exista
         */
        for (i = 0; i < constants.NUMBERLEGALOB; i++) {
            if (getApparence()[i] > max) {
                max = getApparence()[i];
                index = i;
            } else if (getApparence()[i] == max) {
                if (goodsFactory.getGoodsById(getApparence()[i]) == null
                        || goodsFactory.getGoodsById(index) == null) {
                    continue;
                } else if (goodsFactory.getGoodsById(i).getProfit()
                        > goodsFactory.getGoodsById(index).getProfit()) {
                    index = i;
                } else if (goodsFactory.getGoodsById(i).getProfit()
                        == goodsFactory.getGoodsById(index).getProfit()
                        && i > index) {
                    index = i;
                }
            }
        }
        if (index >= 0) {
            return index;
        } else {
            // altfel returnez cartea ilegala cea mai scumpa
            int illegalMax = 0;
            for (i = constants.NUMBERLEGALOB; i < constants.NUMBEROB; i++) {
                if (getApparence()[i] != 0 && illegalMax
                        < goodsFactory.getGoodsById(i).getProfit()) {
                    illegalMax = goodsFactory.getGoodsById(i).getProfit();
                    index = i;
                }
            }
            return index;
        }
    }

    public final Bag basicBag() {
        int n = this.getApparence()[this.frequentCard()];
        Goods goods = goodsFactory.getGoodsById(this.frequentCard());
        /*daca functia frequentCard returneaza indicele unei carti
        legale, atunci adaug in sac acel bun de cate ori indica
        valoarea din vectorul de aparitie
         */
        if (this.frequentCard() < constants.NUMBERLEGALOB) {
            for (int i = 0; i < n && i < constants.BAGCARDSNUMBER; i++) {
                this.getBagCards().add(goods);
            }
            return new Bag(this.getBagCards(), this.frequentCard(), 0);
        } else {
            // altfel adaug o singura data bunul ilegal
            if (this.getBagCards().size() == 0) {
                this.getBagCards().add(goods);
            }
        }
        return new Bag(this.getBagCards(), 0, 0);
    }
}
