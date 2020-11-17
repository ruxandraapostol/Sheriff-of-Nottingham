package com.tema1.sheriff;

import com.tema1.players.Player;
import com.tema1.result.Result;

import java.util.List;

public class GreedySheriff extends BasicSheriff {
    public GreedySheriff(final int budget) {
        super(budget);
    }
    public final void inspectGreedy(final Player player) {
        //accept mita daca exista altfel il verific
        if (player.getBagCreated().getBribe() != 0) {
            this.setBudget(this.getBudget() + player.getBagCreated().getBribe());
        } else {
            inspectBasic(player);
        }
    }

    public final  void playGreedy(final List<Player> playerList,
                                   final List<Result> result, final int id) {
        //controlez pe toata lumea
        for (Player actualPlayer : playerList) {
            this.inspectGreedy(actualPlayer);
            result.get(id).setBudget(this.getBudget());
            result.get(actualPlayer.getId()).setBudget(actualPlayer.getBudget());
        }
    }
}
