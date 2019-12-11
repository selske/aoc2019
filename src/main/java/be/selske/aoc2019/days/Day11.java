package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;
import be.selske.aoc2019.Direction;
import be.selske.aoc2019.days.intcomputer.IntComputer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day11 extends AocDay {

    public static void main(String[] args) {
        new Day11().solve(() -> Day11.class.getResource("/inputs/day11.txt"));
    }

    private Day11() {
        super(
                Day11::part1,
                Day11::part2
        );
    }

    private static String part1(Stream<String> input) {
        Map<Coordinate, Long> colors = calculateColors(input, 0L);

        return colors.size() + "";
    }

    private static String part2(Stream<String> input) {
        Map<Coordinate, Long> colors = calculateColors(input, 1L);

        int left = Integer.MAX_VALUE;
        int top = Integer.MIN_VALUE;
        int right = Integer.MIN_VALUE;
        int bottom = Integer.MAX_VALUE;
        for (Coordinate coordinate : colors.keySet()) {
            if (coordinate.getX() < left) left = coordinate.getX();
            if (coordinate.getX() > right) right = coordinate.getX();
            if (coordinate.getY() > top) top = coordinate.getY();
            if (coordinate.getY() < bottom) bottom = coordinate.getY();
        }
        for (int row = top; row >= bottom; row--) {
            for (int col = left; col <= right; col++) {
                Long color = colors.getOrDefault(new Coordinate(col, row), 0L);
                System.out.print(color == 0 ? " " : "X");
            }
            System.out.println();
        }
        // TODO: dotletter implementation
        return null;
    }

    private static Map<Coordinate, Long> calculateColors(Stream<String> input, long initialSquareColor) {
        IntComputer intComputer = new IntComputer(IntComputer.parseInput(input.findFirst().orElseThrow()));
        Coordinate location = new Coordinate(0, 0);
        Direction direction = Direction.UP;
        Map<Coordinate, Long> colors = new HashMap<>();
        colors.put(location, initialSquareColor);
        while (intComputer.isRunning()) {
            intComputer.run(colors.getOrDefault(location, 0L));
            List<Long> instructions = intComputer.getOutput();
            direction = switch (instructions.get(1).intValue()) {
                case 0 -> direction.turnLeft();
                case 1 -> direction.turnRight();
                default -> throw new IllegalArgumentException();
            };
            Long color = instructions.get(0);
            colors.put(location, color);
            intComputer.clearOutput();

            location = direction.move(location);
        }
        return colors;
    }

}
