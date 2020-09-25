import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class app {
    private JPanel initialPanel;
    private JButton rectanglesBasedMethodsButton;
    private JButton toleranceButton;
    private JButton rombergButton;

    public app() {
        rectanglesBasedMethodsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Methods with subintervals");
                frame.setContentPane(new LeftRightWindow().mainPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });
        rombergButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Romberg's Method");
                frame.setContentPane(new rombergWindow().mainPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });
        toleranceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Tolerance for monotonoic functions");
                frame.setContentPane(new toleranceWindow().mainPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Integral Estimator");
        frame.setContentPane(new app().initialPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
