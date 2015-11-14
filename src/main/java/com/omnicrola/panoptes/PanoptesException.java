package com.omnicrola.panoptes;

/**
 * Created by omnic on 10/31/2015.
 */
public class PanoptesException extends RuntimeException {
    private Exception exception;

    public PanoptesException(String message) {
        super(message);
    }

    public PanoptesException(Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }
}
