package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.days.intcomputer.IntComputer;

import java.util.Arrays;
import java.util.stream.Stream;

import static be.selske.aoc2019.days.intcomputer.IntComputer.parseInput;

public class Day2 extends AocDay {

    public static void main(String[] args) {
        new Day2().solve(() -> Day2.class.getResource("/inputs/day2.txt"));
    }

    private Day2() {
        super(Day2::part1, Day2::part2);
    }

    private static String part1(Stream<String> input) {
        return runProgram(parseInput(input.findFirst().orElseThrow()), 12, 2);
    }

    private static String part2(Stream<String> input) {
        long[] inputs = parseInput(input.findFirst().orElseThrow());
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if ("19690720".equals(runProgram(inputs, i, j))) {
                    return i * 100 + j + "";
                }
            }
        }
        return null;
    }

    private static String runProgram(long[] inputs, int noun, int verb) {
        long[] memory = Arrays.copyOf(inputs, inputs.length);
        memory[1] = noun;
        memory[2] = verb;

        IntComputer intComputer = new IntComputer(memory);
        intComputer.run();
        return intComputer.getMemoryValue(0L) + "";
    }

}
