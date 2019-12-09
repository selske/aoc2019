package be.selske.aoc2019.days.intcomputer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.toList;

public final class IntComputer {

    private final List<Long> memory;
    private Long input;
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

    public long getOutput() {
        return output;
    }

    void output(long output) {
        this.output = output;
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

}
