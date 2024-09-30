import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class CalculatorGui extends JFrame implements ActionListener  {

    private final SpringLayout springLayout = new SpringLayout();
    private JTextField displayField; // for showing input result
    private CalculatorService calculatorService;
    private boolean startNewNumber = true; //Flag to reset the display for new number

    //Constructor for GUI
    public CalculatorGui() {
        super("Calculator");//Title
        setSize(CommonConstants.APP_S[0], CommonConstants.APP_S[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); //Centre window
        setLayout(new BorderLayout());

        //Initialize service for calculation
        calculatorService = new CalculatorService();

        //DisplayField setup
        displayField = new JTextField("0");
        displayField.setFont(new Font("Dialog", Font.BOLD, CommonConstants.TEXTFIELD_F));
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);
        displayField.setEditable(false);
        add(displayField, BorderLayout.NORTH);
        displayField.setPreferredSize(new Dimension(CommonConstants.DISPLAY[0], CommonConstants.DISPLAY[1]));





        //Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(CommonConstants.BUTTON_R, CommonConstants.BUTTON_C,CommonConstants.BUTTON_H,CommonConstants.BUTTON_V));

        //Array of buttons
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "sqrt", "%", "~"
        };

        //Create and add buttons
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Dialog", Font.PLAIN,CommonConstants.BUTTON_F));

            //Color
            if (label.matches("[0-9]")) {
                button.setBackground(CommonConstants.NUMBER_BUTTON_COLOR);
                button.setForeground(CommonConstants.NUMBER_BUTTONTEXT_COLOR);
            } else if (label.matches("[+\\-*/]")) {
                button.setBackground(CommonConstants.OPERATOR_BUTTON_COLOR);
                button.setForeground(CommonConstants.OPERATOR_BUTTONTEXT_COLOR);
            } else if (label.matches("C") ) {
                button.setBackground(CommonConstants.CLEAR_BUTTON_COLOR);
                button.setForeground(CommonConstants.NUMBER_BUTTONTEXT_COLOR);
            } else if (label.matches("=")) {
                button.setBackground(CommonConstants.OPERATOR_BUTTONTEXT_COLOR);
                button.setForeground(CommonConstants.OPERATOR_BUTTON_COLOR);
            } else if (label.matches("~")) {
                button.setBackground(CommonConstants.OPERATOR_BUTTON_COLOR);
                button.setForeground(CommonConstants.OPERATOR_BUTTONTEXT_COLOR);
            } else if (label.matches("%")) {
                button.setBackground(CommonConstants.OPERATOR_BUTTON_COLOR);
                button.setForeground(CommonConstants.OPERATOR_BUTTONTEXT_COLOR);
            } else if (label.matches("sqrt")) {
                button.setBackground(CommonConstants.OPERATOR_BUTTON_COLOR);
                button.setForeground(CommonConstants.OPERATOR_BUTTONTEXT_COLOR);
            } else  if (label.matches(".")) {
                button.setBackground(CommonConstants.NUMBER_BUTTON_COLOR);
                button.setForeground(CommonConstants.NUMBER_BUTTONTEXT_COLOR);
            }
            button.addActionListener(this); //Add action listener to each button
            buttonPanel.add(button); //Add button to panel
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void  actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            handleButtonXlick(command);
        } catch (Exception ex) {
            displayField.setText("Error");
        }
    }

    //Handel button actions based on command
    private void handleButtonXlick(String command) {
        if (command.matches("[0-9]") || command.equals(".")) {
            if (startNewNumber) {
                displayField.setText(command.equals(".") ? "0." : command);
            } else {
                displayField.setText(displayField.getText() + command);
            }
        } else if (command.matches("[+\\-*/]")) {
            calculatorService.setNum1(new BigDecimal(displayField.getText()));
            calculatorService.setMathSymbol(command.charAt(0));
            startNewNumber = true;
        } else if (command.equals("=")) {
            calculatorService.setNum2(new BigDecimal(displayField.getText()));
            BigDecimal result = calculatorService.calculate();
            displayField.setText(result.stripTrailingZeros().toPlainString());
            startNewNumber = true;
        } else if (command.equals("C")) {
            displayField.setText("0");
            calculatorService.clear();
            startNewNumber = true;
        } else if (command.equals("sqrt")) {
            BigDecimal sqrtResult = calculatorService.squareRoot(new BigDecimal(displayField.getText()));
            displayField.setText(sqrtResult.stripTrailingZeros().toPlainString());
            startNewNumber = true;
        } else if (command.equals("%")) {
            BigDecimal percentageR = calculatorService.percentage(new BigDecimal(displayField.getText()));
            displayField.setText(percentageR.stripTrailingZeros().toPlainString());
            startNewNumber = true;
        } else if (command.equals("~")) {
            BigDecimal negated = new BigDecimal(displayField.getText()).negate();
            displayField.setText(negated.stripTrailingZeros().toPlainString());
        }
    }

}