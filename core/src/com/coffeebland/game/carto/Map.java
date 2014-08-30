package com.coffeebland.game.carto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dagothig on 8/23/14.
 */
public class Map {
    public static Map getMap() {
        float origin = 89600f;
        float tileSize = 32f;
        float buildingSize = 7 * tileSize;
        float crossingSize = 5 * tileSize;
        List<Street> streets = new ArrayList<Street>();

        streets.add(new Street(
                origin - (buildingSize * 10) - (crossingSize * 2),
                origin,
                ((buildingSize * 10) + (crossingSize * 2)) * 2,
                false,
                (long) Math.random() * Long.MAX_VALUE
        ));
        streets.add(new Street(
                origin - (buildingSize * 5) - tileSize,
                origin - (buildingSize * 10) + (crossingSize),
                (buildingSize * 10) + (crossingSize),
                true,
                (long) Math.random() * Long.MAX_VALUE
        ));

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
