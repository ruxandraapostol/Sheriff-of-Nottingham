package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BribePlayer extends BasicPlayer {
    public BribePlayer(final int[] cards, final int budget, final int id, final int nrRound) {
        super(cards, budget, id, nrRound);
    }

    public final List<Goods> sortList(final int budget) {
        //adaug intr-o lista toate bunurile
        List<Goods> goodsListBag = new ArrayList<>();
        for (int i = 0; i < constants.NUMBEROB; i++) {
            int copy = this.getApparence()[i];
            while (this.getApparence()[i] != 0) {
                goodsListBag.add(goodsFactory.getGoodsById(i));
                setApparence(this.getApparence()[i] - 1, i);
            }
            //rescriu de cate ori aparea in mana de carti
            setApparence(copy, i);
        }

        //sortez lista in functie de profit si de id
        Collections.sort(goodsListBag, new Comparator<Goods>() {
            @Override
            public int compare(final Goods goods, final Goods goods2) {
                if (goods.getProfit() > goods2.getProfit()) {
                    return -1;
                } else if (goods.getProfit() == goods2.getProfit()) {
                    if (goods.getId() > goods2.getId()) {
                        return -1;
                    } else if (goods.getId() == goods2.getId()) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else {
                    return 1;
                }
            }
        });

        /*adaug obiecte in lista cu bunurile pentru sac doar daca suma
        penaltiului lor nu va aduce jucatorul pe minus
         */
        int penaltyPay = budget;
        List<Goods> secondList = new ArrayList<>();
        for (int i = 0; i < goodsListBag.size() && i < constants.BAGCARDSNUMBER; i++) {
            if (penaltyPay - goodsListBag.get(i).getPenalty() > 0) {
                secondList.add(goodsListBag.get(i));
                penaltyPay -= goodsListBag.get(i).getPenalty();
            }
            if (penaltyPay == 0) {
                break;
            }
        }
        return secondList;
    }

    public final Bag bribeBag() {
        //calculez mita pe care o va da jucatorul
        int bribe = 0, nr = 0;
        for (Goods element : this.sortList(this.getBudget())) {
            if (element.getType() == GoodsType.Illegal) {
                nr++;
            }
        }
        if (nr <= 2) {
            bribe = constants.CARDSNUMBER / 2;
        } else {
            bribe = constants.CARDSNUMBER;
        }

        //creez sacul cu lista returnata de functia sortList
        if (this.getBudget() > bribe && nr > 0) {
            this.setBudget(this.getBudget() - bribe);
            return new Bag(this.sortList(this.getBudget() + bribe), 0, bribe);
        } else {
            return basicBag();
        }
    }
}
