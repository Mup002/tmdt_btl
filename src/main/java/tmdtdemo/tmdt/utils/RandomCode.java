package tmdtdemo.tmdt.utils;

import java.util.Random;

public class RandomCode {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;
    private static final Random random = new Random();

    public static String genarateCodeForOrder(){
        StringBuilder stringBuilder = new StringBuilder(LENGTH);
        for(int i = 0 ; i < LENGTH ; i++){
            int randomIndex = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }
}
