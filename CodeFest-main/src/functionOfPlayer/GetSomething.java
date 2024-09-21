package functionOfPlayer;

import gameManagementDistances.DistanceCalculator;
import gameManagementCheckingPosition.InSafePlaceChecker;
import jsclub.codefest2024.sdk.Hero;
import jsclub.codefest2024.sdk.algorithm.PathUtils;
import jsclub.codefest2024.sdk.base.Node;
import jsclub.codefest2024.sdk.model.GameMap;
import jsclub.codefest2024.sdk.model.equipments.Armor;
import jsclub.codefest2024.sdk.model.equipments.HealingItem;
import jsclub.codefest2024.sdk.model.obstacles.Obstacle;
import jsclub.codefest2024.sdk.model.players.Player;
import jsclub.codefest2024.sdk.model.weapon.Weapon;
import myClass.Priority;

import java.util.List;
import java.util.Objects;

public class GetSomething {
    public static final int step = 7;
    public static void getGun(Hero hero, List<Node> restrictedNodes, List<Node> otherPlayesNode, Player player) {

        try {
            Priority priority = new Priority();
            GameMap gameMap = hero.getGameMap();
            Weapon isUseGun = hero.getInventory().getGun();
            final boolean[] pickedUpGun = {isUseGun != null};

            System.out.println("Gun inventory: " + isUseGun);
            System.out.println("is picked up: " + pickedUpGun[0]);

            List<Weapon> gunList = gameMap.getAllGun();
            if (!gunList.isEmpty()) {
                Weapon targetGun = gunList.getFirst();

                for (Weapon gun : gunList) {
                    if (InSafePlaceChecker.checkWeapon(gun, gameMap) &&
                            DistanceCalculator.calc(gun, player) < DistanceCalculator.calc(targetGun, player))
                        targetGun = gun;
                }
                if (player.getX() == targetGun.getX() && player.getY() == targetGun.getY()) {
                    if (hero.getInventory().getGun() == null) {
                        hero.pickupItem();
                        pickedUpGun[0] = true;
                    }
                } else {
                    String _t = PathUtils.getShortestPath(gameMap, restrictedNodes, player, targetGun, false);
                    if (_t != null && _t.length() <= step) {
                        if (hero.getInventory().getGun() != null) {
                            if (Priority.getGunPriority(hero.getInventory().getGun().getId()) < Priority.getGunPriority(gameMap.getElementByIndex(targetGun.x, targetGun.y).getId())){
                                hero.revokeItem(hero.getInventory().getGun().getId());
                                pickedUpGun[0] = false;
                                restrictedNodes.addAll(otherPlayesNode);
                                hero.move(_t);
                            }
                        }
                        if (!pickedUpGun[0]) {
                            restrictedNodes.addAll(otherPlayesNode);
                            hero.move(_t);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void getMelee(Hero hero, List<Node> restrictedNodes, List<Node> otherPlayesNode, Player player) {

        try {
            GameMap gameMap = hero.getGameMap();
            Weapon isUseMelee = hero.getInventory().getMelee();
            final boolean[] pickedUpMelee = {isUseMelee.getId() != "HAND"};

            System.out.println("Melee inventory: " + isUseMelee);
            System.out.println("is picked up: " + pickedUpMelee[0]);


            List<Weapon> meleeList = gameMap.getAllMelee();
            if (!meleeList.isEmpty()) {
                Weapon targetMelee = meleeList.getFirst();

                for (Weapon melee : meleeList) {
                    if (InSafePlaceChecker.checkWeapon(melee, gameMap) &&
                            DistanceCalculator.calc(melee, player) < DistanceCalculator.calc(targetMelee, player))
                        targetMelee = melee;
                }

                if (player.getX() == targetMelee.getX() && player.getY() == targetMelee.getY()) {
                    if (!Objects.equals(hero.getInventory().getMelee().getId(), "HAND") ||
                            Priority.getMeleePriority(hero.getInventory().getMelee().getId()) < Priority.getMeleePriority(gameMap.getElementByIndex(player.getX(), player.getY()).getId())) {
                        hero.revokeItem(hero.getInventory().getMelee().getId());
                    }
                    hero.pickupItem();
                        pickedUpMelee[0] = true;
                } else {
                    String _t = PathUtils.getShortestPath(gameMap, restrictedNodes, player, targetMelee, false);
                    if (_t != null && _t.length() <= step) {
                            if (Priority.getMeleePriority(hero.getInventory().getMelee().getId()) < Priority.getMeleePriority(gameMap.getElementByIndex(targetMelee.x, targetMelee.y).getId())){
                                if (!Objects.equals(hero.getInventory().getMelee().getId(), "HAND")) {
                                    hero.revokeItem(hero.getInventory().getMelee().getId());
                                    pickedUpMelee[0] = false;
                                }
                                restrictedNodes.addAll(otherPlayesNode);
                                hero.move(_t);
                            } else {
                                return;
                            }
                        if (!pickedUpMelee[0]) {
                            restrictedNodes.addAll(otherPlayesNode);
                            hero.move(_t);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void getThrowable(Hero hero, List<Node> restrictedNodes, List<Node> otherPlayesNode, Player player) {

        try {
            GameMap gameMap = hero.getGameMap();
            Weapon isUseThrowable = hero.getInventory().getThrowable();
            final boolean[] pickedUpThrowable = {isUseThrowable != null};

            System.out.println("Throwable inventory: " + isUseThrowable);
            System.out.println("is picked up: " + pickedUpThrowable[0]);


            List<Weapon> ThrowableList = gameMap.getAllThrowable();

            if (!ThrowableList.isEmpty()) {
                Weapon targetThrowable = ThrowableList.getFirst();

                for (Weapon throwable : ThrowableList) {
                    if (InSafePlaceChecker.checkWeapon(throwable, gameMap) &&
                            DistanceCalculator.calc(throwable, player) < DistanceCalculator.calc(targetThrowable, player))
                        targetThrowable = throwable;
                }

                if (player.getX() == targetThrowable.getX() && player.getY() == targetThrowable.getY()) {
                    if (hero.getInventory().getThrowable() == null){
                        hero.pickupItem();
                        pickedUpThrowable[0] = true;
                    } else {
                        return;
                    }
                } else {
                    String _t = PathUtils.getShortestPath(gameMap, restrictedNodes, player, targetThrowable, false);
                    if (_t != null && _t.length() <= step) {
                        System.out.println("throwableee"+hero.getInventory().getThrowable());
                        if (hero.getInventory().getThrowable() != null) {
                            if (Priority.getThrowableNamePriority(hero.getInventory().getThrowable().getId()) < Priority.getThrowableNamePriority(gameMap.getElementByIndex(targetThrowable.x, targetThrowable.y).getId())){
                                hero.revokeItem(hero.getInventory().getThrowable().getId());
                                pickedUpThrowable[0] = false;
                                restrictedNodes.addAll(otherPlayesNode);
                                hero.move(_t);
                            } else {
                                return;
                            }
                        }
                        if (!pickedUpThrowable[0]) {
                            restrictedNodes.addAll(otherPlayesNode);
                            hero.move(_t);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void getChess(Hero hero, List<Node> restrictedNodes, List<Node> otherPlayesNode, Player player, Obstacle closestChest) {

        try {
            String Pathway = "";
            GameMap gameMap = hero.getGameMap();

            if (player.getIsAlive()) {
                if ((Math.abs(closestChest.getX() - player.getX()) + Math.abs(closestChest.getY() - player.getY())) == 1) {
                    if (closestChest.getY() > player.getY()) {
                        hero.attack("u");
                    } else if (closestChest.getY() < player.getY()) {
                        hero.attack("d");
                    } else if (closestChest.getX() < player.getX()) {
                        hero.attack("l");
                    } else if (closestChest.getX() > player.getX()) {
                        hero.attack("r");
                    }
                } else {
                restrictedNodes.addAll(otherPlayesNode);
                Pathway = PathUtils.getShortestPath(gameMap, restrictedNodes, player, closestChest, false);
                hero.move(Pathway);
            }
        }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void getArmor(Hero hero, List<Node> restrictedNodes, List<Node> otherPlayesNode, Player player) {

        try {
            GameMap gameMap = hero.getGameMap();


            List<Armor> ArmorsList = gameMap.getListArmors();

            if (!ArmorsList.isEmpty()) {
                Armor targetArmor = ArmorsList.getFirst();


                for (Armor armor : ArmorsList) {
                    if (InSafePlaceChecker.checkArmor(armor, gameMap) &&
                            DistanceCalculator.calc(armor, player) < DistanceCalculator.calc(targetArmor, player))
                        targetArmor = armor;
                }

                if (player.getX() == targetArmor.getX() && player.getY() == targetArmor.getY()) {
                    if (hero.getInventory().getListArmor().size() < 2){
                        hero.pickupItem();
                    } else {
                        return;
                    }

                } else {
                    String _t = PathUtils.getShortestPath(gameMap, restrictedNodes, player, targetArmor, false);
                    if (_t != null &&
                            _t.length() <= step &&
                            (hero.getInventory().getListArmor().isEmpty() ||
                                    !hero.getInventory().getListArmor().isEmpty() && hero.getInventory().getListArmor().size() < 2)) {
                        restrictedNodes.addAll(otherPlayesNode);
                        hero.move(_t);
                    } else {
                        return;
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void getHealing(Hero hero, List<Node> restrictedNodes, List<Node> otherPlayesNode, Player player) {

        try {
            GameMap gameMap = hero.getGameMap();


            List<HealingItem> HealingList = gameMap.getListHealingItems();
            if (!HealingList.isEmpty()) {
               HealingItem targetHealing = HealingList.getFirst();


                for (HealingItem healing : HealingList) {
                    if (InSafePlaceChecker.checkHealing(healing, gameMap) &&
                            DistanceCalculator.calc(healing, player) < DistanceCalculator.calc(targetHealing, player))
                        targetHealing = healing;
                }

                if (player.getX() == targetHealing.getX() && player.getY() == targetHealing.getY()) {
                    if (hero.getInventory().getListHealingItem().size() < 4) {
                        hero.pickupItem();
                    } else {
                        return;
                    }

                } else {
                    String _t = PathUtils.getShortestPath(gameMap, restrictedNodes, player, targetHealing, false);
                    if (_t != null &&
                            (hero.getInventory().getListHealingItem().isEmpty() ||
                                    (!hero.getInventory().getListHealingItem().isEmpty() &&
                                            hero.getInventory().getListHealingItem().size() < 4)) &&
                            _t.length() <= step) {
                        restrictedNodes.addAll(otherPlayesNode);
                        hero.move(_t);
                    } else {
                        return;
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
