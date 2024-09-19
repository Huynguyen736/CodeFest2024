package getInfo;

import jsclub.codefest2024.sdk.Hero;
import jsclub.codefest2024.sdk.algorithm.PathUtils;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import myClass.PlayerAndID;

import java.util.HashMap;
import java.util.List;

public class GetStepApproach {
    public static HashMap<Integer, Integer> setupStep() {
        HashMap<Integer, Integer> countStep = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            countStep.put(i, 0);
        }

        return countStep;
    }

    public static HashMap<Integer, Integer> checkStep(Hero hero, List<Node> restrictedNodes,
                                                      HashMap<Integer, Integer> preCountStep,
                                                      List<PlayerAndID> playerAndIDS, Node curNode) {
        HashMap<Integer, Integer> countStep = new HashMap<>();
        int n = playerAndIDS.size();
        GameMap gameMap = hero.getGameMap();

        for (int i = 0; i < n; i++) {
            PlayerAndID p = playerAndIDS.get(i);

            Node playerNode = new Node(p.p.getX(), p.p.getY());
            String pathCtoP = PathUtils.getShortestPath(gameMap, restrictedNodes, curNode, playerNode, false);
            String pathPtoC = PathUtils.getShortestPath(gameMap, restrictedNodes, playerNode, curNode, false);

            char cCtoP = pathCtoP.charAt(pathCtoP.length() - 1);
            char cPtoC = pathPtoC.charAt(0);
            if ((cCtoP == 'D' && cPtoC == 'U') ||
                    (cCtoP == 'U' && cPtoC == 'D') ||
                    (cCtoP == 'L' && cPtoC == 'R') ||
                    (cCtoP == 'R' && cPtoC == 'L'))
                countStep.put(i, countStep.get(i) + 1);
            else
                countStep.put(i, 0);
        }

        return countStep;
    }
}
