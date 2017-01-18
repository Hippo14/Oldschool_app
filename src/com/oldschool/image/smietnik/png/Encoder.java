package com.oldschool.image.smietnik.png;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by MSI on 2017-01-03.
 */
public class Encoder extends Object {

    /** black and white image mode. */
    public static final byte BW_MODE = 0;
    /** grey scale image mode. */
    public static final byte GREYSCALE_MODE = 1;
    /** full color image mode. */
    public static final byte COLOR_MODE = 2;

    OutputStream out;
    CRC32 crc32;
    byte mode;

    public Encoder (OutputStream out) {
        this(out, GREYSCALE_MODE);
    }

    public Encoder(OutputStream out, byte mode) {
        crc32 = new CRC32();
        this.out = out;
        if (mode < 0 || mode > 2)
            throw new IllegalArgumentException("Unknown color mode");
        this.mode = mode;
    }

    void write(int i) throws IOException {
        byte b[]={(byte)((i>>24)&0xff),(byte)((i>>16)&0xff),(byte)((i>>8)&0xff),(byte)(i&0xff)};
        write(b);
    }

    void write(byte b[]) throws IOException {
        out.write(b);
        crc32.update(b);
    }

    public void encode(BufferedImage image) throws IOException {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        final byte id[] = {-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13};
        write(id);
        crc32.reset();
        write("IHDR".getBytes());
        write(width);
        write(height);
        byte head[] = null;
        switch (mode) {
            case BW_MODE:
                head = new byte[]{1, 0, 0, 0, 0};
            break;
            case GREYSCALE_MODE:
                head = new byte[]{8, 0, 0, 0, 0};
            break;
            case COLOR_MODE:
                head = new byte[]{8, 2, 0, 0, 0};
            break;
        }
        write(head);
        write((int) crc32.getValue());
        ByteArrayOutputStream compressed = new ByteArrayOutputStream(65536);
        BufferedOutputStream bos = new BufferedOutputStream(new DeflaterOutputStream(compressed, new Deflater(9)));
        int pixel;
        int color;
        int colorSet;
        switch (mode) {
            case BW_MODE:
            {
                int rest = width%8;
                int bytes = width/8;
                for (int y = 0; y < height; y++) {
                    bos.write(0);
                    for (int x = 0; x < bytes; x++) {
                        colorSet = 0;
                        for (int sh = 0; sh < 8; sh++) {
                            pixel = image.getRGB(x * 8 + sh, y);
                            color = ((pixel >> 16) & 0xff);
                            color += ((pixel >> 8) & 0xff);
                            color += (pixel & 0xff);
                            colorSet <<= 1;
                            if (color >= 3 * 128)
                                colorSet |= 1;
                        }
                        bos.write((byte) colorSet);
                    }
                    if (rest > 0) {
                        colorSet = 0;
                        for (int sh = 0; sh < width % 8; sh++) {
                            pixel = image.getRGB(bytes * 8+ sh, y);
                            color = ((pixel >> 16) & 0xff);
                            color += ((pixel >> 8) & 0xff);
                            color += (pixel & 0xff);
                            colorSet <<= 1;
                            if (color >= 3 * 128)
                                colorSet |= 1;
                        }
                        colorSet <<= 8 - rest;
                        bos.write((byte) colorSet);
                    }
                }
            }
            break;
            case GREYSCALE_MODE:
            {
                for (int y = 0; y < height; y++) {
                    bos.write(0);
                    for (int x = 0; x < width; x++) {
                        pixel = image.getRGB(x, y);
                        color = ((pixel >> 16) & 0xff);
                        color += ((pixel >> 8) & 0xff);
                        color += (pixel & 0xff);
                        bos.write((byte) (color / 3));
                    }
                }
            }
            break;
            case COLOR_MODE:
            {
                for (int y = 0; y < height; y++) {
                    bos.write(0);
                    for (int x = 0; x < width; x++) {
                        pixel = image.getRGB(x, y);
                        bos.write((byte) ((pixel >> 16) & 0xff));
                        bos.write((byte) ((pixel >> 8) & 0xff));
                        bos.write((byte) (pixel & 0xff));
                    }
                }
            }
            break;
        }
        bos.close();
        write(compressed.size());
        crc32.reset();
        write("IDAT".getBytes());
        write(compressed.toByteArray());
        write((int) crc32.getValue());
        write(0);
        crc32.reset();
        write("IEND".getBytes());
        write((int) crc32.getValue());
        out.close();
    }

    public static void encode(BufferedImage image, String filename, byte mode) {
        try {
            BufferedOutputStream file = new BufferedOutputStream(new FileOutputStream(filename));
            Encoder encoder = new Encoder(file, mode);
            encoder.encode(image);
        } catch (IOException e) {
            throw(new RuntimeException("IOException during image writing "+ e));
        }
    }

    public static void encode(BufferedImage image, String filename) {
        encode(image, filename, GREYSCALE_MODE);
    }
}
