package be.selske.aoc2019.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public enum DotLetter {

    UNKNOWN('?', new int[][]{
            { 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1 },
    }),
    SPACE(' ', new int[][]{
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    }),
    A('A', new int[][]{
            { 0, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 1, 1, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
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
    D('D', new int[][]{
            { 1, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 1, 1, 0, 0 },
    }),
    E('E', new int[][]{
            { 1, 1, 1, 1, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 1, 1, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 0 },
    }),
    F('F', new int[][]{
            { 1, 1, 1, 1, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 1, 1, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
    }),
    G('G', new int[][]{
            { 0, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 0, 1, 1, 1, 0 },
    }),
    H('H', new int[][]{
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 1, 1, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
    }),
    // TODO: actual I
    I('I', new int[][]{
            { 0, 1, 1, 1, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 1, 1, 1, 0 },
    }),
    J('J', new int[][]{
            { 0, 0, 1, 1, 0 },
            { 0, 0, 0, 1, 0 },
            { 0, 0, 0, 1, 0 },
            { 0, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 0, 1, 1, 0, 0 },
    }),
    K('K', new int[][]{
            { 1, 0, 0, 1, 0 },
            { 1, 0, 1, 0, 0 },
            { 1, 1, 0, 0, 0 },
            { 1, 0, 1, 0, 0 },
            { 1, 0, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
    }),
    L('L', new int[][]{
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 0 },
    }),
    // TODO: actual M
    M('M', new int[][]{
            { 1, 0, 0, 0, 1 },
            { 1, 1, 0, 1, 1 },
            { 1, 0, 1, 0, 1 },
            { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 },
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
    // TODO: actual O
    O('O', new int[][]{
            { 0, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 0, 1, 1, 0, 0 },
    }),
    P('P', new int[][]{
            { 1, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 1, 1, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
    }),
    // TODO: actual Q
    Q('Q', new int[][]{
            { 0, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 0, 1, 1, 1, 0 },
    }),
    R('R', new int[][]{
            { 1, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 1, 1, 0, 0 },
            { 1, 0, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
    }),
    // TODO: actual S
    S('S', new int[][]{
            { 0, 1, 1, 1, 0 },
            { 1, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 0, 1, 0 },
            { 1, 1, 1, 0, 0 },
    }),
    // TODO: actual T
    T('T', new int[][]{
            { 1, 1, 1, 1, 1 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
    }),
    U('U', new int[][]{
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 0, 1, 1, 0, 0 },
    }),
    // TODO: actual V
    V('V', new int[][]{
            { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 },
            { 0, 1, 0, 1, 0 },
            { 0, 1, 0, 1, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
    }),
    // TODO: actual W
    W('W', new int[][]{
            { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 },
            { 1, 0, 1, 0, 1 },
            { 0, 1, 0, 1, 0 },
    }),
    // TODO: actual X
    X('X', new int[][]{
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
            { 0, 1, 1, 0, 0 },
            { 0, 1, 1, 0, 0 },
            { 1, 0, 0, 1, 0 },
            { 1, 0, 0, 1, 0 },
    }),
    Y('Y', new int[][]{
            { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 },
            { 0, 1, 0, 1, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
    }),
    Z('Z', new int[][]{
            { 1, 1, 1, 1, 0 },
            { 0, 0, 0, 1, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 1, 0, 0, 0 },
            { 1, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 0 },
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

        int offset = getOffset(value);
        for (int row = 0; row < value.length; row++) {
            for (int col = 0; col < length; col++) {
                int currentRow = row;
                int[] fragment = Arrays.copyOfRange(value[row], col * 5 + offset, col * 5 + offset + 5);
                possibleValues.get(col).removeIf(not(dl -> Arrays.equals(dl.pixels[currentRow], fragment)));
            }
            if (possibleValues.stream().allMatch(pv -> pv.size() == 1)) {
                break;
            }
        }
        return possibleValues.stream()
                .map(pv -> pv.stream().findFirst().orElse(DotLetter.UNKNOWN))
                .map(dl -> dl.value + "")
                .collect(joining());
    }

    private static int getOffset(int[][] value) {
        int offset;
        for (int col = 0; col < value[0].length; col++) {
            offset = col;
            for (int[] rows : value) {
                if (rows[col] != 0) {
                    return offset;
                }
            }
        }
        return 0;
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

        int[][] day8_pieter = {
                { 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0 },
                { 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
                { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
                { 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
                { 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
                { 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0 },
        };

        System.out.println(DotLetter.getText(day8_pieter));
    }
}
