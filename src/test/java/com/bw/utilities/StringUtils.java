package com.bw.utilities;

public class StringUtils {
    public static synchronized Integer extractNumberFromString(String input){
        String numberString =  input.replaceAll("[^0-9 ]", "").replaceAll(" +", " ").trim();
        Integer number = Integer.parseInt(numberString);
        return  number;
    }
}
