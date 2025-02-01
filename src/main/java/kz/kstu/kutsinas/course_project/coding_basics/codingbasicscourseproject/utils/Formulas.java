package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.utils;

import java.util.HashMap;
import java.util.Map;

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
}
