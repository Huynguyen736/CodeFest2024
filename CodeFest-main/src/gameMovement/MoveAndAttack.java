package gameMovement;

import gameManagement.getClosestSomething;
import jsclub.codefest2024.sdk.Hero;
import jsclub.codefest2024.sdk.algorithm.PathUtils;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.players.Player;

import java.util.List;

public class MoveAndAttack {
    public static void moveAndAttack(Player closestPlayer, Node currentNode, Hero hero, List<Player> otherPlayers, List<Node> restrictedNodes) {
        try {
            GameMap gameMap = hero.getGameMap();

            Player player = gameMap.getCurrentPlayer();

            if (hero.getInventory().getThrowable() != null && PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false).length() >= 5) {
                // Check for nearby enemies to throw
                if ((currentNode.getX() - closestPlayer.getX() == 0 ||
                        currentNode.getY() - closestPlayer.getY() == 0) &&
                        Math.abs(currentNode.getX() - closestPlayer.getX()) + Math.abs(currentNode.getY() - closestPlayer.getY()) <= hero.getInventory().getThrowable().getRange()) {
                    if (closestPlayer.getY() > currentNode.getY()) {
                        hero.throwItem("u");
                    } else if (closestPlayer.getY() < currentNode.getY()) {
                        hero.throwItem("d");
                    } else if (closestPlayer.getX() < currentNode.getX()) {
                        hero.throwItem("l");
                    } else if (closestPlayer.getX() > currentNode.getX()) {
                        hero.throwItem("r");
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
                    } else if (closestPlayer.getY() < currentNode.getY()) {
                        hero.shoot("d");
                    } else if (closestPlayer.getX() < currentNode.getX()) {
                        hero.shoot("l");
                    } else if (closestPlayer.getX() > currentNode.getX()) {
                        hero.shoot("r");
                    }
                } else {
                    closestPlayer = getClosestSomething.getClosestPlayer(otherPlayers, player);
                    hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false));
                }
            } else {
                if ((currentNode.getX() - closestPlayer.getX() == 0 ||
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

//    public static void moveAndUnChest(Node chest, Node currentNode, Hero hero,List<Node> restrictedNodes) {
//        try {
//            GameMap gameMap = hero.getGameMap();
//
//            Player player = gameMap.getCurrentPlayer();
//
//
//            // Check for nearby enemies to shoot
//            if ((currentNode.getX() - chest.getX() == 0 ||
//                    currentNode.getY() - chest.getY() == 0) &&
//                    Math.abs(currentNode.getX() - chest.getX()) + Math.abs(currentNode.getY() - chest.getY()) <= hero.getInventory().getGun().getRange()) {
//                if (chest.getY() > currentNode.getY()) {
//                    hero.shoot("u");
//                } else if (chest.getY() < currentNode.getY()) {
//                    hero.shoot("d");
//                } else if (chest.getX() < currentNode.getX()) {
//                    hero.shoot("l");
//                } else if (chest.getX() > currentNode.getX()) {
//                    hero.shoot("r");
//                }
//            } else {
//                //chest = getClosestChest.get(chest, player);
//                hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, chest, false));
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
