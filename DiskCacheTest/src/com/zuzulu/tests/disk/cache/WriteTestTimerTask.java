package com.zuzulu.tests.disk.cache;

import java.util.TimerTask;

/**
 * when triggered this task will kill the disk drive
 * (more specifically, run a script to kill the drive)
 * optionally, a delay may be introduced to allow
 * time for the device to write to media. note, for
 * this to work, the writing thread, must synchronize
 * on the task object before writing to the disk.
 *
 */
public class WriteTestTimerTask extends TimerTask {
    long delay;

    public WriteTestTimerTask(long delay) {
        this.delay = delay;
    }

    public void run() {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
                WriteBackCacheTest.killDisk();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            WriteBackCacheTest.killDisk();
        }
    }

}
