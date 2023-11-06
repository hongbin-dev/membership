package me.hongbin.generic.exception;

public class LockConflictException extends RuntimeException {

    public LockConflictException(String msg) {
        super(msg);
    }
}
