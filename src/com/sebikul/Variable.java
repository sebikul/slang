package com.sebikul;

import com.sebikul.exceptions.InvalidVariableNameException;

/**
 * Created by sebikul on 24/05/14.
 */
public class Variable {

    static public final int TYPE_INPUT = 1;
    static public final int TYPE_TEMPORARY = 2;
    static public final int TYPE_RETURN = 3;
    private final int type;
    private final String name;
    private int value = 0;
    public Variable(int type, String name) throws InvalidVariableNameException {

        if (name == null || name.isEmpty()) {
            throw new InvalidVariableNameException();
        }

        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public int getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (type != variable.type) return false;
        if (!name.equals(variable.name)) return false;

        return true;
    }

    public void increment() {
        value++;
    }

    public void decrement() {

        if (value != 0)
            value--;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
