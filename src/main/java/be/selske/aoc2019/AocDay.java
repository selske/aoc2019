package be.selske.aoc2019;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public abstract class AocDay {

    private final List<Solution> solutions;

    public AocDay(PartSolution partSolution1, PartSolution... partSolutions) {
        AtomicInteger count = new AtomicInteger(1);
        this.solutions = concat(Stream.of(partSolution1), Stream.of(partSolutions))
                .map(partSolution -> new Solution("part " + count.getAndIncrement(), partSolution))
                .collect(toList());
    }

    public void solve(Supplier<InputStream> input) {
        solutions.stream()
                .map(solution -> solution.solve(input.get()))
                .forEach(result -> System.out.println(result.name + ": " + result.result + " (" + result.timeTaken + "ms)"));
    }

    protected interface PartSolution extends Function<InputStream, String> {

    }

    private static class Solution {

        private final String name;
        private final PartSolution solutionSupplier;

        private Solution(String name, PartSolution solutionSupplier) {
            this.name = name;
            this.solutionSupplier = solutionSupplier;
        }

        private Result solve(InputStream input) {
            long before = System.currentTimeMillis();
            String result = solutionSupplier.apply(input);
            long after = System.currentTimeMillis();
            return new Result(name, result, after - before);
        }

    }

    private static class Result {

        private final String name;
        private final String result;
        private final long timeTaken;

        Result(String name, String result, long timeTaken) {
            this.name = name;
            this.result = result;
            this.timeTaken = timeTaken;
        }

    }

}
