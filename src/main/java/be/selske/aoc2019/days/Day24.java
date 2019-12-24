package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;
import be.selske.aoc2019.Direction;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Day24 extends AocDay {

    public static void main(String[] args) {
        new Day24().solve(() -> Day24.class.getResource("/inputs/day24.txt"));
    }

    private Day24() {
        super(
                Day24::part1,
                Day24::part2
        );
    }

    private static String part1(Stream<String> input) {
        Map<Coordinate, Character> planet = parseInput(input);

        Set<Map<Coordinate, Character>> previousStates = new HashSet<>();

        while (!previousStates.contains(planet)) {
            previousStates.add(planet);
            planet = cycle(planet);
        }

        return calculateBiodiversityRating(planet) + "";
    }

    private static Map<Coordinate, Character> cycle(Map<Coordinate, Character> planet) {
        return planet.entrySet().parallelStream()
                .collect(toMap(
                        Entry::getKey,
                        e -> getNewCharacter(e.getValue(), countLivingNeighbours(planet, e.getKey()))
                ));
    }

    private static long countLivingNeighbours(Map<Coordinate, Character> planet, Coordinate coordinate) {
        return Stream.of(Direction.values())
                .map(direction -> direction.move(coordinate))
                .map(planet::get)
                .filter(Objects::nonNull)
                .filter(c -> c == '#')
                .count();
    }

    private static long calculateBiodiversityRating(Map<Coordinate, Character> planet) {
        return planet.entrySet().stream()
                .filter(e -> e.getValue() == '#')
                .mapToInt(e -> (int) Math.pow(2, e.getKey().getX() + e.getKey().getY() * 5))
                .sum();
    }

    private static String part2(Stream<String> input) {
        Map<Coordinate, Character> planet = parseInput(input);

        Map<Integer, Map<Coordinate, Character>> levels = new HashMap<>();
        levels.put(0, planet);

        for (int i = 0; i < 200; i++) {
            levels = cycleRecursive(levels);
        }

        return countBugs(levels) + "";
    }

    private static Map<Integer, Map<Coordinate, Character>> cycleRecursive(Map<Integer, Map<Coordinate, Character>> levels) {
        IntSummaryStatistics summary = levels.keySet().stream().mapToInt(Integer::intValue).summaryStatistics();
        if (levels.get(summary.getMin()).values().stream().anyMatch(c -> c != '.')) {
            levels.put(summary.getMin() - 1, newLevel());
        }
        if (levels.get(summary.getMax()).values().stream().anyMatch(c -> c != '.')) {
            levels.put(summary.getMax() + 1, newLevel());
        }
        return levels.entrySet().parallelStream()
                .collect(toMap(
                        Entry::getKey,
                        levelEntry -> levelEntry.getValue().entrySet().parallelStream()
                                .collect(toMap(
                                        Entry::getKey,
                                        planetEntry -> getNewCharacter(planetEntry.getValue(), countLivingNeighbours(levels, levelEntry.getKey(), planetEntry.getKey()))))));
    }

    private static Map<Coordinate, Character> newLevel() {
        Map<Coordinate, Character> newLevel = new HashMap<>();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                newLevel.put(new Coordinate(col, row), '.');
            }
        }
        return newLevel;
    }

    private static char getNewCharacter(Character character, long livingNeighbours) {
        char newCharacter = character;
        if (character == '.') {
            if (livingNeighbours == 1 || livingNeighbours == 2) {
                newCharacter = '#';
            }
        } else if (character == '#') {
            if (livingNeighbours != 1) {
                newCharacter = '.';
            }
        }
        return newCharacter;
    }

    private static long countLivingNeighbours(Map<Integer, Map<Coordinate, Character>> levels, int level, Coordinate coordinate) {
        List<LevelCoordinate> neighbours = getNeighbours(coordinate, level);

        return neighbours.stream()
                .map(lc -> levels.getOrDefault(lc.level, emptyMap()).getOrDefault(lc.coordinate, '.'))
                .filter(v -> v == '#')
                .count();
    }

    private static List<LevelCoordinate> getNeighbours(Coordinate coordinate, int level) {
        if (new Coordinate(2, 2).equals(coordinate)) {
            return emptyList();
        }
        List<LevelCoordinate> neighbours = Stream.of(Direction.values())
                .map(direction -> direction.move(coordinate))
                .map(c -> new LevelCoordinate(level, c))
                .collect(toList());

        if (coordinate.getX() == 0) {
            neighbours.add(new LevelCoordinate(level - 1, new Coordinate(1, 2)));
        }
        if (coordinate.getY() == 2) {
            if (coordinate.getX() == 1) {
                for (int i = 0; i < 5; i++) {
                    neighbours.add(new LevelCoordinate(level + 1, new Coordinate(0, i)));
                }
            }
            if (coordinate.getX() == 3) {
                for (int i = 0; i < 5; i++) {
                    neighbours.add(new LevelCoordinate(level + 1, new Coordinate(4, i)));
                }
            }
        }
        if (coordinate.getX() == 4) {
            neighbours.add(new LevelCoordinate(level - 1, new Coordinate(3, 2)));
        }

        if (coordinate.getY() == 0) {
            neighbours.add(new LevelCoordinate(level - 1, new Coordinate(2, 1)));
        }
        if (coordinate.getX() == 2) {
            if (coordinate.getY() == 1) {
                for (int i = 0; i < 5; i++) {
                    neighbours.add(new LevelCoordinate(level + 1, new Coordinate(i, 0)));
                }
            }
            if (coordinate.getY() == 3) {
                for (int i = 0; i < 5; i++) {
                    neighbours.add(new LevelCoordinate(level + 1, new Coordinate(i, 4)));
                }
            }
        }
        if (coordinate.getY() == 4) {
            neighbours.add(new LevelCoordinate(level - 1, new Coordinate(2, 3)));
        }

        return neighbours;
    }

    private static class LevelCoordinate {

        private final int level;
        private final Coordinate coordinate;

        private LevelCoordinate(int level, Coordinate coordinate) {
            this.level = level;
            this.coordinate = coordinate;
        }

    }

    private static long countBugs(Map<Integer, Map<Coordinate, Character>> levels) {
        return levels.values().stream()
                .flatMap(planet -> planet.values().stream())
                .filter(v -> v == '#')
                .count();
    }

    private static Map<Coordinate, Character> parseInput(Stream<String> inputStream) {
        Map<Coordinate, Character> planet = new HashMap<>();
        List<String> input = inputStream.collect(toList());
        for (int row = 0; row < input.size(); row++) {
            String line = input.get(row);
            for (int col = 0; col < line.length(); col++) {
                planet.put(new Coordinate(col, row), line.charAt(col));
            }
        }
        return planet;
    }

}
