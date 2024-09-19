package gameManagementNodes;

import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.players.Player;

import java.util.ArrayList;
import java.util.List;

public class GetOtherPlayerNodes {
    public static List<Node> getNodes(List<Player> otherPlayers) {
        List<Node> otherPlayerNodes = new ArrayList<>();

        for (Player p : otherPlayers) {
            if (p.getIsAlive()) {
                otherPlayerNodes.add(new Node(p.getX(), p.getY()));
            }
        }

        return  otherPlayerNodes;
    }
}
