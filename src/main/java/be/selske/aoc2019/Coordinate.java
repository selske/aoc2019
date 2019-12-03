package be.selske.aoc2019;

import java.util.Objects;

import static java.lang.Math.abs;

public final class Coordinate {

    private final int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate getRight() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate getLeft() {
        return new Coordinate(x - 1, y);
    }

    public Coordinate getUp() {
        return new Coordinate(x, y + 1);
    }

    public Coordinate getDown() {
        return new Coordinate(x, y - 1);
    }

    public long distance(Coordinate other) {
        return abs(x - other.x) + abs(y - other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + ']';
    }

}
