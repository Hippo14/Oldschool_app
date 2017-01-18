package com.oldschool.image.png;

import com.oldschool.image.IEncoder;
import com.oldschool.image.virtual.Image;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by MSI on 2017-01-07.
 */
public class Encoder extends Object implements IEncoder {

    public static final byte BW_MODE = 0;
    public static final byte GREYSCALE_MODE = 1;
    public static final byte COLOR_MODE = 2;

    private final OutputStream out;
    private final Image image;
    private final CRC32 crc32;

    public Encoder (OutputStream out, Image image) {
        this.out = out;
        this.image = image;
        this.crc32 = new CRC32();
    }

    @Override
    public void encode() throws IOException {
        // Write Signature
        writeSignature();
        crc32.reset();

        // Write Header
        writeIhdr();

        write((int) crc32.getValue());

        // Write IDAT
        writeImageData();
        crc32.reset();

        // Write IEND
        writeIend();

        // Close stream
        out.close();
    }

    private void writeIend() throws IOException {
        write("IEND".getBytes());
        write((int) crc32.getValue());
    }

    private void writeImageData() throws IOException {
        ByteArrayOutputStream compressed = new ByteArrayOutputStream(65536);
        BufferedOutputStream bos = new BufferedOutputStream(new DeflaterOutputStream(compressed, new Deflater(9)));

        int width = image.getWidth();
        int height = image.getHeight();
        int color;
        int colorSet;
        int pixel;

        switch (image.getType()) {
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
    }

    private void writeIhdr() throws IOException {
        write("IHDR".getBytes());

        write(image.getWidth());
        write(image.getHeight());
        switch (image.getType()) {
            case Encoder.BW_MODE:
                write(PngConstants.typeBw);
            break;
            case Encoder.GREYSCALE_MODE:
                write(PngConstants.typeGray);
            break;
            case Encoder.COLOR_MODE:
                write(PngConstants.typeColor);
            break;
        }
    }

    private void writeSignature() throws IOException {
        write(image.getSignature());
    }

    private void write(int i) throws IOException {
        byte b[]={(byte)((i>>24)&0xff),(byte)((i>>16)&0xff),(byte)((i>>8)&0xff),(byte)(i&0xff)};
        write(b);
    }

    private void write(byte b[]) throws IOException {
        out.write(b);
        crc32.update(b);
    }
}
