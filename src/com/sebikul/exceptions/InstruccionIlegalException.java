package com.sebikul.exceptions;

/**
 * Created by sekul on 03/06/14.
 */
public class InstruccionIlegalException extends Exception {

    private final String instruction;

    public InstruccionIlegalException(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }


}
