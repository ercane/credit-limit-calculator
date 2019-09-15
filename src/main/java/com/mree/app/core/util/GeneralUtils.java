package com.mree.app.core.util;

import java.util.Random;

public class GeneralUtils {

    public static int generateRandom(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
