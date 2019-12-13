package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;
import be.selske.aoc2019.Coordinate;
import be.selske.aoc2019.days.intcomputer.IntComputer;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day13 extends AocDay {

    public static void main(String[] args) {
        new Day13().solve(() -> Day13.class.getResource("/inputs/day13.txt"));
    }

    private Day13() {
        super(
                Day13::part1,
                Day13::part2
        );
    }

    private static String part1(Stream<String> input) {
        IntComputer intComputer = new IntComputer(IntComputer.parseInput(input.findFirst().orElseThrow()));

        intComputer.run();

        List<Long> output = intComputer.getOutput();
        int count = 0;
        for (int i = 2; i < output.size(); i += 3) {
            if (output.get(i) == 2) {
                count++;
            }
        }
        return count + "";
    }

    private static String part2(Stream<String> input) {
        long[] memory = IntComputer.parseInput(input.findFirst().orElseThrow());
        memory[0] = 2;
        IntComputer intComputer = new IntComputer(memory);

        GameState gameState = new GameState();
        Coordinate previousBallPosition = null;
        long movement = 0L;
        do {
            intComputer.run(movement);
            calculateGameState(intComputer.getOutput(), gameState);
            intComputer.clearOutput();

            Coordinate ballPosition = gameState.getBallPosition();
            Coordinate paddlePosition = gameState.getPaddlePosition();
            if (previousBallPosition != null) {
                int deltaX = ballPosition.getX() - previousBallPosition.getX();
                if (ballPosition.getY() == paddlePosition.getY() - 1 && paddlePosition.getX() == ballPosition.getX()) {
                    movement = 0;
                } else {
                    movement = deltaX;
                }
            }
            previousBallPosition = ballPosition;
        } while (intComputer.isRunning());

        return gameState.getScore() + "";
    }

    private static void calculateGameState(List<Long> output, GameState gameState) {
        for (int i = 0; i < output.size(); i += 3) {
            Long value = output.get(i + 2);
            Coordinate coordinate = new Coordinate(output.get(i).intValue(), output.get(i + 1).intValue());
            if (value == 3) {
                gameState.setPaddlePosition(coordinate);
            } else if (value == 4) {
                gameState.setBallPosition(coordinate);
            } else if (new Coordinate(-1, 0).equals(coordinate)) {
                gameState.setScore(value);
            }
        }
    }

    private static final class GameState {

        private Coordinate ballPosition;
        private Coordinate paddlePosition;
        private Long score;

        public Coordinate getBallPosition() {
            return ballPosition;
        }

        public void setBallPosition(Coordinate ballPosition) {
            this.ballPosition = ballPosition;
        }

        public Coordinate getPaddlePosition() {
            return paddlePosition;
        }

        public void setPaddlePosition(Coordinate paddlePosition) {
            this.paddlePosition = paddlePosition;
        }

        public Long getScore() {
            return score;
        }

        public void setScore(Long score) {
            this.score = score;
        }

    }

}
