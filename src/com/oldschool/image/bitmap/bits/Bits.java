package com.oldschool.image.bitmap.bits;

import com.oldschool.image.Pixel;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.BmpHeader;
import com.oldschool.image.bitmap.BmpImage;
import com.oldschool.image.bitmap.read.LitEndInputStream;
import com.oldschool.image.bitmap.write.LitEndOutputStream;

import java.awt.image.IndexColorModel;
import java.io.IOException;

/**
 * Created by KMacioszek on 2016-08-29.
 */
public enum Bits {

    BIT1(Constants.BITS_1) {
        @Override
        public BmpImage read(BmpHeader header, LitEndInputStream input, Pixel[] pixels) throws IOException {
            BmpImage outputImage = new BmpImage(Constants.BITS_1, header.getWidth(), header.getHeight());
            outputImage.setPixels(pixels);

            byte[] r = new byte[pixels.length];
            byte[] g = new byte[pixels.length];
            byte[] b = new byte[pixels.length];

            for (int i = 0; i < pixels.length; i++) {
                r[i] = (byte) pixels[i].getRed();
                g[i] = (byte) pixels[i].getGreen();
                b[i] = (byte) pixels[i].getBlue();
            }

            outputImage.setIndexColorModel(new IndexColorModel(1, 2, r, g, b));

            int dataBitsPerLine = header.getWidth();
            int bitsPerLine = dataBitsPerLine;
            if (bitsPerLine % 32 != 0) bitsPerLine = (bitsPerLine / 32 + 1) * 32;

            int bytesPerLine = (bitsPerLine / 8);
            int[] line = new int[bytesPerLine];

            for (int y = header.getHeight() - 1; y >= 0; y--) {
                for (int i = 0; i < bytesPerLine; i++) line[i] = input.readUnsignedByte();

                for (int x = 0; x < header.getWidth(); x++) {
                    int iByte = x / 8;
                    int i = x % 8;
                    int n = line[iByte];
                    int index = getBit(n, i);
                    outputImage.setBit(index, x, y);
                }
            }

            return outputImage;
        }

        @Override
        public int getBytesPerLine(int width) {
            int bytesPerLine = width / 8;
            if (bytesPerLine * 8 < width) bytesPerLine++;
            if (bytesPerLine % 4 != 0) bytesPerLine = ( bytesPerLine / 4 + 1 ) * 4;
            return bytesPerLine;
        }

        @Override
        public void write(BmpFile file, LitEndOutputStream input) throws IOException {
            int bytesPerLine = getBytesPerLine(file.getHeader().getWidth());

            byte[] line = new byte[bytesPerLine];

            for (int y = file.getHeader().getHeight() - 1; y >=0; y--) {
                for (int i = 0; i < bytesPerLine; i++) line[i] = 0;

                for (int x = 0; x < file.getHeader().getWidth(); x++) {
                    int bi = x / 8;
                    int i = x % 8;
                    int index = file.getImage().getBit(x, y);
                    line[bi] = setBit(line[bi], i, index);
                }

                input.write(line);
            }
        }
    },
    BIT8(Constants.BITS_8) {
        @Override
        public BmpImage read(BmpHeader header, LitEndInputStream input, Pixel[] pixels) throws IOException {
            BmpImage outputImage = new BmpImage(Constants.BITS_8, header.getWidth(), header.getHeight());
            outputImage.setPixels(pixels);

            byte[] r = new byte[pixels.length];
            byte[] g = new byte[pixels.length];
            byte[] b = new byte[pixels.length];

            for (int i = 0; i < pixels.length; i++) {
                r[i] = (byte) pixels[i].getRed();
                g[i] = (byte) pixels[i].getGreen();
                b[i] = (byte) pixels[i].getBlue();
            }

            outputImage.setIndexColorModel(new IndexColorModel(8, header.getNumColors(), r, g, b));


            int dataPerLine = header.getWidth();
            int bytesPerLine = dataPerLine;
            if (bytesPerLine % 4 != 0) bytesPerLine = (bytesPerLine / 4 + 1) * 4;
            int padBytesPerLine = bytesPerLine - dataPerLine;

            for (int y = header.getHeight() - 1; y >= 0; y--) {
                for (int x = 0; x < header.getWidth(); x++) {
                    int iByte = input.readUnsignedByte();
                    outputImage.setValue(iByte, x, y);
                }

                input.skip(padBytesPerLine);
            }

            return outputImage;
        }

        @Override
        public int getBytesPerLine(int width) {
            int bytesPerLine = width;
            return (bytesPerLine % 4 != 0) ? ( bytesPerLine / 4 +1 ) * 4 : bytesPerLine;
        }

        @Override
        public void write(BmpFile file, LitEndOutputStream input) throws IOException {
            int width = file.getHeader().getWidth();
            int height = file.getHeader().getHeight();

            int bytesPerLine = getBytesPerLine(width);

            for (int y = height - 1; y >= 0; y--) {
                for (int x = 0; x < width; x++) {
                    int index = file.getImage().getValue(x, y);
                    input.writeByte(index);
                }
                for (int i = width; i < bytesPerLine; i++) input.writeByte(0);
            }
        }
    },
    BIT24(Constants.BITS_24) {
        @Override
        public BmpImage read(BmpHeader header, LitEndInputStream input, Pixel[] pixels) throws IOException {
            BmpImage outputImage = new BmpImage(Constants.BITS_24, header.getWidth(), header.getHeight());

            int dataPerLine = header.getWidth() * 3;
            int bytesPerLine = dataPerLine;
            if (bytesPerLine % 4 != 0) bytesPerLine = (bytesPerLine / 4 + 1) * 4;
            int padBytesPerLine = bytesPerLine - dataPerLine;

            for (int y = header.getHeight() - 1; y >= 0; y--) {
                for (int x = 0; x < header.getWidth(); x++) {
                    int b = input.readUnsignedByte();
                    int g = input.readUnsignedByte();
                    int r = input.readUnsignedByte();

                    outputImage.setRed(x, y, r);
                    outputImage.setGreen(x, y, g);
                    outputImage.setBlue(x, y, b);
                }
                input.skip(padBytesPerLine);
            }

            return outputImage;
        }

        @Override
        public int getBytesPerLine(int width) {
            int bytesPerLine = width * 3;
            return (bytesPerLine % 4 != 0) ? (bytesPerLine / 4 + 1) * 4 : bytesPerLine;
        }

        @Override
        public void write(BmpFile file, LitEndOutputStream input) throws IOException {
            int width = file.getHeader().getWidth();
            int height = file.getHeader().getHeight();

            int bytesPerLine = getBytesPerLine(width);

            for (int y = height - 1; y >= 0; y--) {
                for (int x = 0; x < width; x++) {
                    int r = file.getImage().getRed(x, y);
                    int g = file.getImage().getGreen(x, y);
                    int b = file.getImage().getBlue(x, y);

                    input.writeByte(b);
                    input.writeByte(g);
                    input.writeByte(r);
                }

                for (int i = width * 3; i < bytesPerLine; i++) input.writeByte(0);
            }
        }
    };

    final int bit;

    private Bits(final int bit) {
        this.bit = bit;
    }

    public abstract BmpImage read(BmpHeader header, LitEndInputStream input, Pixel[] pixels) throws IOException;

    public abstract int getBytesPerLine(int width);

    protected static int getBit(int n, int i) {
        return (n >> (7 - i)) & 1;
    }

    protected static int getNibble(int n, int i) {
        return (n >> (4 * (1 - i))) & 0xF;
    }

    protected static byte setBit(byte b, int i, int index) {
        if (index == 0) {
            b &= ~(1 << (7 - i));
        }
        else {
            b |= 1 << (7 - i);
        }
        return b;
    }

    protected static byte setNibble(byte b, int i, int index) {
        return b |= (index << ((1 - i) * 4));
    }

    public abstract void write(BmpFile file, LitEndOutputStream input) throws IOException;
}
