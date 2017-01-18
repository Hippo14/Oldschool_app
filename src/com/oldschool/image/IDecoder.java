package com.oldschool.image;

import com.oldschool.image.virtual.Image;

import java.io.IOException;

/**
 * Created by MSI on 2017-01-07.
 */
public interface IDecoder {

    Image decode() throws IOException;

}
