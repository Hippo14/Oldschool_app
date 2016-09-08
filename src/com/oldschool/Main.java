package com.oldschool;

import com.oldschool.image.bitmap.exception.UnknownFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        Application application = new Application();
        try {
            application.initialize();
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("[ERROR] : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
