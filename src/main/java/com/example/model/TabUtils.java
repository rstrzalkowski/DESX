package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class TabUtils {

    public static byte[] permutate(byte[] pattern, byte[] block, int length) {
        byte[] newBlock = new byte[length];
        for (int i = 0; i < length; i++) {
            newBlock[i] = block[pattern[i]-1];
        }
        return newBlock;
    }


    public static byte[] bytesToBits(byte[] block) {
        byte[] bitBlock = new byte[64];
        String string = "";
        int counter = 0;
        for (int i = 0; i < 64; i++) {
            if ((i%8) == 0) {
                String tmp = Integer.toBinaryString(block[counter++]);
                string = "00000000".substring(tmp.length()) + tmp;
                System.out.println(string);
            }
            if(string.charAt(i % 8) == '0') {
                bitBlock[i] = 0;
            } else {
                bitBlock[i] = 1;
            }

        }
        return bitBlock;
    }

    public static int[] charsToInts(char[] block) {
        int[] blockInt = new int[8];
        for (int i = 0; i < 8; i++) {
            blockInt[i] = block[i];
        }

        return blockInt;
    }


    public static char[] stringToChars(String text) {
        char[] result = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = text.charAt(i);
        }

        return result;
    }

    public static byte[] stringToBytes(String text) {
        byte[] result = new byte[0];
        for (int i = 0; i < text.length(); i++) {
            result = text.getBytes();
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


}
