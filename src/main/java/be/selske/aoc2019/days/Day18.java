package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class Day18 extends AocDay {

    public static void main(String[] args) {
        new Day18().solve(() -> Day18.class.getResource("/inputs/day18.txt"));
    }

    private Day18() {
        super(
                Day18::part1,
                Day18::part2
        );
    }

    private static String part1(Stream<String> input) {
        Map<Coordinate, Character> map = parseInput(input);

        Set<Coordinate> visited = new HashSet<>();
        int distanceTravelled = 0;
        List<Coordinate> edges = List.of(map.entrySet().stream().filter(e -> e.getValue() == '@').findAny().map(Map.Entry::getKey).orElseThrow());
        while (!edges.isEmpty()) {
            List<Coordinate> newEdges = edges.stream()
                    .flatMap(coordinate -> Arrays.stream(Movement.values()).map(m -> m.newCoordinateGetter.apply(coordinate)))
                    .filter(not(visited::contains))
                    .filter(coordinate -> map.getOrDefault(coordinate, '#') != '#')
                    .collect(toList());
            visited.addAll(edges);
            edges = newEdges;
            distanceTravelled++;
            if (newEdges.stream().anyMatch(edge -> map.getOrDefault(edge, '.') != '.')) {
                System.out.println("edge after = " + distanceTravelled);
            }
        }

//        print(map, new HashSet<>(), distanceTravelled);
        return null;
    }

    private static String part2(Stream<String> input) {
        return null;
    }

    private static Map<Coordinate, Character> parseInput(Stream<String> input) {
        Map<Coordinate, Character> map = new HashMap<>();
        List<String> rows = input.collect(Collectors.toList());
        for (int r = 0; r < rows.size(); r++) {
            String row = rows.get(r);
            for (int c = 0; c < row.toCharArray().length; c++) {
                if ('#' == row.toCharArray()[c]) {
                    continue;
                }
                map.put(new Coordinate(c, -1 * r), row.toCharArray()[c]);
            }
        }
        return map;
    }

    @SuppressWarnings("unused")
    private enum Movement {
        NORTH(1, Coordinate::getUp),
        SOUTH(2, Coordinate::getDown),
        WEST(3, Coordinate::getLeft),
        EAST(4, Coordinate::getRight);

        private final long inputValue;
        private final Function<Coordinate, Coordinate> newCoordinateGetter;

        Movement(long inputValue, Function<Coordinate, Coordinate> newCoordinateGetter) {
            this.inputValue = inputValue;
            this.newCoordinateGetter = newCoordinateGetter;
        }
    }

    private static void print(Map<Coordinate, Character> map, Set<Coordinate> visited, int distanceTravelled) {
        Coordinate.Edges edges = Coordinate.getEdges(map.keySet());

        int left = edges.getLeft() - 1;
        int top = edges.getTop() + 1;
        int right = edges.getRight() + 1;
        int bottom = edges.getBottom() - 1;

        for (int row = top; row >= bottom; row--) {
            for (int col = left; col <= right; col++) {
                Character value = map.getOrDefault(new Coordinate(col, row), '#');
                if (visited.contains(new Coordinate(col, row))) {
                    value = ' ';
                }
                System.out.print(value);
            }
            System.out.println();
        }
        System.out.println("distanceTravelled = " + distanceTravelled);
        System.out.println();
    }

}

