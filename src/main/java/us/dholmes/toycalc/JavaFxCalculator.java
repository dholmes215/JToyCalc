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

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import us.dholmes.toycalc.Calculator.Operation;

/**
 * JavaFX JToyCalc application.
 */
public final class JavaFxCalculator extends Application {

    /*
     * (non-Javadoc)
     * 
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Calculator calculator = new Calculator();

        primaryStage.setTitle("JToyCalc");

        GridPane grid = new GridPane();

        Label displayLabel = new Label(calculator.getDisplayString());
        displayLabel.setFont(Font.font(48.0));
        displayLabel.setAlignment(Pos.CENTER_RIGHT);
        displayLabel.setMaxWidth(Double.MAX_VALUE);
        displayLabel.setMaxHeight(Double.MAX_VALUE);
        calculator.addDisplayListener((String s) -> displayLabel.setText(s));
        addNodeWithConstraints(grid, displayLabel, 1, 1, 4, 1);

        List<Button> digitButtons = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            digitButtons.add(createDigitButton(i, calculator));
        }
        addNodeWithConstraints(grid, digitButtons.get(0), 1, 5, 2, 1);
        addNodeWithConstraints(grid, digitButtons.get(1), 1, 4, 1, 1);
        addNodeWithConstraints(grid, digitButtons.get(2), 2, 4, 1, 1);
        addNodeWithConstraints(grid, digitButtons.get(3), 3, 4, 1, 1);
        addNodeWithConstraints(grid, digitButtons.get(4), 1, 3, 1, 1);
        addNodeWithConstraints(grid, digitButtons.get(5), 2, 3, 1, 1);
        addNodeWithConstraints(grid, digitButtons.get(6), 3, 3, 1, 1);
        addNodeWithConstraints(grid, digitButtons.get(7), 1, 2, 1, 1);
        addNodeWithConstraints(grid, digitButtons.get(8), 2, 2, 1, 1);
        addNodeWithConstraints(grid, digitButtons.get(9), 3, 2, 1, 1);

        Button addButton = createButton("+", 32.0,
                (ActionEvent e) -> calculator.pressOperation(Operation.Add));
        Button subButton = createButton("-", 32.0, (ActionEvent e) -> calculator
                .pressOperation(Operation.Subtract));
        Button mulButton = createButton("\u00d7", 32.0,
                (ActionEvent e) -> calculator
                        .pressOperation(Operation.Multiply));
        Button divButton = createButton("\u00f7", 32.0,
                (ActionEvent e) -> calculator.pressOperation(Operation.Divide));
        Button eqButton = createButton("=", 32.0,
                (ActionEvent e) -> calculator.pressEquals());

        addNodeWithConstraints(grid, addButton, 4, 2, 1, 1);
        addNodeWithConstraints(grid, subButton, 4, 3, 1, 1);
        addNodeWithConstraints(grid, mulButton, 4, 4, 1, 1);
        addNodeWithConstraints(grid, divButton, 4, 5, 1, 1);
        addNodeWithConstraints(grid, eqButton, 3, 5, 1, 1);

        primaryStage.setScene(new Scene(grid, 350, 350));
        primaryStage.show();
    }

    private static void addNodeWithConstraints(GridPane grid, Node node, int x,
            int y, int width, int height) {

        GridPane.setConstraints(node, x, y, width, height, HPos.CENTER,
                VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        grid.getChildren().add(node);
    }

    private static Button createButton(String text, double fontSize,
            EventHandler<ActionEvent> eventHandler) {

        Button button = new Button();
        button.setText(text);
        button.setFont(Font.font(fontSize));
        button.setOnAction(eventHandler);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private static Button createDigitButton(int digit, Calculator calc) {

        return createButton("" + digit, 32.0,
                (ActionEvent e) -> calc.pressDigit(digit));
    }

    /**
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        launch(args);
    }
}
