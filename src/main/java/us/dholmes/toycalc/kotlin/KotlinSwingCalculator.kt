/**
 * Copyright (C) 2018 David A Holmes Jr
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
package us.dholmes.toycalc.kotlin

import us.dholmes.toycalc.Calculator
import java.awt.Component
import java.awt.Container
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import java.util.ArrayList
import javax.swing.*

private fun createAndShowGui() {

    val calculator = Calculator()

    val layout = GridBagLayout()

    val frame = JFrame("Kotlin Swing Calculator")
    frame.layout = layout
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    val displayLabel = JLabel(calculator.displayString)
    displayLabel.font = displayLabel.font.deriveFont(48.0f)
    displayLabel.horizontalAlignment = SwingConstants.RIGHT
    calculator.addDisplayListener { s: String -> displayLabel.text = s }
    addComponentWithConstraints(frame, displayLabel, 0, 0, 4, 1)

    val digitButtons = ArrayList<JButton>()
    for (i in 0..9) {
        digitButtons.add(createDigitButton(i, calculator))
    }

    addComponentWithConstraints(frame, digitButtons[0], 0, 4, 2, 1)
    addComponentWithConstraints(frame, digitButtons[1], 0, 3, 1, 1)
    addComponentWithConstraints(frame, digitButtons[2], 1, 3, 1, 1)
    addComponentWithConstraints(frame, digitButtons[3], 2, 3, 1, 1)
    addComponentWithConstraints(frame, digitButtons[4], 0, 2, 1, 1)
    addComponentWithConstraints(frame, digitButtons[5], 1, 2, 1, 1)
    addComponentWithConstraints(frame, digitButtons[6], 2, 2, 1, 1)
    addComponentWithConstraints(frame, digitButtons[7], 0, 1, 1, 1)
    addComponentWithConstraints(frame, digitButtons[8], 1, 1, 1, 1)
    addComponentWithConstraints(frame, digitButtons[9], 2, 1, 1, 1)

    val addButton = createButton("+", 32.0f,
            { _: ActionEvent -> calculator.pressOperation(Calculator.Operation.Add) })
    val subButton = createButton("-", 32.0f,
            { _: ActionEvent -> calculator.pressOperation(Calculator.Operation.Subtract) })
    val mulButton = createButton("\u00d7", 32.0f,
            { _: ActionEvent -> calculator.pressOperation(Calculator.Operation.Multiply) })
    val difButton = createButton("\u00f7", 32.0f,
            { _: ActionEvent -> calculator.pressOperation(Calculator.Operation.Divide) })
    val eqButton = createButton("=", 32.0f,
            { _: ActionEvent -> calculator.pressEquals() })

    addComponentWithConstraints(frame, addButton, 3, 1, 1, 1)
    addComponentWithConstraints(frame, subButton, 3, 2, 1, 1)
    addComponentWithConstraints(frame, mulButton, 3, 3, 1, 1)
    addComponentWithConstraints(frame, difButton, 3, 4, 1, 1)
    addComponentWithConstraints(frame, eqButton, 2, 4, 1, 1)

    frame.setSize(300, 300)
    frame.isVisible = true
}

private fun addComponentWithConstraints(container: Container,
                                        component: Component, x: Int, y: Int, width: Int, height: Int) {

    val constraints = GridBagConstraints()
    constraints.fill = GridBagConstraints.BOTH
    constraints.gridx = x
    constraints.gridy = y
    constraints.gridwidth = width
    constraints.gridheight = height
    constraints.weightx = 0.5
    constraints.weighty = 0.5
    container.add(component, constraints)
}

private fun createButton(text: String, fontSize: Float,
                         listener: (ActionEvent) -> Unit): JButton {

    val button = JButton(text)
    button.addActionListener(listener)
    button.font = button.font.deriveFont(fontSize)
    return button
}

private fun createDigitButton(digit: Int, calc: Calculator): JButton {

    return createButton("" + digit, 32.0f,
            { _: ActionEvent -> calc.pressDigit(digit) })
}

/**
 * @param args Command-line arguments.
 */
fun main(args: Array<String>) {

    SwingUtilities.invokeLater { createAndShowGui() }
}