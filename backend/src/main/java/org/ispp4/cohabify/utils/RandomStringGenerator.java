package org.ispp4.cohabify.utils;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class RandomStringGenerator {

    private SecureRandom srd = new SecureRandom();
    private char[] accepted_characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-.".toCharArray();

    public String getRandomStringUrlSafe(Integer size) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < size; i++) {
            stringBuilder.append(accepted_characters[srd.nextInt(accepted_characters.length)]);
        }

        return stringBuilder.toString();
    }

    public String extendStringWithRandomCharactersUrlSafe(String string, Integer size) {
        if(string.length() >= size) {
            return string;
        }

        StringBuilder stringBuilder = new StringBuilder(string);
        for(int i = string.length(); i < size; i++) {
            stringBuilder.append(accepted_characters[srd.nextInt(accepted_characters.length)]);
        }

        return stringBuilder.toString();
    }

}
