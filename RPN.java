import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;

class RPN {
    private TokensStack numbersStack;    
    private TokensStack operatorsStack;   
    private Tokenizer tokenizer;

    public RPN() {
        numbersStack = new TokensStack();
        operatorsStack = new TokensStack();
        tokenizer = new Tokenizer();
    }

    public double evaluate(String infixExpression) {
        double operand1 = 0.0;
        double operand2 = 0.0;
        double result = 0.0;

        ArrayList<Token> tokens = tokenizer.tokenizeExpression(infixExpression);
        Iterator<Token> it = tokens.iterator();
        Token currentToken;

        while (it.hasNext()) {
            currentToken = it.next();

            if (currentToken.getTag() == Tag.NUMBER) {
                numbersStack.push(currentToken);
            } else if (currentToken.getValue().equals("(")) {
                operatorsStack.push(currentToken);
            } else if (currentToken.getValue().equals(")")) {
                while (!operatorsStack.peek().getValue().equals("(")) {
                    if (operatorsStack.peek().getValue().equals("-")
                            && operatorsStack.peek().getAssociativity() == 'r') {
                        operand1 = 0.0;
                        operand2 = Double.parseDouble(numbersStack.pop().getValue());
                    } else {
                        operand2 = Double.parseDouble(numbersStack.pop().getValue());
                        operand1 = Double.parseDouble(numbersStack.pop().getValue());
                    }
                    
                    result = calculate(operand1, operand2, operatorsStack.pop().getValue());

                    if (result == Double.POSITIVE_INFINITY) {
                        return result;
                    }

                    numbersStack.push(new Token(Tag.NUMBER, String.valueOf(result)));
                }

                operatorsStack.pop(); 
            } else if (currentToken.getTag() == Tag.OPERATOR) {
                while ((!operatorsStack.isEmpty()) 
                       && ((!operatorsStack.peek().getValue().equals("(")) 
                           && ((currentToken.getPrecedence() <= operatorsStack.peek().getPrecedence()) 
                              && (currentToken.getAssociativity() == 'l')))) {
                    if (operatorsStack.peek().getValue().equals("-")
                            && operatorsStack.peek().getAssociativity() == 'r') {
                        operand1 = 0.0;
                        operand2 = Double.parseDouble(numbersStack.pop().getValue());
                    } else {
                        operand2 = Double.parseDouble(numbersStack.pop().getValue());
                        operand1 = Double.parseDouble(numbersStack.pop().getValue());
                    }

                    result = calculate(operand1, operand2, operatorsStack.pop().getValue());

                    if (result == Double.POSITIVE_INFINITY) {
                        return result;
                    }

                    numbersStack.push(new Token(Tag.NUMBER, String.valueOf(result)));
                }

                operatorsStack.push(currentToken);
            }
        }

        while (!operatorsStack.isEmpty()) {
            if (operatorsStack.peek().getValue().equals("-")
                    && operatorsStack.peek().getAssociativity() == 'r') {
                operand1 = 0.0;
                operand2 = Double.parseDouble(numbersStack.pop().getValue());
            } else {
                operand2 = Double.parseDouble(numbersStack.pop().getValue());
                operand1 = Double.parseDouble(numbersStack.pop().getValue());
            }

            result = calculate(operand1, operand2, operatorsStack.pop().getValue());

            if (result == Double.POSITIVE_INFINITY) {
                return result;
            }
            
            numbersStack.push(new Token(Tag.NUMBER, String.valueOf(result)));
        }

        result = Double.parseDouble(numbersStack.pop().getValue());

        return result;
    }

    private double calculate(double operand1, double operand2, String operator) {
        double result = 0.0;
        
        switch (operator) {
        case "+":
            result = operand1 + operand2;
            break;
        case "-":
            result = operand1 - operand2;
            break;
        case "*":
            result = operand1 * operand2;
            break;
        case "/":
            if (operand2 == 0) {
                return Double.POSITIVE_INFINITY;
            }
            result = operand1 / operand2;
            break;
        case "^":
            result = power(operand1, operand2);
            break;
        case "m":
            if (operand2 == 0) {
                return Double.POSITIVE_INFINITY;
            }
            result = operand1 % operand2;
        default:
            break;
        }

        return result;
    }

    private double power(double base, double exponent) {
        double result = base;

        if (exponent == 0) {
            return 1;
        }
        else if (exponent < 0) {
            result = 1 / (power(base, Math.abs(exponent)));
        } else {
            for (int i = 1; i < exponent; i++) {
                result = result * base;
            }
        }

        return result;
    }
}