package com.sebikul;

public class Main {

    public static void main(String[] args) {


        String p = "Y <- Y + 1\n";
        p += "Y <- Y + 1\n";
        p += "X1 <- X1 + 1\n";
        p += "IF X1 != 0 GOTO A1\n";
        p += "X2 <- X2 + 1\n";
        p += "[A1] Y <- Y + 1\n";
        p += "Y <- Y + 1";


        Program program = new Program(p);

        program.run();


    }
}
