package gameManagementNodes;

import jsclub.codefest2024.sdk.Hero;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.enemies.Enemy;
import jsclub.codefest2024.sdk.model.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class GetRestrictedNodes {
    public static List<Node> getNodesAll(Hero hero) {
        GameMap gameMap = hero.getGameMap();

        List<Obstacle> restrictedList = new ArrayList<>();
        restrictedList.addAll(gameMap.getListIndestructibleObstacles());
        restrictedList.addAll(gameMap.getListChests());
        restrictedList.addAll(gameMap.getListTraps());

        List<Node> restrictedNodes = new ArrayList<>();

        for (Obstacle o : restrictedList) {
            restrictedNodes.add(new Node(o.getX(), o.getY()));
        }

        for (Enemy e : gameMap.getListEnemies()) {
            restrictedNodes.add(new Node(e.getX(), e.getY()));
            restrictedNodes.add(new Node(e.getX()-1, e.getY()));
            restrictedNodes.add(new Node(e.getX()+1, e.getY()));
            restrictedNodes.add(new Node(e.getX(), e.getY()-1));
            restrictedNodes.add(new Node(e.getX(), e.getY()+1));
            restrictedNodes.add(new Node(e.getX()-1, e.getY()-1));
            restrictedNodes.add(new Node(e.getX()+1, e.getY()+1));
            restrictedNodes.add(new Node(e.getX()-1, e.getY()+1));
            restrictedNodes.add(new Node(e.getX()+1, e.getY()-1));
        }

        return restrictedNodes;
    }

    public static List<Node> getNodesChess(Hero hero) {
        GameMap gameMap = hero.getGameMap();

        List<Obstacle> restrictedListChess = new ArrayList<>();
        restrictedListChess.addAll(gameMap.getListIndestructibleObstacles());
        restrictedListChess.addAll(gameMap.getListTraps());

        List<Node> restrictedNodesChess = new ArrayList<>();

        for (Obstacle o : restrictedListChess) {
            restrictedNodesChess.add(new Node(o.getX(), o.getY()));
        }

        for (Enemy e : gameMap.getListEnemies()) {
            restrictedNodesChess.add(new Node(e.getX(), e.getY()));
            restrictedNodesChess.add(new Node(e.getX()-1, e.getY()));
            restrictedNodesChess.add(new Node(e.getX()+1, e.getY()));
            restrictedNodesChess.add(new Node(e.getX(), e.getY()-1));
            restrictedNodesChess.add(new Node(e.getX(), e.getY()+1));
            restrictedNodesChess.add(new Node(e.getX()-1, e.getY()-1));
            restrictedNodesChess.add(new Node(e.getX()+1, e.getY()+1));
            restrictedNodesChess.add(new Node(e.getX()-1, e.getY()+1));
            restrictedNodesChess.add(new Node(e.getX()+1, e.getY()-1));
        }

        return restrictedNodesChess;
    }

    public static List<Node> getNodesGas(Hero hero) {
        GameMap gameMap = hero.getGameMap();

        List<Obstacle> restrictedListGas = new ArrayList<>();
        restrictedListGas.addAll(gameMap.getListIndestructibleObstacles());
        restrictedListGas.addAll(gameMap.getListChests());

        List<Node> restrictedNodesGas = new ArrayList<>();

        for (Obstacle o : restrictedListGas) {
            restrictedNodesGas.add(new Node(o.getX(), o.getY()));
        }

        for (Enemy e : gameMap.getListEnemies()) {
            restrictedNodesGas.add(new Node(e.getX(), e.getY()));
            restrictedNodesGas.add(new Node(e.getX()-1, e.getY()));
            restrictedNodesGas.add(new Node(e.getX()+1, e.getY()));
            restrictedNodesGas.add(new Node(e.getX(), e.getY()-1));
            restrictedNodesGas.add(new Node(e.getX(), e.getY()+1));
            restrictedNodesGas.add(new Node(e.getX()-1, e.getY()-1));
            restrictedNodesGas.add(new Node(e.getX()+1, e.getY()+1));
            restrictedNodesGas.add(new Node(e.getX()-1, e.getY()+1));
            restrictedNodesGas.add(new Node(e.getX()+1, e.getY()-1));
        }

        return restrictedNodesGas;
    }
}
