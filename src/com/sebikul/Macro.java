package com.sebikul;

import com.sebikul.exceptions.HasNotRunException;
import com.sebikul.exceptions.InvalidVariableNameException;

import java.util.HashMap;

public class Macro extends Program {

    private final String name;
    private final int paramCount;

    public Macro(String name, int paramCount, String program) {
        super(program);
        this.name = name;
        this.paramCount = paramCount;
    }

    public int getParamCount() {
        return paramCount;
    }

    public String getName() {
        return name;
    }

    public void resetValues() {

        hasRun = false;
        variables = new HashMap<String, Variable>();
    }

    public int call(HashMap<String, Integer> params) throws InvalidVariableNameException {

        this.run(params);

        try {
            return this.result();
        } catch (HasNotRunException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
