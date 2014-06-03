package com.sebikul;

import javax.crypto.Mac;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sebikul on 24/05/14.
 */
public class Instruction {

    public static final int TYPE_INCREMENT = 1;
    public static final int TYPE_DECREMENT = 2;
    public static final int TYPE_JUMP = 3;
    public static final int TYPE_MACRO = 4;

    private final int type;
    private final String label;
    private final Variable variable;

    private final Macro macro;
    private final HashMap<String, Variable> macroParams;

    private String jumpToLabel = null;

    public Instruction(int type, String label, Variable variable) {
        this(type, label, variable, null, null);
    }

    public Instruction(int type, String label, Variable variable, Macro macro, HashMap<String, Variable> macroParams) {
        this.type = type;

        this.label = label;

        this.variable = variable;

        this.macro = macro;

        this.macroParams = macroParams;


    }

    public String getJumpToLabel() {
        return jumpToLabel;
    }

    public void setJumpToLabel(String jumpToLabel) {
        this.jumpToLabel = jumpToLabel;
    }

    public int getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public void execute(Program program) throws Exception {

        switch (type) {
            case TYPE_INCREMENT:
                variable.increment();
                break;

            case TYPE_DECREMENT:
                variable.decrement();
                break;

            case TYPE_JUMP:

                if (variable.getValue() == 0) {
                    return;
                }

                if (jumpToLabel == null || jumpToLabel.isEmpty()) {
                    throw new Exception();
                }

                program.jump(jumpToLabel);

                break;

            case TYPE_MACRO:

                HashMap<String, Integer> parameters = new HashMap<String, Integer>();

                for (Map.Entry<String, Variable> param : macroParams.entrySet()) {
                    parameters.put(param.getKey(), param.getValue().getValue());
                }

                int macroResult = macro.call(parameters);

                variable.setValue(macroResult);

                break;


        }

    }

    @Override
    public String toString() {

        String ret = "";

        if (label != null && !label.isEmpty()) {
            ret += "[" + label + "] ";
        }

        switch (type) {
            case TYPE_DECREMENT:
            case TYPE_INCREMENT:
                ret += variable.getName() + " <- " + variable.getName() + (type == TYPE_INCREMENT ? " +" : " -") + " 1";
                break;

            case TYPE_JUMP:
                ret += "IF " + variable.getName() + " != 0 GOTO " + jumpToLabel;
                break;

        }


        return ret;
    }
}
