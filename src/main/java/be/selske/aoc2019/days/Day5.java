package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day5 extends AocDay {

    public static void main(String[] args) {
        new Day5().solve(() -> Day5.class.getResource("/inputs/day5.txt"));
    }

    private Day5() {
        super(
                Day5::part1,
                Day5::part2
        );
    }

    private static String part1(Stream<String> input) {
        return runProgram(1, parseInput(input));
    }

    private static String part2(Stream<String> input) {
        return runProgram(5, parseInput(input));
    }

    private static String runProgram(int inputValue, long[] inputs) {
        IntComputer intComputer = new IntComputer(Arrays.copyOf(inputs, inputs.length));

        intComputer.run(inputValue);

        return intComputer.getOutput() + "";
    }

    private static long[] parseInput(Stream<String> input) {
        return Stream.of(input.findFirst().orElseThrow().split(","))
                .mapToLong(Long::valueOf)
                .toArray();
    }

}
