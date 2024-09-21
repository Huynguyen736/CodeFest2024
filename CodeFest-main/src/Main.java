
import functionOfPlayer.GetSomething;
import functionOfPlayer.RunInSideSafePlace;
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
    private static final String GAME_ID = "156389";
    private static final String PLAYER_NAME = "test-03";
    private static final String PLAYER_KEY = "ed866d66-b1ec-4578-b5ad-9f12b9f55a23";
    private static final Logger log = LogManager.getLogger(Main.class);


    public static HashMap<Integer, Integer> countStep;
    public static int closestPlayerCount = 0;
    public static int closestPlayerDis = 0;
    public static int tackleCount = 0;
    public static String closestPlayerID = null;

    public static List<Integer> dis = new ArrayList<>();
    public static int countAttack = 0;

    public static int preDarkSize;

    public static void main(String[] args) throws IOException {
        Hero hero = new Hero(GAME_ID , PLAYER_NAME, PLAYER_KEY);
        countStep = GetStepApproach.setupStep();
        Emitter.Listener onMapUpdate = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
//                    Game map
                    GameMap gameMap = hero.getGameMap();
                    gameMap.updateOnUpdateMap(args[0]);

                    boolean darkBig = false;
                    if (preDarkSize != gameMap.getDarkAreaSize())
                        darkBig = true;

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
                    Obstacle closestGas = getClosestSomething.getClosestObstacle(darkBig, trapList, player, gameMap);

//                    chay bo
                    RunInSideSafePlace.run(hero, player, gameMap, new Node(player.getX(), player.getY()), restrictedNodesAll);

                    //Get closest chess
                    List<Obstacle> chestList = new ArrayList<>(gameMap.getListChests());
                    Obstacle closestChest = gameManagement.getClosestSomething.getClosestObstacle(darkBig, chestList, player, gameMap);

//                    Get Gun
                    //GetGuner.getGun(hero, currentNode, restrictedNodes, otherPlayerNodes, player);

//                    Get player closest
                    Player closestPlayer = getClosestSomething.getClosestPlayer(otherPlayers, player);
                    System.out.println("closest: " + closestPlayer);

//                    tactic for game
                    if (closestPlayer != null) {
                        try {
                            Node closestPlayerNode = new Node(closestPlayer.getX(), closestPlayer.getY());

                            String path = PathUtils.getShortestPath(gameMap, restrictedNodesAll, player, closestPlayer, false);
                            if (path != null && path.length() < 25) {
                                if (!(Objects.equals(closestPlayerID, closestPlayer.getId()))) {
                                    closestPlayerID = closestPlayer.getId();
                                    closestPlayerCount = 0;
                                    closestPlayerDis = path.length();
                                    tackleCount = 0;
                                    System.out.println("yes");
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
                        } catch (RuntimeException e) {
                            tackleCount = 0;
                            closestPlayerCount = 0;
                            throw new RuntimeException(e);
                        }
                    } else {
                        tackleCount = 0;
                        closestPlayerCount = 0;
                    }



//                     gas tank position
                    Node gasNode = new Node(closestGas.getX(), closestGas.getY());



                    if (player.getIsAlive()) {
                        boolean isUseHealing = false;
                        List<HealingItem> HealingItems = hero.getInventory().getListHealingItem();
                        if (!HealingItems.isEmpty()) {
//                            hero.useItem(HealingItems.getFirst().getId());
                            for (HealingItem myHealItem : HealingItems) {
                                if (player.getHp() + myHealItem.getHealingHP() < 100) {
                                    hero.useItem(myHealItem.getId());
                                    isUseHealing = true;
                                }
                                if (!isUseHealing) {
                                    if (player.getHp() < 60) {
                                        hero.useItem(HealingItems.getFirst().getId());
                                        isUseHealing = true;
                                    }
                                }

                            }

                        }
                    }
                    System.out.println(gameMap.getDarkAreaSize());
                    System.out.println("Tackle: " + tackleCount);
                    if (tackleCount <= 3) {
                        if (closestPlayerCount <= 5) {
                            if (hero.getInventory().getMelee().getId() != "HAND" && hero.getInventory().getGun() != null &&
                                    hero.getInventory().getListArmor().size() == 2 && hero.getInventory().getListHealingItem().size() >= 3
                                    && player.getHp() > 50 && closestPlayer != null){
                                countAttack = MoveAndAttack.moveAndAttack(closestPlayer, player, hero, otherPlayers, restrictedNodesAll, false, dis, countAttack);
                            } else if (closestChest != null){
                                GetSomething.getChess(hero, restrictedNodesChess, otherPlayerNodes, player, closestChest);
                            }
                            GetSomething.getGun(darkBig, hero, restrictedNodesAll, otherPlayerNodes, player);
                            GetSomething.getThrowable(darkBig, hero, restrictedNodesAll, otherPlayerNodes, player);
                            GetSomething.getHealing(hero, restrictedNodesAll, otherPlayerNodes, player);
                            GetSomething.getArmor(hero, restrictedNodesAll, otherPlayerNodes, player);
                            GetSomething.getMelee(darkBig, hero, restrictedNodesAll, restrictedNodesChess, otherPlayerNodes, closestChest, otherPlayerNodes, player);
                        } else {
                            if (player.getHp() > 30){
                                countAttack =  MoveAndAttack.moveAndAttack(closestPlayer, player, hero, otherPlayers, restrictedNodesAll, true, dis, countAttack);
                            }else {
                                if (gameMap.getDarkAreaSize() < 24){
                                    if ((Math.abs(closestPlayer.x-player.x)+Math.abs(closestPlayer.y-player.y)) > 1) {
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
                                    if (player.x != 25 && player.y != 25) {
                                        hero.move(PathUtils.getShortestPath(gameMap, restrictedNodesAll, player, new Node(25, 25), false));
                                    }
                                }
                            }
                        }
                    } else if (closestPlayer != null) {
                        countAttack = MoveAndAttack.moveAndAttack(closestPlayer, player, hero, otherPlayers, restrictedNodesAll, false, dis, countAttack);
                    }



                    preDarkSize = gameMap.getDarkAreaSize();

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



