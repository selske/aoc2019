package be.selske.aoc2019.days;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.toList;

public final class IntComputer {

    private final List<Long> memory;
    public Long input;
    private long pointer;
    private int relativeBase;
    private long output;
    private boolean running = true;
    private boolean waitingForInput = false;

    public IntComputer(long[] memory) {
        this.memory = Arrays.stream(memory).boxed().collect(toList());
        this.pointer = 0;
        this.relativeBase = 0;
    }

    void run(long input) {
        this.input = input;
        this.setWaitingForInput(false);
        while (isRunning() && !isWaitingForInput()) {
            int value = toIntExact(getMemoryValue(pointer));

            Instruction.fromValue(value).accept(this);
        }
    }

    public void setPointer(long position) {
        this.pointer = position;
    }

    public void advancePointer(long amount) {
        this.pointer += amount;
    }

    private void advanceRelativeBase(long amount) {
        this.relativeBase += amount;
    }

    @Override
    public String toString() {
        return "Program{" +
                ", input=" + input +
                ", pointer=" + pointer +
                ", output=" + output +
                ", running=" + isRunning() +
                '}';
    }

    public long getOutput() {
        return output;
    }

    public void output(long output) {
        this.output = output;
    }

    public Long takeInput() {
        Long input = this.input;
        this.input = null;
        return input;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isWaitingForInput() {
        return waitingForInput;
    }

    public void setWaitingForInput(boolean waitingForInput) {
        this.waitingForInput = waitingForInput;
    }

    public void setMemoryValue(long index, long value) {
        while (memory.size() <= index) {
            memory.add(0L);
        }
        memory.set(toIntExact(index), value);
    }

    public long getMemoryValue(long index) {
        while (memory.size() <= index) {
            memory.add(0L);
        }
        return memory.get(toIntExact(index));
    }

    private interface ParameterMode extends BiFunction<IntComputer, Long, Long> {

        default Long getParameter(IntComputer intComputer, int pointerOffset) {
            return apply(intComputer, (long) pointerOffset);
        }

    }

    private interface Instruction extends Consumer<IntComputer> {

        static Instruction fromValue(int instructionValue) {
            int value = instructionValue % 100;
            ParameterMode positionMode = (intComputer, i) -> intComputer.getMemoryValue(intComputer.pointer + i);
            ParameterMode immediateMode = (intComputer, i) -> intComputer.pointer + i;
            ParameterMode relativeMode = (intComputer, i) -> intComputer.relativeBase + intComputer.getMemoryValue(intComputer.pointer + i);

            final Function<IntComputer, Long> firstParameterGetter;
            final ParameterMode firstParameterMode;
            int firstParamMode = (instructionValue / 100) % 10;
            if (firstParamMode == 0) {
                firstParameterMode = positionMode;
                firstParameterGetter = (intComputer) -> intComputer.getMemoryValue(firstParameterMode.apply(intComputer, 1L));
            } else if (firstParamMode == 1) {
                firstParameterMode = immediateMode;
                firstParameterGetter = (intComputer) -> intComputer.getMemoryValue(firstParameterMode.apply(intComputer, 1L));
            } else if (firstParamMode == 2) {
                firstParameterMode = relativeMode;
                firstParameterGetter = (intComputer) -> intComputer.getMemoryValue(firstParameterMode.apply(intComputer, 1L));
            } else {
                throw new IllegalArgumentException();
            }
            final Function<IntComputer, Long> secondParameterGetter;
            final ParameterMode secondParameterMode;
            int secondParamMode = (instructionValue / 1_000) % 10;
            if (secondParamMode == 0) {
                secondParameterMode = positionMode;
                secondParameterGetter = (intComputer) -> intComputer.getMemoryValue(secondParameterMode.apply(intComputer, 2L));
            } else if (secondParamMode == 1) {
                secondParameterMode = immediateMode;
                secondParameterGetter = (intComputer) -> intComputer.getMemoryValue(secondParameterMode.apply(intComputer, 2L));
            } else if (secondParamMode == 2) {
                secondParameterMode = relativeMode;
                secondParameterGetter = (intComputer) -> intComputer.getMemoryValue(secondParameterMode.apply(intComputer, 2L));
            } else {
                throw new IllegalArgumentException();
            }
            final Function<IntComputer, Long> thirdParameterGetter;
            final ParameterMode thirdParameterMode;
            int thirdParamMode = (instructionValue / 10_000) % 10;
            if (thirdParamMode == 0) {
                thirdParameterMode = positionMode;
                thirdParameterGetter = (intComputer) -> intComputer.getMemoryValue(thirdParameterMode.apply(intComputer, 3L));
            } else if (thirdParamMode == 1) {
                thirdParameterMode = immediateMode;
                thirdParameterGetter = (intComputer) -> intComputer.getMemoryValue(thirdParameterMode.apply(intComputer, 3L));
            } else if (thirdParamMode == 2) {
                thirdParameterMode = relativeMode;
                thirdParameterGetter = (intComputer) -> intComputer.getMemoryValue(thirdParameterMode.apply(intComputer, 3L));
            } else {
                throw new IllegalArgumentException();
            }

            return switch (value) {
                case 1 -> intComputer -> {
                    Long firstParam = firstParameterGetter.apply(intComputer);
                    Long secondParam = secondParameterGetter.apply(intComputer);
                    long thirdParameter = thirdParameterMode.getParameter(intComputer, 3);
                    intComputer.setMemoryValue(thirdParameter, firstParam + secondParam);
                    intComputer.advancePointer(4);
                };
                case 2 -> intComputer -> {
                    Long firstParam = firstParameterGetter.apply(intComputer);
                    Long secondParam = secondParameterGetter.apply(intComputer);
                    long thirdParameter = thirdParameterMode.getParameter(intComputer, 3);
                    intComputer.setMemoryValue(thirdParameter, firstParam * secondParam);
                    intComputer.advancePointer(4);
                };
                case 3 -> intComputer -> {
                    Long input = intComputer.takeInput();
                    if (input != null) {
                        Long firstParam = firstParameterMode.getParameter(intComputer, 1);
                        intComputer.setMemoryValue(firstParam, input);
                        intComputer.advancePointer(2);
                    } else {
                        intComputer.setWaitingForInput(true);
                    }
                };
                case 4 -> intComputer -> {
                    intComputer.output(firstParameterGetter.apply(intComputer));
                    intComputer.advancePointer(2);
                };
                case 5 -> intComputer -> {
                    long firstParam = firstParameterGetter.apply(intComputer);
                    if (firstParam != 0) {
                        intComputer.setPointer(secondParameterGetter.apply(intComputer));
                    } else {
                        intComputer.advancePointer(3);
                    }
                };
                case 6 -> intComputer -> {
                    long firstParam = firstParameterGetter.apply(intComputer);
                    if (firstParam == 0) {
                        intComputer.setPointer(secondParameterGetter.apply(intComputer).intValue());
                    } else {
                        intComputer.advancePointer(3);
                    }
                };
                case 7 -> intComputer -> {
                    long firstParam = firstParameterGetter.apply(intComputer);
                    long secondParam = secondParameterGetter.apply(intComputer);
                    long thirdParameter = thirdParameterMode.getParameter(intComputer, 3);
                    intComputer.setMemoryValue(thirdParameter, firstParam < secondParam ? 1 : 0);
                    intComputer.advancePointer(4);
                };
                case 8 -> intComputer -> {
                    long firstParam = firstParameterGetter.apply(intComputer);
                    long secondParam = secondParameterGetter.apply(intComputer);
                    long thirdParameter = thirdParameterMode.getParameter(intComputer, 3);
                    intComputer.setMemoryValue(thirdParameter, firstParam == secondParam ? 1 : 0);
                    intComputer.advancePointer(4);
                };
                case 9 -> intComputer -> {
                    long firstParam = firstParameterGetter.apply(intComputer);
                    intComputer.advanceRelativeBase(firstParam);
                    intComputer.advancePointer(2);
                };
                case 99 -> intComputer -> intComputer.setRunning(false);
                default -> throw new IllegalArgumentException();
            };
        }

    }

}
