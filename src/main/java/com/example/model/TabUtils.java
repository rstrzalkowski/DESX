package com.example.model;

import java.nio.charset.StandardCharsets;

public class TabUtils {

    public static byte[] getInitialPermutationPattern() {
        return new byte[]{
                58, 50, 42, 34, 26, 18, 10, 2,
                60, 52, 44, 36, 28, 20, 12, 4,
                62, 54, 46, 38, 30, 22, 14, 6,
                64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17,  9, 1,
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5,
                63, 55, 47, 39, 31, 23, 15, 7};
    }

    private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);

    public static byte[] permutate(byte[] pattern, byte[] block) {
        byte length = (byte) ((pattern.length-1) / 8 + 1);
        byte[] newBlock = new byte[length];
        for (int i = 0; i < pattern.length; i++) {
            int value = getBit(block, pattern[i] - 1);
            setBit(newBlock, i, value);
        }
        return newBlock;
    }


    public static byte getBit(byte[] block, int index) {
        int bytePos = index / 8;
        byte value = block[bytePos];
        return getBit(value, index);
    }

    public static byte getBit(byte number, int index) {
        return (byte) (number >> (8 - ((index % 8) + 1)) & 0x0001);
    }

    public static void setBit(byte[] block, int index, int val) {
        int bytePos = index / 8;
        int bitPos = index % 8;
        byte oldByte = block[bytePos];
        oldByte = (byte) (((0xFF7F>>bitPos) & oldByte) & 0x00FF);
        byte newByte = (byte) ((val<<(8-(bitPos+1))) | oldByte);
        block[bytePos] = newByte;
    }

    public static byte[] makeBlocks(byte[] data, int length) {
        int numberOfBytes = (8 * data.length - 1)/length + 1;
        byte[] result = new byte[numberOfBytes];
        for (int i = 0; i < numberOfBytes; i++) {
            for (int j = 0; j < length; j++) {
                int value = getBit(data, length * i + j);
                setBit(result,8*i+j,value);
            }
        }
        return result;
    }

    public static byte[] xor(byte[] first, byte[] second) {

        byte[] result = new byte[second.length];

        for (int i = 0; i < second.length; i++) {
            result[i] = (byte) (first[i] ^ second[i]);
        }

        return result;
    }

    public static byte[] hexStringToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] stringToBytes(String text) {
        byte[] result = new byte[text.length()];

        for (int i = 0; i < text.length(); i++) {
            result[i] = (byte) text.charAt(i);
        }

        return result;
    }

    public static String byteToBinString(byte byte1) {
        StringBuilder builder = new StringBuilder();
        for ( int j = 0; j < 8; j++ )
        {
            byte tmp =  (byte) ( byte1 >> j );
            int expect1 = tmp & 1;

            builder.append(expect1);
        }
        return ( builder.reverse().toString() );
    }

    public static String bytesToString(byte[] block) {
        StringBuilder string = new StringBuilder();
        for (byte b : block) {
            String s = TabUtils.byteToBinString(b);
            char c = TabUtils.binStringToChar(s);
            string.append(c);
        }

        return string.toString();

    }

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);

    }

    public static char binStringToChar(String text) {
        char c = 0;
        byte temp = 1;

            for (int i = 7;i >= 0; i--) {
                if (text.charAt(i) == '1') {
                    c += temp;
                }
                temp *= 2;
            }
        return c;

    }
}
