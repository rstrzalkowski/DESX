package com.example.model;


import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        String s = "01100101";
        byte[] bits = {1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1};
        System.out.println(Arrays.toString(TabUtils.bitsToBytes(bits)));
        System.out.println((char) 216);



    }

}
