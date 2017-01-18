package com.oldschool.image.png;

import com.oldschool.image.IDecoder;
import com.oldschool.image.smietnik.png.Encoder;
import com.oldschool.image.virtual.Image;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * Created by MSI on 2017-01-07.
 */
public class Decoder extends Object implements IDecoder{

    private InputStream in;

    private Image image;


    public Decoder (InputStream in) {
        this.in = in;
        this.image = null;
    }

    @Override
    public Image decode() throws IOException {
        // Initialize image
        this.image = new Image();

        // Check Signature
        checkSignature();

        // Check header
        checkIhdr();

        // Read header data
        readIhdr();

        //FIXME CRC - Check crc checksum
        readInt();

        // Get image size
        readSize();

        // Read IDAT
        readIDat();

        //FIXME CRC - Check crc checksum
        readInt();

        // 0
        readInt();

        // Read IEND
        readIend();

        //FIXME CRC - Check crc checksum
        readInt();

        // Close stream
        in.close();

        return image;
    }

    private void readIend() throws IOException {
        byte[] iend = read(4);
        checkEquality(iend, "IEND".getBytes(), "Unknown IEND type.");
    }

    private void readIDat() throws IOException {
        // Init image matrix
        image.initImageMatrix();

        // Check for IDAT header
        byte[] idat = read(4);
        checkEquality(idat, "IDAT".getBytes(), "Unknown IDAT type.");

        // Read IDAT data
        byte[] data = read(image.getSize());
        Inflater inflater = new Inflater();
        inflater.setInput(data, 0, image.getSize());

        int color;

        int width = image.getWidth();
        int height = image.getHeight();

        try {
            switch (image.getType()) {
                case Encoder.BW_MODE:
                {
                    int bytes = (width / 8);
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
                                    image.setRGB(x * 8 + sh, y, Color.white.getRGB());
                                }
                                else {
                                    image.setRGB(x * 8 + sh, y, Color.black.getRGB());
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
                            image.setRGB(x, y, (color << 16) + (color << 8) + color);
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
                            image.setRGB(x, y,((row[x * 3 + 0]&0xff) << 16) + ((row[x * 3 + 1]&0xff) << 8) + ((row[x * 3 + 2]&0xff)));
                        }
                    }

                }
                break;
            }
        } catch (DataFormatException e) {
            throw(new RuntimeException("Read image data error. " + e));
        }
    }

    private void readSize() throws IOException {
        int size = readInt();
        image.setSize(size);
    }

    private void readIhdr() throws IOException {
        // Read image size
        int width = readInt();
        int height = readInt();

        image.setWidth(width);
        image.setHeight(height);

        // Read image type
        byte[] imageType = read(5);
        if (compare(imageType, PngConstants.typeBw)) {
            image.setType(Encoder.BW_MODE);
        }
        else if (compare(imageType, PngConstants.typeGray)) {
            image.setType(Encoder.GREYSCALE_MODE);
        }
        else if (compare(imageType, PngConstants.typeColor)) {
            image.setType(Encoder.COLOR_MODE);
        }
        else {
            throw (new RuntimeException("Unknown image type error."));
        }
    }

    private void checkIhdr() throws IOException {
        byte[] ihdr = read(4);
        checkEquality(ihdr, "IHDR".getBytes(), "Unknown header format.");
    }

    private void checkSignature() throws IOException {
        byte[] signature = read(12);
        checkEquality(signature, PngConstants.signature, "Unknown PNG signature.");
        image.setSignature(signature);
    }

    private void checkEquality(byte[] a, byte[] b, String errorMsg) {
        if(!compare(a, b)) {
            throw(new RuntimeException(errorMsg));
        }
    }

    int readInt() throws IOException {
        byte b[] = read(4);
        return(((b[0]&0xff)<<24) + ((b[1]&0xff)<<16) + ((b[2]&0xff)<<8) + ((b[3]&0xff)));
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

    byte read() throws IOException {
        byte b = (byte) in.read();
        return b;
    }

    byte[] read (int count) throws IOException {
        byte[] result = new byte[count];
        for (int i  = 0; i < count; i++) {
            result[i] = read();
        }
        return result;
    }

}
