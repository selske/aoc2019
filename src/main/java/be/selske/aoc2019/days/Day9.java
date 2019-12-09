package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.days.intcomputer.IntComputer;

import java.util.stream.Stream;

import static be.selske.aoc2019.days.intcomputer.IntComputer.parseInput;

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
        long[] memory = parseInput(input.findFirst().orElseThrow());
        IntComputer intComputer = new IntComputer(memory);
        intComputer.run(i);
        return intComputer.getOutput() + "";
    }

}
