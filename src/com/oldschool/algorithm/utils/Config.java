package com.oldschool.algorithm.utils;

import com.oldschool.algorithm.binary.logical.Negation;
import com.oldschool.algorithm.binary.logical.Product;
import com.oldschool.algorithm.binary.logical.Sum;
import com.oldschool.algorithm.binary.logical.XOR;
import com.oldschool.algorithm.grayscale.arytm.SumConst;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by MSI on 2016-09-10.
 */
public class Config {

    public static Properties properties;
    public static HashMap<String, HashMap<String, Object>> menuHashmap;

    public static void init() {
        properties = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            properties.load(input);

            // init menu hashmap
            //init(menuHashmap);

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

    private static void init(HashMap<String, HashMap<String, Object>> menuHashmap) {
        menuHashmap = new HashMap<>();

        HashMap<String, Object> _1 = new HashMap<>();
        _1.put("1.1", com.oldschool.algorithm.binary.logical.Negation.class);
        _1.put("1.2", com.oldschool.algorithm.binary.logical.Sum.class);
        _1.put("1.3", com.oldschool.algorithm.binary.logical.Product.class);
        _1.put("1.4", com.oldschool.algorithm.binary.logical.XOR.class);

        HashMap<String, Object> _2 = new HashMap<>();
        _2.put("2.1", com.oldschool.algorithm.grayscale.arytm.Sum.class);
        _2.put("2.2", com.oldschool.algorithm.grayscale.arytm.SumConst.class);
        _2.put("2.3", com.oldschool.algorithm.grayscale.arytm.Multiplication.class);
        _2.put("2.4", com.oldschool.algorithm.grayscale.arytm.MultiplicationConst.class);
        _2.put("2.5", com.oldschool.algorithm.grayscale.arytm.Exponentation.class);
        _2.put("2.6", com.oldschool.algorithm.grayscale.arytm.Divide.class);
        _2.put("2.7", com.oldschool.algorithm.grayscale.arytm.DivideConst.class);
        _2.put("2.8", com.oldschool.algorithm.grayscale.arytm.Roots.class);
        _2.put("2.9", com.oldschool.algorithm.grayscale.arytm.Log.class);

        HashMap<String, Object> _3 = new HashMap<>();
        _3.put("3.1", com.oldschool.algorithm.rgb.arytm.Sum.class);
        _3.put("3.2", com.oldschool.algorithm.rgb.arytm.SumConst.class);
        _3.put("3.3", com.oldschool.algorithm.rgb.arytm.Multiplication.class);
        _3.put("3.4", com.oldschool.algorithm.rgb.arytm.MultiplicationConst.class);
        _3.put("3.5", com.oldschool.algorithm.rgb.arytm.Exponentation.class);
        _3.put("3.6", com.oldschool.algorithm.rgb.arytm.Divide.class);
        _3.put("3.7", com.oldschool.algorithm.rgb.arytm.DivideConst.class);
        _3.put("3.8", com.oldschool.algorithm.rgb.arytm.Roots.class);
        _3.put("3.9", com.oldschool.algorithm.rgb.arytm.Log.class);

        HashMap<String, Object> _4 = new HashMap<>();
        _4.put("4.1", com.oldschool.algorithm.geomet.Transform.class);
        _4.put("4.2", com.oldschool.algorithm.geomet.Transform.class);
        _4.put("4.3", com.oldschool.algorithm.geomet.Transform.class);
        _4.put("4.4", com.oldschool.algorithm.geomet.Transform.class);
        _4.put("4.5", com.oldschool.algorithm.geomet.Transform.class);
        _4.put("4.6", com.oldschool.algorithm.geomet.Transform.class);
    }

    public static String get(String name) {
        return properties.getProperty(name);
    }

}
