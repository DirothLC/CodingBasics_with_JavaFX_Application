package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab4;

import java.io.*;
import java.util.Random;

public class RLECodingAlgorithm {
    public void encodeFile(File inputFile, File outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            StringBuilder inputString = new StringBuilder();
            for(String line; (line = reader.readLine()) != null; ) {
                inputString.append(line);
               // inputString.append("\n");
            }
            for(int i=0;i<inputString.length();i++){
                char currentChar= inputString.charAt(i);
                char nextChar='\uFFFF';
                if(i<inputString.length()-1){
                nextChar = inputString.charAt(i+1);
                }
                int count=1;
                while (currentChar == nextChar) {
                    count++;
                    i++;
                    if(i<inputString.length()-1){
                        nextChar = inputString.charAt(i+1);
                    }
                    else{
                        break;
                    }
                }
                writer.write(currentChar);

                if(count>1)writer.write(String.valueOf(count));

            }

        }

    }

    public void decodeFile(File inputFile, File outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            StringBuilder inputString = new StringBuilder();
            for(String line; (line = reader.readLine()) != null; ) {
                inputString.append(line);
            }

            for(int i=0; i<inputString.length();i++){
                int count=1;
                char currentChar = inputString.charAt(i);
                if(Character.isDigit(currentChar))continue;
                if(i<inputString.length()-1){
                    char nextChar= inputString.charAt(i+1);
                    if(Character.isDigit(nextChar)){
                        count=Character.getNumericValue(nextChar);
                    }
                }
                for(int j=0;j<count;j++){
                    writer.write(currentChar);
                }
            }

        }
    }
    public static void fileGenerator(){
        File outputFile = new File("200kbThird.txt");
        int fileSizeInKB = 200;
        int fileSizeInBytes = fileSizeInKB * 1024;
        char[] charSet = {'a', 'b'};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            Random random = new Random();
            for (int i = 0; i < fileSizeInBytes; i++) {
                char randomChar = charSet[random.nextInt(charSet.length)];
                writer.write(randomChar);
            }
            System.out.println("Файл успешно создан: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

