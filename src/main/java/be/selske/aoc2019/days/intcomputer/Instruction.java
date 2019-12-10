package be.selske.aoc2019.days.intcomputer;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static be.selske.aoc2019.days.intcomputer.Instruction.ParameterAccessor.parameterAccessor;
import static java.lang.Math.pow;
import static java.util.Arrays.asList;

interface Instruction extends Consumer<IntComputer> {

    class ParameterAccessor {

        private final Function<IntComputer, Long> getter;
        private final BiConsumer<IntComputer, Long> setter;

        private ParameterAccessor(Function<IntComputer, Long> getter, BiConsumer<IntComputer, Long> setter) {
            this.getter = getter;
            this.setter = setter;
        }

        long get(IntComputer intComputer) {
            return getter.apply(intComputer);
        }

        void set(IntComputer intComputer, long value) {
            setter.accept(intComputer, value);
        }

        static ParameterAccessor parameterAccessor(int instructionValue, long index) {
            BiFunction<IntComputer, Long, Long> parameterMode = getParameterMode(instructionValue, index);

            return new ParameterAccessor(
                    (intComputer) -> intComputer.getMemoryValue(parameterMode.apply(intComputer, index)),
                    (intComputer, val) -> intComputer.setMemoryValue(parameterMode.apply(intComputer, index), val)
            );
        }

        static BiFunction<IntComputer, Long, Long> getParameterMode(int instructionValue, long index) {
            final BiFunction<IntComputer, Long, Long> firstParameterMode;
            firstParameterMode = switch ((int) ((instructionValue / pow(10, index + 1)) % 10)) {
                case 0 -> (intComputer, i) -> intComputer.getMemoryValue(intComputer.getPointer() + i);
                case 1 -> (intComputer, i) -> intComputer.getPointer() + i;
                case 2 -> (intComputer, i) -> intComputer.getRelativeBase() + intComputer.getMemoryValue(intComputer.getPointer() + i);
                default -> throw new IllegalArgumentException();
            };
            return firstParameterMode;
        }

    }

    default void runOn(IntComputer intComputer) {
        accept(intComputer);
    }

    static Instruction fromValue(int instructionValue) {

        List<ParameterAccessor> parameters = asList(
                parameterAccessor(instructionValue, 1L),
                parameterAccessor(instructionValue, 2L),
                parameterAccessor(instructionValue, 3L)
        );

        return switch (instructionValue % 100) {
            case 1 -> intComputer -> {
                Long firstParam = parameters.get(0).get(intComputer);
                Long secondParam = parameters.get(1).get(intComputer);

                parameters.get(2).set(intComputer, firstParam + secondParam);
                intComputer.advancePointer(4);
            };
            case 2 -> intComputer -> {
                Long firstParam = parameters.get(0).get(intComputer);
                Long secondParam = parameters.get(1).get(intComputer);
                parameters.get(2).set(intComputer, firstParam * secondParam);
                intComputer.advancePointer(4);
            };
            case 3 -> intComputer -> {
                intComputer.takeInput()
                        .ifPresentOrElse(input -> {
                                    parameters.get(0).set(intComputer, input);
                                    intComputer.advancePointer(2);
                                },
                                intComputer::waitForInput
                        );
            };
            case 4 -> intComputer -> {
                intComputer.output(parameters.get(0).get(intComputer));
                intComputer.advancePointer(2);
            };
            case 5 -> intComputer -> {
                long firstParam = parameters.get(0).get(intComputer);
                if (firstParam != 0) {
                    intComputer.setPointer(parameters.get(1).get(intComputer));
                } else {
                    intComputer.advancePointer(3);
                }
            };
            case 6 -> intComputer -> {
                long firstParam = parameters.get(0).get(intComputer);
                if (firstParam == 0) {
                    intComputer.setPointer(parameters.get(1).get(intComputer));
                } else {
                    intComputer.advancePointer(3);
                }
            };
            case 7 -> intComputer -> {
                long firstParam = parameters.get(0).get(intComputer);
                long secondParam = parameters.get(1).get(intComputer);
                parameters.get(2).set(intComputer, firstParam < secondParam ? 1L : 0L);
                intComputer.advancePointer(4);
            };
            case 8 -> intComputer -> {
                long firstParam = parameters.get(0).get(intComputer);
                long secondParam = parameters.get(1).get(intComputer);
                parameters.get(2).set(intComputer, firstParam == secondParam ? 1L : 0L);
                intComputer.advancePointer(4);
            };
            case 9 -> intComputer -> {
                long firstParam = parameters.get(0).get(intComputer);
                intComputer.advanceRelativeBase(firstParam);
                intComputer.advancePointer(2);
            };
            case 99 -> IntComputer::terminate;
            default -> throw new IllegalArgumentException();
        };
    }

}
