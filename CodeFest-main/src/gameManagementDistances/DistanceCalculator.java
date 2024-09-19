package gameManagementDistances;

import jsclub.codefest2024.sdk.model.Element;

public class DistanceCalculator {
    public static int calc(Element e1, Element e2) {
        return (int) (Math.pow(e1.getX() - e2.getX(), 2) + Math.pow(e1.getY() - e2.getY(), 2));
    }
}
