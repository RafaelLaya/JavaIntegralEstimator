import jdk.nashorn.internal.scripts.JO;
import net.objecthunter.exp4j.Expression;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class toleranceWindow {
    private JTextField functionField;
    private JTextField lowerBoundField;
    private JTextField upperBoundField;
    private JTextField stepsField;
    private JTextField accuracyField;
    private JButton jumpButton;
    public JPanel mainPanel;
    private JLabel functionLabel;
    private JLabel lowerBoundLabel;
    private JLabel upperBoundLabel;
    private JLabel stepsLabel;
    private JLabel toleranceLabel;
    private JLabel rectanglesLabel;
    private JLabel leftLabel;
    private JLabel rightLabel;
    private JLabel averageLabel;
    private JLabel toGivenAccLabel;
    private JLabel currentRectanglesField;
    private JLabel leftValueField;
    private JLabel rightValueField;
    private JLabel averageValueField;
    private JLabel toGivenAccField;
    private JButton resetCurrentRectanglesButton;
    private int currentRectangles;

    public toleranceWindow() {
        currentRectangles = 0;
        jumpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integrator integrator;
                int steps = 0;

                try {
                    Expression expression = Integrator.buildExpression(functionField.getText().replace(" ", ""));
                    double lowerBound = Double.parseDouble(lowerBoundField.getText().replace(" ", ""));
                    double upperBound = Double.parseDouble(upperBoundField.getText().replace(" ", ""));
                    steps = Integer.parseInt(stepsField.getText().replace(" ", ""));

                    if (steps <= 0) {
                        throw new IllegalStateException("Steps has to be a positive integer");
                    }

                    double tolerance = Double.parseDouble(accuracyField.getText().replace(" ", ""));

                    if (tolerance <= 0) {
                        throw new IllegalStateException("Tolerance has to be positive");
                    }

                    integrator = new Integrator(expression, lowerBound, upperBound, currentRectangles);
                    integrator.setTolerance(tolerance);
                    currentRectangles += steps;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"There has been an error. Make sure that your input is appropraite");
                    return;

                }

                try {
                    ResultWithTolerance result = integrator.tolerance(steps);
                    leftValueField.setText(String.valueOf(result.getLeft().value));
                    rightValueField.setText(String.valueOf(result.getRight().value));
                    averageValueField.setText(String.valueOf(result.getAverage().value));
                    currentRectanglesField.setText(String.valueOf(result.rectangles));

                    if (result.meetsTolerance()) {
                        toGivenAccField.setText("YES");
                    } else {
                        toGivenAccField.setText("NO");
                    }
                } catch (Exception exe) {
                    JOptionPane.showMessageDialog(null,"There has been an error. Make sure that your" +
                            " function is continous on the given interval. Also, if the result is incredibly high it may" +
                            " go out of the bounds possible, in that case try using a smaller interval.");
                    return;
                }
            }
        });
        resetCurrentRectanglesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentRectangles = 0;
            }
        });
    }
}
