package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShannonFanoCoding {
    public static class Node {
        char symbol;
        double probability;
        String code = ""; // код символа

        public Node(char symbol, double probability) {
            this.symbol = symbol;
            this.probability = probability;
        }
    }

    public static Map<Character, String> shannonFanoCoding(Map<Character, Double> probabilities) {
        List<Node> nodes = new ArrayList<>();
        for (Map.Entry<Character, Double> entry : probabilities.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }

        nodes.sort((a, b) -> Double.compare(b.probability, a.probability));

        Map<Character, String> codes = new HashMap<>();

        generateCodes(nodes, 0, nodes.size(), codes);

        return codes;
    }

    private static void generateCodes(List<Node> nodes, int start, int end, Map<Character, String> codes) {

        if (start >= end) {
            return;
        }

        if (start == end - 1) {
            codes.put(nodes.get(start).symbol, nodes.get(start).code);
            return;
        }

        double totalProbability = 0;
        for (int i = start; i < end; i++) {
            totalProbability += nodes.get(i).probability;
        }

        double halfProbability = 0;
        int split = start;
        for (; split < end; split++) {
            if (halfProbability + nodes.get(split).probability > totalProbability / 2) {
                break;
            }
            halfProbability += nodes.get(split).probability;
        }


        if (split == start || split == end) {
            split = (start + end) / 2;
        }

        for (int i = start; i < split; i++) {
            nodes.get(i).code += "0";
        }
        for (int i = split; i < end; i++) {
            nodes.get(i).code += "1";
        }

        generateCodes(nodes, start, split, codes);
        generateCodes(nodes, split, end, codes);

    }

    public static String encode(String input, Map<Character, String> codes) {
        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            encoded.append(codes.get(c));
        }
        return encoded.toString();
    }

}
