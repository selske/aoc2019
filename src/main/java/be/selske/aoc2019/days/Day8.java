package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day8 extends AocDay {

    public static void main(String[] args) {
        new Day8().solve(() -> Day8.class.getResource("/inputs/day8.txt"));
    }

    public Day8() {
        super(Day8::part1, Day8::part2);
    }

    private static String part1(Stream<String> input) {
        char[] chars = input.findFirst().orElseThrow().toCharArray();

        int minZeroes = Integer.MAX_VALUE;
        int[] counts = new int[10];
        int[] winner = counts;
        int pixelsPerLayer = 25 * 6;
        for (int i = 0; i < chars.length; i += pixelsPerLayer) {
            for (int j = 0; j < pixelsPerLayer; j++) {
                char c = chars[i + j];
                counts[c - 48]++;
            }
            if (counts[0] < minZeroes) {
                minZeroes = counts[0];
                winner = counts;
            }
            counts = new int[10];
        }
        return winner[1] * winner[2] + "";
    }

    private static String part2(Stream<String> input) {
        char[] chars = input.findFirst().orElseThrow().toCharArray();

        int pixelsPerLayer = 25 * 6;
        int[] image = IntStream.generate(() -> 2).limit(pixelsPerLayer).toArray();

        for (int i = 0; i < chars.length; i += pixelsPerLayer) {
            for (int j = 0; j < pixelsPerLayer; j++) {
                char c = chars[i + j];
                if (image[j] == 2) {
                    image[j] = c - 48;
                }
            }
        }
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 25; col++) {
                if (image[row * 25 + col] == 1) {
                    System.out.print("X");
                } else if (image[row * 25 + col] == 0) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        return null;
    }

}
