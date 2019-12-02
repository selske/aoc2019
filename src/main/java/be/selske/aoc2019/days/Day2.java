package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Day2 extends AocDay {

    public static void main(String[] args) {
        new Day2().solve(() -> Day2.class.getResourceAsStream("/inputs/day2.txt"));
    }

    private Day2() {
        super(Day2::part1, Day2::part2);
    }

    private static String part1(InputStream input) {
        return runProgram(parseInput(input), 12, 2);
    }

    private static String part2(InputStream input) {
        List<Integer> inputs = parseInput(input);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if ("19690720".equals(runProgram(inputs, i, j))) {
                    return i * 100 + j + "";
                }
            }
        }
        return null;
    }

    private static String runProgram(List<Integer> inputs, int noun, int verb) {
        List<Integer> memory = new ArrayList<>(inputs);
        memory.set(1, noun);
        memory.set(2, verb);

        program:
        for (int i = 0; i < memory.size(); i += 4) {
            int value = memory.get(i);
            switch (value) {
                case 1:
                    memory.set(memory.get(i + 3), memory.get(memory.get(i + 1)) + memory.get(memory.get(i + 2)));
                    break;
                case 2:
                    memory.set(memory.get(i + 3), memory.get(memory.get(i + 1)) * memory.get(memory.get(i + 2)));
                    break;
                case 99:
                    break program;
            }
        }

        return memory.get(0) + "";
    }

    private static List<Integer> parseInput(InputStream input) {
        try {
            return Stream.of(new BufferedReader(new InputStreamReader(input)).readLine().split(","))
                    .map(Integer::valueOf)
                    .collect(toList());
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

}
