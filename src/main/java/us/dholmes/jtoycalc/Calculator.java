/**
 * Copyright (C) 2016 David A Holmes Jr
 * 
 * This file is part of JToyCalc.
 * 
 * JToyCalc is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JToyCalc is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JToyCalc.  If not, see <http://www.gnu.org/licenses/>.
 */
package us.dholmes.jtoycalc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A simple calculator.  The public interface and behavior of this class are
 * modeled after a classic pocket calculator.
 */
public final class Calculator {

    static final int MAX_DIGITS = 8;
    
    private final List<Consumer<String>> displayListeners = new ArrayList<>();

    private long accumulator = 0;
    private long input = 0;
    private long storedOperand = 0;
    private Operation currentOperation = Operation.None;
    private Operation storedOperation = Operation.None;
    private Display currentDisplay = Display.Input;
    private boolean equalsPressed = false;

    /**
     * The operations the calculator can perform.
     */
    public enum Operation {
        None, Add, Subtract, Multiply, Divide;
    }

    /**
     * The calculator stores two numbers at a time: an input field and an
     * accumulator field.  Only one of these numbers is displayed on the screen
     * at any given time.
     */
    private enum Display {
        Input, Accumulator;
    }

    /**
     * Constructor.
     */
    public Calculator() {
        
    }
    /**
     * Press a digit button on the calculator.
     * 
     * @param digit The digit to press.
     */
    public void pressDigit(int digit) {

        if (equalsPressed) {
            reset();
        }
        
        if (currentDisplay == Display.Accumulator) {
            currentDisplay = Display.Input;
        }

        if (input == 0) {
            input = digit;
        } else if (input < (Math.pow(10, MAX_DIGITS - 1))) {
            input = input * 10 + digit;
        }

        updateDisplay();
    }

    /**
     * Press an operation button on the calculator (+-/*).
     * 
     * @param operation The operation to perform.
     */
    public void pressOperation(Operation operation) {

        if (!equalsPressed) {
            performOperation();
            input = 0;
        }

        currentDisplay = Display.Accumulator;
        currentOperation = operation;
        equalsPressed = false;

        updateDisplay();
    }

    /**
     * Press the "equals" button on the calculator.
     */
    public void pressEquals() {

        if (equalsPressed) {
            input = storedOperand;
            currentOperation = storedOperation;
        }
        performOperation();
        storedOperation = currentOperation;
        currentOperation = Operation.None;
        storedOperand = input;
        equalsPressed = true;
        currentDisplay = Display.Accumulator;
        input = 0;
        updateDisplay();
    }

    /**
     * Returns the currently-displayed number as a String.
     * 
     * @return The currently-displayed number as a String.
     */
    public String getDisplayString() {

        return "" + getDisplayValue();
    }

    /**
     * Registers a listener for display update events. Every time the calculator
     * display is updated, the listeners will be called with the new number to
     * display.
     * 
     * @param listener The listener to add.
     */
    public void addDisplayListener(Consumer<String> listener) {

        displayListeners.add(listener);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "Calculator [current display: " + getDisplayString() + "]";
    }

    private void performOperation() {

        switch (currentOperation) {
        case None:
            accumulator = input;
            break;
        case Add:
            accumulator += input;
            break;
        case Subtract:
            accumulator -= input;
            break;
        case Multiply:
            accumulator *= input;
            break;
        case Divide:
            accumulator /= input;
            break;
        }
    }
    
    private long getDisplayValue() {

        return (currentDisplay == Display.Input) ? input : accumulator;
    }

    private void updateDisplay() {

        for (Consumer<String> listener : displayListeners) {
            listener.accept(getDisplayString());
        }
    }

    private void reset() {
        
        accumulator = 0;
        input = 0;
        storedOperand = 0;
        currentOperation = Operation.None;
        storedOperation = Operation.None;
        currentDisplay = Display.Input;
        equalsPressed = false;
    }
}
