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

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import us.dholmes.jtoycalc.Calculator;
import us.dholmes.jtoycalc.Calculator.Operation;

/**
 * Swing JToyCalc application.
 */
public final class SwingCalculator {

    /**
     * Private constructor to prevent instantiation.
     */
    private SwingCalculator() {
        
    }

    private static void createAndShowGui() {

        Calculator calculator = new Calculator();

        GridBagLayout layout = new GridBagLayout();

        JFrame frame = new JFrame("JToyCalc");
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel displayLabel = new JLabel(calculator.getDisplayString());
        displayLabel.setFont(displayLabel.getFont().deriveFont(48.0f));
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        calculator.addDisplayListener((String s) -> displayLabel.setText(s));
        addComponentWithConstraints(frame, displayLabel, 0, 0, 4, 1);

        List<JButton> digitButtons = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            digitButtons.add(createDigitButton(i, calculator));
        }

        addComponentWithConstraints(frame, digitButtons.get(0), 0, 4, 2, 1);
        addComponentWithConstraints(frame, digitButtons.get(1), 0, 3, 1, 1);
        addComponentWithConstraints(frame, digitButtons.get(2), 1, 3, 1, 1);
        addComponentWithConstraints(frame, digitButtons.get(3), 2, 3, 1, 1);
        addComponentWithConstraints(frame, digitButtons.get(4), 0, 2, 1, 1);
        addComponentWithConstraints(frame, digitButtons.get(5), 1, 2, 1, 1);
        addComponentWithConstraints(frame, digitButtons.get(6), 2, 2, 1, 1);
        addComponentWithConstraints(frame, digitButtons.get(7), 0, 1, 1, 1);
        addComponentWithConstraints(frame, digitButtons.get(8), 1, 1, 1, 1);
        addComponentWithConstraints(frame, digitButtons.get(9), 2, 1, 1, 1);

        JButton addButton = createButton("+", 32.0f, 
                (ActionEvent e) -> calculator.pressOperation(Operation.Add));
        JButton subButton = createButton("-", 32.0f, 
                (ActionEvent e) -> calculator.pressOperation(Operation.Subtract));
        JButton mulButton = createButton("\u00d7", 32.0f, 
                (ActionEvent e) -> calculator.pressOperation(Operation.Multiply));
        JButton difButton = createButton("\u00f7", 32.0f, 
                (ActionEvent e) -> calculator.pressOperation(Operation.Divide));
        JButton eqButton = createButton("=", 32.0f, 
                (ActionEvent e) -> calculator.pressEquals());

        addComponentWithConstraints(frame, addButton, 3, 1, 1, 1);
        addComponentWithConstraints(frame, subButton, 3, 2, 1, 1);
        addComponentWithConstraints(frame, mulButton, 3, 3, 1, 1);
        addComponentWithConstraints(frame, difButton, 3, 4, 1, 1);
        addComponentWithConstraints(frame, eqButton, 2, 4, 1, 1);
        
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    private static void addComponentWithConstraints(Container container,
            Component component, int x, int y, int width, int height) {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        container.add(component, constraints);
    }
    
    private static JButton createButton(String text, float fontSize, 
            ActionListener listener) {
        
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setFont(button.getFont().deriveFont(fontSize));
        return button;
    }

    private static JButton createDigitButton(int digit, Calculator calc) {

        JButton button = createButton("" + digit, 32.0f, 
                (ActionEvent e) -> calc.pressDigit(digit));
        return button;
    }

    /**
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(SwingCalculator::createAndShowGui);
    }
}
