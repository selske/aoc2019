package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;
import be.selske.aoc2019.days.intcomputer.IntComputer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static be.selske.aoc2019.days.intcomputer.IntComputer.parseInput;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class Day15 extends AocDay {

    public static void main(String[] args) {
        new Day15().solve(() -> Day15.class.getResource("/inputs/day15.txt"));
    }

    private Day15() {
        super(
                Day15::part1,
                Day15::part2
        );
    }

    private static String part1(Stream<String> input) {
        long[] memory = parseInput(input.findFirst().orElseThrow());

        return mapShip(memory).paths.stream()
                .mapToInt(Collection::size)
                .min().orElseThrow() + "";
    }

    private static String part2(Stream<String> input) {
        long[] memory = parseInput(input.findFirst().orElseThrow());

        Ship ship = mapShip(memory);

        List<Coordinate> edges = List.of(ship.oxygenSystemLocation);
        Set<Coordinate> filledSpaces = new HashSet<>();
        int timer = -1;
        while (!edges.isEmpty()) {
            List<Coordinate> newEdges = edges.stream()
                    .flatMap(coordinate -> Arrays.stream(Movement.values()).map(m -> m.newCoordinateGetter.apply(coordinate)))
                    .filter(not(filledSpaces::contains))
                    .filter(coordinate -> ship.map.getOrDefault(coordinate, 0L) != 0L)
                    .collect(toList());
            filledSpaces.addAll(edges);
            edges = newEdges;
            timer++;
        }
        return timer + "";
    }

    private static final class Ship {

        private final Collection<Set<Coordinate>> paths = new ArrayList<>();
        private Coordinate oxygenSystemLocation;
        private Map<Coordinate, Long> map = new HashMap<>();

    }

    private static Ship mapShip(long[] memory) {
        Ship ship = new Ship();
        for (Movement movement : Movement.values()) {
            IntComputer intComputer = new IntComputer(memory);
            mapShip(intComputer, movement, new Coordinate(0, 0), new HashSet<>(), ship);
        }
        return ship;
    }

    private static void mapShip(IntComputer intComputer, Movement movement, final Coordinate currentPosition, Set<Coordinate> visitedCoordinates, Ship ship) {
        if (visitedCoordinates.contains(currentPosition)) {
            return;
        }
        intComputer.run(movement.inputValue);
        long output = intComputer.getLastOutput();
        Coordinate newPosition = movement.newCoordinateGetter.apply(currentPosition);
        ship.map.put(newPosition, output);
        visitedCoordinates.add(currentPosition);
        switch ((int) output) {
            case 0:
                return;
            case 1:
                for (Movement newMovement : Movement.values()) {
                    mapShip(intComputer.copy(), newMovement, newPosition, new HashSet<>(visitedCoordinates), ship);
                }
                return;
            case 2:
                ship.oxygenSystemLocation = newPosition;
                ship.paths.add(visitedCoordinates);
                return;
            default:
                throw new IllegalArgumentException();
        }
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

}
