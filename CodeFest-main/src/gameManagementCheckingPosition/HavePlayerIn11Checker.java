package gameManagementCheckingPosition;

import jsclub.codefest2024.sdk.model.players.Player;
import myClass.PlayerAndID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HavePlayerIn11Checker {

    private static List<Player> foundPlayerAround(Player curPlayer, List<PlayerAndID> playerAndIDS, HashMap<Integer, Integer> countStep) {
        List<Player> existPlayers = new ArrayList<>();

        for (PlayerAndID pID : playerAndIDS) {
            Player p = pID.p;

            if (Math.abs(p.getX() - curPlayer.getX()) <= 5 &&
                Math.abs(p.getY() - curPlayer.getY()) <= 5 &&
                countStep.get(pID.id) >= 5) {
                existPlayers.add(p);
            }
        }

        return existPlayers;
    }
}
