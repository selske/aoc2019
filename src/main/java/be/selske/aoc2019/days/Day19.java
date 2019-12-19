package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;
import be.selske.aoc2019.days.intcomputer.IntComputer;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class Day19 extends AocDay {

    public static void main(String[] args) {
        new Day19().solve(() -> Day19.class.getResource("/inputs/day19.txt"));
    }

    private Day19() {
        super(
                Day19::part1,
                Day19::part2
        );
    }

    private static String part1(Stream<String> input) {
        long[] memory = IntComputer.parseInput(input.findFirst().orElseThrow());
        return getBeam(memory, 50).size() + "";
    }

    private static String part2(Stream<String> input) {
        long[] memory = IntComputer.parseInput(input.findFirst().orElseThrow());
        Set<Coordinate> beam = getBeam(memory, 100);

        Coordinate topCoordinate = beam.stream()
                .filter(coordinate -> coordinate.getX() == 50)
                .min(Comparator.comparingInt(Coordinate::getY))
                .orElseThrow();
        double topGradient = topCoordinate.getX() / (double) topCoordinate.getY();
        Coordinate bottomCoordinate = beam.stream()
                .filter(coordinate -> coordinate.getY() == 50)
                .min(Comparator.comparingInt(Coordinate::getX))
                .orElseThrow();
        double bottomGradient = bottomCoordinate.getX() / (double) bottomCoordinate.getY();
        double averageGradient = (topGradient + bottomGradient) / .2;
        double floorAvgGradient = floor(averageGradient) / 10;
        double ceilAvgGradient = ceil(averageGradient) / 10;

        int startY = (int) (100 / (topGradient - bottomGradient));

        for (int y = startY; ; y++) {
            for (int x = (int) (floorAvgGradient * y); x < ceilAvgGradient * y; x++) {
                if (isAttracted(memory, x + 99, y)) {
                    continue;
                }
                if (isAttracted(memory, x, y + 99)) {
                    continue;
                }
                return x * 10_000 + y + "";
            }
        }
    }

    private static boolean isAttracted(long[] memory, long x, long y) {
        IntComputer intComputer = new IntComputer(memory);
        intComputer.run(x);
        intComputer.run(y);
        return intComputer.getLastOutput() == 0;
    }

    private static Set<Coordinate> getBeam(long[] memory, int size) {
        Set<Coordinate> beam = new HashSet<>();
        int startFrom = 0;
        for (int x = 0; x < size; x++) {
            boolean dontBreakYet = true;
            for (int y = startFrom; y < size; y++) {
                IntComputer intComputer = new IntComputer(memory);
                intComputer.run((long) x);
                intComputer.run((long) y);
                boolean attracted = intComputer.getLastOutput() == 1;
                if (attracted) {
                    dontBreakYet = false;
                    beam.add(new Coordinate(x, y));
                } else if (!dontBreakYet) {
                    break;
                } else {
                    startFrom = x;
                }
            }
        }
        return beam;
    }

}

