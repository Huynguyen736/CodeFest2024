package gameMovement;

import gameManagement.getClosestSomething;
import jsclub.codefest2024.sdk.Hero;
import jsclub.codefest2024.sdk.algorithm.PathUtils;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.players.Player;

import java.util.List;

public class MoveAndAttack {
    static boolean Atk = false;
    public static void moveAndAttack(Player closestPlayer, Node currentNode, Hero hero, List<Player> otherPlayers, List<Node> restrictedNodes) {
        try {
            GameMap gameMap = hero.getGameMap();
            Player player = gameMap.getCurrentPlayer();

            if (Atk) {
                if (currentNode.getX() - closestPlayer.getX() == 0){
                    if (currentNode.getY() > closestPlayer.getY()){
                        hero.move("u");
                    } else {hero.move("d");}
                } else {
                    if (currentNode.getX() < closestPlayer.getX()){
                        hero.move("l");
                    } else {hero.move("r");}
                }
                Atk = false;
            } else if (hero.getInventory().getThrowable() != null && PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false).length() >= 5) {
                // Check for nearby enemies to throw
                if ((Math.abs(currentNode.getX() - closestPlayer.getX()) <= 1 ||
                        Math.abs(currentNode.getY() - closestPlayer.getY()) <= 1) &&
                        Math.abs(currentNode.getX() - closestPlayer.getX()) + Math.abs(currentNode.getY() - closestPlayer.getY()) <= hero.getInventory().getThrowable().getRange()) {
                    if (closestPlayer.getY() > currentNode.getY()) {
                        hero.throwItem("u");
                        Atk = true;
                    } else if (closestPlayer.getY() < currentNode.getY()) {
                        hero.throwItem("d");
                        Atk = true;
                    } else if (closestPlayer.getX() < currentNode.getX()) {
                        hero.throwItem("l");
                        Atk = true;
                    } else if (closestPlayer.getX() > currentNode.getX()) {
                        hero.throwItem("r");
                        Atk = true;
                    }
                } else {
                    closestPlayer = getClosestSomething.getClosestPlayer(otherPlayers, player);
                    hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false));
                }
            }   else if (hero.getInventory().getGun() != null && PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false).length() >= 4) {
                if ((currentNode.getX() - closestPlayer.getX() == 0 ||
                        currentNode.getY() - closestPlayer.getY() == 0) &&
                        Math.abs(currentNode.getX() - closestPlayer.getX()) + Math.abs(currentNode.getY() - closestPlayer.getY()) <= hero.getInventory().getGun().getRange()) {
                    if (closestPlayer.getY() > currentNode.getY()) {
                        hero.shoot("u");
                        Atk = true;
                    } else if (closestPlayer.getY() < currentNode.getY()) {
                        hero.shoot("d");
                        Atk = true;
                    } else if (closestPlayer.getX() < currentNode.getX()) {
                        hero.shoot("l");
                        Atk = true;
                    } else if (closestPlayer.getX() > currentNode.getX()) {
                        hero.shoot("r");
                        Atk = true;
                    }
                } else {
                    closestPlayer = getClosestSomething.getClosestPlayer(otherPlayers, player);
                    hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false));
                }
            } else {
                if ((currentNode.getX() - closestPlayer.getX() == 0 ||
                        currentNode.getY() - closestPlayer.getY() == 0) &&
                        (Math.abs(currentNode.x-closestPlayer.x)+Math.abs(currentNode.y-closestPlayer.y)) > 1) {
                    if (currentNode.getX() - closestPlayer.getX() == 0) {
                        if (PathUtils.checkInsideSafeArea(player, gameMap.getDarkAreaSize(), gameMap.getMapSize())){
                            hero.move("l");
                        } else {hero.move("r");}
                    } else {
                        if (PathUtils.checkInsideSafeArea(player, gameMap.getDarkAreaSize(), gameMap.getMapSize())){
                            hero.move("u");
                        } else {hero.move("d");}
                    }
                } else if ((currentNode.getX() - closestPlayer.getX() == 0 ||
                        currentNode.getY() - closestPlayer.getY() == 0) &&
                        (Math.abs(currentNode.x-closestPlayer.x)+Math.abs(currentNode.y-closestPlayer.y)) <= 1) {
                    if (closestPlayer.getY() > currentNode.getY()) {
                        hero.attack("u");
                    } else if (closestPlayer.getY() < currentNode.getY()) {
                        hero.attack("d");
                    } else if (closestPlayer.getX() < currentNode.getX()) {
                        hero.attack("l");
                    } else if (closestPlayer.getX() > currentNode.getX()) {
                        hero.attack("r");
                    }
                } else {
                    closestPlayer = getClosestSomething.getClosestPlayer(otherPlayers, player);
                    hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false));
                    }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
