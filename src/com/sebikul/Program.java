package com.sebikul;

import com.sebikul.exceptions.HasNotRunException;
import com.sebikul.exceptions.InstruccionIlegalException;
import com.sebikul.exceptions.InvalidVariableNameException;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Program {

    protected HashMap<String, Variable> variables = new HashMap<String, Variable>();
    private ArrayList<Instruction> instructions = new ArrayList<Instruction>();
    protected boolean hasRun = false;
    private int nextInstruction = 0;
    private HashMap<String, Macro> macros = new HashMap<String, Macro>();

    public Program(String program, ArrayList<Macro> macros) {

        if (macros != null) {
            for (Macro macro : macros) {
                this.addMacro(macro);
            }
        }


        parseProgram(program);
    }

    public Program(String program) {


    }

    private void parseProgram(String program) {
        for (String line : program.split("\n")) {

            Instruction instruction = null;
            try {
                instruction = Parser.fromLine(line, variables, macros);
                instructions.add(instruction);
            } catch (InstruccionIlegalException e) {

                System.out.println("Instruccion Ilegal: " + e.getInstruction());
                break;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public HashMap<String, Macro> getMacros() {
        return macros;
    }

    public void addMacro(Macro macro) {
        if (macro == null) {
            return;
        }

        macros.put(macro.getName(), macro);
    }

    public final void jump(String label) {

        int i = 0;
        for (Instruction instruction : instructions) {
            if (instruction.getLabel().equals(label)) {
                nextInstruction = i;
            }
            i++;
        }
    }

    public final void run(HashMap<String, Integer> params) throws InvalidVariableNameException {

        if (params != null) {
            for (Map.Entry<String, Integer> param : params.entrySet()) {

                String varName = param.getKey();

                if (!varName.matches(Parser.INSTRUCCION_VAR_MATCH) && !varName.startsWith("X")) {
                    throw new InvalidVariableNameException(varName);
                }

                Variable variable = Parser.getVariable(varName, variables);

                variable.setValue(param.getValue());

            }
        }


        for (; nextInstruction < instructions.size(); nextInstruction++) {

            try {
                System.out.println(instructions.get(nextInstruction));
                instructions.get(nextInstruction).execute(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(variables);
        hasRun = true;

    }

    public final int result() throws HasNotRunException {

        if (!hasRun) {
            throw new HasNotRunException();
        }

        Variable retVar = variables.get("Y");

        return retVar.getValue();
    }
}
