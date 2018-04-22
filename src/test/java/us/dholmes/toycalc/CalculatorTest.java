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
package us.dholmes.toycalc;

import static org.junit.Assert.*;

import org.junit.Test;

import us.dholmes.toycalc.Calculator.Operation;

/**
 * Calculator JUnit tests.
 */
public class CalculatorTest {

    /**
     * Test method for {@link us.dholmes.jtoycalc.Calculator#pressDigit(int)}.
     */
    @Test
    public void testPressDigit() {

        Calculator calc = new Calculator();

        calc.pressDigit(0);
        calc.pressDigit(0);
        calc.pressDigit(1);
        calc.pressDigit(5);
        calc.pressDigit(3);
        calc.pressDigit(8);
        calc.pressDigit(9);
        calc.pressDigit(2);
        calc.pressDigit(7);
        calc.pressDigit(4);

        assertEquals("15389274", calc.getDisplayString());
    }

    /**
     * Test method for {@link us.dholmes.jtoycalc.Calculator#pressDigit(int)}
     * with too many button presses.
     */
    @Test
    public void testPressDigitOverflow() {

        Calculator calc = new Calculator();

        long expectedLong = 0;
        for (int i = 1; i <= Calculator.MAX_DIGITS; i++) {
            expectedLong = expectedLong * 10 + i;
        }
        String expectedString = "" + expectedLong;

        for (int i = 1; i <= Calculator.MAX_DIGITS + 1; i++) {
            calc.pressDigit(i % 10);
        }

        assertEquals(expectedString, calc.getDisplayString());
    }

    /**
     * Test method for the assorted calculator operations.
     */
    @Test
    public void testOperations() {

        Calculator calc = new Calculator();
        
        calc.pressDigit(5);
        assertEquals("5", calc.getDisplayString());
        
        calc.pressOperation(Operation.Add);
        assertEquals("5", calc.getDisplayString());
        
        calc.pressDigit(6);
        assertEquals("6", calc.getDisplayString());
        
        calc.pressOperation(Operation.Subtract);
        assertEquals("11", calc.getDisplayString());
        
        calc.pressDigit(3);
        assertEquals("3", calc.getDisplayString());
        
        calc.pressEquals();
        assertEquals("8", calc.getDisplayString());
        
        calc.pressOperation(Operation.Multiply);
        assertEquals("8", calc.getDisplayString());
        
        calc.pressDigit(3);
        assertEquals("3", calc.getDisplayString());
        
        calc.pressOperation(Operation.Divide);
        assertEquals("24", calc.getDisplayString());
        
        calc.pressDigit(2);
        assertEquals("2", calc.getDisplayString());
        
        calc.pressEquals();
        assertEquals("12", calc.getDisplayString());
        
        calc.pressEquals();
        assertEquals("6", calc.getDisplayString());
        
        calc.pressEquals();
        assertEquals("3", calc.getDisplayString());
    }
    
    /**
     * Test method for
     * {@link us.dholmes.jtoycalc.Calculator#getDisplayString()}.
     */
    @Test
    public void testGetDisplayString() {

        Calculator calc = new Calculator();

        calc.pressDigit(2);
        calc.pressDigit(3);
        calc.pressDigit(4);
        assertEquals("234", calc.getDisplayString());
        calc.pressOperation(Operation.Add);
        assertEquals("234", calc.getDisplayString());
        calc.pressDigit(5);
        calc.pressDigit(6);
        calc.pressDigit(7);
        assertEquals("567", calc.getDisplayString());
    }
}
