package com.oldschool.image.png;

/**
 * Created by MSI on 2017-01-07.
 */
public class PngConstants {

    public static byte[] signature = new byte[] {-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13};

    public static byte[] typeBw = new byte[] {1, 0, 0, 0, 0};
    public static byte[] typeGray = new byte[]{8, 0, 0, 0, 0};
    public static byte[] typeColor = new byte[]{8, 2, 0, 0, 0};

}
