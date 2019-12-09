package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.days.intcomputer.IntComputer;

import java.util.stream.Stream;

import static be.selske.aoc2019.days.intcomputer.IntComputer.parseInput;

public class Day5 extends AocDay {

    public static void main(String[] args) {
        new Day5().solve(() -> Day5.class.getResource("/inputs/day5.txt"));
    }

    private Day5() {
        super(
                (input) -> runProgram(input, 1),
                (input) -> runProgram(input, 5)
        );
    }

    private static String runProgram(Stream<String> input, long inputValue) {
        long[] memory = parseInput(input.findFirst().orElseThrow());
        IntComputer intComputer = new IntComputer(memory);

        intComputer.run(inputValue);

        return intComputer.getOutput() + "";
    }

}
