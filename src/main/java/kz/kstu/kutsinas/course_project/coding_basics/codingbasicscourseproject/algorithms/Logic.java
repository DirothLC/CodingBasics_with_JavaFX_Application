package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms;

import java.util.Map;

public class Logic {
    public static String Lab2T1Logic(String str){
        String result;
        Map<Character,Double> probs=Formulas.calculateProbs(str);
        result="Дерево вероятностей:\n"+probs+"\n";
        String code=HuffmanCoding.encodeWithHuffman(probs,str);
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

}
