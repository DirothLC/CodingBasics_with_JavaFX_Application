package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.utils;

import java.util.HashMap;
import java.util.Map;

public class Logic {
    public static String Lab2T1Logic(String str){
        String result;
        Map<Character,Double> probs=new HashMap<>();
        probs=Formulas.calculateProbs(str);
        result="Дерево вероятностей:\n"+probs+"\n";
        String code=HuffmanCoding.encodeWithHuffman(probs,str);
        result+=code+"\n";

        result+="Сравнение эффективности кодирования:\n"+Formulas.compareEfficiency(str,code );

        return result;
    }
}
