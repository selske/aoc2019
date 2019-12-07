package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static be.selske.aoc2019.days.Day7.Permutator.permute;
import static be.selske.aoc2019.util.MathUtil.factorial;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

public class Day7 extends AocDay {

    public static void main(String[] args) {
        new Day7().solve(() -> Day7.class.getResource("/inputs/day7.txt"));
    }

    public Day7() {
        super(
                (input) -> solve(input, 0, 4),
                (input) -> solve(input, 5, 9)
        );
    }

    private static String solve(Stream<String> input, int from, int to) {
        int[] initialMemory = parseInput(input);

        List<Integer> outputSignals = new ArrayList<>(factorial(to - from + 1));
        permute(rangeClosed(from, to).toArray(), (phaseSettings) -> {
            outputSignals.add(getOutput(initialMemory, phaseSettings));
        });
        return outputSignals.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow() + "";
    }

    static class Permutator {

        public static void permute(int[] arr, Consumer<int[]> consumer) {
            permute(arr, 0, consumer);
        }

        private static void permute(int[] arr, int k, Consumer<int[]> consumer) {
            for (int i = k; i < arr.length; i++) {
                swap(arr, i, k);
                permute(arr, k + 1, consumer);
                swap(arr, k, i);
            }
            if (k == arr.length - 1) {
                consumer.accept(arr);
            }
        }

        private static void swap(int[] arr, int a, int b) {
            int tmp = arr[a];
            arr[a] = arr[b];
            arr[b] = tmp;
        }

    }

    private static int getOutput(int[] memory, int[] phaseSettings) {
        int output = 0;

        List<IntComputer> amplifiers = Arrays.stream(phaseSettings)
                .mapToObj(ps -> new IntComputer(Arrays.copyOf(memory, memory.length)))
                .collect(toList());

        boolean running = true;
        for (int i = 0; i < phaseSettings.length; i++) {
            int phaseSetting = phaseSettings[i];
            IntComputer intComputer = amplifiers.get(i);
            intComputer.run(phaseSetting);
            intComputer.run(output);
            output = intComputer.getOutput();
            if (!intComputer.isRunning()) {
                running = false;
            }
        }
        while (running) {
            for (int i = 0; i < phaseSettings.length; i++) {
                IntComputer intComputer = amplifiers.get(i);
                intComputer.run(output);
                output = intComputer.getOutput();
                if (!intComputer.isRunning()) {
                    running = false;
                }
            }
        }
        return output;
    }

    private static int[] parseInput(Stream<String> input) {
        return Stream.of(input.findFirst().orElseThrow().split(","))
                .mapToInt(Integer::valueOf)
                .toArray();
    }

}
