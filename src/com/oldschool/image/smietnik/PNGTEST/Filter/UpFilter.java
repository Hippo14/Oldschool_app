package com.oldschool.image.smietnik.PNGTEST.Filter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MSI on 2017-01-04.
 */
public class UpFilter extends Filter {

    public UpFilter(InputStream imageFile, int length) {
        this.imageFile = imageFile;
        this.length = length;
    }

    @Override
    public int read(int left, int top, int lefttop) throws IOException {
        if(count<length) {
            int r = imageFile.read();
            count++;
            if(r==-1) return -1;
            return (int) (r+top)%256;
        }
        return -1;
    }
}
