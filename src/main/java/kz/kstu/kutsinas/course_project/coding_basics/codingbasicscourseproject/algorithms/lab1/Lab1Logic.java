package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab1;

import kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.Formulas;

import java.util.Map;

import static kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.Formulas.huffmanCoding;

public class Lab1Logic {
    public static String programRun() {
        String result="";
        char[] alphabet = {'A', 'B', 'C', 'D'};
        double[] prob = {0.1, 0.2, 0.3, 0.4};
        int[] codeLengths = {2, 2, 2, 2};
        Formulas.entropy(prob);
        result+= "Энтропия: "+Formulas.entropy(prob)+"\n";
        Map<Character, String> huffmanCodes = huffmanCoding(alphabet, prob);
        result+= "Huffman Codes:\n";
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            result+=entry.getKey() + ": " + entry.getValue()+"\n";
        }
        Formulas.codingEfficiency(prob, codeLengths);
        result+="\nЭффективность кодирования: "+Formulas.codingEfficiency(prob, codeLengths)+"\n";
return result;
    }
}
