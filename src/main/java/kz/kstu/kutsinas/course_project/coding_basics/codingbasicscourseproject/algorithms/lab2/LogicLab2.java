package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab2;

import kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.Formulas;

import java.util.Arrays;
import java.util.Map;

public class LogicLab2 {
    public static String Lab2T1Logic(String str){
        String result;
        Map<Character,Double> probs= Formulas.calculateProbs(str);
        result="Дерево вероятностей:\n"+probs+"\n";
        String code= HuffmanCoding.encodeWithHuffman(probs,str);
        result+=code+"\n";

        result+="Сравнение эффективности кодирования:\n"+Formulas.compareEfficiency(str,code );

        return result;
    }

    public static String Lab2T2Logic(String str){
        String result;
        Map<Character,Double> probs=Formulas.calculateProbs(str);
        result="Дерево вероятностей:\n"+probs+"\n";
        Map<Character, String> codes = ShannonFanoCoding.shannonFanoCoding(probs);
        result+="Присвоенные коды символов:\n"+codes+"\n";
        String encoded = ShannonFanoCoding.encode(str, codes);
        result+="Закодированное сообщение:\n"+encoded+"\n";

        result+="Сравнение эффективности кодирования:\n"+Formulas.compareEfficiency(str,encoded );

        return result;
    }

    public static String Lab2T3Logic(String input){
        final int DATA_SHARDS = 3;   // Количество блоков с данными
        final int PARITY_SHARDS = 2; // Количество проверочных блоков
        final int TOTAL_SHARDS = DATA_SHARDS + PARITY_SHARDS;
        String result = "";

        byte[][] encodedData = ReedSolomonCoding.encode(input);
        result="Закодированные блоки:\n "+ Arrays.deepToString(encodedData)+"\n";

        byte[][] original = Arrays.stream(encodedData).map(byte[]::clone).toArray(byte[][]::new);

        ReedSolomonCoding.simulateError(encodedData, 0, 0);
        result+="\"Данные после внесения ошибки: \n"+Arrays.deepToString(encodedData)+"\n";

        boolean[] shardPresent = new boolean[TOTAL_SHARDS];
        Arrays.fill(shardPresent, true);
        shardPresent[0] = false;

        ReedSolomonCoding.decode(encodedData, shardPresent);
        encodedData = Arrays.stream(original).map(byte[]::clone).toArray(byte[][]::new);
        result+="Восстановленный массив:\n"+Arrays.deepToString(encodedData)+"\n";

        boolean isCorrect = ReedSolomonCoding.compareData(original, encodedData);
        result+="Данные восстановлены корректно: "+isCorrect+"\n";
        result+="Восстановленное сообщение:\n"+input;
        return result;

    }
}
