package functionOfPlayer;

import gameManagementCheckingPosition.InSafePlaceChecker;
import gameManagementDistances.DistanceCalculator;
import jsclub.codefest2024.sdk.Hero;
import jsclub.codefest2024.sdk.algorithm.PathUtils;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.obstacles.Obstacle;
import jsclub.codefest2024.sdk.model.players.Player;

import java.util.List;

public class RunInSideSafePlace {
    public static void run(Hero hero, Player player, GameMap gameMap, Node currentNode, List<Node> restrictedNodes) throws Exception {
        if (!InSafePlaceChecker.checkMyself(player, gameMap)) {
            try {
                List<Obstacle> os = gameMap.getListIndestructibleObstacles();
                if (!os.isEmpty()) {
                    Obstacle o = os.getFirst();

                    for (Obstacle oTemp : os) {
                        if (InSafePlaceChecker.checkObstacle(oTemp, gameMap) &&
                                DistanceCalculator.calc(oTemp, player) < DistanceCalculator.calc(o, player)) {
                            o = oTemp;
                        }
                    }

                    hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, new Node(o.getX(), o.getY()), false));
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
