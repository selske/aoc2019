package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;
import be.selske.aoc2019.Direction;
import be.selske.aoc2019.days.intcomputer.IntComputer;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.joining;

public class Day17 extends AocDay {

    public static void main(String[] args) {
        new Day17().solve(() -> Day17.class.getResource("/inputs/day17.txt"));
    }

    private Day17() {
        super(
                Day17::part1,
                Day17::part2
        );
    }

    private static String part1(Stream<String> input) {
        IntComputer intComputer = new IntComputer(IntComputer.parseInput(input.findFirst().orElseThrow()));
        intComputer.run();

        Scaffold scaffold = Scaffold.fromValues(intComputer.getOutput());

        return scaffold.scaffold.parallelStream()
                .filter(spot -> scaffold.contains(spot.getUp()) && scaffold.contains(spot.getDown()) && scaffold.contains(spot.getLeft()) && scaffold.contains(spot.getRight()))
                .mapToInt(spot -> abs(spot.getX() * spot.getY()))
                .sum() + "";
    }

    private static final class Scaffold {

        private final Set<Coordinate> scaffold;
        private Coordinate position;
        private Direction direction;

        private Scaffold(Set<Coordinate> scaffold, Coordinate position, Direction direction) {
            this.scaffold = scaffold;
            this.position = position;
            this.direction = direction;
        }

        public boolean contains(Coordinate coordinate) {
            return scaffold.contains(coordinate);
        }

        public static Scaffold fromValues(List<Long> values) {
            int col = 0;
            int row = 0;
            Set<Coordinate> scaffold = new HashSet<>();
            Coordinate position = null;
            Direction direction = null;
            for (Long o : values) {
                char c = (char) o.intValue();
                if (c == '#') {
                    scaffold.add(new Coordinate(col, row));
                }
                switch (c) {
                    case 'v' -> {
                        position = new Coordinate(col, row);
                        direction = Direction.DOWN;
                    }
                    case '^' -> {
                        position = new Coordinate(col, row);
                        direction = Direction.UP;
                    }
                    case '<' -> {
                        position = new Coordinate(col, row);
                        direction = Direction.LEFT;
                    }
                    case '>' -> {
                        position = new Coordinate(col, row);
                        direction = Direction.RIGHT;
                    }
                }
                if (c == '\n') {
                    col = 0;
                    row--;
                } else {
                    col++;
                }
            }
            return new Scaffold(scaffold, position, direction);
        }

    }

    private static String part2(Stream<String> input) {
        long[] memory = IntComputer.parseInput(input.findFirst().orElseThrow());
        memory[0] = 2;

        IntComputer intComputer = new IntComputer(memory);
        intComputer.run();

        Scaffold scaffold = Scaffold.fromValues(intComputer.getOutput());

        String[] path = getPath(scaffold);

        List<Group> groups = findGroups(path);

        String function = IntStream.range(0, path.length)
                .mapToObj(i -> groups.stream().filter(g -> g.startIndices.contains(i)).findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Group::getGroupName)
                .collect(joining(",")) + "\n";

        input(intComputer, function);
        groups.forEach(group -> input(intComputer, group.value));
        input(intComputer, "n\n");

        return intComputer.getLastOutput() + "";
    }

    private static List<Group> findGroups(String[] path) {
        for (int a = 1; ; a++) {
            String[] aPath = Arrays.copyOf(path, path.length);
            Group aGroup = findGroup(aPath, a, "A");
            if (aGroup.value.length() > 20) {
                throw new IllegalArgumentException();
            }
            for (int b = 1; ; b++) {
                String[] bPath = Arrays.copyOf(aPath, aPath.length);
                Group bGroup = findGroup(bPath, b, "B");
                if (bGroup.value.length() > 20) {
                    break;
                }

                for (int c = 1; ; c++) {
                    String[] cPath = Arrays.copyOf(bPath, bPath.length);
                    Group cGroup = findGroup(cPath, c, "C");
                    if (cGroup.value.length() > 20) {
                        break;
                    }
                    if (Arrays.stream(cPath).allMatch(Objects::isNull)) {
                        return List.of(aGroup, bGroup, cGroup);
                    }
                }
            }
        }
    }

    private static String[] getPath(Scaffold scaffold) {
        StringBuilder sb = new StringBuilder();
        int movesForward = 0;
        while (true) {
            if (scaffold.contains(scaffold.direction.move(scaffold.position))) {
                movesForward++;
            } else if (scaffold.contains(scaffold.direction.turnLeft().move(scaffold.position))) {
                if (movesForward > 0) {
                    sb.append(movesForward).append(',');
                }
                sb.append("L");
                movesForward = 1;
                scaffold.direction = scaffold.direction.turnLeft();
            } else if (scaffold.contains(scaffold.direction.turnRight().move(scaffold.position))) {
                if (movesForward > 0) {
                    sb.append(movesForward).append(',');
                }
                sb.append("R");
                movesForward = 1;
                scaffold.direction = scaffold.direction.turnRight();
            } else {
                break;
            }
            scaffold.position = scaffold.direction.move(scaffold.position);
        }
        sb.append(movesForward);

        return sb.toString().split(",");
    }

    private static final class Group {

        private final String groupName;
        private final String value;
        private final List<Integer> startIndices = new ArrayList<>();

        private Group(String groupName, List<String> group) {
            this.groupName = groupName;
            this.value = group.stream().map(g -> g.charAt(0) + "," + g.substring(1)).collect(joining(",")) + "\n";
        }

        public String getGroupName() {
            return groupName;
        }

    }

    private static Group findGroup(String[] path, int groupSize, String groupName) {
        List<String> groupList = new ArrayList<>(groupSize);
        int startIndex = -1;
        for (int i = 0; groupList.size() < groupSize; i++) {
            if (path[i] != null) {
                if (startIndex < 0) {
                    startIndex = i;
                }
                groupList.add(path[i]);
                path[i] = null;
            }
        }

        Group group = new Group(groupName, groupList);
        group.startIndices.add(startIndex);
        int matchStart = -1;
        int matchIndex = 0;
        for (int i = startIndex; i < path.length; i++) {
            if (path[i] == null) {
                matchStart = -1;
                matchIndex = 0;
                continue;
            }

            if (matchStart < 0) {
                if (groupList.get(matchIndex).equals(path[i])) {
                    matchStart = i;
                    matchIndex++;
                }
            } else {
                if (groupList.get(matchIndex).equals(path[matchStart + matchIndex])) {
                    matchIndex++;
                } else if (groupList.get(matchIndex).equals(path[i])) {
                    matchStart = i;
                    matchIndex = 1;
                } else {
                    matchStart = -1;
                    matchIndex = 0;
                }
            }
            if (matchIndex == groupSize) {
                group.startIndices.add(matchStart);
                for (int j = matchStart; j < matchStart + matchIndex; j++) {
                    path[j] = null;
                }
                matchIndex = 0;
                matchStart = -1;
            }
        }
        return group;
    }

    private static void input(IntComputer intComputer, String input) {
        input.chars().forEach(c -> intComputer.run((long) c));
    }

}

