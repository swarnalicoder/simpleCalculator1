import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatorService {

    private BigDecimal num1;
    private BigDecimal num2;
    private char mathSymbol; // Operator

    //set num 1
    public void setNum1(BigDecimal num1) {
        this.num1 = num1;
    }

    // set num 2
    public void setNum2(BigDecimal num2) {
        this.num2 = num2;
    }

    //set operator
    public void setMathSymbol(char mathSymbol) {
        this.mathSymbol = mathSymbol;
    }

    //clear reset value
    public void clear() {
        num1 = BigDecimal.ZERO;
        num2 = BigDecimal.ZERO;
        mathSymbol = ' ';
    }

    //Perform calculation based on the operator
    public BigDecimal calculate() {
       switch (mathSymbol) {
           case '+':
               return num1.add(num2);
           case '-':
               return num1.subtract(num2);
           case '*':
               return num1.multiply(num2);
           case '/':
               if (num2.compareTo(BigDecimal.ZERO) == 0) {
                   throw new ArithmeticException("Can't divide by zero");
               }
               return num1.divide(num2, MathContext.DECIMAL128);
           default:
               return BigDecimal.ZERO;
       }
    }

    //Calculate squrt
    public BigDecimal squareRoot(BigDecimal number) {
        return BigDecimal.valueOf(Math.sqrt(number.doubleValue()));
    }

    //percentage
    public BigDecimal percentage(BigDecimal nummber) {
        return nummber.multiply(BigDecimal.valueOf(0.01));
    }
}
