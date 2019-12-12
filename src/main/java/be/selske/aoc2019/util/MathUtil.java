package be.selske.aoc2019.util;

import java.util.Arrays;

public class MathUtil {

    private MathUtil() {
    }

    public static int factorial(int i) {
        if (i == 1) {
            return 1;
        }
        return i * factorial(i - 1);
    }

    public static long lcm(int... input) {
        int[] values = Arrays.copyOf(input, input.length);
        long lcm = 1;
        int divisor = 2;

        while (true) {
            int oneCount = 0;
            boolean divisible = false;

            for (int i = 0; i < values.length; i++) {
                if (values[i] == 1) {
                    oneCount++;
                }

                if (values[i] % divisor == 0) {
                    divisible = true;
                    values[i] = values[i] / divisor;
                }
            }

            if (divisible) {
                lcm = lcm * divisor;
            } else {
                divisor++;
            }

            if (oneCount == values.length) {
                return lcm;
            }
        }
    }

}
