package com.oldschool.image.smietnik.png;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * Created by MSI on 2017-01-03.
 */
public class Decoder extends Object {

    private InputStream in;

    public Decoder (InputStream in) {
        this.in = in;
    }

    byte read() throws IOException {
        byte b = (byte) in.read();
        return (b);
    }

    byte[] read(int count) throws IOException {
        byte[] result = new byte[count];
        for(int i = 0; i < count; i++) {
            result[i] = read();
        }
        return(result);
    }

    int readInt() throws IOException {
        byte b[] = read(4);
        return(((b[0]&0xff)<<24) +
                ((b[1]&0xff)<<16) +
                ((b[2]&0xff)<<8) +
                ((b[3]&0xff)));
    }

    boolean compare(byte[] b1, byte[] b2) {
        if(b1.length != b2.length) {
            return(false);
        }
        for(int i = 0; i < b1.length; i++) {
            if(b1[i] != b2[i]) {
                return(false);
            }
        }
        return(true);
    }

    void checkEquality(byte[] b1, byte[] b2) {
        if(!compare(b1, b2)) {
            throw(new RuntimeException("Format error"));
        }
    }

    public BufferedImage decode() throws IOException {
        byte[] id = read(12);
        checkEquality(id, new byte[] {-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13});

        byte[] ihdr = read(4);
        checkEquality(ihdr, "IHDR".getBytes());

        int width = readInt();
        int height = readInt();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        byte[] head = read(5);
        int mode;
        if (compare(head, new byte[] {1, 0, 0, 0, 0})) {
            mode = Encoder.BW_MODE;
        }
        else if(compare(head, new byte[]{8, 0, 0, 0, 0})) {
            mode = Encoder.GREYSCALE_MODE;
        } else if(compare(head, new byte[]{8, 2, 0, 0, 0})) {
            mode = Encoder.COLOR_MODE;
        } else {
            throw(new RuntimeException("Format error"));
        }

        // CRC
        readInt();

        int size = readInt();

        byte[] idat = read(4);
        String test = new String(idat, "UTF-8");
        checkEquality(idat, "IDAT".getBytes());


//        boolean running = true;
//        while (running) {
//            byte[] data = read(4);
//            String test = new String(data, "UTF-8");
//            if ("IDAT".equals(test)) {
//
//            }
//        }


        byte[] data = read(size);


        Inflater inflater = new Inflater();
        inflater.setInput(data, 0, size);

        int color;

        try {
            switch (mode) {
                case Encoder.BW_MODE:
                {
                    int bytes = (int) (width / 8);
                    if ((width % 8) != 0) {
                        bytes++;
                    }
                    byte colorSet;
                    byte[] row = new byte[bytes];
                    for (int y = 0; y < height; y++) {
                        inflater.inflate(new byte[1]);
                        inflater.inflate(row);
                        for (int x = 0; x < bytes; x++) {
                            colorSet = row[x];
                            for (int sh = 0; sh < 8; sh++) {
                                if (x * 8 + sh >= width) {
                                    break;
                                }
                                if ((colorSet & 0x80) == 0x80) {
                                    result.setRGB(x * 8 + sh, y, Color.white.getRGB());
                                }
                                else {
                                    result.setRGB(x * 8 + sh, y, Color.black.getRGB());
                                }
                                colorSet <<= 1;
                            }
                        }
                    }
                }
                break;
                case Encoder.GREYSCALE_MODE:
                {
                    byte[] row = new byte[width];
                    for (int y = 0; y < height; y++) {
                        inflater.inflate(new byte[1]);
                        inflater.inflate(row);
                        for (int x = 0; x < width; x++) {
                            color = row[x];
                            result.setRGB(x, y, (color << 16) + (color << 8) + color);
                        }
                    }
                }
                break;
                case Encoder.COLOR_MODE:
                {
                    byte[] row = new byte[width * 3];
                    for (int y = 0; y < height; y++) {
                        inflater.inflate(new byte[1]);
                        inflater.inflate(row);
                        for (int x = 0; x < width; x++) {
                            result.setRGB(x, y,((row[x * 3 + 0]&0xff) << 16) + ((row[x * 3 + 1]&0xff) << 8) + ((row[x * 3 + 2]&0xff)));
                        }
                    }

                }
                break;
            }
        } catch (DataFormatException e) {
            throw(new RuntimeException("ZIP error"+e));
        }

        // CRC
        readInt();
        // 0
        readInt();

        byte[] iend = read(4);
        checkEquality(iend, "IEND".getBytes());

        // CRC
        readInt();
        in.close();

        return result;
    }

    public static BufferedImage decode(String filename) {
        try {
            return (new Decoder(new FileInputStream(filename)).decode());
        } catch (IOException e) {
            throw(new RuntimeException("IOException during image reading"+ e));
        }
    }

}
