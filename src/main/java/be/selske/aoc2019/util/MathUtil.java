package be.selske.aoc2019.util;

public class MathUtil {

    private MathUtil() {
    }

    public static int factorial(int i) {
        if (i == 1) {
            return 1;
        }
        return i * factorial(i - 1);
    }

}
