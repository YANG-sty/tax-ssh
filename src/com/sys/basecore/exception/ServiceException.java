package com.sys.basecore.exception;

public class ServiceException extends SysException {
    public ServiceException() {
        super("ServiceException,4444444444444444444");
    }

    public ServiceException(String message) {
        super(message);
    }
}
