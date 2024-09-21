package gameManagementCheckingPosition;

import jsclub.codefest2024.sdk.algorithm.PathUtils;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.equipments.Armor;
import jsclub.codefest2024.sdk.model.equipments.HealingItem;
import jsclub.codefest2024.sdk.model.obstacles.Obstacle;
import jsclub.codefest2024.sdk.model.players.Player;
import jsclub.codefest2024.sdk.model.weapon.Weapon;

public class InSafePlaceChecker {
    public static boolean checkWeapon(Weapon targetW, GameMap gameMap, boolean darkBig) {
        int darksize = gameMap.getDarkAreaSize();
        if (darkBig)
            darksize = darksize * 3 / 2;

        return (PathUtils.checkInsideSafeArea(new Node(targetW.getX(), targetW.getY()), darksize, gameMap.getMapSize()));

    }
    public static boolean checkArmor(Armor targetW, GameMap gameMap) {
        return (PathUtils.checkInsideSafeArea(new Node(targetW.getX(), targetW.getY()), gameMap.getDarkAreaSize() * 3 / 2, gameMap.getMapSize()));

    }
    public static boolean checkHealing(HealingItem targetW, GameMap gameMap) {
        return (PathUtils.checkInsideSafeArea(new Node(targetW.getX(), targetW.getY()), gameMap.getDarkAreaSize() * 3 / 2, gameMap.getMapSize()));

    }

    public static boolean checkChess(Obstacle targetW, GameMap gameMap, boolean darkBig) {
        int darksize = gameMap.getDarkAreaSize();
        if (darkBig)
            darksize = darksize * 3 / 2;
        return (PathUtils.checkInsideSafeArea(new Node(targetW.getX(), targetW.getY()), darksize, gameMap.getMapSize()));

    }

    public static boolean checkObstacle(Obstacle targetW, GameMap gameMap) {
        return (PathUtils.checkInsideSafeArea(new Node(targetW.getX(), targetW.getY()), gameMap.getDarkAreaSize() * 3 / 2, gameMap.getMapSize()));
    }

    public static boolean checkMyself(Player targetW, GameMap gameMap) {
        return (PathUtils.checkInsideSafeArea(new Node(targetW.getX(), targetW.getY()), gameMap.getDarkAreaSize() * 3 / 2, gameMap.getMapSize()));

    }
}
