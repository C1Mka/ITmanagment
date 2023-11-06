import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


public class Calculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Калькулятор");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        JTextField inputField = new JTextField();
        frame.add(inputField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = inputField.getText();
                    String command = e.getActionCommand();

                    if (command.equals("=")) {
                        try {
                            String result = evaluateExpression(text);
                            inputField.setText(result);
                        } catch (Exception ex) {
                            inputField.setText("Помилка");
                        }
                    } else if (command.equals("C")) {
                        inputField.setText("");
                    } else {
                        inputField.setText(text + command);
                    }
                }
            });
            panel.add(button);
        }

        frame.setVisible(true);
    }

    private static String evaluateExpression(String expression) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            Object result = engine.eval(expression);
            if (result instanceof Number) {
                return result.toString();
            } else {
                throw new ArithmeticException("Помилка обчислення");
            }
        } catch (ScriptException e) {
            throw new ArithmeticException("Помилка обчислення");
        }
    }
}
