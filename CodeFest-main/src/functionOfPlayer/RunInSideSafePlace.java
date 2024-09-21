package functionOfPlayer;

import gameManagementCheckingPosition.InSafePlaceChecker;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.players.Player;

public class RunInSideSafePlace {
    public static void run(Player player, GameMap gameMap) {
        if (!InSafePlaceChecker.checkMyself(player, gameMap)) {

        }
    }
}
