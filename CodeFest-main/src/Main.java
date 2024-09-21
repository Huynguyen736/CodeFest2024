
import functionOfPlayer.GetSomething;
import gameManagement.getClosestSomething;
import gameManagementDistances.SortDistancer;
import gameManagementNodes.GetOtherPlayerNodes;
import gameManagementNodes.GetRestrictedNodes;
import gameMovement.MoveAndAttack;
import gameMovement.MoveToObject;
import getInfo.GetStepApproach;
import io.socket.emitter.Emitter;
import jsclub.codefest2024.sdk.Hero;
import jsclub.codefest2024.sdk.algorithm.PathUtils;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.equipments.HealingItem;
import jsclub.codefest2024.sdk.model.obstacles.Obstacle;
import jsclub.codefest2024.sdk.model.players.Player;
import myClass.PlayerAndDistance;
import myClass.PlayerAndID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class Main {
    private static final String SERVER_URL = "https://cf-server.jsclub.dev";
    private static final String GAME_ID = "125555";
    private static final String PLAYER_NAME = "test-02";
    private static final String PLAYER_KEY = "ed866d66-b1ec-4578-b5ad-9f12b9f55a23";
    private static final Logger log = LogManager.getLogger(Main.class);


    public static HashMap<Integer, Integer> countStep;
    public static int closestPlayerCount = 0;
    public static int closestPlayerDis = 0;
    public static int tackleCount = 0;
    public static Node closestPlayerNode = null;

    public static void main(String[] args) throws IOException {
        Hero hero = new Hero(GAME_ID, PLAYER_NAME, PLAYER_KEY);
        countStep = GetStepApproach.setupStep();

        Emitter.Listener onMapUpdate = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
//                    Game map
                    GameMap gameMap = hero.getGameMap();
                    gameMap.updateOnUpdateMap(args[0]);
                    System.out.println();
//                    Current Player
                    Player player = gameMap.getCurrentPlayer();

//                    Other Player
                    List<Player> otherPlayers = new ArrayList<>();
                    List<PlayerAndID> otherPlayersAndID = new ArrayList<>();
                    List<Player> tempOtherPlayers = gameMap.getOtherPlayerInfo();
                    int n = tempOtherPlayers.size();
                    //System.out.println(tempOtherPlayers);

                    for (int i = 0; i < n; i++) {
                        Player p = tempOtherPlayers.get(i);

                        if (p.getIsAlive()) {
                            otherPlayers.add(p);
                            otherPlayersAndID.add(new PlayerAndID(p, i));
                        }
                    }

//                    Other Player Distance To Current Node
                    List<PlayerAndDistance> playerOrdered = SortDistancer.sorter(otherPlayers, player);

//                    Other Player Nodes
                    List<Node> otherPlayerNodes = GetOtherPlayerNodes.getNodes(otherPlayers);

//                    Restricted Nodes
                    List<Node> restrictedNodesChess = new ArrayList<>(GetRestrictedNodes.getNodesChess(hero));
                    List<Node> restrictedNodesAll = GetRestrictedNodes.getNodesAll(hero);
                    List<Node> restrictedNodesGas = GetRestrictedNodes.getNodesGas(hero);

//                    Check if other players come to us or not
                    //countStep = GetStepApproach.checkStep(hero, restrictedNodes, countStep, otherPlayersAndID, currentNode);

//                    Move to safe palace


                    List<Obstacle> trapList = new ArrayList<>(gameMap.getListTraps());
                    Obstacle closestGas = getClosestSomething.getClosestObstacle(trapList, player);

                    //Get closest chess
                    List<Obstacle> chestList = new ArrayList<>(gameMap.getListChests());
                    Obstacle closestChest = gameManagement.getClosestSomething.getClosestObstacle(chestList, player);

//                    Get Gun
                    //GetGuner.getGun(hero, currentNode, restrictedNodes, otherPlayerNodes, player);

//                    Get player closest
                    Player closestPlayer = getClosestSomething.getClosestPlayer(otherPlayers, player);
                    Node closestPlayerNode = new Node(closestPlayer.getX(), closestPlayer.getY());
                    String closestPlayerID = closestPlayer.getId();

//                     gas tank position
                    Node gasNode = new Node(closestGas.getX(), closestGas.getY());

//                    tactic for game
                    if (PathUtils.getShortestPath(gameMap, restrictedNodesAll, player, closestPlayer, false).length() < 5) {
                        if (!(Objects.equals(closestPlayerID, closestPlayer.getId()))) {
                            closestPlayerID = closestPlayer.getId();
                            closestPlayerCount = 0;
                            closestPlayerDis = PathUtils.getShortestPath(gameMap, restrictedNodesAll, player, closestPlayer, false).length();

                        }else {
                            if (closestPlayerNode.x == closestPlayer.x && closestPlayerNode.y == closestPlayer.y) {
                                tackleCount++;
                            } else {
                                tackleCount = 0;
                            }

                            closestPlayerNode.x = closestPlayer.x;
                            closestPlayerNode.y = closestPlayer.y;

                            if (PathUtils.getShortestPath(gameMap, restrictedNodesAll, player, closestPlayer, false).length() <= closestPlayerDis){
                                closestPlayerCount++;
                            }else {
                                closestPlayerCount = 0;
                            }
                        }
                    }

                    if (!closestPlayer.getIsAlive()) {
                        tackleCount = 0;
                        closestPlayerCount = 0;
                    }

                    if (player.getHp() <= 60) {
                        List<HealingItem> HealingItems = hero.getInventory().getListHealingItem();
                        if (!HealingItems.isEmpty()) {
                            hero.useItem(HealingItems.getFirst().getId());
                        }
                    }

                    if (tackleCount <= 8) {
                        if (closestPlayerCount <= 5) {
                            GetSomething.getMelee(hero, restrictedNodesAll, otherPlayerNodes, player);
                            GetSomething.getArmor(hero, restrictedNodesAll, otherPlayerNodes, player);
                            GetSomething.getHealing(hero, restrictedNodesAll, otherPlayerNodes, player);
                            GetSomething.getThrowable(hero, restrictedNodesAll, otherPlayerNodes, player);
                            GetSomething.getGun(hero, restrictedNodesAll, otherPlayerNodes, player);

                            GetSomething.getChess(hero, restrictedNodesChess, otherPlayerNodes, player, closestChest);
                        } else {
                            if (player.getHp() > 30){
                                MoveAndAttack.moveAndAttack(closestPlayer, player, hero, otherPlayers, restrictedNodesAll);
                            }else {
                                if (gameMap.getDarkAreaSize() < 24){
                                    if ((Math.abs(closestPlayer.x-closestGas.x)+Math.abs(closestPlayer.y-closestGas.y)) > 1) {
                                        if (player.x - closestPlayer.x > 0) {
                                            closestGas.x = closestGas.x + 1;
                                        } else {
                                            closestGas.x = closestGas.x - 1;
                                        }
                                        hero.move(PathUtils.getShortestPath(gameMap, restrictedNodesGas, player, closestGas, false));
                                    }else {
                                        hero.move(PathUtils.getShortestPath(gameMap, restrictedNodesGas, player, closestGas, false));
                                    }
                                } else {
                                    if (player.x != 24 && player.y != 24) {
                                        hero.move(PathUtils.getShortestPath(gameMap, restrictedNodesAll, player, new Node(24, 24), false));
                                    }
                                }
                            }
                        }
                    }else {
                        MoveAndAttack.moveAndAttack(closestPlayer, player, hero, otherPlayers, restrictedNodesAll);
                    }


//                    for (int i = 0; i < hero.getInventory().getListHealingItem().size(); i++) {
//                        if (player.getHp() >= 80) {
//                            MoveAndAttack.moveAndAttack(closestPlayer, player, hero, otherPlayers, restrictedNodesAll);
//                        } else if (player.getHp() < 80 && hero.getInventory().getListHealingItem().get(i) != null) {
//                            // using healing item
//                        } else if (player.getHp() < 70 && hero.getInventory().getListHealingItem().get(i) == null) {
//                            // find chest and unbox find items
//                        } else {
//                            MoveToObject.moveToTrap(gasNode, player, hero, trapList, restrictedNodesAll, 80);
//                        }
//
//
//                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };


        hero.setOnMapUpdate(onMapUpdate);
        hero.start(SERVER_URL);
    }
}



