package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.lang.StrictMath.atan2;
import static java.util.Comparator.*;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Day10 extends AocDay {

    public static void main(String[] args) {
        new Day10().solve(() -> Day10.class.getResource("/inputs/day10.txt"));
    }

    private Day10() {
        super(
                Day10::part1,
                Day10::part2
        );
    }

    private static String part1(Stream<String> input) {
        Collection<Coordinate> coordinates = parseInput(input);
        return getBase(coordinates).getTargets() + "";
    }

    private static Asteroid getBase(Collection<Coordinate> coordinates) {
        return coordinates.parallelStream()
                .map(c1 -> {
                    long targets = coordinates.parallelStream()
                            .filter(not(c1::equals))
                            .filter(c2 -> coordinates.parallelStream()
                                    .filter(not(c1::equals))
                                    .filter(not(c2::equals))
                                    .noneMatch(c3 -> c3.between(c1, c2)))
                            .count();
                    return new Asteroid(c1, targets);
                })
                .max(comparingLong(Asteroid::getTargets))
                .orElseThrow();
    }

    private static final class Asteroid {

        private final Coordinate coordinate;
        private final long targets;

        private Asteroid(Coordinate coordinate, long targets) {
            this.coordinate = coordinate;
            this.targets = targets;
        }

        Coordinate getCoordinate() {
            return coordinate;
        }

        long getTargets() {
            return targets;
        }

    }

    private static String part2(Stream<String> input) {
        Collection<Coordinate> coordinates = parseInput(input);
        Coordinate base = getBase(coordinates).getCoordinate();

        List<List<Coordinate>> collect = coordinates.parallelStream()
                .filter(not(base::equals))
                .collect(groupingBy(c -> getAngle(c, base)))
                .entrySet()
                .stream()
                .sorted((comparing(e -> e.getKey() * -1)))
                .map((e) -> e.getValue().stream().sorted(comparingDouble(base::distance)).collect(toList()))
                .collect(toList());

        int destroyCount = 0;
        while (destroyCount < 200) {
            for (int i = 0; i < collect.size() && destroyCount < 200; i++) {
                if (collect.get(i).size() > 0) {
                    Coordinate destroyed = collect.get(i).remove(0);
                    destroyCount++;
                    if (destroyCount == 200) {
                        return destroyed.getX() * 100 + destroyed.getY() + "";
                    }
                }
            }
        }

        return null;
    }

    private static double getAngle(Coordinate c, Coordinate base) {
        // flip axes and reverse direction later. TODO: flip 90°
        return atan2(c.getX() - base.getX(), c.getY() - base.getY());
    }

    private static Collection<Coordinate> parseInput(Stream<String> input) {
        List<Coordinate> coordinates = new ArrayList<>();
        List<String> inputList = input.collect(toList());
        for (int row = 0; row < inputList.size(); row++) {
            char[] chars = inputList.get(row).toCharArray();
            for (int col = 0; col < chars.length; col++) {
                if (chars[col] == '#') {
                    Coordinate coordinate = new Coordinate(col, row);
                    coordinates.add(coordinate);
                }
            }
        }
        return coordinates;
    }

}
