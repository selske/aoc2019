package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSet;

public class Day6 extends AocDay {

    public static void main(String[] args) {
        new Day6().solve(() -> Day6.class.getResource("/inputs/day6.txt"));
    }

    public Day6() {
        super(Day6::part1, Day6::part2);
    }

    private static String part1(Stream<String> input) {
        return getOrbitMap(input).values().stream()
                .mapToInt(OrbitingObject::getOrbitAmount)
                .sum() + "";

    }

    private static String part2(Stream<String> input) {
        Map<String, OrbitingObject> orbitMap = getOrbitMap(input);
        OrbitingObject you = orbitMap.get("YOU");
        OrbitingObject santa = orbitMap.get("SAN");
        Collection<OrbitingObject> youCenters = new HashSet<>(you.getCenters());
        Collection<OrbitingObject> santaCenters = new HashSet<>(santa.getCenters());

        if (youCenters.contains(santa)) {
            return you.getOrbitAmount() - santa.getOrbitAmount() + "";
        } else if (santaCenters.contains(you)) {
            return santa.getOrbitAmount() - you.getOrbitAmount() + "";
        }

        youCenters.retainAll(santaCenters);
        OrbitingObject closestCommon = youCenters.stream()
                .max(Comparator.comparingInt(OrbitingObject::getOrbitAmount))
                .orElseThrow();

        return you.getOrbitAmount() - closestCommon.getOrbitAmount() - 1
                + santa.getOrbitAmount() - closestCommon.getOrbitAmount() - 1
                + "";
    }

    private static Map<String, OrbitingObject> getOrbitMap(Stream<String> input) {
        Map<String, OrbitingObject> orbitingObjects = new HashMap<>();
        input.forEach(orbit -> {
            OrbitingObject center = orbitingObjects.computeIfAbsent(orbit.split("\\)")[0], OrbitingObject::new);
            OrbitingObject orbiter = orbitingObjects.computeIfAbsent(orbit.split("\\)")[1], OrbitingObject::new);
            orbiter.setCenter(center);
        });
        return orbitingObjects;
    }

    private static final class OrbitingObject {

        private final String name;
        private OrbitingObject center;

        private OrbitingObject(String name) {
            this.name = name;
        }

        public void setCenter(OrbitingObject center) {
            this.center = center;
        }

        private int orbitAmount = -1;

        private int getOrbitAmount() {
            if (orbitAmount == -1) {
                if (center == null) {
                    orbitAmount = 0;
                } else if (orbitAmount > -1) {
                    return orbitAmount;
                } else {
                    orbitAmount = center.getOrbitAmount() + 1;
                }
            }
            return orbitAmount;
        }

        private Collection<OrbitingObject> getCenters() {
            if (center == null) {
                return Collections.emptySet();
            } else {
                var centers = new HashSet<>(center.getCenters());
                centers.add(center);
                return unmodifiableSet(centers);
            }
        }

        @Override
        public String toString() {
            return name + ((center != null) ? " orbits " + center : "");
        }

    }

}


