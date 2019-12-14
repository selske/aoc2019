package be.selske.aoc2019.days;

import be.selske.aoc2019.AocDay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

public class Day14 extends AocDay {

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
        return reactions.get("FUEL").getMaterialRequired("ORE", reactions) + "";
    }

    private static String part2(Stream<String> input) {
        return null;
    }

    private static Map<String, Reaction> parseInput(Stream<String> input) {
        String[] inputStrings = input.toArray(String[]::new);
        Map<String, Reaction> reactions = new HashMap<>();
        for (String inputString : inputStrings) {
            Map<Reaction, Integer> reagents = Arrays.stream(inputString.split(" => ")[0].split(", "))
                    .map(is -> is.split(" "))
                    .collect(toMap(ia -> reactions.computeIfAbsent(ia[1], Reaction::new), ia -> parseInt(ia[0])));

            int amount = parseInt(inputString.split(" => ")[1].split(" ")[0]);
            String result = inputString.split(" => ")[1].split(" ")[1];
            Reaction reaction = reactions.computeIfAbsent(result, Reaction::new);
            reaction.addReagents(reagents);
            reaction.setOutputAmount(amount);
        }
        reactions.get("ORE").setOutputAmount(1);
        return reactions;
    }

    private static final class Reaction {

        private final String output;
        private final Map<Reaction, Integer> reagents = new HashMap<>();
        private int outputAmount = 0;

        private Reaction(String output) {
            this.output = output;
        }

        public void setOutputAmount(int outputAmount) {
            this.outputAmount = outputAmount;
        }

        public void addReagents(Map<Reaction, Integer> reagents) {
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

        public int getMaterialRequired(String material, Map<String, Reaction> reactions) {
            ReactionContext reactionContext = new ReactionContext(material, reactions);

            reagents.forEach(reactionContext::react);

            return reactionContext.getSourceMaterialRequired();
        }

    }

    private static class ReactionContext {

        private final String sourceMaterial;
        private final Map<String, Reaction> reactions;
        private final Map<String, Integer> availableReagents = new HashMap<>();
        private final Map<String, Integer> baseReagentsNeeded = new HashMap<>();

        private ReactionContext(String sourceMaterial, Map<String, Reaction> reactions) {
            this.sourceMaterial = sourceMaterial;
            this.reactions = reactions;
            this.reactions.values().forEach(reaction -> availableReagents.put(reaction.output, 0));
        }

        public void react(Reaction reaction, int amount) {
            if (reaction.reagents.keySet().stream().anyMatch(r -> r.output.equals(sourceMaterial))) {
                baseReagentsNeeded.compute(reaction.output, (k, v) -> v == null ? amount : v + amount);
                return;
            }

            int availableAmount = availableReagents.get(reaction.output);
            if (availableAmount >= amount) {
                availableReagents.compute(reaction.output, (k, v) -> v == null ? 0 : v - amount);
                return;
            } else {
                availableReagents.put(reaction.output, 0);
            }

            int amountNeeded = amount - availableAmount;

            int numberOfReactions = (int) Math.ceil((double) amountNeeded / reaction.outputAmount);
            availableReagents.put(reaction.output, numberOfReactions * reaction.outputAmount - amountNeeded);

            reaction.reagents.entrySet().stream()
                    .filter(not(e -> e.getKey().output.equals(sourceMaterial)))
                    .forEach(e -> react(e.getKey(), e.getValue() * numberOfReactions));
        }

        public int getSourceMaterialRequired() {
            return baseReagentsNeeded.entrySet().stream()
                    .mapToInt(e -> {
                        int outputAmount = reactions.get(e.getKey()).outputAmount;
                        int inputAmount = reactions.get(e.getKey()).reagents.values().stream().mapToInt(Integer::intValue).sum();
                        return (int) Math.ceil((double) e.getValue() / outputAmount) * inputAmount;
                    })
                    .sum();
        }

    }

}
