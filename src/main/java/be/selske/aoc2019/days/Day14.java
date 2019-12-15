package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.Long.parseLong;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

public class Day14 extends AocDay {

    public static final String ORE = "ORE";
    public static final String FUEL = "FUEL";
    public static final long ONE_TRILLION = 1_000_000_000_000L;

    public static void main(String[] args) {
        new Day14().solve(() -> Day14.class.getResource("/inputs/day14.txt"));
    }

    private Day14() {
        super(
                Day14::part1,
                Day14::part2
        );
    }

    private static String part1(Stream<String> input) {
        Map<String, Reaction> reactions = parseInput(input);
        ReactionContext reactionContext = new ReactionContext(ORE, reactions);

        return reactionContext.getSourceMaterialRequired(FUEL, 1) + "";
    }

    private static String part2(Stream<String> input) {
        Map<String, Reaction> reactions = parseInput(input);
        ReactionContext reactionContext = new ReactionContext(ORE, reactions);

        long sourceMaterialPerReaction = reactionContext.getSourceMaterialRequired(FUEL, 1);

        long high = Long.MAX_VALUE / sourceMaterialPerReaction; // to avoid overflow fuel * ore should be less than Long.MAX_VALUE
        long low = ONE_TRILLION / sourceMaterialPerReaction;
        long guess = low;
        long ore = 0;
        while (low <= high) {
            guess = (low + high) / 2;
            ore = new ReactionContext(ORE, reactions).getSourceMaterialRequired(FUEL, guess);
            if (ore > ONE_TRILLION) {
                high = guess - 1;
            } else {
                low = guess + 1;
            }
        }
        if (ore > ONE_TRILLION) {
            guess = guess - 1;
        }
        return guess + "";
    }

    private static Map<String, Reaction> parseInput(Stream<String> input) {
        String[] inputStrings = input.toArray(String[]::new);
        Map<String, Reaction> reactions = new HashMap<>();
        for (String inputString : inputStrings) {
            Map<Reaction, Long> reagents = Arrays.stream(inputString.split(" => ")[0].split(", "))
                    .map(is -> is.split(" "))
                    .collect(toMap(ia -> reactions.computeIfAbsent(ia[1], Reaction::new), ia -> parseLong(ia[0])));

            long amount = parseLong(inputString.split(" => ")[1].split(" ")[0]);
            String result = inputString.split(" => ")[1].split(" ")[1];
            Reaction reaction = reactions.computeIfAbsent(result, Reaction::new);
            reaction.addReagents(reagents);
            reaction.setOutputAmount(amount);
        }
        reactions.get(ORE).setOutputAmount(1);
        return reactions;
    }

    private static final class Reaction {

        private final String output;
        private final Map<Reaction, Long> reagents = new HashMap<>();
        private long outputAmount = 0;

        private Reaction(String output) {
            this.output = output;
        }

        public void setOutputAmount(long outputAmount) {
            this.outputAmount = outputAmount;
        }

        public void addReagents(Map<Reaction, Long> reagents) {
            this.reagents.putAll(reagents);
        }

        @Override
        public String toString() {
            return "Reaction{" +
                    "output='" + output + '\'' +
                    "amount='" + outputAmount + '\'' +
                    (reagents.isEmpty() ? "" : ", reagents=" + reagents) +
                    '}';
        }

    }

    private static class ReactionContext {

        private final String sourceMaterial;
        private final Map<String, Reaction> reactions;
        private final Map<String, Long> availableReagents = new HashMap<>();
        private final Map<String, Long> baseReagentsNeeded = new HashMap<>();

        private ReactionContext(String sourceMaterial, Map<String, Reaction> reactions) {
            this.sourceMaterial = sourceMaterial;
            this.reactions = reactions;
        }

        public void react(String material, long amount) {
            baseReagentsNeeded.clear();
            reactions.get(material).reagents.forEach((k, v) -> react(k, v * amount));
        }

        private void react(Reaction reaction, long amount) {
            if (reaction.reagents.keySet().stream().anyMatch(r -> r.output.equals(sourceMaterial))) {
                baseReagentsNeeded.compute(reaction.output, (k, v) -> v == null ? amount : v + amount);
                return;
            }

            long availableAmount = availableReagents.getOrDefault(reaction.output, 0L);
            if (availableAmount >= amount) {
                availableReagents.compute(reaction.output, (k, v) -> v == null ? 0 : v - amount);
                return;
            } else {
                availableReagents.put(reaction.output, 0L);
            }

            long amountNeeded = amount - availableAmount;

            long numberOfReactions = (long) Math.ceil((double) amountNeeded / reaction.outputAmount);
            availableReagents.put(reaction.output, numberOfReactions * reaction.outputAmount - amountNeeded);

            reaction.reagents.entrySet().stream()
                    .filter(not(e -> e.getKey().output.equals(sourceMaterial)))
                    .forEach(e -> react(e.getKey(), e.getValue() * numberOfReactions));
        }

        private long getSourceMaterialRequired() {
            return baseReagentsNeeded.entrySet().stream()
                    .mapToLong(e -> {
                        String reagent = e.getKey();
                        Reaction reaction = reactions.get(reagent);
                        long outputAmount = reaction.outputAmount;
                        long inputAmount = reaction.reagents.values().stream().mapToLong(Long::longValue).sum();
                        long amountNeeded = e.getValue();
                        long availableAmount = availableReagents.getOrDefault(reaction.output, 0L);
                        if (availableAmount >= amountNeeded) {
                            availableReagents.put(reaction.output, availableAmount - amountNeeded);
                            return 0;
                        } else {
                            amountNeeded -= availableAmount;
                            availableReagents.put(reaction.output, 0L);
                        }
                        long numberOfReactions = (long) Math.ceil((double) amountNeeded / outputAmount);
                        long amountLeftOver = outputAmount * numberOfReactions - amountNeeded;
                        availableReagents.compute(reagent, (k, v) -> v == null ? amountLeftOver : v + amountLeftOver);
                        return numberOfReactions * inputAmount;
                    })
                    .sum();
        }

        public long getSourceMaterialRequired(String material, long amount) {
            react(material, amount);
            return getSourceMaterialRequired();
        }

    }

}
