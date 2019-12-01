package be.selske.aoc2019;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class AocDay {

    private final List<Solution> solutions;

    public AocDay(Part part1, Part... parts) {
        AtomicInteger count = new AtomicInteger(1);
        this.solutions = Stream.concat(Stream.of(part1), Stream.of(parts))
                .map(part -> new Solution("part " + count.getAndIncrement(), part))
                .collect(toList());
    }

    public void solve(InputStream input) {
        solutions.stream()
                .map(solution -> solution.solve(input))
                .forEach(result -> System.out.println(result.name + ": " + result.result + " (" + result.timeTaken + "ms)"));
    }

    public interface Part extends Function<InputStream, String> {

    }

    private static class Solution {

        private final String name;
        private final Part solutionSupplier;

        private Solution(String name, Part solutionSupplier) {
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
