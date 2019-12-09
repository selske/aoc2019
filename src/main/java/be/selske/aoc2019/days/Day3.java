package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Day3 extends AocDay {

    public static void main(String[] args) {
        new Day3().solve(() -> Day3.class.getResource("/inputs/day3.txt"));
    }

    public Day3() {
        super(Day3::part1, Day3::part2);
    }

    private static class PathPart {

        private final Function<Coordinate, Coordinate> coordinateTransformation;
        private final int amount;

        private PathPart(String value) {
            coordinateTransformation = switch (value.charAt(0)) {
                case 'R' -> Coordinate::getRight;
                case 'L' -> Coordinate::getLeft;
                case 'U' -> Coordinate::getUp;
                case 'D' -> Coordinate::getDown;
                default -> throw new IllegalArgumentException();
            };
            amount = Integer.parseInt(value.substring(1));
        }

    }

    private static final Coordinate CENTRAL_PORT = new Coordinate(0, 0);

    private static String part1(Stream<String> input) {
        List<List<Coordinate>> coordinates = mapCoordinates(input);

        HashSet<Coordinate> coordinates1 = new HashSet<>(coordinates.get(1));
        return coordinates.get(0).stream()
                .filter(coordinates1::contains)
                .mapToLong(CENTRAL_PORT::distance)
                .min()
                .orElseThrow() + "";
    }

    private static String part2(Stream<String> input) {
        List<List<Coordinate>> coordinates = mapCoordinates(input);

        HashSet<Coordinate> coordinates1 = new HashSet<>(coordinates.get(1));

        return coordinates.get(0).stream()
                .filter(coordinates1::contains)
                .mapToInt(c -> coordinates.get(0).indexOf(c) + 1 + coordinates.get(1).indexOf(c) + 1)
                .min()
                .orElseThrow() + "";
    }

    private static List<List<Coordinate>> mapCoordinates(Stream<String> input) {
        return input
                .map(line -> line.split(","))
                .map(path -> Arrays.stream(path)
                        .map(PathPart::new)
                        .collect(toList()))
                .map(Day3::pathToCoordinates)
                .collect(toList());
    }

    private static List<Coordinate> pathToCoordinates(List<PathPart> path) {
        List<Coordinate> coordinates = new ArrayList<>();
        Coordinate currentPosition = CENTRAL_PORT;
        for (PathPart p : path) {
            for (int i = 0; i < p.amount; i++) {
                Coordinate newPosition = p.coordinateTransformation.apply(currentPosition);
                coordinates.add(newPosition);
                currentPosition = newPosition;
            }
        }
        return coordinates;
    }

}
