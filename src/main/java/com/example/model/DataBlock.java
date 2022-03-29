package com.example.model;

public class DataBlock {

    private byte[] byteBlock;


    public DataBlock(byte[] block) {
        byteBlock = block.clone();
    }

    public byte[] getPermutatedBitBlock() {
        return TabUtils.permutate(TabUtils.getInitialPermutationPattern(), TabUtils.bytesToBits(byteBlock), 64);
    }

    public byte[] getLPT() {
        byte[] LPT = new byte[32];
        System.arraycopy(getPermutatedBitBlock(), 0, LPT, 0, 32);
        return LPT;
    }

    public byte[] getRPT() {
        byte[] RPT = new byte[32];
        System.arraycopy(getPermutatedBitBlock(), 32, RPT, 0, 32);
        return RPT;
    }

    public void xorBlock(byte[] arr) {
        byte[] tmp = TabUtils.bytesToBits(byteBlock);
        byte[] xored = TabUtils.xor(tmp, arr);
        byteBlock = TabUtils.bitsToBytes(xored);
    }

    public byte[] getPrimaryBitBlock() {
        return TabUtils.bytesToBits(byteBlock);
    }

}
