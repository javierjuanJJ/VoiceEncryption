package com.example.voiceencryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public static File createFileFromInputStream(InputStream inputStream, File f) throws IOException {

        OutputStream outputStream = new FileOutputStream(f);
        byte buffer[] = new byte[1024];
        int length = 0;

        while((length=inputStream.read(buffer)) > 0) {
            outputStream.write(buffer,0,length);
        }

        outputStream.close();
        inputStream.close();

        return f;
    }

}
