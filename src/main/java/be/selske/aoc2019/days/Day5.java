package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Day5 extends AocDay {

    public static void main(String[] args) {
        new Day5().solve(() -> Day5.class.getResource("/inputs/day5.txt"));
    }

    private Day5() {
        super(
                Day5::part1,
                Day5::part2
        );
    }

    private static String part1(Stream<String> input) {
        return runProgram(1, parseInput(input));
    }

    private static String part2(Stream<String> input) {
        return runProgram(5, parseInput(input));
    }

    private static String runProgram(int inputValue, int[] inputs) {
        int[] memory = Arrays.copyOf(inputs, inputs.length);

        Program program = new Program(memory, inputValue);

        while (program.running) {
            int value = program.memory[program.pointer];
            if (program.output != 0) {
                System.out.println("program = " + program);
                return null;
            }

            Instruction instruction = Instruction.fromValue(value);
            instruction.accept(program);
        }

        return program.output + "";
    }

    private static final class Program {

        private final int[] memory;
        public final int input;
        private int pointer;
        private int output;
        private boolean running = true;

        private Program(int[] memory, int input) {
            this.memory = memory;
            this.input = input;
            this.pointer = 0;
        }

        @Override
        public String toString() {
            return "Program{" +
                    "memory=" + Arrays.toString(memory) +
                    ", input=" + input +
                    ", pointer=" + pointer +
                    ", output=" + output +
                    ", running=" + running +
                    '}';
        }

    }

    private interface Instruction extends Consumer<Program> {

        static Instruction fromValue(int instructionValue) {

            int value = instructionValue % 100;
            BiFunction<int[], Integer, Integer> positionMode = (mem, i) -> mem[mem[i]];
            BiFunction<int[], Integer, Integer> immediateMode = (mem, i) -> mem[i];

            final BiFunction<int[], Integer, Integer> firstParameterGetter;
            if ((instructionValue / 100) % 10 == 0) {
                firstParameterGetter = positionMode;
            } else {
                firstParameterGetter = immediateMode;
            }
            final BiFunction<int[], Integer, Integer> secondParameterGetter;
            if ((instructionValue / 1000) % 10 == 0) {
                secondParameterGetter = positionMode;
            } else {
                secondParameterGetter = immediateMode;
            }
//            final BiFunction<int[], Integer, Integer> thirdParameterGetter;
//            if ((instructionValue / 10_000) % 10 == 0) {
//                thirdParameterGetter = positionMode;
//            } else {
//                thirdParameterGetter = immediateMode;
//            }
            return switch (value) {
                case 1 -> program -> {
                    program.memory[program.memory[program.pointer + 3]] = firstParameterGetter.apply(program.memory, program.pointer + 1) + secondParameterGetter.apply(program.memory, program.pointer + 2);
                    program.pointer += 4;
                };
                case 2 -> program -> {
                    program.memory[program.memory[program.pointer + 3]] = firstParameterGetter.apply(program.memory, program.pointer + 1) * secondParameterGetter.apply(program.memory, program.pointer + 2);
                    program.pointer += 4;
                };
                case 3 -> program -> {
                    program.memory[program.memory[program.pointer + 1]] = program.input;
                    program.pointer += 2;
                };
                case 4 -> program -> {
                    program.output = firstParameterGetter.apply(program.memory, program.pointer + 1);
                    program.pointer += 2;
                };
                case 5 -> program -> {
                    int firstParam = firstParameterGetter.apply(program.memory, program.pointer + 1);
                    if (firstParam != 0) {
                        program.pointer = secondParameterGetter.apply(program.memory, program.pointer + 2);
                    } else {
                        program.pointer += 3;
                    }
                };
                case 6 -> program -> {
                    int firstParam = firstParameterGetter.apply(program.memory, program.pointer + 1);
                    if (firstParam == 0) {
                        program.pointer = secondParameterGetter.apply(program.memory, program.pointer + 2);
                    } else {
                        program.pointer += 3;
                    }
                };
                case 7 -> program -> {
                    int firstParam = firstParameterGetter.apply(program.memory, program.pointer + 1);
                    int secondParam = secondParameterGetter.apply(program.memory, program.pointer + 2);
                    program.memory[program.memory[program.pointer + 3]] = firstParam < secondParam ? 1 : 0;
                    program.pointer += 4;
                };
                case 8 -> program -> {
                    int firstParam = firstParameterGetter.apply(program.memory, program.pointer + 1);
                    int secondParam = secondParameterGetter.apply(program.memory, program.pointer + 2);
                    program.memory[program.memory[program.pointer + 3]] = firstParam == secondParam ? 1 : 0;
                    program.pointer += 4;
                };
                case 99 -> program -> program.running = false;
                default -> throw new IllegalArgumentException();
            };

        }

    }

    private static int[] parseInput(Stream<String> input) {
        return Stream.of(input.findFirst().orElseThrow().split(","))
                .mapToInt(Integer::valueOf)
                .toArray();
    }

}
