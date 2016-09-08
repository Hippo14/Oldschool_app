package com.oldschool.image.bitmap;

import com.oldschool.image.Pixel;
import com.oldschool.image.bitmap.bits.Constants;

import java.awt.image.IndexColorModel;

/**
 * Created by KMacioszek on 2016-09-05.
 */
public class BmpFile {

    BmpHeader header;
    BmpImage image;

    public BmpFile(BmpHeader header, BmpImage image) {
        this.header = header;
        this.image = image;
    }

    public BmpHeader getHeader() {
        return header;
    }

    public BmpImage getImage() {
        return image;
    }

}
