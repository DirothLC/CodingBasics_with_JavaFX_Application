package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab4;

import java.io.*;

public class RLECodingAlgorithm {
    public void encodeFile(File inputFile, File outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            int count;
            char currentChar, previousChar = (char) -1;

            while ((count = reader.read()) != -1) {
                currentChar = (char) count;
                int runLength = 1;

                while ((count = reader.read()) != -1 && currentChar == (char) count) {
                    runLength++;
                }

                writer.write(currentChar);
                writer.write(Integer.toString(runLength));

                if (count != -1) {
                    currentChar = (char) count;
                }
            }
        }

    }

    public void decodeFile(File inputFile, File outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            int count;
            while ((count = reader.read()) != -1) {
                char currentChar = (char) count;
                StringBuilder runLengthStr = new StringBuilder();

                while ((count = reader.read()) != -1 && Character.isDigit((char) count)) {
                    runLengthStr.append((char) count);
                }

                int runLength = Integer.parseInt(runLengthStr.toString());
                for (int i = 0; i < runLength; i++) {
                    writer.write(currentChar);
                }
            }
        }
    }
}
