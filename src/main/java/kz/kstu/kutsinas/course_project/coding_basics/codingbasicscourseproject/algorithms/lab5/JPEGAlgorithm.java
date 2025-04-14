package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab5;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class JPEGAlgorithm {
    private static final int BLOCK_SIZE = 8;
    private static final double C0 = 1.0 / Math.sqrt(2);

    private static int[][] quantizationMatrix = {
            {16, 11, 10, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 100, 103, 99}
    };

    public static final int[][] zigZagOrder = {
            {0, 0},{0, 1},{1, 0},{2, 0},{1, 1},{0, 2},{0, 3},{1, 2},
            {2, 1},{3, 0},{4, 0},{3, 1},{2, 2},{1, 3},{0, 4},{0, 5},
            {1, 4},{2, 3},{3, 2},{4, 1},{5, 0},{6, 0},{5, 1},{4, 2},
            {3, 3},{2, 4},{1, 5},{0, 6},{0, 7},{1, 6},{2, 5},{3, 4},
            {4, 3},{5, 2},{6, 1},{7, 0},{7, 1},{6, 2},{5, 3},{4, 4},
            {3, 5},{2, 6},{1, 7},{2, 7},{3, 6},{4, 5},{5, 4},{6, 3},
            {7, 2},{7, 3},{6, 4},{5, 5},{4, 6},{3, 7},{4, 7},{5, 6},
            {6, 5},{7, 4},{7, 5},{6, 6},{5, 7},{6, 7},{7, 6},{7, 7}
    };


    public static void jpegAlgorithm(String inputPath, String outputPath, double compressionLevel) throws IOException {
        BufferedImage img = ImageIO.read(new File(inputPath));
        int width = img.getWidth();
        int height = img.getHeight();

        int[][] scaledQuantTable = scaleQuantizationMatrix(compressionLevel);

        double[][] Y = new double[height][width];
        double[][] Cb = new double[height][width];
        double[][] Cr = new double[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                Y[y][x] = 0.299 * r + 0.587 * g + 0.114 * b;
                Cb[y][x] = 128 - 0.168736 * r - 0.331364 * g + 0.5 * b;
                Cr[y][x] = 128 + 0.5 * r - 0.418688 * g - 0.081312 * b;
            }
        }

        int blocksX = (int) Math.ceil((double) width / BLOCK_SIZE);
        int blocksY = (int) Math.ceil((double) height / BLOCK_SIZE);

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputPath))) {
            for (int by = 0; by < blocksY; by++) {
                for (int bx = 0; bx < blocksX; bx++) {
                    double[][] yBlock = new double[BLOCK_SIZE][BLOCK_SIZE];
                    double[][] cbBlock = new double[BLOCK_SIZE][BLOCK_SIZE];
                    double[][] crBlock = new double[BLOCK_SIZE][BLOCK_SIZE];

                    for (int dy = 0; dy < BLOCK_SIZE; dy++) {
                        for (int dx = 0; dx < BLOCK_SIZE; dx++) {
                            int px = bx * BLOCK_SIZE + dx;
                            int py = by * BLOCK_SIZE + dy;
                            yBlock[dy][dx] = (px < width && py < height) ? Y[py][px] - 128 : 0;
                            cbBlock[dy][dx] = (px < width && py < height) ? Cb[py][px] - 128 : 0;
                            crBlock[dy][dx] = (px < width && py < height) ? Cr[py][px] - 128 : 0;
                        }
                    }

                    // DCT
                    double[][] dctY = applyDCT(yBlock);
                    double[][] dctCb = applyDCT(cbBlock);
                    double[][] dctCr = applyDCT(crBlock);

                    // Quantize
                    int[][] qY = quantize(dctY, scaledQuantTable);
                    int[][] qCb = quantize(dctCb, scaledQuantTable);
                    int[][] qCr = quantize(dctCr, scaledQuantTable);

                    // ZigZag
                    int[] zzY = zigZagScan(qY);
                    int[] zzCb = zigZagScan(qCb);
                    int[] zzCr = zigZagScan(qCr);

                    // RLE
                    List<int[]> rleY = runLengthEncode(zzY);
                    List<int[]> rleCb = runLengthEncode(zzCb);
                    List<int[]> rleCr = runLengthEncode(zzCr);

                    Map<Integer, String> huffmanY = buildHuffmanCodes(rleY);
                    Map<Integer, String> huffmanCb = buildHuffmanCodes(rleCb);
                    Map<Integer, String> huffmanCr = buildHuffmanCodes(rleCr);


                    writeHuffmanTable(dos, huffmanY);
                    writeHuffmanEncoded(dos, rleY, huffmanY);

                    writeHuffmanTable(dos, huffmanCb);
                    writeHuffmanEncoded(dos, rleCb, huffmanCb);

                    writeHuffmanTable(dos, huffmanCr);
                    writeHuffmanEncoded(dos, rleCr, huffmanCr);

                    writeRLEBlock(dos, rleY);
                    writeRLEBlock(dos, rleCb);
                    writeRLEBlock(dos, rleCr);
                }
            }
        }
    }

    private static void writeRLEBlock(DataOutputStream dos, List<int[]> rle) throws IOException {
        for (int[] pair : rle) {
            dos.writeByte(pair[0]);
            dos.writeShort(pair[1]);
        }
        dos.writeByte(0);
        dos.writeShort(0);
    }

    public static double[][] applyDCT(double[][] block) {
        double[][] dctBlock = new double[BLOCK_SIZE][BLOCK_SIZE];

        for (int u = 0; u < BLOCK_SIZE; u++) {
            for (int v = 0; v < BLOCK_SIZE; v++) {
                double sum = 0.0;

                for (int x = 0; x < BLOCK_SIZE; x++) {
                    for (int y = 0; y < BLOCK_SIZE; y++) {
                        sum += block[x][y] *
                                Math.cos(((2 * x + 1) * u * Math.PI) / (2 * BLOCK_SIZE)) *
                                Math.cos(((2 * y + 1) * v * Math.PI) / (2 * BLOCK_SIZE));
                    }
                }

                double cu = (u == 0) ? C0 : 1.0;
                double cv = (v == 0) ? C0 : 1.0;
                dctBlock[u][v] = 0.25 * cu * cv * sum;
            }
        }

        return dctBlock;
    }

    public static int[][] quantize(double[][] dctBlock, int[][] quantTable) {
        int[][] result = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                result[i][j] = (int) Math.round(dctBlock[i][j] / quantTable[i][j]);
            }
        }
        return result;
    }

    static double[][] dequantize(int[][] quantBlock, int[][] quantTable) {
        double[][] result = new double[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                result[i][j] = quantBlock[i][j] * quantTable[i][j];
            }
        }
        return result;
    }

    public static int[] zigZagScan(int[][] block) {
        int[] zigzag = new int[64];

        int[][] zigzagOrder = {
                {0, 0}, {0, 1}, {1, 0}, {2, 0}, {1, 1}, {0, 2}, {0, 3}, {1, 2},
                {2, 1}, {3, 0}, {4, 0}, {3, 1}, {2, 2}, {1, 3}, {0, 4}, {0, 5},
                {1, 4}, {2, 3}, {3, 2}, {4, 1}, {5, 0}, {6, 0}, {5, 1}, {4, 2},
                {3, 3}, {2, 4}, {1, 5}, {0, 6}, {0, 7}, {1, 6}, {2, 5}, {3, 4},
                {4, 3}, {5, 2}, {6, 1}, {7, 0}, {7, 1}, {6, 2}, {5, 3}, {4, 4},
                {3, 5}, {2, 6}, {1, 7}, {2, 7}, {3, 6}, {4, 5}, {5, 4}, {6, 3},
                {7, 2}, {7, 3}, {6, 4}, {5, 5}, {4, 6}, {3, 7}, {4, 7}, {5, 6},
                {6, 5}, {7, 4}, {7, 5}, {6, 6}, {5, 7}, {6, 7}, {7, 6}, {7, 7}
        };

        for (int i = 0; i < 64; i++) {
            int x = zigzagOrder[i][0];
            int y = zigzagOrder[i][1];
            zigzag[i] = block[x][y];
        }

        return zigzag;
    }

    public static List<int[]> runLengthEncode(int[] zigzag) {
        List<int[]> encoded = new ArrayList<>();
        int zeroCount = 0;

        for (int i = 1; i < zigzag.length; i++) {
            if (zigzag[i] == 0) {
                zeroCount++;
            } else {
                encoded.add(new int[]{zeroCount, zigzag[i]});
                zeroCount = 0;
            }
        }

        encoded.add(new int[]{0, 0});
        return encoded;
    }

    private static int[][] scaleQuantizationMatrix(double level) {
        int[][] scaledMatrix = new int[8][8];
        double scale;

        if (level < 50) {
            scale = 5000 / level;
        } else {
            scale = 200 - 2 * level;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int val = (int) Math.round((quantizationMatrix[i][j] * scale) / 100.0);
                scaledMatrix[i][j] = Math.max(1, val);
            }
        }

        return scaledMatrix;
    }

    static Map<Integer, String> buildHuffmanCodes(List<int[]> rle) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int[] pair : rle) {
            int val = pair[1];
            freqMap.put(val, freqMap.getOrDefault(val, 0) + 1);
        }

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (var entry : freqMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            pq.add(new HuffmanNode(left, right));
        }

        HuffmanNode root = pq.poll();
        Map<Integer, String> codes = new HashMap<>();
        generateCodes(root, "", codes);
        return codes;
    }

    static void generateCodes(HuffmanNode node, String code, Map<Integer, String> codes) {
        if (node == null) return;
        if (node.isLeaf()) {
            codes.put(node.value, code);
        }
        generateCodes(node.left, code + "0", codes);
        generateCodes(node.right, code + "1", codes);
    }

    private static void writeHuffmanEncoded(DataOutputStream dos, List<int[]> rle, Map<Integer, String> codes) throws IOException {
        BitSet bits = new BitSet();
        int bitIndex = 0;

        for (int[] pair : rle) {
            String code = codes.get(pair[1]);
            for (char c : code.toCharArray()) {
                bits.set(bitIndex++, c == '1');
            }
        }

        byte[] byteArray = bits.toByteArray();
        dos.writeInt(byteArray.length); // количество байт
        dos.write(byteArray);
    }

    private static void writeHuffmanTable(DataOutputStream dos, Map<Integer, String> codes) throws IOException {
        dos.writeInt(codes.size());
        for (Map.Entry<Integer, String> entry : codes.entrySet()) {
            dos.writeInt(entry.getKey()); // записываем значение
            dos.writeInt(entry.getValue().length()); // длину кода
            dos.writeBytes(entry.getValue()); // сам код
        }
    }

    private static Map<String, Integer> readHuffmanTable(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        Map<String, Integer> table = new HashMap<>();
        for (int i = 0; i < size; i++) {
            int value = dis.readInt();
            int length = dis.readInt();
            byte[] bytes = new byte[length];
            dis.readFully(bytes);
            String code = new String(bytes);
            table.put(code, value);
        }
        return table;
    }

    private static List<int[]> decodeHuffman(DataInputStream dis, Map<String, Integer> huffmanTable) throws IOException {
        int byteLength = dis.readInt();
        byte[] byteArray = new byte[byteLength];
        dis.readFully(byteArray);
        BitSet bits = BitSet.valueOf(byteArray);

        List<int[]> rle = new ArrayList<>();
        StringBuilder currentCode = new StringBuilder();
        int i = 0;

        while (i < byteLength * 8) {
            currentCode.append(bits.get(i) ? '1' : '0');
            i++;

            if (huffmanTable.containsKey(currentCode.toString())) {
                int value = huffmanTable.get(currentCode.toString());

                if (value == 0) break; // EOB
                rle.add(new int[]{0, value});
                currentCode.setLength(0);
            }
        }

        rle.add(new int[]{0, 0}); // EOB
        return rle;
    }

    public static void jpegDecode(String inputPath, String outputPath, double compressionLevel) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputPath))) {
            // Предполагаем, что в начале файла сохранены размеры изображения
            int width = dis.readInt();
            int height = dis.readInt();

            int[][] quantTable = scaleQuantizationMatrix(compressionLevel);
            int blocksX = (int) Math.ceil(width / 8.0);
            int blocksY = (int) Math.ceil(height / 8.0);

            // Чтение таблиц Хаффмана
            Map<String, Integer> huffmanTableY = readHuffmanTable(dis);
            Map<String, Integer> huffmanTableCb = readHuffmanTable(dis);
            Map<String, Integer> huffmanTableCr = readHuffmanTable(dis);

            double[][] Y = new double[height][width];
            double[][] Cb = new double[height][width];
            double[][] Cr = new double[height][width];

            for (int by = 0; by < blocksY; by++) {
                for (int bx = 0; bx < blocksX; bx++) {
                    int[][] qY = decodeBlock(dis, huffmanTableY);
                    int[][] qCb = decodeBlock(dis, huffmanTableCb);
                    int[][] qCr = decodeBlock(dis, huffmanTableCr);

                    double[][] yBlock = applyIDCT(dequantize(qY, quantTable));
                    double[][] cbBlock = applyIDCT(dequantize(qCb, quantTable));
                    double[][] crBlock = applyIDCT(dequantize(qCr, quantTable));

                    for (int dy = 0; dy < 8; dy++) {
                        for (int dx = 0; dx < 8; dx++) {
                            int px = bx * 8 + dx;
                            int py = by * 8 + dy;
                            if (px < width && py < height) {
                                Y[py][px] = yBlock[dy][dx] + 128;
                                Cb[py][px] = cbBlock[dy][dx] + 128;
                                Cr[py][px] = crBlock[dy][dx] + 128;
                            }
                        }
                    }
                }
            }

            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    double yy = Y[y][x];
                    double cb = Cb[y][x];
                    double cr = Cr[y][x];

                    int r = (int) Math.round(yy + 1.402 * (cr - 128));
                    int g = (int) Math.round(yy - 0.344136 * (cb - 128) - 0.714136 * (cr - 128));
                    int b = (int) Math.round(yy + 1.772 * (cb - 128));

                    r = Math.min(255, Math.max(0, r));
                    g = Math.min(255, Math.max(0, g));
                    b = Math.min(255, Math.max(0, b));

                    int rgb = (r << 16) | (g << 8) | b;
                    result.setRGB(x, y, rgb);
                }
            }

            ImageIO.write(result, "png", new File(outputPath));
        }
    }
    private static int[][] decodeBlock(DataInputStream dis, Map<String, Integer> huffmanTable) throws IOException {
        int length = dis.readInt();
        byte[] data = new byte[length];
        dis.readFully(data);

        BitSet bitSet = BitSet.valueOf(data);
        StringBuilder current = new StringBuilder();
        List<Integer> values = new ArrayList<>();
        int bitIndex = 0;

        while (bitIndex < bitSet.length()) {
            current.append(bitSet.get(bitIndex++) ? '1' : '0');
            if (huffmanTable.containsKey(current.toString())) {
                int val = huffmanTable.get(current.toString());
                values.add(val);
                current.setLength(0);
            }
        }

        int[] zz = new int[64];
        int i = 1;
        zz[0] = 0;

        for (int val : values) {
            if (val == 0 && i < 64) {
                zz[i++] = 0;
            } else if (i < 64) {
                zz[i++] = val;
            }
        }

        return inverseZigZag(zz);
    }

    public static int[][] inverseZigZag(int[] zigzag) {
        int[][] block = new int[8][8];
        int[][] zigzagOrder = JPEGAlgorithm.zigZagOrder;

        for (int i = 0; i < 64; i++) {
            int x = zigzagOrder[i][0];
            int y = zigzagOrder[i][1];
            block[x][y] = zigzag[i];
        }

        return block;
    }

    public static double[][] applyIDCT(double[][] input) {
        double[][] output = new double[8][8];

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                double sum = 0.0;
                for (int u = 0; u < 8; u++) {
                    for (int v = 0; v < 8; v++) {
                        double cu = (u == 0) ? 1 / Math.sqrt(2) : 1.0;
                        double cv = (v == 0) ? 1 / Math.sqrt(2) : 1.0;
                        sum += cu * cv *
                                input[u][v] *
                                Math.cos((2 * x + 1) * u * Math.PI / 16.0) *
                                Math.cos((2 * y + 1) * v * Math.PI / 16.0);
                    }
                }
                output[x][y] = 0.25 * sum;
            }
        }

        return output;
    }

}
