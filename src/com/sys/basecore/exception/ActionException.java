package com.sys.basecore.exception;

public class ActionException extends SysException {
    public ActionException() {
        super("ActionException,0000000000000000");
    }

    public ActionException(String message) {
        super(message);
    }
}
