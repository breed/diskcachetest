package com.zuzulu.tests.disk.cache;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * Reads from a file until it can't find anymore timestamps
 *
 */
public class ReadTest {
    public static ReadResult readTest(File f, int bufferSize) throws Exception {
        FileInputStream fis = new FileInputStream(f);
        FileChannel c = fis.getChannel();
        c.position(0);
        ByteBuffer time = ByteBuffer.wrap(new byte[bufferSize]);
        long lastTime = 0;
        long lastPos = 0;
        while (true) {
            time.clear();
            c.read(time);
            time.flip();
            if (time.remaining() < 8) {
                break;
            }
            long t = time.getLong();
            if (t == 0)
                break;
            lastPos = c.position();
            lastTime = t;
        }
        return new ReadResult(lastTime, lastPos);
    }
}
