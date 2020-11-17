package com.tema1.main;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.goods.IllegalGoods;
import com.tema1.players.BasicPlayer;
import com.tema1.players.BribePlayer;
import com.tema1.players.GreedyPlayer;
import com.tema1.players.Player;
import com.tema1.result.KingQueenBonus;
import com.tema1.result.Result;
import com.tema1.sheriff.BasicSheriff;
import com.tema1.sheriff.GreedySheriff;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class Main {

    private Main() {
        throw new AssertionError("Error");
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        GoodsFactory goodsFactory = GoodsFactory.getInstance();

        int i;
        int j;
        int k;
        int currentIndex = 0;

        Constants constants = new Constants();
        List<Player> playerList = new ArrayList<>();

        /*in aceasta lista voi retine bunurile ajunse pe taraba,
        id-ul, tipul si bugetul fiecarui jucator
         */
        List<Result> result = new ArrayList<>();
        for (i = 0; i < gameInput.getPlayerNames().size(); i++) {
            result.add(new Result(i, gameInput.getPlayerNames().get(i)));
        }

        for (j = 1; j <= gameInput.getRounds(); j++) {
            for (k = 0; k < gameInput.getPlayerNames().size(); k++) {
                //creez lista cu jucatori si sacurile acestora
                for (i = 0; i < gameInput.getPlayerNames().size(); i++) {
                    // nu dau carti serifului
                    if (i == k) {
                        continue;
                    }
                    //impart cartile
                    int[] subArray;
                    subArray = gameInput.getAssetIds().stream().mapToInt(x -> x).toArray();
                    subArray = Arrays.copyOfRange(subArray, currentIndex,
                            currentIndex + constants.CARDSNUMBER);

                    if (gameInput.getPlayerNames().get(i).equals("bribed")) {
                        BribePlayer player = new BribePlayer(subArray,
                                result.get(i).getBudget(), i, j);
                        playerList.add(player);
                    } else if (gameInput.getPlayerNames().get(i).equals("basic")) {
                        BasicPlayer player = new BasicPlayer(subArray,
                                result.get(i).getBudget(), i, j);
                        playerList.add(player);
                    } else {
                        GreedyPlayer player = new GreedyPlayer(subArray,
                                result.get(i).getBudget(), i, j);
                        playerList.add(player);
                    }
                    currentIndex += constants.CARDSNUMBER;
                }

                //apar in joc serifii si fac inspectia
                if (result.get(k).getBudget() >= constants.MINIMVENIT) {
                    if (gameInput.getPlayerNames().get(k).equals("basic")) {
                        BasicSheriff sheriff = new BasicSheriff(result.get(k).getBudget());
                        sheriff.playBasic(playerList, result, k);
                    } else if (gameInput.getPlayerNames().get(k).equals("bribed")) {
                        BasicSheriff sheriff = new BasicSheriff(result.get(k).getBudget());
                        sheriff.playBribe(playerList, result, k, gameInput.getPlayerNames().size());
                    } else {
                        GreedySheriff sheriff = new GreedySheriff(result.get(k).getBudget());
                        sheriff.playGreedy(playerList, result, k);
                    }
                } else if (gameInput.getPlayerNames().get(k).equals("bribed")) {
                    BasicSheriff sheriff = new BasicSheriff(result.get(k).getBudget());
                    sheriff.playBribeBankrupt(playerList, result, k,
                            gameInput.getPlayerNames().size());
                }

                /*bunurile care ajung pe taraba sunt adaugate in lista cu bunuri
                din componenta listei result cu id-ul jucatoruli
                 */
                for (Player actualPlayer : playerList) {
                    for (Goods element : actualPlayer.getBagCreated().getBagCards()) {
                        result.get(actualPlayer.getId()).addGoods(element);
                    }
                }
                playerList.clear();
            }
        }

        /*adaug bonusul produselor ilegale atat ca bani cat si ca bunuri fizice
        ce vor putea primi ulterior King/Queen Bonus
         */
        for (Result curent : result) {
            int pay = 0;
            List<Goods> copy = new ArrayList<>(curent.getGoodsOnTable());
            for (Goods element : copy) {
                pay += element.getProfit();
                if (element.getType() == GoodsType.Illegal) {
                    Map<Goods, Integer> map = ((IllegalGoods) element).getIllegalBonus();
                    for (Goods good : map.keySet()) {
                        for (int c = 0; c < map.get(good); c++) {
                            curent.addGoods(good);
                        }
                        pay += good.getProfit() * map.get(good);
                    }
                }
            }
            curent.setBudget(curent.getBudget() + pay);
        }

        //adaug bonusurile legale
        KingQueenBonus bonus = new KingQueenBonus(result);
        bonus.setKingBonus();

        //sortez lista in functie de buget, apoi de id
        result.sort(new Comparator<Result>() {
            @Override
            public int compare(final Result r1, final Result r2) {
                if (r1.getBudget() < r2.getBudget()) {
                    return 1;
                } else if (r1.getBudget() == r2.getBudget()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        //afisez clasamentul
        for (Result current : result) {
            System.out.println(current.toString());
        }
    }
}
