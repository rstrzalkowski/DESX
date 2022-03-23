package com.example.model;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        String s = "01234567";
        byte[] block = {127,1,1,1,1,1,1,1};
        byte[] result = TabUtils.bytesToBits(block);
        System.out.println(Arrays.toString(result));
        result = TabUtils.bytesToBits(TabUtils.stringToBytes(s));


    }

}
