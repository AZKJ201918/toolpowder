package com.azkj.toolpowder.common.utils;

import java.util.Random;

/**
 * 随机生成一个6位数工具类
 */

public class RandomCodeUtil {

    public static String generateCode() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
}
