package com.tema1.result;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.goods.LegalGoods;

import java.util.List;

public class KingQueenBonus {
    private Constants constants = new Constants();
    private List<Result> result;
    //de cate ori apare fiecare cel mai des
    private int[] kingApparence = new int[constants.NUMBERLEGALOB];
    private int[] queenApparence = new int[constants.NUMBERLEGALOB];
    //la ce jucator apare bunul cel mai des
    private int[] kingClasament = new int[constants.NUMBERLEGALOB];
    private int[] queenClasament = new int[constants.NUMBERLEGALOB];

    public KingQueenBonus(final List<Result> result) {
        this.result = result;
        for (int i = 0; i < constants.NUMBERLEGALOB; i++) {
            kingClasament[i] = constants.SMALLNUMBER;
            queenClasament[i] = constants.SMALLNUMBER - 1;
        }
    }

    public final void setKingBonus() {
        for (Result currentResult : result) {
            int[] containsLegal = new int[constants.NUMBERLEGALOB];
            //calculez de cate ori apar toate bunurile pe taraba unui jucator
            for (Goods currentGood : currentResult.getGoodsOnTable()) {
                if (currentGood.getType() == GoodsType.Legal) {
                    containsLegal[currentGood.getId()]++;
                }
            }
            /*daca pe taraba lui apare cel mai des un bun atunci ii salvez id-ul
            jucatorului in vectorul kingClasament
             */
            for (int i = 0; i < constants.NUMBERLEGALOB; i++) {
                if (containsLegal[i] > this.kingApparence[i]) {
                    this.kingApparence[i] = containsLegal[i];
                    this.kingClasament[i] = currentResult.getId();
                } else if (containsLegal[i] == this.kingApparence[i]
                        && currentResult.getId() < this.kingClasament[i]) {
                    this.kingApparence[i] = containsLegal[i];
                    this.kingClasament[i] = currentResult.getId();
                }
            }
        }

        for (Result currentResult : result) {
            //calculez de cate ori apar toate bunurile pe taraba unui jucator
            int[] containsLegal = new int[constants.NUMBERLEGALOB];
            for (Goods currentGood : currentResult.getGoodsOnTable()) {
                if (currentGood.getType() == GoodsType.Legal) {
                    containsLegal[currentGood.getId()]++;
                }
            }
            /*daca pe taraba lui apare al doilea cel mai des un bun atunci ii salvez
             id-ul jucatorului in vectorul queenClasament
             */
            for (int i = 0; i < constants.NUMBERLEGALOB; i++) {
                if (containsLegal[i] > this.queenApparence[i]
                        && (containsLegal[i] < this.kingApparence[i]
                        || (containsLegal[i] == this.kingApparence[i]
                        && this.kingClasament[i] != currentResult.getId()))) {
                    this.queenApparence[i] = containsLegal[i];
                    this.queenClasament[i] = currentResult.getId();
                }

            }
        }
        //adauga bonusul jucatorilor care merita
        GoodsFactory goodsFactory = GoodsFactory.getInstance();
        for (int i = 0; i < constants.NUMBERLEGALOB; i++) {
            LegalGoods legalGoods = (LegalGoods) goodsFactory.getGoodsById(i);
            if (legalGoods == null) {
                continue;
            }
            int king = legalGoods.getKingBonus();
            int queen = legalGoods.getQueenBonus();
            for (Result curent : this.result) {
                if (this.kingClasament[i] == curent.getId()) {
                    curent.setBudget(curent.getBudget() + king);
                }
                if (this.queenClasament[i] == curent.getId()) {
                    curent.setBudget(curent.getBudget() + queen);
                }
            }
        }
    }
}
