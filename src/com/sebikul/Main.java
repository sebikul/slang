package com.sebikul;

import com.sebikul.exceptions.InvalidVariableNameException;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        Macro macro = new Macro("ASSIGN", 1, "[A1] Y <- Y + 1\n" +
                "X1 <- X1 - 1\n" +
                "IF X1 != 0 GOTO A1");

        String p = "Y <- Y + 1\n";
        p += "Y <- Y + 1\n";
        p += "Y <- Y + 1\n";
        p += "Y <- Y + 1\n";
        p += "X1 <- ASSIGN(Y)\n";

        ArrayList<Macro> macros = new ArrayList<Macro>();
        macros.add(macro);

        Program program = new Program(p, macros);


        try {
            program.run(null);
        } catch (InvalidVariableNameException e) {
            e.printStackTrace();
        }


    }
}
