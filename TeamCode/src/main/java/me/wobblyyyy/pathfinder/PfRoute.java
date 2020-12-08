package me.wobblyyyy.pathfinder;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;

import java.util.ArrayList;

/**
 * Manage routes for the pathfinder.
 *
 * @author Colin Robertson
 */
public class PfRoute {
    public ArrayList<Coordinate<Double>> targets = new ArrayList<>();

    public void add(ArrayList<Coordinate<Double>> toAdd) {
        targets.addAll(toAdd);
    }

    public void remove() {
        targets.remove(0);
    }

    public void clear() {
        targets = new ArrayList<>();
    }
}
