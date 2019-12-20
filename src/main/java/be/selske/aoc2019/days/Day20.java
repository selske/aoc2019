package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;
import be.selske.aoc2019.Direction;

import java.util.*;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class Day20 extends AocDay {

    public static void main(String[] args) {
        new Day20().solve(() -> Day20.class.getResource("/inputs/day20.txt"));
    }

    private Day20() {
        super(
                Day20::part1,
                Day20::part2
        );
    }

    private static String part1(Stream<String> input) {
        Maze maze = parseInput(input);

        List<Integer> distances = getDistances(maze);

        return distances.stream().mapToInt(Integer::intValue).min().orElseThrow() + "";
    }

    private static List<Integer> getDistances(Maze maze) {
        return getDistances(maze, maze.start, 0, new HashSet<>());
    }

    private static List<Integer> getDistances(Maze maze, Coordinate currentPosition, int currentDistance, Set<Coordinate> visited) {
        if (currentPosition.equals(maze.end)) {
            return List.of(currentDistance);
        } else if (visited.contains(currentPosition)) {
            return Collections.emptyList();
        }

        visited.add(currentPosition);

        Coordinate portalTarget = maze.portals.get(currentPosition);
        if (portalTarget != null && !visited.contains(portalTarget)) {
            return getDistances(maze, portalTarget, currentDistance + 1, new HashSet<>(visited));
        }

        return Stream.of(Direction.values())
                .parallel()
                .map(d -> d.move(currentPosition))
                .filter(maze.coordinates::contains)
                .map(c -> getDistances(maze, c, currentDistance + 1, new HashSet<>(visited)))
                .flatMap(Collection::parallelStream)
                .collect(toList());
    }

    private static String part2(Stream<String> input) {
        Maze maze = parseInput(input);

        List<Integer> distances = getDistancesRecursive(maze);

        return distances.stream()
                .mapToInt(Integer::intValue)
                .min().orElseThrow() + "";
    }

    private static List<Integer> getDistancesRecursive(Maze maze) {
        return getDistancesRecursive(maze, 0, maze.start, 0, new HashMap<>());
    }

    private static List<Integer> getDistancesRecursive(Maze maze, int currentLevel, Coordinate currentPosition, int currentDistance, Map<Integer, Set<Coordinate>> visited) {
        if (currentPosition.equals(maze.end) && currentLevel == 0) {
            return List.of(currentDistance);
        } else if (visited.computeIfAbsent(currentLevel, i -> new HashSet<>()).contains(currentPosition)) {
            return Collections.emptyList();
        }
        if (currentLevel > 25) {
            return Collections.emptyList();
        }

        visited.get(currentLevel).add(currentPosition);

        Coordinate portalTarget = maze.portals.get(currentPosition);
        if (portalTarget != null && !visited.computeIfAbsent(currentLevel + maze.getLevelDelta(currentPosition), i -> new HashSet<>()).contains(portalTarget)) {
            int levelDelta = maze.getLevelDelta(currentPosition);
            if (currentLevel == 0 && levelDelta == -1) {
                return Collections.emptyList();
            } else {
                return getDistancesRecursive(maze, currentLevel + levelDelta, portalTarget, currentDistance + 1, new HashMap<>(visited));
            }
        }

        return Stream.of(Direction.values())
                .map(d -> d.move(currentPosition))
                .filter(maze.coordinates::contains)
                .map(c -> getDistancesRecursive(maze, currentLevel, c, currentDistance + 1, new HashMap<>(visited)))
                .flatMap(Collection::parallelStream)
                .collect(toList());
    }

    private static Maze parseInput(Stream<String> inputStream) {
        List<String> input = inputStream.collect(toList());
        Set<Coordinate> coordinates = new HashSet<>();

        Map<Coordinate, Character> labelParts = new HashMap<>();
        Map<String, List<Coordinate>> labels = new HashMap<>();
        for (int row = 0; row < input.size(); row++) {
            String line = input.get(row);
            for (int col = 0; col < line.toCharArray().length; col++) {
                char c = line.toCharArray()[col];
                Coordinate coordinate = new Coordinate(col, row);
                if (c == '.') {
                    coordinates.add(coordinate);
                } else if ('A' <= c && c <= 'Z') {
                    Character labelPart = labelParts.get(coordinate.getDown());
                    if (labelPart == null) {
                        labelPart = labelParts.get(coordinate.getLeft());
                    }
                    if (labelPart != null) {
                        String label = "" + labelPart + c;
                        labels.computeIfAbsent(label, k -> new ArrayList<>()).add(coordinate);
                    }

                    labelParts.put(coordinate, c);
                }
            }
        }

        labels.forEach((l, cs) -> {
            List<Coordinate> fixedCoordinates = cs.stream()
                    .map(c -> {
                        if (coordinates.contains(Direction.UP.move(c))) {
                            return Direction.UP.move(c);
                        }
                        if (coordinates.contains(Direction.LEFT.move(Direction.LEFT.move(c)))) {
                            return Direction.LEFT.move(Direction.LEFT.move(c));
                        }
                        if (coordinates.contains(Direction.RIGHT.move(c))) {
                            return Direction.RIGHT.move(c);
                        }
                        if (coordinates.contains(Direction.DOWN.move(Direction.DOWN.move(c)))) {
                            return Direction.DOWN.move(Direction.DOWN.move(c));
                        }
                        return c;
                    })
                    .collect(toList());
            cs.clear();
            cs.addAll(fixedCoordinates);
        });

        Map<Coordinate, Coordinate> portals = new HashMap<>();
        labels.entrySet().stream()
                .filter(not(e -> e.getKey().equals("AA")))
                .filter(not(e -> e.getKey().equals("ZZ")))
                .map(Map.Entry::getValue)
                .forEach(c -> {
                    portals.put(c.get(0), c.get(1));
                    portals.put(c.get(1), c.get(0));
                });

        Coordinate start = labels.get("AA").get(0);
        Coordinate end = labels.get("ZZ").get(0);

        return new Maze(start, end, coordinates, portals);
    }

    private static class Maze {

        private final Coordinate start;
        private final Coordinate end;
        private final Set<Coordinate> coordinates;
        private final Map<Coordinate, Coordinate> portals;
        private final int minCol, minRow, maxCol, maxRow;

        public Maze(Coordinate start, Coordinate end, Set<Coordinate> coordinates, Map<Coordinate, Coordinate> portals) {
            this.start = start;
            this.end = end;
            this.coordinates = coordinates;
            this.portals = portals;

            int minR = Integer.MAX_VALUE;
            int maxR = Integer.MIN_VALUE;
            int minC = Integer.MAX_VALUE;
            int maxC = Integer.MIN_VALUE;

            for (Coordinate coordinate : coordinates) {
                if (coordinate.getX() < minC) minC = coordinate.getX();
                if (coordinate.getX() > maxC) maxC = coordinate.getX();
                if (coordinate.getY() < minR) minR = coordinate.getY();
                if (coordinate.getY() > maxR) maxR = coordinate.getY();
            }
            minCol = minC;
            maxCol = maxC;
            minRow = minR;
            maxRow = maxR;
        }

        public int getLevelDelta(Coordinate portal) {
            if (!portals.containsKey(portal)) {
                return 0;
            }
            if (minRow == portal.getY() || maxRow == portal.getY() || minCol == portal.getX() || maxCol == portal.getX()) {
                return -1;
            } else {
                return 1;
            }
        }

    }

}
