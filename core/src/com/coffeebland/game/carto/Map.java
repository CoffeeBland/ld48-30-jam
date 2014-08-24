package com.coffeebland.game.carto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dagothig on 8/23/14.
 */
public class Map {
    public static Map getMap() {
        List<Street> streets = new ArrayList<Street>();

        for (int i = 0; i < 50; i++) {
            Street street = new Street(
                    ((float)Math.random() * 5000),
                    ((float)Math.random() * 5000),
                    ((float)Math.random() * 1250) + 500,
                    Math.random() < 0.5f ? true : false,
                    ((long)Math.random() * Long.MAX_VALUE)
            );

            streets.add(street);
        }

        Map map = new Map(streets);

        return map;
    }

    public Map(List<Street> streets) {
        this.streets = streets;
        connectStreets(streets);
    }

    private List<Street> streets;

    public List<Street> getStreets() {
        return streets;
    }

    public void connectStreets(Collection<Street> streets) {
        for (Street street : streets) {
            for (Street possibleConnection : streets) {
                if (street == possibleConnection) {
                    continue;
                }

                if (street.getStartX() < possibleConnection.getEndX() && street.getEndX() > possibleConnection.getStartX() &&
                        street.getStartY() < possibleConnection.getEndY() && street.getEndY() > possibleConnection.getStartY()) {
                    street.connectingStreets.add(possibleConnection);
                    possibleConnection.connectingStreets.add(street);
                }
            }
        }
    }
}
