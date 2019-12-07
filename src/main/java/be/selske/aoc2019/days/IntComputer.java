package be.selske.aoc2019.days;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class IntComputer {

    private final int[] memory;
    public Integer input;
    private int pointer;
    private int output;
    private boolean running = true;
    private boolean waitingForInput = false;

    public IntComputer(int[] memory) {
        this.memory = memory;
        this.pointer = 0;
    }

    void run(int input) {
        this.input = input;
        this.setWaitingForInput(false);
        while (isRunning() && !isWaitingForInput()) {
            int value = memory[pointer];

            Instruction.fromValue(value).accept(this);
        }
    }

    public void setPointer(int position) {
        this.pointer = position;
    }

    public void advancePointer(int amount) {
        this.pointer += amount;
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

    public int getOutput() {
        return output;
    }

    public void output(int output) {
        this.output = output;
    }

    public Integer getInput() {
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

    private interface Instruction extends Consumer<IntComputer> {

        static Instruction fromValue(int instructionValue) {

            int value = instructionValue % 100;
            BiFunction<IntComputer, Integer, Integer> positionMode = (intComputer, i) -> intComputer.memory[intComputer.pointer + i];
            BiFunction<IntComputer, Integer, Integer> immediateMode = (intComputer, i) -> intComputer.pointer + i;

            final Function<IntComputer,Integer> firstParameterGetter;
            if ((instructionValue / 100) % 10 == 0) {
                firstParameterGetter = (intComputer) -> intComputer.memory[positionMode.apply(intComputer, 1)];
            } else {
                firstParameterGetter = (intComputer) -> intComputer.memory[immediateMode.apply(intComputer, 1)];
            }
            final Function<IntComputer, Integer> secondParameterGetter;
            if ((instructionValue / 1000) % 10 == 0) {
                secondParameterGetter = (intComputer) -> intComputer.memory[positionMode.apply(intComputer, 2)];
            } else {
                secondParameterGetter = (intComputer) -> intComputer.memory[immediateMode.apply(intComputer, 2)];
            }

            return switch (value) {
                case 1 -> intComputer -> {
                    intComputer.memory[intComputer.memory[intComputer.pointer + 3]] = firstParameterGetter.apply(intComputer) + secondParameterGetter.apply(intComputer);
                    intComputer.advancePointer(4);
                };
                case 2 -> intComputer -> {
                    intComputer.memory[intComputer.memory[intComputer.pointer + 3]] = firstParameterGetter.apply(intComputer) * secondParameterGetter.apply(intComputer);
                    intComputer.advancePointer(4);
                };
                case 3 -> intComputer -> {
                    if (intComputer.getInput() != null) {
                        intComputer.memory[intComputer.memory[intComputer.pointer + 1]] = intComputer.getInput();
                        intComputer.input = null;
                        intComputer.pointer += 2;
                    } else {
                        intComputer.setWaitingForInput(true);
                    }
                };
                case 4 -> intComputer -> {
                    intComputer.output(firstParameterGetter.apply(intComputer));
                    intComputer.advancePointer(2);
                };
                case 5 -> intComputer -> {
                    int firstParam = firstParameterGetter.apply(intComputer);
                    if (firstParam != 0) {
                        intComputer.setPointer(secondParameterGetter.apply(intComputer));
                    } else {
                        intComputer.advancePointer(3);
                    }
                };
                case 6 -> intComputer -> {
                    int firstParam = firstParameterGetter.apply(intComputer);
                    if (firstParam == 0) {
                        intComputer.setPointer(secondParameterGetter.apply(intComputer));
                    } else {
                        intComputer.advancePointer(3);
                    }
                };
                case 7 -> intComputer -> {
                    int firstParam = firstParameterGetter.apply(intComputer);
                    int secondParam = secondParameterGetter.apply(intComputer);
                    intComputer.memory[intComputer.memory[intComputer.pointer + 3]] = firstParam < secondParam ? 1 : 0;
                    intComputer.advancePointer(4);
                };
                case 8 -> intComputer -> {
                    int firstParam = firstParameterGetter.apply(intComputer);
                    int secondParam = secondParameterGetter.apply(intComputer);
                    intComputer.memory[intComputer.memory[intComputer.pointer + 3]] = firstParam == secondParam ? 1 : 0;
                    intComputer.advancePointer(4);
                };
                case 99 -> intComputer -> intComputer.setRunning(false);
                default -> throw new IllegalArgumentException();
            };

        }

    }

}
