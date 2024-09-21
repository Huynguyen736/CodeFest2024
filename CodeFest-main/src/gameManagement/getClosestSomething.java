package gameManagement;

import gameManagementCheckingPosition.InSafePlaceChecker;
import gameManagementDistances.DistanceCalculator;
import jsclub.codefest2024.sdk.model.Element;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.obstacles.Obstacle;
import jsclub.codefest2024.sdk.model.players.Player;

import java.util.List;

public class getClosestSomething {
    public static Player getClosestPlayer(List<Player> elements, Element curPos) {
        if (elements.isEmpty())
            return null;

        Player result = elements.getFirst();
        int minDist = DistanceCalculator.calc(result, curPos);

        for (Player e : elements) {
            int tempDist = DistanceCalculator.calc(e, curPos);
            if (tempDist < minDist) {
                if (e.getIsAlive()){
                    minDist = tempDist;
                    result = e;
                }
            }
        }

        return result;
    }

    public static Obstacle getClosestObstacle(boolean darkBig, List<Obstacle> elements, Element curPos, GameMap gameMap) {
        Obstacle result = elements.getFirst();
        int minDist = DistanceCalculator.calc(result, curPos);

        for (Obstacle e : elements ) {
            int tempDist = DistanceCalculator.calc(e, curPos);
            if (InSafePlaceChecker.checkChess(e, gameMap, darkBig) && tempDist < minDist) {
                minDist = tempDist;
                result = e;
            }
        }

        return result;
    }
}
