package com.vision.service;

import java.io.IOException;
import java.util.List;

/**
 * Created by vipul.pant on 7/30/17.
 */
public interface IClassifierService {

    List<String> detectImage(byte[] pixels) throws IOException;
}
