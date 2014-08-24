package com.coffeebland.game.carto;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by dagothig on 8/23/14.
 */
public class Map {
    private Collection<Street> streets = new HashSet<Street>();

    public Map(Collection<Street> streets) {
        this.streets = streets;
        connectStreets(streets);
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
