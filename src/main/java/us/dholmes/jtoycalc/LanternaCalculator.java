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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.LinearLayout.Alignment;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListenerAdapter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import us.dholmes.jtoycalc.Calculator.Operation;

/**
 * Lanterna JToyCalc application.
 */
public final class LanternaCalculator {

    /**
     * Private constructor to prevent instantiation.
     */
    private LanternaCalculator() {
        
    }
    
    /**
     * @param args Command-line arguments.
     * @throws IOException when terminal I/O throws an IOException.
     */
    public static void main(String[] args) throws IOException {

        Calculator calculator = new Calculator();

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        Panel outerVerticalBox = new Panel(
                new LinearLayout(Direction.VERTICAL));

        Label label = new Label(calculator.getDisplayString());
        calculator.addDisplayListener((String s) -> label.setText(s));
        label.setLayoutData(LinearLayout.createLayoutData(Alignment.End));
        outerVerticalBox.addComponent(label);

        Panel gridPanel = new Panel(new GridLayout(4));
        outerVerticalBox.addComponent(gridPanel);

        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            final int digit = i;
            Button button = createButton("" + digit,
                    () -> calculator.pressDigit(digit));
            buttons.add(button);
        }

        Button addButton = createButton("+",
                () -> calculator.pressOperation(Operation.Add));
        Button subButton = createButton("-",
                () -> calculator.pressOperation(Operation.Subtract));
        Button mulButton = createButton("*",
                () -> calculator.pressOperation(Operation.Multiply));
        Button divButton = createButton("/",
                () -> calculator.pressOperation(Operation.Divide));
        Button eqButton = createButton("=", () -> calculator.pressEquals());

        gridPanel.addComponent(buttons.get(7));
        gridPanel.addComponent(buttons.get(8));
        gridPanel.addComponent(buttons.get(9));
        gridPanel.addComponent(addButton);
        gridPanel.addComponent(buttons.get(4));
        gridPanel.addComponent(buttons.get(5));
        gridPanel.addComponent(buttons.get(6));
        gridPanel.addComponent(subButton);
        gridPanel.addComponent(buttons.get(1));
        gridPanel.addComponent(buttons.get(2));
        gridPanel.addComponent(buttons.get(3));
        gridPanel.addComponent(mulButton);
        gridPanel.addComponent(buttons.get(0));
        gridPanel.addComponent(new EmptySpace());
        gridPanel.addComponent(eqButton);
        gridPanel.addComponent(divButton);

        BasicWindow window = new BasicWindow();
        window.setComponent(outerVerticalBox);

        window.addWindowListener(new WindowListenerAdapter() {
            @Override
            public void onUnhandledInput(Window basePane, KeyStroke keyStroke,
                    AtomicBoolean hasBeenHandled) {

                if (keyStroke.getKeyType() == KeyType.Escape) {
                    System.exit(0);
                }

                if (keyStroke.getKeyType() == KeyType.Character) {

                    char c = keyStroke.getCharacter().charValue();

                    if (c >= '0' && c <= '9') {
                        int digit = c - '0';
                        calculator.pressDigit(digit);
                        buttons.get(digit).takeFocus();
                    } else {
                        switch (c) {
                        case '+':
                            calculator.pressOperation(Operation.Add);
                            addButton.takeFocus();
                            break;
                        case '-':
                            calculator.pressOperation(Operation.Subtract);
                            subButton.takeFocus();
                            break;
                        case '*':
                            calculator.pressOperation(Operation.Multiply);
                            mulButton.takeFocus();
                            break;
                        case '/':
                            calculator.pressOperation(Operation.Divide);
                            divButton.takeFocus();
                            break;
                        case '=':
                            calculator.pressEquals();
                            eqButton.takeFocus();
                            break;
                        }
                    }
                }
            }
        });

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen,
                new DefaultWindowManager(),
                new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);
    }

    private static Button createButton(String label, Runnable action) {

        Button button = new Button(label, action);
        button.setRenderer(new Button.BorderedButtonRenderer());
        return button;
    }
}
