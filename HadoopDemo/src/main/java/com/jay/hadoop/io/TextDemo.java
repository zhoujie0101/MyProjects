package com.jay.hadoop.io;

/**
 * Created by jay on 17/2/2.
 */
public class TextDemo {

    private static final String[] digit = new String[]{
        "零", "一", "二", "三", "四","五", "六", "七", "八", "九"
    };

    private static final String[] chineseUnit = new String[] {
        "十", "百", "千", "万", "亿"    
    };

    public static void main(String[] args) {
        // 3            三
        // 23           二十三    
        // 123          一百二十三
        // 3,123        三千 一百二十三
        // 23,123       二万三千 一百二十三
        // 123,123      十二万三千 一百二十三
        // 3,123,123    三百 一十二万三千 一百二十三
        // 23,123,123   二千三百 一十二万三千 一百二十三
        // 123,123,123  一亿二千三百 一十二万三千 一百二十三
        // 3,123,123,123 三十 一亿二千三百 一十二万三千 一百二十三
        String result = num2Voice(23_123);
        System.out.println(result);
    }

    private static String num2Voice(int num) {
        if(num < 0) {
            return null;
        }
        String result = "";
        int count = 0;

        while (num / 1000 > 0) {
            int tmp = num % 1000;
            count++;

            int n = tmp % 10;
            result = digit[n] + result;

            int ten = (tmp % 100) / 10;
            result = digit[ten] + chineseUnit[0] + result;

            int hundred = tmp / 100;
            result = digit[hundred] + chineseUnit[1] + result;

            num /= 1000;
        }
        return result;
    }
}
