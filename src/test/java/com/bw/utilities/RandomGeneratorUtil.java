package com.bw.utilities;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class RandomGeneratorUtil {
    private static Faker faker = new Faker(new Locale("en-US"));

    public static synchronized String randomStringGenerator(int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        System.out.println(output);
        return output;
    }

    public static synchronized Integer getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(100);
    }
    
    public static String getRandomNumber(int range) {
        String randomInt = ""+((int)(Math.random()*range));
        return randomInt;
    }

    public static synchronized String getRandomFirstName() {
        String firstName = faker.name().firstName();
        return firstName;
    }

    public static synchronized String getRandomLastName() {
        String lastName = faker.name().lastName();
        return lastName;
    }

    public static synchronized String getRandomEmailAddress() {
        String email = faker.bothify("????##@gmail.com");
        return email;
    }

    public static synchronized Integer getRandomNumber(int lowerBound, int upperBound){
        Random random = new Random();
        Integer value = random.nextInt(upperBound - lowerBound) + lowerBound;
        return  value;
    }
    
}
