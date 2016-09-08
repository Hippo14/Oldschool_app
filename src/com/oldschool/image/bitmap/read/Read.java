package com.oldschool.image.bitmap.read;

import com.oldschool.image.Pixel;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.BmpHeader;
import com.oldschool.image.bitmap.BmpImage;
import com.oldschool.image.bitmap.bits.Bits;
import com.oldschool.image.bitmap.bits.Constants;
import com.oldschool.image.bitmap.exception.UnknownFormatException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KMacioszek on 2016-08-29.
 */
public class Read {

    BmpHeader header;
    BmpImage image;

    public Read(InputStream inputStream) throws IOException, UnknownFormatException {
        LitEndInputStream input = new LitEndInputStream(inputStream);

        // File header
        this.header = readHeader(input);

        // Image
        this.image = readImage(input);
    }

    private BmpHeader readHeader(LitEndInputStream input) throws IOException {
        return new BmpHeader(input);
    }

    private BmpImage readImage(LitEndInputStream input) throws IOException, UnknownFormatException {
        Pixel[] pixels = null;

        // Get color table
        if (header.getBitsPerPixel() <= 8) pixels = readPixels(header, input);

        // Check pixel colors (binary, grayscale, rgb)
        checkPixelColors(pixels);

        // Get pixels
        return getPixels(pixels, input);
    }

    private void checkPixelColors(Pixel[] pixels) {
        if (header.getBitsPerPixel() == Constants.BITS_1)
            header.setBinary(true);
        else if (header.getBitsPerPixel() <= 8 && checkGrayscale(pixels))
            header.setGrayscale(true);
        else
            header.setRGB(true);
    }

    private Pixel[] readPixels(BmpHeader header, LitEndInputStream input) throws IOException {
        Pixel[] pixels = new Pixel[header.getNumColors()];
        for (int i = 0; i < header.getNumColors(); i++) {
            Pixel pixel = new Pixel(input);
            pixels[i] = pixel;
        }

        return pixels;
    }

    private boolean checkGrayscale(Pixel[] pixels) {
        for (int x = 0; x < pixels.length; x++) {
            if (pixels[x].getRed() != pixels[x].getGreen() && pixels[x].getRed() != pixels[x].getBlue()) {
                return false;
            }
        }
        return true;
    }

    private BmpImage getPixels(Pixel[] pixels, LitEndInputStream input) throws IOException, UnknownFormatException {
        if (header.getCompressionType() == 0) {
            if (header.getBitsPerPixel() == Constants.BITS_1)       return Bits.BIT1.read(header, input, pixels);
            else if (header.getBitsPerPixel() == Constants.BITS_8)  return Bits.BIT8.read(header, input, pixels);
            else if (header.getBitsPerPixel() == Constants.BITS_24) return Bits.BIT24.read(header, input, pixels);
        }
        throw new UnknownFormatException("Nieznany format bmp: bitCount= " + header.getBitsPerPixel() + ", compression= " + header.getCompressionType());
    }

    public BmpFile getBmpFile() {
        return new BmpFile(header, image);
    }
}