package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.util.MathUtil;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

public class Day12 extends AocDay {

    public static void main(String[] args) {
        new Day12().solve(() -> Day12.class.getResource("/inputs/day12.txt"));
    }

    private Day12() {
        super(
                Day12::part1,
                Day12::part2
        );
    }

    private static String part1(Stream<String> input) {
        List<MoonState> moonStates = parseInput(input);
        int numberOfSteps = 1000;
        for (int step = 0; step < numberOfSteps; step++) {
            moonStates = step(moonStates);
        }

        return moonStates.stream()
                .mapToInt(MoonState::getEnergy)
                .sum() + "";
    }

    public static String part2(Stream<String> input) {
        List<MoonState> moonStates = parseInput(input);

        int[] loopSizes = Stream.<DimensionGetter>of(MoonState::getX, MoonState::getY, MoonState::getZ)
                .mapToInt(getter -> findCycle(moonStates, getter))
                .toArray();

        return MathUtil.lcm(loopSizes) + "";
    }

    private static int findCycle(List<MoonState> moonStates, Function<MoonState, SingleDimensionMoonState> getter) {
        Set<List<SingleDimensionMoonState>> visitedStates = new HashSet<>();
        visitedStates.add(moonStates.stream().map(getter).collect(toList()));

        for (int step = 1; ; step++) {
            moonStates = step(moonStates);
            List<SingleDimensionMoonState> singleDimensionMoonStates = moonStates.stream().map(getter).collect(toList());
            if (visitedStates.contains(singleDimensionMoonStates)) {
                return step;
            }
            visitedStates.add(singleDimensionMoonStates);
        }
    }

    private static class SingleDimensionMoonState {

        private final int position;
        private final int velocity;

        private SingleDimensionMoonState(int position, int velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SingleDimensionMoonState that = (SingleDimensionMoonState) o;
            return position == that.position &&
                    velocity == that.velocity;
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, velocity);
        }

        @Override
        public String toString() {
            return '<' + "pos=" + position + ", vel=" + velocity + '>';
        }

    }

    private static List<MoonState> step(List<MoonState> moonStates) {
        List<MoonState> newMoonStates = new ArrayList<>();

        for (MoonState moonState : moonStates) {
            int deltaX = 0;
            int deltaY = 0;
            int deltaZ = 0;
            for (MoonState otherMoonState : moonStates) {
                if (otherMoonState == moonState) {
                    continue;
                }
                if (moonState.position.x < otherMoonState.position.x) {
                    deltaX++;
                } else if (moonState.position.x > otherMoonState.position.x) {
                    deltaX--;
                }
                if (moonState.position.y < otherMoonState.position.y) {
                    deltaY++;
                } else if (moonState.position.y > otherMoonState.position.y) {
                    deltaY--;
                }
                if (moonState.position.z < otherMoonState.position.z) {
                    deltaZ++;
                } else if (moonState.position.z > otherMoonState.position.z) {
                    deltaZ--;
                }
            }

            Vector deltaV = new Vector(deltaX, deltaY, deltaZ);
            newMoonStates.add(moonState.apply(deltaV));
        }
        return newMoonStates;
    }

    private static final Pattern INPUT_PARSER = Pattern.compile("<x=(?<x>-?\\d*), y=(?<y>-?\\d*), z=(?<z>-?\\d*)>$");

    private static List<MoonState> parseInput(Stream<String> input) {
        return input
                .map(INPUT_PARSER::matcher)
                .filter(Matcher::matches)
                .map(matcher -> {
                    int x = parseInt(matcher.group("x"));
                    int y = parseInt(matcher.group("y"));
                    int z = parseInt(matcher.group("z"));

                    return new MoonState(new Vector(x, y, z), Vector.ZERO);
                })
                .collect(toList());
    }

    private static final class MoonState {

        private final Vector position;
        private final Vector velocity;

        private MoonState(Vector position, Vector velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        public SingleDimensionMoonState getX() {
            return new SingleDimensionMoonState(position.x, velocity.x);
        }

        public SingleDimensionMoonState getY() {
            return new SingleDimensionMoonState(position.y, velocity.y);
        }

        public SingleDimensionMoonState getZ() {
            return new SingleDimensionMoonState(position.z, velocity.z);
        }

        public int getPotentialEnergy() {
            return position.getEnergy();
        }

        public int getKineticEnergy() {
            return velocity.getEnergy();
        }

        public int getEnergy() {
            return getPotentialEnergy() * getKineticEnergy();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MoonState moonState = (MoonState) o;
            return Objects.equals(position, moonState.position) &&
                    Objects.equals(velocity, moonState.velocity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, velocity);
        }

        @Override
        public String toString() {
            return "pos=" + position + ", vel=" + velocity;
        }

        public MoonState apply(Vector deltaV) {
            Vector newVelocity = velocity.add(deltaV);
            return new MoonState(position.add(newVelocity), newVelocity);
        }

    }

    private static final class Vector {

        public static final Vector ZERO = new Vector(0, 0, 0);
        private final int x, y, z;

        private Vector(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        private int getEnergy() {
            return abs(x) + abs(y) + abs(z);
        }

        public Vector add(Vector other) {
            return new Vector(x + other.x, y + other.y, z + other.z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vector vector = (Vector) o;
            return x == vector.x &&
                    y == vector.y &&
                    z == vector.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return '<' + "x=" + x + ", y=" + y + ", z=" + z + '>';
        }

    }

    private interface DimensionGetter extends Function<MoonState, SingleDimensionMoonState> {

    }

}
