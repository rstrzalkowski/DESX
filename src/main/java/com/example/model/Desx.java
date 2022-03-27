package com.example.model;

import java.util.Arrays;

public class Desx {

    private byte[] expansionPermutationPattern = {
            32,  1,  2,  3,  4,  5,
            4,  5,  6,  7,  8,  9,
            8,  9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32,  1
    };

    private byte[] patternPermutation = {
            16,  7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26,  5, 18, 31, 10,
            2,  8, 24, 14, 32, 27,  3,  9,
            19, 13, 30,  6, 22, 11,  4, 25
    };
    private byte[] finalPermutation = {
            40,  8, 48, 16, 56, 24, 64, 32,
            39,  7, 47, 15, 55, 23, 63, 31,
            38,  6, 46, 14, 54, 22, 62, 30,
            37,  5, 45, 13, 53, 21, 61, 29,
            36,  4, 44, 12, 52, 20, 60, 28,
            35,  3, 43, 11, 51, 19, 59, 27,
            34,  2, 42, 10, 50, 18, 58, 26,
            33,  1, 41,  9, 49, 17, 57, 25
    };

    private final byte[] sBox = {
            14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, // S1
            0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
            4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
            15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13,

            15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, // S2
            3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
            0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
            13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9,

            10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, // S3
            13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
            13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
            1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12,

            7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, // S4
            13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
            10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
            3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14,

            2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, // S5
            14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
            4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
            11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3,

            12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, // S6
            10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
            9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
            4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13,

            4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, // S7
            13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
            1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
            6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12,

            13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, // S8
            1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
            7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
            2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
            };

    private DataBlock[] blocks;
    private byte[] cipherText = new byte[64];
    private DataBlock[] cipherArr;
    private byte[] tmp;
    Key key1;
    Key key2;
    Key key3;

    public Desx(String key1, String key2, String key3) {
        this.key1 = new Key(key1);
        this.key2 = new Key(key2);
        this.key3 = new Key(key3);
    }

    public void divideData(byte[] allBytes) {

        int numberOfWholeBlocks = allBytes.length / 8;
        if(allBytes.length % 8 == 0) {
            blocks = new DataBlock[numberOfWholeBlocks];
            cipherArr = new DataBlock[numberOfWholeBlocks];
        } else {
            blocks = new DataBlock[numberOfWholeBlocks + 1];
            cipherArr = new DataBlock[numberOfWholeBlocks + 1];
        }

        int counter = 0;
        int blockCounter = 0;
        for (int i = 0; i < numberOfWholeBlocks; i++) {
            byte[] tmp = new byte[64];
            for (int j = 0; j < 64; j++) {
                tmp[j] = TabUtils.bytesToBits(allBytes)[counter++];

            }
            tmp = TabUtils.xor(tmp, key1.getBitBlock());  //xorowanie początkowego bloku danych z pierwszym kluczem
            blocks[blockCounter++] = new DataBlock(tmp);
        }
        if (allBytes.length % 8 != 0) {
            byte[] tmp = new byte[64];
            for (int k = 0; k < (allBytes.length % 8) * 8; k++) {
                tmp[k] = TabUtils.bytesToBits(allBytes)[counter++];
            }
            tmp = TabUtils.xor(tmp, key1.getBitBlock()); //xorowanie początkowego bloku danych z pierwszym kluczem
            blocks[blockCounter] = new DataBlock(tmp);
        }

    }

    private int calculatePosition(byte[] tab) {
        byte[] row = { tab[0], tab[5] };
        byte[] column = { tab[1], tab[2], tab[3], tab[4] };

        return TabUtils.bitsToInt(row) * 16 + TabUtils.bitsToInt(column);
    }

    public void encrypt(String text, boolean isEncrypted) {

        byte[] allBytes = TabUtils.stringToBytes(text);

        divideData(allBytes);

        int counter = 0;
        byte[] left;
        byte[] right;


        for (DataBlock block : blocks) {
            left = block.getLPT();
            right = block.getRPT();
            byte[] sixBits = new byte[6];
            byte[] afterSBox = new byte[32];

            byte sBoxResult;

            for (int i = 0; i < 16; i++) {
                tmp = right;                                                                //zapamietanie RPT, by potem przypisać do LPT
                byte[] subKey = key2.roundEncrypt(i, isEncrypted);                                       //generowanie 48 bitowego podklucza poprzez przesuwanie bitów i permutacje
                right = TabUtils.permutate(expansionPermutationPattern, right, 48);   //permutacja rozszerzająca RPT z 32 do 48 bitów
                right = TabUtils.xor(right, subKey);                                        //xorowanie RPT z podkluczem
                for (int j = 0; j < 8; j++) {
                    System.arraycopy(right, j*6, sixBits, 0, 6);
                    sBoxResult = sBox[(j * 64) + calculatePosition(sixBits)];
                    System.arraycopy(TabUtils.byteToBits(sBoxResult), 4, afterSBox, j * 4, 4); //od 4 bo bajta przedstawiamy jako 8 bitów a potrzebujemy 4 najmniejszych
                }
                afterSBox = TabUtils.permutate(patternPermutation, afterSBox, 32);

                right = TabUtils.xor(left, afterSBox);
                left = tmp;
        }
            System.arraycopy(right, 0, cipherText, 0, 32);
            System.arraycopy(left, 0, cipherText, 32, 32);

            cipherText = TabUtils.permutate(finalPermutation, cipherText, 64);
            cipherText = TabUtils.xor(key3.getBitBlock(), cipherText);  //xorowanie końcowego bloku danych z trzecim kluczem
            cipherArr[counter++] = new DataBlock(cipherText);
        }

    }


    public String getCipherText() {
        StringBuilder cipherText = new StringBuilder();
        for (DataBlock dataBlock : cipherArr) {
            cipherText.append(TabUtils.bitsToHex(dataBlock.getPrimaryBitBlock()));

        }
        return cipherText.toString();
    }

    public String getPlainText() {
        StringBuilder plainText = new StringBuilder();
        for (DataBlock dataBlock : cipherArr) {
            plainText.append(TabUtils.bitsToInt(dataBlock.getPrimaryBitBlock()));

        }
        return plainText.toString();
    }

    public String getDecipherTextString() {
        char[] tab = new char[decipherText.length/8];
        for (int i = 0; i < decipherText.length/8; i++) {
            tab[i] = (char) (ToTab.toInt(ToTab.cutTab(decipherText, i*8, 8)));
        }

        return new String(tab);
    }


}
