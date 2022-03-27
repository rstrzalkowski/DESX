package com.example.model;

import javafx.scene.control.Tab;

public class DataBlock {

    private byte[] bitBlock;
    private byte[] permutatedBitBlock;

    private byte[] initialPermutationPattern = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17,  9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };


    public DataBlock(byte[] block) {
        bitBlock = block.clone();
        permutatedBitBlock = TabUtils.permutate(initialPermutationPattern, bitBlock, 64);
    }

    public byte[] getLPT() {
        byte[] LPT = new byte[32];
        System.arraycopy(permutatedBitBlock, 0, LPT, 0, 32);
        return LPT;
    }

    public byte[] getRPT() {
        byte[] RPT = new byte[32];
        System.arraycopy(permutatedBitBlock, 32, RPT, 0, 32);
        return RPT;
    }

    public byte[] getPrimaryBitBlock() {
        return bitBlock;
    }

}
