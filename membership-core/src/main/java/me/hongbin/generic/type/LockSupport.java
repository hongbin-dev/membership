package me.hongbin.generic.type;

public interface LockSupport {

    void getLock(String key);

    void release(String key);
}
