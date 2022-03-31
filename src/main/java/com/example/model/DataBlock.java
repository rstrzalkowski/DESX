package com.example.model;

public class DataBlock {

    private byte[] byteBlock;


    public DataBlock(byte[] block) {
        byteBlock = block.clone();
    }

    public byte[] getPermutatedByteBlock() {
        return TabUtils.permutate(TabUtils.getInitialPermutationPattern(), byteBlock);
    }

    public byte[] getLPT() {
        byte[] LPT = new byte[4];
        System.arraycopy(getPermutatedByteBlock(), 0, LPT, 0, 4);
        return LPT;
    }

    public byte[] getRPT() {
        byte[] RPT = new byte[4];
        System.arraycopy(getPermutatedByteBlock(), 4, RPT, 0, 4);
        return RPT;
    }

    public void xorBlock(byte[] arr) {
        byteBlock = TabUtils.xor(byteBlock, arr);
    }

    public byte[] getPrimaryByteBlock() {
        return byteBlock;
    }

}
