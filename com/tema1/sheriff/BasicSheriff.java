package com.tema1.sheriff;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.players.Bag;
import com.tema1.players.Player;
import com.tema1.result.Result;

import java.util.ArrayList;
import java.util.List;

public class BasicSheriff extends Sheriff {
    public BasicSheriff(final int budget) {
        super(budget);
    }

    public final void inspectBasic(final Player player) {
        List<Goods> list = player.getBagCreated().getBagCards();
        //returnez mita
        if (player.getBagCreated().getBribe() != 0) {
            player.setBudget(player.getBudget() + player.getBagCreated().getBribe());
        }
        List<Goods> copy = new ArrayList<>();
        boolean ok = true;
        int penaltyPay = 0;
        /*parcurg lista de bunuri din sac pentru a vedea daca este
         ceva in neregula, retinand suma de penalti pentru acestea
         si adaugand bunurile corecte intr-o noua lista
         */
        for (Goods element : list) {
            if (element.getType() == GoodsType.Illegal
                    || element.getId() != player.getBagCreated().getId()) {
                ok = false;
                penaltyPay += element.getPenalty();
            } else {
                copy.add(element);
            }
        }
        /*daca nu afost gasit nimic gresit calulez suma de bani pe care
        seriful trebuie sa o plateasca jucatorului
        */
        if (penaltyPay == 0) {
            GoodsFactory goodsFactory = GoodsFactory.getInstance();
            penaltyPay = list.size() * goodsFactory.getGoodsById(
                    player.getBagCreated().getId()).getPenalty();
        }
        //creez noul sac al jucatorului verificat
        player.setBagCreated(new Bag(copy, player.getBagCreated().getId(), 0));

        //este returnata suma de bani a penaltiului dupa caz
        if (ok) {
            player.setBudget(player.getBudget() + penaltyPay);
            this.setBudget(this.getBudget() - penaltyPay);
        } else {
            player.setBudget(player.getBudget() - penaltyPay);
            this.setBudget(this.getBudget() + penaltyPay);
        }
    }

    public final void playBasic(final List<Player> playerList,
                                final List<Result> result, final int id) {
        //inspectez toti jucatorii
        for (Player actualPlayer : playerList) {
            this.inspectBasic(actualPlayer);
            result.get(id).setBudget(this.getBudget());
            result.get(actualPlayer.getId()).setBudget(actualPlayer.getBudget());
        }
    }

    public  final void playBribe(final List<Player> playerList,
            final List<Result> result, final int id, final int playersNumber) {
        //stabilesc vecinii
        int left = constants.BIGNUMBER;
        int right = constants.BIGNUMBER;
        if (id != 0) {
            left = id;
        } else if (playersNumber != 2) {
            left = playersNumber - 1;
        }
        if (id != playersNumber - 1) {
            right = id;
        } else if (playersNumber != 2) {
            right = 0;
        }

        //ii inspectez
        if (left != constants.BIGNUMBER) {
            this.inspectBasic(playerList.get(left - 1));
            result.get(playerList.get(left - 1).getId()).setBudget(
                    playerList.get(left - 1).getBudget());
        }
        if (right != constants.BIGNUMBER) {
            this.inspectBasic(playerList.get(right));
            result.get(playerList.get(right).getId()).setBudget(
                    playerList.get(right).getBudget());
        }
        result.get(id).setBudget(this.getBudget());

        /*parcurg toata lista de jucatori de la care incerc
                sa iau mita daca nu sunt vecinii
         */
        for (Player actualPlayer: playerList) {
            if (actualPlayer.getId() != right && actualPlayer.getId() != left) {
                result.get(id).setBudget(result.get(id).getBudget()
                        + actualPlayer.getBagCreated().getBribe());
                result.get(actualPlayer.getId()).setBudget(
                        actualPlayer.getBudget());
            }
        }
    }

    public final void playBribeBankrupt(final List<Player> playerList,
              final List<Result> result, final int id, final int playersNumber) {
        //stabilesc vecinii
        int left = constants.BIGNUMBER;
        int right = constants.BIGNUMBER;
        if (id != 0) {
            left = id - 1;
        } else if (playersNumber != 2) {
            left = playersNumber - 1;
        }
        if (id != playersNumber - 1) {
            right = id + 1;
        } else if (playersNumber != 2) {
            right = 0;
        }
        /*parcurg toata lista de jucatori de la care incerc
                sa iau mita daca nu sunt vecinii
         */
        for (Player actualPlayer: playerList) {
            if (actualPlayer.getId() != right && actualPlayer.getId() != left) {
                result.get(id).setBudget(result.get(id).getBudget()
                        + actualPlayer.getBagCreated().getBribe());
                result.get(actualPlayer.getId()).setBudget(actualPlayer.getBudget());
            }
        }
    }
}
