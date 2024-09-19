package gameManagementDistances;

import jsclub.codefest2024.sdk.model.Element;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.players.Player;
import myClass.PlayerAndDistance;

import java.util.ArrayList;
import java.util.List;

public class SortDistancer {
    public static List<PlayerAndDistance> sorter(List<Player> elements, Player player) {
        List<PlayerAndDistance> result = new ArrayList<>();

        List<Integer> dists = new ArrayList<>();
        int n = elements.size();
        for (int i = 0; i < n; i++) {
            result.add(new PlayerAndDistance(null, 0));

            Player e = elements.get(i);

            dists.add(DistanceCalculator.calc(e, player));
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (dists.get(i) > dists.get(j)) {
                    int tmp = dists.get(i);
                    Player tmpE = elements.get(i);

                    dists.set(i, dists.get(j));
                    dists.set(j, tmp);

                    elements.set(i, elements.get(j));
                    elements.set(j, tmpE);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            result.set(i, new PlayerAndDistance(elements.get(i), dists.get(i)));
        }

        return result;
    }

//    public static List<AllElementsMap> allElementArray(GameMap gameMap, ArrayList elements, Player player) {
//        List<PlayerAndDistance> result = new ArrayList<>();
//
//        List<Integer> dists = new ArrayList<>();
//        int n = elements.size();
//        for (int i = 0; i < n; i++) {
//            result.add(new PlayerAndDistance(null, 0));
//
//            Element e = gameMap.getElementByIndex();
//
//            dists.add(DistanceCalculator.calc(e, player));
//        }
//
//        for (int i = 0; i < n - 1; i++) {
//            for (int j = i + 1; j < n; j++) {
//                if (dists.get(i) > dists.get(j)) {
//                    int tmp = dists.get(i);
//                    Player tmpE = elements.get(i);
//
//                    dists.set(i, dists.get(j));
//                    dists.set(j, tmp);
//
//                    elements.set(i, elements.get(j));
//                    elements.set(j, tmpE);
//                }
//            }
//        }
//
//        for (int i = 0; i < n; i++) {
//            result.set(i, new PlayerAndDistance(elements.get(i), dists.get(i)));
//        }
//
//        return result;
//    }


}
