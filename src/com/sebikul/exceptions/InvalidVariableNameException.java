package com.sebikul.exceptions;

/**
 * Created by sekul on 03/06/14.
 */
public class InvalidVariableNameException extends Exception {


    public InvalidVariableNameException() {
    }

    public InvalidVariableNameException(String varName) {
        super(varName);
    }

}
