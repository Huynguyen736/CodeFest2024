package gameMovement;

import gameManagement.getClosestSomething;
import jsclub.codefest2024.sdk.Hero;
import jsclub.codefest2024.sdk.algorithm.PathUtils;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.players.Player;

import java.util.List;

public class MoveAndAttack {
    static String Atk = "-";
    public static int moveAndAttack(Player closestPlayer, Node currentNode, Hero hero, List<Player> otherPlayers, List<Node> restrictedNodes, boolean atkandmove, List<Integer> dis, int countAttack) {
        try {
            GameMap gameMap = hero.getGameMap();
            Player player = gameMap.getCurrentPlayer();

            String path = PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false);
            if (path != null)
                dis.add(path.length());
            int n = dis.size();
            int k = 4;
            if (n >= 4) {
                boolean check = false;
                for (int i = n - 1; i >= n - k; i--) {
                    for (int j = i + 1; j < n; j++) {
                        if (dis.get(i) - dis.get(j) > 2) {
                            check = true;
                        }
                    }
                }

                if (!check && countAttack == 0) {
                    dis.clear();
                    return 0;
                }
            }

            if (Atk != "-") {
                if (Atk == "u") {
                    hero.move("u");
                    Atk = "-";
                } else if (Atk == "d") {
                    hero.move("d");
                    Atk = "-";
                } else if (Atk == "r") {
                    hero.move("r");
                    Atk = "-";
                } else if (Atk == "l") {
                    hero.move("l");
                    Atk = "-";
                }
            } else if (hero.getInventory().getThrowable() != null && PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false).length() >= 5) {
                // Check for nearby enemies to throw
                if ((Math.abs(currentNode.getX() - closestPlayer.getX()) <= 1 ||
                        Math.abs(currentNode.getY() - closestPlayer.getY()) <= 1) &&
                        Math.abs(currentNode.getX() - closestPlayer.getX()) + Math.abs(currentNode.getY() - closestPlayer.getY()) <= hero.getInventory().getThrowable().getRange()) {
                    if (closestPlayer.getY() > currentNode.getY()) {
                        hero.throwItem("u");
                        if (atkandmove) {
                            Atk = "d";
                        }
                    } else if (closestPlayer.getY() < currentNode.getY()) {
                        hero.throwItem("d");
                        if (atkandmove) {
                            Atk = "u";
                        }
                    } else if (closestPlayer.getX() < currentNode.getX()) {
                        hero.throwItem("l");
                        if (atkandmove) {
                            Atk = "r";
                        }
                    } else if (closestPlayer.getX() > currentNode.getX()) {
                        hero.throwItem("r");
                        if (atkandmove) {
                            Atk = "l";
                        }
                    }
                } else {
                    closestPlayer = getClosestSomething.getClosestPlayer(otherPlayers, player);
                    hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false));
                }
            } else if (hero.getInventory().getGun() != null && (hero.getInventory().getMelee().getId() == "HAND" || (closestPlayer != null && PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false).length() >= 4))) {
                if ((currentNode.getX() - closestPlayer.getX() == 0 ||
                        currentNode.getY() - closestPlayer.getY() == 0) &&
                        Math.abs(currentNode.getX() - closestPlayer.getX()) + Math.abs(currentNode.getY() - closestPlayer.getY()) <= hero.getInventory().getGun().getRange()) {
                    countAttack++;

                    if (closestPlayer.getY() > currentNode.getY()) {
                        hero.shoot("u");
                        if (atkandmove) {
                            Atk = "d";
                        }
                    } else if (closestPlayer.getY() < currentNode.getY()) {
                        hero.shoot("d");
                        if (atkandmove) {
                            Atk = "u";
                        }
                    } else if (closestPlayer.getX() < currentNode.getX()) {
                        hero.shoot("l");
                        if (atkandmove) {
                            Atk = "r";
                        }
                    } else if (closestPlayer.getX() > currentNode.getX()) {
                        hero.shoot("r");
                        if (atkandmove) {
                            Atk = "l";
                        }
                    }
                } else {
                    closestPlayer = getClosestSomething.getClosestPlayer(otherPlayers, player);
                    hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false));
                }
            } else {
                if ((currentNode.getX() - closestPlayer.getX() == 0 ||
                        currentNode.getY() - closestPlayer.getY() == 0) &&
                        (Math.abs(currentNode.x - closestPlayer.x) + Math.abs(currentNode.y - closestPlayer.y)) > 2) {
                    if (currentNode.getX() - closestPlayer.getX() == 0) {
                        if (PathUtils.checkInsideSafeArea(player, gameMap.getDarkAreaSize(), gameMap.getMapSize())) {
                            hero.move("l");
                        } else {
                            hero.move("r");
                        }
                    } else {
                        if (PathUtils.checkInsideSafeArea(player, gameMap.getDarkAreaSize(), gameMap.getMapSize())) {
                            hero.move("u");
                        } else {
                            hero.move("d");
                        }
                    }
                } else if ((currentNode.getX() - closestPlayer.getX() == 0 ||
                        currentNode.getY() - closestPlayer.getY() == 0) &&
                        (Math.abs(currentNode.x - closestPlayer.x) + Math.abs(currentNode.y - closestPlayer.y)) <= 1) {
                    countAttack++;

                    if (closestPlayer.getY() > currentNode.getY()) {
                        hero.attack("u");
                        if (hero.getInventory().getGun() != null)
                            hero.shoot("u");
                    } else if (closestPlayer.getY() < currentNode.getY()) {
                        hero.attack("d");
                        if (hero.getInventory().getGun() != null)
                            hero.shoot("d");
                    } else if (closestPlayer.getX() < currentNode.getX()) {
                        hero.attack("l");
                        if (hero.getInventory().getGun() != null)
                            hero.shoot("l");
                    } else if (closestPlayer.getX() > currentNode.getX()) {
                        hero.attack("r");
                        if (hero.getInventory().getGun() != null)
                            hero.shoot("r");
                    }
                } else {
                    closestPlayer = getClosestSomething.getClosestPlayer(otherPlayers, player);
                    hero.move(PathUtils.getShortestPath(gameMap, restrictedNodes, currentNode, closestPlayer, false));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return countAttack;
    }
}
