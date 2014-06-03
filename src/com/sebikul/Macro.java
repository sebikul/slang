package com.sebikul;

import com.sebikul.exceptions.HasNotRunException;
import com.sebikul.exceptions.InvalidVariableNameException;

import java.util.HashMap;

public class Macro extends Program {


    public Macro(String program) {
        super(program);
    }

    private void resetValues() {

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
