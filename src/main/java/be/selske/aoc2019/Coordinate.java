package be.selske.aoc2019;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

import static java.lang.StrictMath.*;

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

    public boolean between(Coordinate c1, Coordinate c2) {
        return isInlineWith(c1, c2)
                && between(c1, c2, Coordinate::getX)
                && between(c1, c2, Coordinate::getY);
    }

    private boolean isInlineWith(Coordinate c1, Coordinate c2) {
        return (c1.getY() - c2.getY()) * (c1.getX() - getX()) == (c1.getY() - getY()) * (c1.getX() - c2.getX());
    }

    private boolean between(Coordinate c1, Coordinate c2, Function<Coordinate, Integer> dimension) {
        int p = dimension.apply(this);
        int p1 = dimension.apply(c1);
        int p2 = dimension.apply(c2);

        return (p <= p1 && p >= p2) || (p >= p1 && p <= p2);
    }

    public static Edges getEdges(Collection<Coordinate> coordinates) {
        int left = Integer.MAX_VALUE;
        int top = Integer.MIN_VALUE;
        int right = Integer.MIN_VALUE;
        int bottom = Integer.MAX_VALUE;

        for (Coordinate coordinate : coordinates) {
            if (coordinate.getX() < left) left = coordinate.getX();
            if (coordinate.getX() > right) right = coordinate.getX();
            if (coordinate.getY() > top) top = coordinate.getY();
            if (coordinate.getY() < bottom) bottom = coordinate.getY();
        }

        return new Edges(left, right, top, bottom);
    }

    public static class Edges {

        private final int left;
        private final int right;
        private final int top;
        private final int bottom;

        private Edges(int left, int right, int top, int bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }

        public int getTop() {
            return top;
        }

        public int getBottom() {
            return bottom;
        }

    }

}
