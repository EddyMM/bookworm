package com.eddy.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestUtils {

    public static String readFile(String pathName) throws IOException {
        BufferedReader inputStreamReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(pathName)));

        String jsonString;
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while((line = inputStreamReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        jsonString = stringBuilder.toString();
        return jsonString;
    }

}
