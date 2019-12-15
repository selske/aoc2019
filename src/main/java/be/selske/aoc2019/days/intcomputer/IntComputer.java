package be.selske.aoc2019.days.intcomputer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;
import static java.util.Collections.unmodifiableList;

public final class IntComputer {

    private long[] memory;
    private Long input;
    private long pointer;
    private int relativeBase;
    private List<Long> output = new ArrayList<>();
    private boolean running = true;
    private boolean waitingForInput = false;

    public IntComputer(long[] memory) {
        this.memory = Arrays.copyOf(memory, memory.length);
        this.pointer = 0;
        this.relativeBase = 0;
    }

    public static long[] parseInput(String input) {
        return Stream.of(input.split(","))
                .mapToLong(Long::valueOf)
                .toArray();
    }

    public void run() {
        run(null);
    }

    public void run(Long input) {
        this.input = input;
        this.waitingForInput = false;
        while (isRunning() && !isWaitingForInput()) {
            int value = toIntExact(getMemoryValue(pointer));

            Instruction.fromValue(value).runOn(this);
        }
    }

    long getPointer() {
        return pointer;
    }

    void setPointer(long position) {
        this.pointer = position;
    }

    long getRelativeBase() {
        return relativeBase;
    }

    void advancePointer(long amount) {
        this.pointer += amount;
    }

    void advanceRelativeBase(long amount) {
        this.relativeBase += amount;
    }

    public void clearOutput() {
        output.clear();
    }

    public List<Long> getOutput() {
        return unmodifiableList(output);
    }

    public long getLastOutput() {
        return output.get(output.size() - 1);
    }

    void output(long output) {
        this.output.add(output);
    }

    Optional<Long> takeInput() {
        Long input = this.input;
        this.input = null;
        return Optional.ofNullable(input);
    }

    boolean isWaitingForInput() {
        return waitingForInput;
    }

    void waitForInput() {
        this.waitingForInput = true;
    }

    public boolean isRunning() {
        return running;
    }

    void terminate() {
        this.running = false;
    }

    void setMemoryValue(long index, long value) {
        if (memory.length <= index) {
            memory = Arrays.copyOf(memory, toIntExact(index + 1));
        }
        memory[toIntExact(index)] = value;
    }

    public long getMemoryValue(long index) {
        if (memory.length <= index) {
            memory = Arrays.copyOf(memory, toIntExact(index + 1));
        }
        return memory[toIntExact(index)];
    }

    public IntComputer copy() {
        return new IntComputer(memory);
    }

}
