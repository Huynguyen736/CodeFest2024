package gameMovement;


import gameManagement.getClosestSomething;
import jsclub.codefest2024.sdk.Hero;
import jsclub.codefest2024.sdk.algorithm.PathUtils;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.obstacles.Obstacle;
import jsclub.codefest2024.sdk.model.players.Player;

import java.util.ArrayList;
import java.util.List;

public class MoveToObject {

    public static void moveToTrap(Node closestGas, Node currentNode, Hero hero, List<Obstacle> trapList, List<Node> restrictedNodes, int HP) {
        try {
            GameMap gameMap = hero.getGameMap();
            Player player = gameMap.getCurrentPlayer();

            List<Obstacle> restrictedList = new ArrayList<>();
            restrictedList.addAll(gameMap.getListTraps());


            for (Obstacle o : restrictedList) {
                restrictedNodes.add(new Node(o.getX(), o.getY()));
            }

            if (player.getHp() >= HP) {
                if (closestGas.getY() > currentNode.getY()) {
                    hero.move("u");
                } else if (closestGas.getY() < currentNode.getY()) {
                    hero.move("d");
                } else if (closestGas.getX() < currentNode.getX()) {
                    hero.move("l");
                } else if (closestGas.getX() > currentNode.getX()) {
                    hero.move("r");
                }
            } else {
                closestGas = getClosestSomething.getClosestObstacle(trapList, player);
                hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestGas, false));

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static void MoveAndChestAttacking(Node closestChest, Node currentNode, Hero hero, List<Node> restrictedNodes) {
        try {
            GameMap gameMap = hero.getGameMap();
            Player player = gameMap.getCurrentPlayer();

//            List<Obstacle> restrictedList = new ArrayList<>();
//            restrictedList.addAll(gameMap.getListChests());
//
//
//            for (Obstacle o : restrictedList) {
//                restrictedNodes.add(new Node(o.getX(), o.getY()));
//            }

            if (player.getIsAlive()) {
                if (Math.abs(closestChest.getX() - currentNode.getX()) + Math.abs(closestChest.getY() - closestChest.getY()) > 1) {
                    hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestChest, false));
                } else {
                    if (closestChest.getY() > currentNode.getY()) {
                        hero.attack("u");
                    } else if (closestChest.getY() < currentNode.getY()) {
                        hero.attack("d");
                    } else if (closestChest.getX() < currentNode.getX()) {
                        hero.attack("l");
                    } else if (closestChest.getX() > currentNode.getX()) {
                        hero.attack("r");
                    }
                }


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
