package com.zuzulu.tests.disk.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.Timer;

public class WriteTest {
    public final static byte zeroSector[] = new byte[512];

    public static WriteResult runWriteTest(File scratchFile,
            int bufferSize, long delay, boolean sync) throws Exception {
        FileOutputStream fos = new FileOutputStream(scratchFile);

        // preallocate the file
        for (int i = 0; i < 256 * 1024; i++) {
            fos.write(zeroSector);
        }
        FileChannel c = fos.getChannel();
        c.force(true);
        // make sure everything is synced and settled
        Thread.sleep(1000);

        c.position(0);
        long runTime = 1000 + new Random().nextInt(4000);
        Timer timer = new Timer(true);
        WriteTestTimerTask writeTestTimerTask = new WriteTestTimerTask(delay);
        timer.schedule(writeTestTimerTask, runTime);
        long start = System.nanoTime();
        long lastTime = 0;
        long lastPos = 0;
        ByteBuffer time = ByteBuffer.wrap(new byte[bufferSize]);
        try {
            while (true) {
                long now = System.nanoTime();
                time.clear();
                time.putLong(now);
                time.clear();
                synchronized (writeTestTimerTask) {
                    c.write(time);
                    if (sync) {
                        c.force(false);
                    }
                }
                lastPos = c.position();
                lastTime = now;
            }
        } catch (IOException e) {
            try {
                c.close();
            } catch(IOException ee) {}
            try {
                fos.close();
            } catch(IOException ee) {}
            // the unmount will not have suceeded in the killDisk
            // from the timer since we still had a file descriptor
            // open
            WriteBackCacheTest.unmountDisk();
            return new WriteResult(lastPos, lastTime, lastTime - start);
        }
    }
}
