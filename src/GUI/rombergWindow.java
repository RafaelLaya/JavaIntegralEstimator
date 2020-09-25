import net.objecthunter.exp4j.Expression;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class rombergWindow {
    private JTextField functionField;
    private JTextField lowerBoundField;
    private JTextField upperBoundField;
    private JTextField maxStepsField;
    private JTextField toleranceField;
    private JButton calculateButton;
    private JLabel functionLabel;
    private JLabel lowerBoundLabel;
    private JLabel upperBoundLabel;
    private JLabel maxStepsLabel;
    private JLabel toleranceLabel;
    private JLabel resultLabel;
    private JLabel resultValueLabel;
    public JPanel mainPanel;
    private JLabel iterationsLabel;
    private JLabel iterationsField;

    public rombergWindow() {
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integrator integrator;

                try {
                    Expression expression = Integrator.buildExpression(functionField.getText().replace(" ", ""));
                    double lowerBound = Double.parseDouble(lowerBoundField.getText().replace(" ", ""));
                    double upperBound = Double.parseDouble(upperBoundField.getText().replace(" ", ""));
                    int maxSteps = Integer.parseInt(maxStepsField.getText().replace(" ", ""));
                    double tolerance = Double.parseDouble(toleranceField.getText().replace(" ", ""));

                    if (tolerance <= 0) {
                        throw new IllegalStateException("Tolerance has to be positive");
                    }

                    integrator = new Integrator(expression, lowerBound, upperBound);
                    integrator.setMaxSteps(maxSteps);
                    integrator.setTolerance(tolerance);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"There has been an error. Make sure that your input is appropraite");
                    return;

                }

                try {
                    ResultWithRomberg result = integrator.romberg();
                    resultValueLabel.setText(String.valueOf(result.value));
                    iterationsField.setText(String.valueOf(result.getSteps()));
                } catch (Exception exe) {
                    JOptionPane.showMessageDialog(null,"There has been an error. Make sure that your" +
                            " function is continous on the given interval. Also, if the result is incredibly high it may" +
                            " go out of the bounds possible, in that case try using a smaller interval.");
                    return;
                }

            }
        });
    }
}
