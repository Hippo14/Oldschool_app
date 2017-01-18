package com.oldschool.image.smietnik.png;

import java.io.UnsupportedEncodingException;

/**
 * Created by MSI on 2017-01-04.
 */
class PNGChunk {
    private byte[] type;
    private byte[] data;

    public PNGChunk(byte[] type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    public String getTypeString() {
        try {
            return new String(type, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public byte[] getData() {
        return data;
    }

    public long getUnsignedInt(int offset) {
        long value = 0;
        for (int i = 0; i < 4; i++)
            value += (data[offset + i] & 0xff) << ((3 - i) * 8);
        return value;
    }

    public short getUnsignedByte(int offset) {
        return (short) (data[offset] & 0x00ff);
    }

}