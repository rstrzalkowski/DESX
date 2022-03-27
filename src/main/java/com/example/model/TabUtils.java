package com.example.model;

public class TabUtils {

    public static byte[] permutate(byte[] pattern, byte[] block, int length) {
        byte[] newBlock = new byte[length];
        for (int i = 0; i < length; i++) {
            newBlock[i] = block[pattern[i]-1];
        }
        return newBlock;
    }


    public static char[] stringToChars(String text) {
        char[] result = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = text.charAt(i);
        }

        return result;
    }

    public static byte[] bytesToBits(byte[] block) {
        byte[] bitBlock = new byte[block.length * 8];
        String string = "";
        int counter = 0;
        for (int i = 0; i < block.length * 8; i++) {
            if ((i%8) == 0) {
                String tmp = Integer.toBinaryString(block[counter++]);
                string = "00000000".substring(tmp.length()) + tmp;
            }
            if(string.charAt(i % 8) == '0') {
                bitBlock[i] = 0;
            } else {
                bitBlock[i] = 1;
            }

        }
        return bitBlock;
    }

    public static byte[] charsToBits(char[] block) {
        byte[] bitBlock = new byte[block.length * 8];
        String string = "";
        int counter = 0;
        for (int i = 0; i < block.length * 8; i++) {
            if ((i%8) == 0) {
                String tmp = Integer.toBinaryString(block[counter++]);
                string = "00000000".substring(tmp.length()) + tmp;
            }
            if(string.charAt(i % 8) == '0') {
                bitBlock[i] = 0;
            } else {
                bitBlock[i] = 1;
            }

        }
        return bitBlock;
    }

    public static byte[] byteToBits(byte number) {
        byte[] bitBlock = new byte[8];
        String string = "";
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                String tmp = Integer.toBinaryString(number);
                string = "00000000".substring(tmp.length()) + tmp;
            }
            if(string.charAt(i) == '0') {
                bitBlock[i] = 0;
            } else {
                bitBlock[i] = 1;
            }

        }
        return bitBlock;
    }

    public static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }

    public static byte[] hexToBits(String input) {
        int n = input.length() * 4;
        StringBuilder inputBuilder = new StringBuilder(Long.toBinaryString(
                Long.parseUnsignedLong(input, 16)));
        while (inputBuilder.length() < n)
            inputBuilder.insert(0, "0");
        input = inputBuilder.toString();
        return binStringToBits(input);
    }

    public static String bitsToHex(byte[] bits) {
        StringBuilder hex = new StringBuilder();
        byte[] tmp = new byte[4];
        for (int i = 0; i < bits.length / 4; i++) {
            System.arraycopy(bits, i * 4, tmp, 0, 4);
            hex.append(Integer.toHexString(bitsToInt(tmp)));
        }
        return hex.toString();

    }

    public static String bitsToString(byte[] bits) {
        StringBuilder str = new StringBuilder();
        byte[] tmp = new byte[8];
        for (int i = 0; i < bits.length / 8; i++) {
            System.arraycopy(bits, i * 8, tmp, 0, 8);
            str.append(Character.toString(TabUtils.bitsToInt(tmp)));
        }
        return str.toString();

    }

    public static byte[] binStringToBits(String bin) {
        byte[] block = new byte[bin.length()];
        for (int i = 0;i < bin.length(); i++) {
            if (bin.charAt(i) == '0') {
                block[i] = 0;
            } else {
                block[i] = 1;
            }

        }
        return block;

    }

    public static int bitsToInt(byte[] tab) {
        int result = 0;
        int temp = 1;

        for (int i = tab.length - 1; i >= 0; i--) {
            result += tab[i] * temp;
            temp *= 2;
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
