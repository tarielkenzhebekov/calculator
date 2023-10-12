import java.util.ArrayList;
import java.text.StringCharacterIterator;
import java.text.CharacterIterator;

class Tokenizer {

    private Token binaryOperatorPlus;    
    private Token binaryOperatorMinus;
    private Token binaryOperatorStar;
    private Token binaryOperatorSlash;
    private Token unaryOperatorMinus;    
    private Token binaryOperatorPower;
    private Token binaryOperatorModulo;
    private Token operatorParenthesesOpening;
    private Token operatorParenthesesClosing;

    public Tokenizer() {
        binaryOperatorPlus = new Token(Tag.OPERATOR, "+", 2, 'l');
        binaryOperatorMinus = new Token(Tag.OPERATOR, "-", 2, 'l');
        binaryOperatorStar = new Token(Tag.OPERATOR, "*", 3, 'l');
        binaryOperatorSlash = new Token(Tag.OPERATOR, "/", 3, 'l');
        unaryOperatorMinus = new Token(Tag.OPERATOR, "-", 5, 'r');
        binaryOperatorPower = new Token(Tag.OPERATOR, "^", 4, 'r');
        binaryOperatorModulo = new Token(Tag.OPERATOR, "m", 3, 'l');
        operatorParenthesesOpening = new Token(Tag.OPERATOR, "(", 6, 'n');
        operatorParenthesesClosing = new Token(Tag.OPERATOR, ")", 6, 'n');
    }

    public ArrayList<Token> tokenizeExpression(String expression) {
        ArrayList<Token> tokens = new ArrayList<>();
        CharacterIterator it = new StringCharacterIterator(expression);
        char ch;

        ch = it.current();

        if (ch == '-') {
            tokens.add(unaryOperatorMinus);
            ch = it.next();
        }

        while (ch != CharacterIterator.DONE) {
            if (Character.isDigit(ch)) {
                tokens.add(tokenizeNumber(it, ch));
                ch = it.current();
                continue;
            } else if (isOperator(ch)) {
                if (ch == '+') {
                    tokens.add(binaryOperatorPlus);
                } else if (ch == '-') {
                    tokens.add(binaryOperatorMinus);
                } else if (ch == '*') {
                    tokens.add(binaryOperatorStar);
                } else if (ch == '/') {
                    tokens.add(binaryOperatorSlash);
                } else if (ch == '^') {
                    tokens.add(binaryOperatorPower);
                } else if (ch == 'm') {
                    tokens.add(binaryOperatorModulo);
                } else if (ch == '(') {
                    tokens.add(operatorParenthesesOpening);
                } else if (ch == ')') {
                    tokens.add(operatorParenthesesClosing);
                }

                ch = it.next();
                while (ch == '-') {
                    tokens.add(unaryOperatorMinus);
                    ch = it.next();
                }
                continue;
            }

            ch = it.next();
        }

        return tokens;
    }

    private Token tokenizeNumber(CharacterIterator it, char c) {
        StringBuilder numberStringBuilderValue = new StringBuilder();
        char ch = it.next();
        
        numberStringBuilderValue.append(c);

        while (ch != CharacterIterator.DONE) {
            if (Character.isDigit(ch) || ch == '.') {
                numberStringBuilderValue.append(ch);
                ch = it.next();
                continue;
            } else {
                break;
            }
        }

        return new Token(Tag.NUMBER, numberStringBuilderValue.toString());
    }

    private boolean isOperator(char ch) {
        String opeartors = "+-*/^()m";

        if (opeartors.indexOf(ch) != -1) {
            return true;
        }

        return false;
    }
}
