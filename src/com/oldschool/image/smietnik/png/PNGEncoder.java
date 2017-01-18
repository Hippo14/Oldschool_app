package com.oldschool.image.smietnik.png;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by MSI on 2017-01-04.
 */
public class PNGEncoder {

    private final byte[] SIGNATURE = {
            (byte)0x89, (byte)0x50, (byte)0x4e, (byte)0x47,
            (byte)0x0d, (byte)0x0a, (byte)0x1a, (byte)0x0a
    };

    private static final byte[] IHDR = { (byte) 'I', (byte) 'H', (byte) 'D', (byte) 'R' };

    private static final byte[] PLTE = { (byte) 'P', (byte) 'L', (byte) 'T', (byte) 'E' };

    private static final byte[] IDAT = { (byte) 'I', (byte) 'D', (byte) 'A', (byte) 'T' };

    private static final byte[] IEND = { (byte) 'I', (byte) 'E', (byte) 'N', (byte) 'D' };

    private static final byte BIT_DEPTH = (byte) 8;

    /** Indexed color type rendered value. */
    private static final byte COLOR_TYPE_INDEXED  = (byte) 3;

    /** RGB color type rendered value. */
    private static final byte COLOR_TYPE_RGB      = (byte) 2;

    /** RGBA color type rendered value. */
    private static final byte COLOR_TYPE_RGBA     = (byte) 6;

    /** Integer-to-integer map used for RGBA/ARGB conversion. */
    private static final int[] INT_TRANSLATOR_CHANNEL_MAP = new int[]{2, 1, 0, 3};

    private void writeInt(OutputStream out, int i ) throws IOException {
        out.write(new byte[]{(byte) (i >> 24), (byte) ((i >> 16) & 0xff), (byte) ((i >> 8) & 0xff), (byte) (i & 0xff)});
    }
}
