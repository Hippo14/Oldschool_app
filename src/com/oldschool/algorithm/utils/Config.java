package com.oldschool.algorithm.utils;

import java.io.*;
import java.util.Properties;

/**
 * Created by MSI on 2016-09-10.
 */
public class Config {

    public static Properties properties;

    public static void init() {
        properties = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String get(String name) {
        return properties.getProperty(name);
    }

}
