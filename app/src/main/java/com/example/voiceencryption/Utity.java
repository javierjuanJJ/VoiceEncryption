package com.example.voiceencryption;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utity {
    public static String getOnlyString(String text){
        Pattern pattern = Pattern.compile("[^a-z A-Z]");
        Matcher matcher = pattern.matcher(text);
        String mint = matcher.replaceAll("");
        return mint;
    }
    public static String getOnlyDigits(String text){
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(text);
        String numDigits = matcher.replaceAll("");
        return numDigits;
    }
}
