package be.selske.aoc2019.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public enum DotLetter {

    space(' ', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: A
    A('A', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    B('B', new int[][]{
            { 1, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 1, 1, 0, 0 },
    }),
    C('C', new int[][]{
            { 0, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 0, 1, 1, 0, 0 },
    }),
    // TODO: D
    D('D', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: E
    E('E', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: F
    F('F', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: G
    G('G', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: H
    H('H', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: I
    I('I', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: J
    J('J', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    K('K', new int[][]{
            { 1, 0, 0, 1, 0 },
            { 1, 0, 1, 0, 0 },
            { 1, 1, 0, 0, 0 },
            { 1, 0, 1, 0, 0 },
            { 1, 0, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
    }),
    // TODO: L
    L('L', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: M
    M('M', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: N
    N('N', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: O
    O('O', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: P
    P('P', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: Q
    Q('Q', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: R
    R('R', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: S
    S('S', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: T
    T('T', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: U
    U('U', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: V
    V('V', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: W
    W('W', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    // TODO: X
    X('X', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    Y('Y', new int[][]{
            { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 },
            { 0, 1, 0, 1, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
    }),
    // TODO: Z
    Z('Z', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    });

    private final char value;
    private final int[][] pixels;

    DotLetter(char value, int[][] pixels) {
        this.value = value;
        this.pixels = pixels;
    }

    @Override
    public String toString() {
        return value + "";
    }

    public static DotLetter dotLetter(int[][] value) {
        return Arrays.stream(values())
                .filter(dl -> Arrays.deepEquals(dl.pixels, value))
                .findFirst()
                .orElseThrow();
    }

    public static String getText(int[][] value) {
        int length = value[0].length / 5;
        List<List<DotLetter>> possibleValues = Stream.generate(() -> Arrays.stream(values()).collect(toList())).limit(length).collect(toList());

        for (int row = 0; row < value.length; row++) {
            for (int col = 0; col < length; col++) {
                int currentRow = row;
                int[] fragment = Arrays.copyOfRange(value[row], col * 5, col * 5 + 5);
                possibleValues.get(col).removeIf(not(dl -> Arrays.equals(dl.pixels[currentRow], fragment)));
            }
            if (possibleValues.stream().allMatch(pv -> pv.size() == 1)) {
                break;
            }
        }
        return possibleValues.stream()
                .map(pv -> pv.stream().findFirst().orElseThrow())
                .map(dl -> dl.value + "")
                .collect(joining());
    }

    public static void main(String[] args) {
        int[][] b = {
                { 1, 1, 1, 0, 0 },
                { 1, 0, 0, 1, 0 },
                { 1, 1, 1, 0, 0 },
                { 1, 0, 0, 1, 0 },
                { 1, 0, 0, 1, 0 },
                { 1, 1, 1, 0, 0 },
        };
        DotLetter dotLetter = DotLetter.dotLetter(b);

        System.out.println(dotLetter.value);
        System.out.println(DotLetter.getText(b));
        int[][] day8 = {
                { 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1 },
                { 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
                { 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
                { 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0 },
        };

        System.out.println(DotLetter.getText(day8));
    }
}
