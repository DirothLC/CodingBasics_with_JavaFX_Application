package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Formulas {
    public static Map<Character, Double> calculateProbs(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int totalCharacters = input.length();

        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        Map<Character, Double> probabilityMap = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            probabilityMap.put(entry.getKey(), (double) entry.getValue() / totalCharacters);
        }

        return probabilityMap;
    }

    public static String compareEfficiency(String input, String encodedText) {
        int originalSize = input.length() * 16;
        int compressedSize = encodedText.length();

        String result = String.format(
                "Размер исходного текста (в битах): %d\nРазмер закодированного текста (в битах): %d\nСтепень сжатия: %.2f%%",
                originalSize,
                compressedSize,
                (100.0 - ((double) compressedSize / originalSize) * 100)
        );
        return result;
    }
    public static double entropy(double[] prob) {
        double entropy = 0;
        for (int i = 0; i < prob.length; i++) {
            if (prob[i] > 0) {
                entropy += prob[i] * (Math.log(prob[i]) / Math.log(2));
            }
        }
        entropy = -entropy;
        System.out.println("Энтропия: " + entropy);
        return entropy;
    }

    static class Node implements Comparable<Node> {
        char symbol;
        double probability;
        Node left;
        Node right;

        Node(char symbol, double probability) {
            this.symbol = symbol;
            this.probability = probability;
        }

        Node(double probability, Node left, Node right) {
            this.symbol = '\0';
            this.probability = probability;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.probability, other.probability);
        }
    }


    public static Map<Character, String> huffmanCoding(char[] alphabet, double[] probabilities) {
        if (alphabet.length != probabilities.length) {
            throw new IllegalArgumentException("Alphabet and probabilities must have the same length.");
        }
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (int i = 0; i < alphabet.length; i++) {
            queue.add(new Node(alphabet[i], probabilities[i]));
        }
        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            Node parent = new Node(left.probability + right.probability, left, right);
            queue.add(parent);
        }
        Node root = queue.poll();
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodes(root, "", huffmanCodes);
        return huffmanCodes;
    }
    private static void generateCodes(Node node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) {
            return;
        }
        if (node.symbol != '\0') {
            huffmanCodes.put(node.symbol, code);
        }
        generateCodes(node.left, code + "0", huffmanCodes);
        generateCodes(node.right, code + "1", huffmanCodes);
    }
    public static double codingEfficiency(double[] prob, int[] codeLengths) {
        if (prob.length != codeLengths.length) {
            throw new IllegalArgumentException("Длины массивов вероятностей и кодов должны совпадать.");
        }
        double averageCodeLength = 0;
        for (int i = 0; i < prob.length; i++) {
            averageCodeLength += prob[i] * codeLengths[i];
        }
        double entropy = entropy(prob);

        double efficiency = entropy / averageCodeLength;
        System.out.println("Эффективность кодирования: " + efficiency);

        return efficiency;
    }
}
