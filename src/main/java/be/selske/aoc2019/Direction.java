package be.selske.aoc2019;

import java.util.function.Function;

public enum Direction {
    RIGHT(c -> new Coordinate(c.getX() + 1, c.getY())),
    UP(c -> new Coordinate(c.getX(), c.getY() + 1)),
    LEFT(c -> new Coordinate(c.getX() - 1, c.getY())),
    DOWN(c -> new Coordinate(c.getX(), c.getY() - 1));

    Function<Coordinate, Coordinate> moveFunction;

    private Direction(final Function<Coordinate, Coordinate> moveFunction) {
        this.moveFunction = moveFunction;
    }

    public Coordinate move(Coordinate from) {
        return moveFunction.apply(from);
    }

    public Direction reverse() {
        return values()[(ordinal() + 2) % values().length];
    }

    public Direction turnLeft() {
        return values()[(ordinal() + 1) % values().length];
    }

    public Direction turnRight() {
        return values()[(ordinal() + values().length - 1) % values().length];
    }
}
