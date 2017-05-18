package de.chrthms.mco.exceptions;

/**
 * Created by christian on 18.05.17.
 */
public class McoRuntimeException extends RuntimeException {

    public McoRuntimeException(String message) {
        super(message);
    }

    public McoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
