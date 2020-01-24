package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;
import be.selske.aoc2019.Coordinate.Edges;
import be.selske.aoc2019.Direction;
import be.selske.aoc2019.days.intcomputer.IntComputer;
import be.selske.aoc2019.util.DotLetter;

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

        Edges edges = Coordinate.getEdges(colors.keySet());

        int height = edges.getTop() - edges.getBottom() + 1;
        int width = edges.getRight() - edges.getLeft() + 1;
        int[][] image = new int[height][width];
        for (int row = height - 1; row >= 0; row--) {
            for (int col = 0; col < width; col++) {
                Long color = colors.getOrDefault(new Coordinate(col, edges.getBottom() + row), 0L);
                image[height - row - 1][col] = color.intValue();
            }
        }
        return DotLetter.getText(image);
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
