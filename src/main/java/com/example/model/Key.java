package com.example.model;


public class Key {

    private final String strKey;
    private final byte[] byteBlock;
    private final byte[] leftBlock;
    private final byte[] rightBlock;
    private final byte[] connectedBlock = new byte[7];

    private final byte[] pattern56to48 = {
            14, 17, 11, 24,  1,  5,  3,
            28, 15,  6, 21, 10, 23, 19,
            12,  4, 26,  8, 16,  7, 27,
            20, 13,  2, 41, 52, 31, 37,
            47, 55, 30, 40, 51, 45, 33,
            48, 44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32};


    private final byte[] shifts = {
            1, 1, 2, 2, 2, 2, 2, 2,
            1, 2, 2, 2, 2, 2, 2, 1, 0
    };

    public String getStrKey() {
        return strKey;
    }

    public Key(String hexaKey) {
        byteBlock = TabUtils.hexStringToBytes(hexaKey);
        strKey = hexaKey;
        byte[] leftPattern = {
                57, 49, 41, 33, 25, 17, 9,
                1, 58, 50, 42, 34, 26, 18,
                10, 2, 59, 51, 43, 35, 27,
                19, 11, 3, 60, 52, 44, 36
        };

        byte[] rightPattern = {
                63, 55, 47, 39, 31, 23, 15,
                7, 62, 54, 46, 38, 30, 22,
                14, 6, 61, 53, 45, 37, 29,
                21, 13, 5, 28, 20, 12, 4
        };

        leftBlock = TabUtils.permutate(leftPattern, byteBlock);
        rightBlock = TabUtils.permutate(rightPattern, byteBlock);
    }

    public byte[] getByteBlock() {
        return byteBlock;
    }

    public void leftShift(int n) {
        byte tmpL;
        byte tmpR;

        for (int j = 0; j < n; j++) {
            tmpL = TabUtils.getBit(leftBlock, 0);
            tmpR = TabUtils.getBit(rightBlock, 0);

            for (int i = 0; i < 27; i++) {
                TabUtils.setBit(leftBlock, i, TabUtils.getBit(leftBlock, i + 1));
                TabUtils.setBit(rightBlock, i, TabUtils.getBit(rightBlock, i + 1));
            }
            TabUtils.setBit(leftBlock, 27, tmpL);
            TabUtils.setBit(rightBlock, 27, tmpR);
        }
    }

    public byte[] roundEncrypt(int n, boolean isEncrypted) {
        if(isEncrypted) {
            rightShift(shifts[n]);
        } else {
            leftShift(shifts[n]);
        }
        connectBlock();

        return TabUtils.permutate(pattern56to48, connectedBlock);
    }

    public void rightShift(int n) {
        byte tmpL;
        byte tmpR;

        for (int j = 0; j < n; j++) {
            tmpL = TabUtils.getBit(leftBlock, 27);
            tmpR = TabUtils.getBit(rightBlock, 27);

            for (int i = 27; i > 0; i--) {
                TabUtils.setBit(leftBlock, i, TabUtils.getBit(leftBlock, i - 1));
                TabUtils.setBit(rightBlock, i, TabUtils.getBit(rightBlock, i - 1));
            }
            TabUtils.setBit(leftBlock, 0, tmpL);
            TabUtils.setBit(rightBlock, 0, tmpR);
        }
    }

    private void connectBlock() {
        for (int i = 0; i < 28; i++) {
            TabUtils.setBit(connectedBlock, i, TabUtils.getBit(leftBlock, i));
        }

        for (int j = 28; j < 56; j++) {
            TabUtils.setBit(connectedBlock, j, TabUtils.getBit(rightBlock, j - 28));
        }
    }
}
