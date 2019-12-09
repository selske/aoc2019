package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.stream.Stream;

public class Day9 extends AocDay {

    public static void main(String[] args) {
        new Day9().solve(() -> Day9.class.getResource("/inputs/day9.txt"));
    }

    public Day9() {
        super(
                (input) -> solve(input, 1),
                (input) -> solve(input, 2)
        );
    }

    private static String solve(Stream<String> input, long i) {
        long[] memory = parseInput(input);
        IntComputer intComputer = new IntComputer(memory);
        intComputer.run(i);
        return intComputer.getOutput() + "";
    }

    private static long[] parseInput(Stream<String> input) {
        return Stream.of(input.findFirst().orElseThrow().split(","))
                .mapToLong(Long::valueOf)
                .toArray();
    }

}
