package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Day4 extends AocDay {

    public static void main(String[] args) {
        new Day4().solve(() -> Day4.class.getResource("/inputs/day4.txt"));
    }

    private Day4() {
        super(
                input -> solve(input, Integer.MAX_VALUE),
                input -> solve(input, 2)
        );
    }

    private static String solve(Stream<String> input, int maxDoubles) {
        String[] inputs = input.findFirst().orElseThrow().split("-");

        return IntStream.rangeClosed(parseInt(inputs[0]), parseInt(inputs[1]))
                .parallel()
                .boxed()
                .map(Object::toString)
                .filter((pwd) -> isValidPassword(pwd, maxDoubles))
                .count() + "";
    }

    private static boolean isValidPassword(String pwd, int max) {
        boolean hasDoubleDigits = false;
        int doubleDigitCount = 1;
        char[] chars = pwd.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] > chars[i + 1]) {
                return false;
            } else if (chars[i] == chars[i + 1]) {
                doubleDigitCount++;
            } else {
                if (doubleDigitCount >= 2 && doubleDigitCount <= max) {
                    hasDoubleDigits = true;
                }
                doubleDigitCount = 1;
            }
        }
        if (doubleDigitCount >= 2 && doubleDigitCount <= max) {
            hasDoubleDigits = true;
        }
        return hasDoubleDigits;
    }

}
