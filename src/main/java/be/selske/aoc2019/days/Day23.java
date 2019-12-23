package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;
import be.selske.aoc2019.days.intcomputer.IntComputer;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Day23 extends AocDay {

    public static void main(String[] args) {
        new Day23().solve(() -> Day23.class.getResource("/inputs/day23.txt"));
    }

    private Day23() {
        super(
                Day23::part1,
                Day23::part2
        );
    }

    private static String part1(Stream<String> input) {
        Map<Long, IntComputer> computers = initializeComputers(input);

        while (true) {
            for (Map.Entry<Long, IntComputer> entry : computers.entrySet()) {
                IntComputer computer = entry.getValue();
                computer.run(-1L);
                List<Long> output = computer.getOutput();
                for (int i = 0; i < output.size(); i += 3) {
                    Long address = output.get(i);
                    if (address == 255) {
                        return output.get(i + 2) + "";
                    }
                    IntComputer target = computers.get(address);
                    target.run(output.get(i + 1));
                    target.run(output.get(i + 2));
                }
                computer.clearOutput();
            }
        }
    }

    private static String part2(Stream<String> input) {
        Map<Long, IntComputer> computers = initializeComputers(input);

        Coordinate natValue = null;
        Set<Integer> values = new HashSet<>();
        while (true) {
            boolean idle = true;
            for (Map.Entry<Long, IntComputer> entry : computers.entrySet()) {
                IntComputer computer = entry.getValue();
                computer.run(-1L);
                List<Long> output = computer.getOutput();
                for (int i = 0; i < output.size(); i += 3) {
                    idle = false;
                    Long address = output.get(i);
                    if (address == 255) {
                        natValue = new Coordinate(output.get(i + 1).intValue(), output.get(i + 2).intValue());
                    } else {
                        IntComputer target = computers.get(address);
                        target.run(output.get(i + 1));
                        target.run(output.get(i + 2));
                    }
                }
                computer.clearOutput();
            }
            if (idle) {
                if (natValue == null) {
                    throw new IllegalStateException();
                }
                IntComputer computerZero = computers.get(0L);
                computerZero.run((long) natValue.getX());
                computerZero.run((long) natValue.getY());
                if (!values.add(natValue.getY())) {
                    return natValue.getY() + "";
                }
            }
        }
    }

    private static Map<Long, IntComputer> initializeComputers(Stream<String> input) {
        long[] memory = IntComputer.parseInput(input.findFirst().orElseThrow());

        return LongStream.range(0, 50)
                .boxed()
                .collect(toMap(Function.identity(), i -> {
                    IntComputer intComputer = new IntComputer(memory);
                    intComputer.run(i);
                    return intComputer;
                }));
    }

}
