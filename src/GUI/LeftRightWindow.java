import net.objecthunter.exp4j.Expression;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeftRightWindow {
    private JTextField functionField;
    private JTextField lowerBoundField;
    private JTextField upperBoundField;
    private JTextField rectanglesField;
    private JComboBox methodField;
    private JButton calculateButton;
    public JPanel mainPanel;
    private JLabel functionLabel;
    private JLabel lowerBoundLabel;
    private JLabel upperBoundLabel;
    private JLabel rectanglesLabel;
    private JLabel methodLabel;
    private JLabel resultLabel;
    private JLabel resultValueLabel;

    public LeftRightWindow() {
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int method;
                Integrator integrator;

                try {
                    Expression expression = Integrator.buildExpression(functionField.getText().replace(" ", ""));
                    double lowerBound = Double.parseDouble(lowerBoundField.getText().replace(" ", ""));
                    double upperBound = Double.parseDouble(upperBoundField.getText().replace(" ", ""));
                    int rectangles = Integer.parseInt(rectanglesField.getText().replace(" ", ""));
                    method = methodField.getSelectedIndex() + 1;
                    integrator = new Integrator(expression, lowerBound, upperBound, rectangles);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"There has been an error. Make sure that your input is appropriate");
                    return;

                }

                try {
                    switch (method) {
                        case Constants.simpson:
                            resultValueLabel.setText(String.valueOf(integrator.simpson().value));
                            break;
                        case Constants.trapezium:
                            resultValueLabel.setText(String.valueOf(integrator.trapezium().value));
                            break;
                        case Constants.left:
                            resultValueLabel.setText(String.valueOf(integrator.leftRiemannSums().value));
                            break;
                        case Constants.right:
                            resultValueLabel.setText(String.valueOf(integrator.rightRiemannSums().value));
                            break;
                        case Constants.midpoint:
                            resultValueLabel.setText(String.valueOf(integrator.midpoint().value));
                            break;
                        case Constants.average:
                            resultValueLabel.setText(String.valueOf(integrator.average().value));
                            break;
                    }
                } catch (Exception exi) {
                    JOptionPane.showMessageDialog(null,"There has been an error. Make sure that your" +
                            " function is continous on the given interval. Also, if the result is incredibly high it may" +
                            " go out of the bounds possible, in that case try using a smaller interval.");
                    return;
                }
            }
        });
    }
}