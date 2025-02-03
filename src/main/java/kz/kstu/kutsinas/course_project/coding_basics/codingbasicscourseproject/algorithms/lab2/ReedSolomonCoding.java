package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab2;


import java.util.Arrays;


public class ReedSolomonCoding {

    private static final int DATA_SHARDS = 3;
    private static final int PARITY_SHARDS = 2;
    private static final int TOTAL_SHARDS = DATA_SHARDS + PARITY_SHARDS;


    public static byte[][] encode(String input) {
        byte[] data = input.getBytes();
        int shardSize = (int) Math.ceil((double) data.length / DATA_SHARDS);


        byte[][] shards = new byte[TOTAL_SHARDS][shardSize];

        for (int i = 0; i < DATA_SHARDS; i++) {
            System.arraycopy(data, i * shardSize,
                    shards[i], 0,
                    Math.min(data.length - i * shardSize, shardSize));
        }

        for (int k = DATA_SHARDS; k < TOTAL_SHARDS; k++) {
            for (int j = 0; j < shardSize; j++) {
                byte parity = 0;


                for (int i = 0; i < DATA_SHARDS; i++) {
                    parity ^= shards[i][j];
                }

                shards[k][j] = parity;
            }
        }

        return shards;
    }


    /**
     * Имитация ошибки в данных.
     */
    public static void simulateError(byte[][] shards, int shardIndex, int byteIndex) {
        shards[shardIndex][byteIndex] ^= 0xFF;
    }

    /**
     * Декодирование данных с исправлением ошибок.
     */
    public static void decode(byte[][] shards, boolean[] shardPresent) {
        int shardSize = shards[0].length;

        for (int missingShard = 0; missingShard < TOTAL_SHARDS; missingShard++) {
            if (!shardPresent[missingShard]) {
                for (int j = 0; j < shardSize; j++) {
                    byte restoredByte = 0;

                    for (int k = 0; k < TOTAL_SHARDS; k++) {
                        if (shardPresent[k]) {
                            restoredByte ^= shards[k][j];
                        }
                    }

                    shards[missingShard][j] = restoredByte;
                }
            }
        }
    }
    /**
     * Сравнение двух массивов данных.
     */
    public static boolean compareData(byte[][] original, byte[][] recovered) {
        for (int i = 0; i < DATA_SHARDS; i++) {
            if (!Arrays.equals(original[i], recovered[i])) {
                return false;
            }
        }
        return true;
    }


}
