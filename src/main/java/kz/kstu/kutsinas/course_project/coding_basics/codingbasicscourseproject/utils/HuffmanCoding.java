package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.utils;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCoding {
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
    public static String encodeWithHuffman(Map<Character, Double> probabilities, String input) {

        Node root = buildHuffmanTree(probabilities);
        Map<Character, String> huffmanCodes = generateHuffmanCodes(root);
        StringBuilder encodedText = new StringBuilder();
        for (char c : input.toCharArray()) {
            encodedText.append(huffmanCodes.get(c));
        }

        return encodedText.toString();
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

    private static Node buildHuffmanTree(Map<Character, Double> probabilities) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.probability));

        for (Map.Entry<Character, Double> entry : probabilities.entrySet()) {
            queue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            Node parent = new Node(null, left.probability + right.probability, left, right);
            queue.add(parent);
        }

        return queue.poll();
    }
    private static Map<Character, String> generateHuffmanCodes(Node root) {
        Map<Character, String> codes = new HashMap<>();
        generateCodesRecursive(root, "", codes);
        return codes;
    }

    private static void generateCodesRecursive(Node node, String code, Map<Character, String> codes) {
        if (node == null) return;
        if (node.character != null) {
            codes.put(node.character, code);
        }
        generateCodesRecursive(node.left, code + "0", codes);
        generateCodesRecursive(node.right, code + "1", codes);
    }

    private static class Node {
        Character character;
        Double probability;
        Node left;
        Node right;

        Node(Character character, Double probability) {
            this.character = character;
            this.probability = probability;
        }

        Node(Character character, Double probability, Node left, Node right) {
            this.character = character;
            this.probability = probability;
            this.left = left;
            this.right = right;
        }
    }


}
