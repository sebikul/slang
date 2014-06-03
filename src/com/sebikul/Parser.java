package com.sebikul;

import com.sebikul.exceptions.InstruccionIlegalException;
import com.sebikul.exceptions.InvalidVariableNameException;

import java.util.HashMap;

/**
 * Created by sebikul on 24/05/14.
 */
public class Parser {

    public static final String INSTRUCCION_INCDEC_MATCH = "(\\[[A-Z][0-9]*\\])?\\s?(Y|(X|Z)[0-9]*)\\s(\\<\\-)\\s(Y|(X|Z)[0-9]*)\\s(\\+|\\-)\\s(1)";
    public static final String INSTRUCCION_GOTO_MATCH = "(\\[[A-Z][0-9]*\\])?\\s?IF\\s(Y|(X|Z)[0-9]*)\\s(\\!\\=)\\s(0)\\sGOTO\\s([A-Z][0-9]*)";
    public static final String INSTRUCCION_LABEL_MATCH = "\\[[A-Z][0-9]*\\]";
    public static final String INSTRUCCION_MACRO_MATCH = "(\\[[A-Z][0-9]*\\])?\\s?(Y|(X|Z)[0-9]*)\\s(\\<\\-)\\s[A-Z]+\\((Y|(X|Z)[0-9]*)+(,(Y|(X|Z)[0-9]*))*\\)";

    public static final String INSTRUCCION_VAR_MATCH = "(Y|(X|Z)[0-9]*)";


    static public Instruction fromLine(String line, HashMap<String, Variable> variables, HashMap<String, Macro> macros) throws Exception {

        int type = 0;
        Instruction instruction;
        String[] lineParts = line.split(" ");

        String label = extractLabel(lineParts[0]);
        int offset = label.isEmpty() ? 0 : 1;

        String varName;

        if (line.matches(INSTRUCCION_GOTO_MATCH)) {

            type = Instruction.TYPE_JUMP;

            if (label.isEmpty()) {
                varName = lineParts[1 + offset];
            } else {
                varName = lineParts[1 + offset];
            }

            String jumpTo = lineParts[5 + offset];

            instruction = new Instruction(type, label, getVariable(varName, variables));

            instruction.setJumpToLabel(jumpTo);


        } else if (line.matches(INSTRUCCION_INCDEC_MATCH)) {


            if (label.isEmpty()) {
                varName = lineParts[offset];
            } else {
                varName = lineParts[offset];
            }

            String operation = lineParts[3 + offset];

            if (operation.equals("+")) {
                type = Instruction.TYPE_INCREMENT;
            } else if (operation.equals("-")) {
                type = Instruction.TYPE_DECREMENT;
            }

            if (!varName.equals(lineParts[2 + offset])) {
                throw new InstruccionIlegalException(line);
            }

            instruction = new Instruction(type, label, getVariable(varName, variables));

        } else if (line.matches(INSTRUCCION_MACRO_MATCH)) {

            type = Instruction.TYPE_MACRO;

            if (label.isEmpty()) {
                varName = lineParts[offset];
            } else {
                varName = lineParts[offset];
            }

            String macroName = lineParts[2 + offset].substring(0, lineParts[2 + offset].indexOf("("));

            Macro macro = macros.get(macroName);

            String parametersString = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
            String[] parametersArray = parametersString.split(",");

            if (macro == null || parametersArray.length != macro.getParamCount()) {
                throw new InstruccionIlegalException(line);
            }

            HashMap<String, Variable> macroParams = new HashMap<String, Variable>();

            for (String paramName : parametersArray) {
                Variable paramVar = getVariable(paramName, variables);
                macroParams.put(paramName, paramVar);
            }

            instruction = new Instruction(type, label, getVariable(varName, variables), macro, macroParams);

        } else {
            throw new InstruccionIlegalException(line);
        }

        assert type != 0;

        return instruction;

    }

    static private String extractLabel(String line) {

        String label = "";
        String[] lineParts = line.split(" ");

        if (line.matches(INSTRUCCION_LABEL_MATCH)) {

            label = lineParts[0].substring(lineParts[0].indexOf("[") + 1, lineParts[0].indexOf("]"));

        }

        return label;

    }

    public static Variable getVariable(String varName, HashMap<String, Variable> variables) throws InvalidVariableNameException {

        Variable var;

        if (variables.containsKey(varName)) {
            var = variables.get(varName);
        } else {
            int varType = 0;
            if (varName.startsWith("X")) {
                varType = Variable.TYPE_INPUT;
            } else if (varName.startsWith("Z")) {
                varType = Variable.TYPE_TEMPORARY;
            } else if (varName.startsWith("Y")) {
                varType = Variable.TYPE_RETURN;
            }

            assert varType != 0;


            var = new Variable(varType, varName);

            variables.put(varName, var);
        }

        return var;
    }

}
