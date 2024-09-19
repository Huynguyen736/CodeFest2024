package myClass;

import java.util.HashMap;
import java.util.Map;

public class Priority {
    private static Map priorityGun = new HashMap();
    private static Map priorityMelee = new HashMap();
    private static Map priorityThrowable = new HashMap();
    private static Map priorityHealingItems = new HashMap();
    private static Map priorityArmors = new HashMap();

    public Priority() {

        // Số điểm cuối vũ khí có được = số điểm base * (dung tích đạn ban đầu-số đạn đã bắn)
        // Điểm sẽ được thay đổi + hoặc - theo gấp 7 lần độ xa vũ khí
        priorityGun.put("WATER_GUN", 200);
        priorityGun.put("LEGO_GUN", 131);
        priorityGun.put("RUBBER_GUN", 81);
        priorityGun.put("BADMINTON", 81);

        // Điểm sẽ được thay đổi + hoặc - theo độ xa vũ khí
        priorityMelee.put("LIGHT_SABER", 100);
        priorityMelee.put("SANDAL", 85);
        priorityMelee.put("BROOM", 84);

        // 5 đồ ném có chỉ số như nhau
        priorityThrowable.put("PAPER_AIRPLANE", 1);
        priorityThrowable.put("BALL", 1);
        priorityThrowable.put("PAPER_DART", 1);
        priorityThrowable.put("TEDDY_BEAR", 1);
        priorityThrowable.put("WATER_BALL", 1);

        // Số điểm của đồ hồi máu phản ánh đồ hồi máu có thể hồi được bao nhiêu máu
        // Điểm sẽ được thay đổi + hoặc - theo gấp 2 lần độ xa vũ khí
        priorityHealingItems.put("LUNCH_BOX", 100);
        priorityHealingItems.put("BANDAGES", 50);
        priorityHealingItems.put("DRINK", 20);
        priorityHealingItems.put("INSECTICIDE", 15);
        priorityHealingItems.put("SNACK", 10);

        // Ưu tiên từ cao đến thấp
        priorityArmors.put("VEST", 3);
        priorityArmors.put("POT", 2);
        priorityArmors.put("HELMET", 1);
    }
    public static int getGunPriority(String gunName) {
        return (int) priorityGun.getOrDefault(gunName, -1);
    }
    public static int getMeleePriority(String meleeName) {
        return (int) priorityMelee.getOrDefault(meleeName, -1);
    }
    public static int getThrowableNamePriority(String throwableName) {
        return (int) priorityThrowable.getOrDefault(throwableName, -1);
    }
    public static int getHealingPriority(String healingName) {
        return (int) priorityHealingItems.getOrDefault(healingName, -1);
    }
    public static int getArmorsPriority(String armorName) {
        return (int) priorityArmors.getOrDefault(armorName, -1);
    }
}
