package com.zuzulu.tests.disk.cache;

public class WriteResult {
    long written;
    long lastTime;
    long duration;
    
    public WriteResult(long written, long lastTime, long duration) {
        this.written = written;
        this.lastTime = lastTime;
        this.duration = duration;
    }
    
    public long getWritten() {
        return written;
    }
    public void setWritten(long written) {
        this.written = written;
    }
    public long getLastTime() {
        return lastTime;
    }
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
}
