package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day2 extends AocDay {

    public static void main(String[] args) {
        new Day2().solve(() -> Day2.class.getResource("/inputs/day2.txt"));
    }

    private Day2() {
        super(Day2::part1, Day2::part1, Day2::part2, Day2::part2);
    }

    private static String part1(Stream<String> input) {
        return runProgram(parseInput(input), 12, 2);
    }

    private static String part2(Stream<String> input) {
        int[] inputs = parseInput(input);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if ("19690720".equals(runProgram(inputs, i, j))) {
                    return i * 100 + j + "";
                }
            }
        }
        return null;
    }

    private static String runProgram(int[] inputs, int noun, int verb) {
        int[] memory = Arrays.copyOf(inputs, inputs.length);
        memory[1] = noun;
        memory[2] = verb;

        program:
        for (int i = 0; i < memory.length; i += 4) {
            int value = memory[i];
            switch (value) {
                case 1:
                    memory[memory[i + 3]] = memory[memory[i + 1]] + memory[memory[i + 2]];
                    break;
                case 2:
                    memory[memory[i + 3]] = memory[memory[i + 1]] * memory[memory[i + 2]];
                    break;
                case 99:
                    break program;
            }
        }

        return memory[0] + "";
    }

    private static int[] parseInput(Stream<String> input) {
        return Stream.of(input.findFirst().orElseThrow().split(","))
                .mapToInt(Integer::valueOf)
                .toArray();
    }

}
