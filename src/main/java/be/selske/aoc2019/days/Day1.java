package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class Day1 extends AocDay {

    public static void main(String[] args) {
        new Day1().solve(() -> Day1.class.getResource("/inputs/day1.txt"));
    }

    private Day1() {
        super(
                (input) -> solve(Day1::fuelNeeded, input),
                (input) -> solve(Day1::rocketEquation, input)
        );
    }

    private static String solve(ToIntFunction<Integer> fuelCalculator, Stream<String> input) {
        return input
                .map(Integer::parseInt)
                .mapToInt(fuelCalculator)
                .sum() + "";
    }

    private static int fuelNeeded(int payload) {
        return (payload / 3) - 2;
    }

    private static int rocketEquation(int payload) {
        int fuelNeeded = fuelNeeded(payload);
        if (fuelNeeded <= 0) {
            return 0;
        } else {
            return fuelNeeded + rocketEquation(fuelNeeded);
        }
    }

}
