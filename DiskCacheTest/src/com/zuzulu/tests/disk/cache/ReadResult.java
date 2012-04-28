package com.zuzulu.tests.disk.cache;

public class ReadResult {
    long lastTime;
    long lastPos;
    public ReadResult(long lastTime, long lastPos) {
        this.lastTime = lastTime;
        this.lastPos = lastPos;
    }
    public long getLastTime() {
        return lastTime;
    }
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
    public long getLastPos() {
        return lastPos;
    }
    public void setLastPos(long lastPos) {
        this.lastPos = lastPos;
    }
    
}
