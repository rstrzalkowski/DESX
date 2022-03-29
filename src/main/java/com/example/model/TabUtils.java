package com.example.model;

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

    public static byte[] permutate(byte[] pattern, byte[] block, int length) {
        byte[] newBlock = new byte[length];
        for (int i = 0; i < length; i++) {
            newBlock[i] = block[pattern[i]-1];
        }
        return newBlock;
    }

    public static byte[] stringToBytes(String text) {
        byte[] result = new byte[text.length()];

        for (int i = 0; i < text.length(); i++) {
            result[i] = (byte) text.charAt(i);
        }

        return result;
    }

    public static byte[] bytesToBits(byte[] block) {
        byte[] bitBlock = new byte[block.length * 8];
        String string = "";
        int counter = 0;
        for (int i = 0; i < block.length * 8; i++) {
            if ((i%8) == 0) {
                String tmp = TabUtils.byteToBinString(block[counter++]);
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

    public static byte[] byteToBits(byte number) {
        byte[] bitBlock = new byte[8];
        String string = "";
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                String tmp = TabUtils.byteToBinString(number);
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
        StringBuilder output = new StringBuilder();

        if(hexStr.length() % 2 == 1) {
            hexStr += "0";
        }

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

    public static String bytesToString(byte[] block) {
        StringBuilder string = new StringBuilder();
        for (byte b : block) {
            String s = TabUtils.byteToBinString(b);
            char c = TabUtils.binStringToChar(s);
            string.append(c);
        }

        return string.toString();

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

    public static byte[] bitsToBytes(byte[] bits) {
        byte[] result = new byte[8];
        byte[] tmp = new byte[8];

        for(int i = 0; i < 8; i++) {
            System.arraycopy(bits, i * 8, tmp, 0, 8);
            String s = TabUtils.bitsToBinString(tmp);
            byte byte1 = TabUtils.binStringToByte(s);
            result[i] = byte1;
        }
        return result;
    }

    public static String bitsToBinString(byte[] bits) {
        StringBuilder s = new StringBuilder();
        for (byte bit : bits) {
            if (bit == 0) {
                s.append("0");
            } else {
                s.append("1");
            }

        }
        return s.toString();
    }

    public static byte binStringToByte(String text) {
        byte b = 0;
        byte temp = 1;

        if (text.charAt(0) == '1') {
            temp = -1;
            for (int i = 7;i > 0; i--) {
                if (text.charAt(i) == '0') {
                    b += temp;

                }
                temp *= 2;
            }
            b -= 1;
        } else {
            for (int i = 7;i > 0; i--) {
                if (text.charAt(i) == '1') {
                    b += temp;

                }
                temp *= 2;
            }
        }
        return b;

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

    public static String bitsToString(byte[] bits) {

        return bytesToString(bitsToBytes(bits));

//        StringBuilder str = new StringBuilder();
//        byte[] tmp = new byte[8];
//        for (int i = 0; i < bits.length / 8; i++) {
//            System.arraycopy(bits, i * 8, tmp, 0, 8);
//            str.append(Character.toString(TabUtils.bitsToInt(tmp)));
//        }
//        return str.toString();

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
