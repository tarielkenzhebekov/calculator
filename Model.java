import java.text.DecimalFormat;

class Model {
    private Viewer viewer;          
    private RPN rpn;                
    private String infixExpression; 
    private String stringForUser;   

    public Model(Viewer viewer) {
        this.viewer = viewer;
        rpn = new RPN();
        infixExpression = "";
        stringForUser = "";
    }

    private static int findIndexOfLastNonZeroDigit(String doubleNumber) {
        for (int i = doubleNumber.length() - 1; i >= 0; i--) {
            if (doubleNumber.charAt(i) == '.'
                    || doubleNumber.charAt(i) == ',') {
                return i;
            } else if (doubleNumber.charAt(i) != '0') {
                return i + 1;
            }
        }
        return 0;    
    }

    public void validationAndGetInfixExpr(String buttonText) {
        if (stringForUser.equals("\u221e")) {
            stringForUser = "";
            infixExpression = "";
        }

        if (buttonText.equals("0")) {
            if (stringForUser.equals("0")) {
                return;
            } 
            stringForUser = stringForUser + buttonText;
        } else if (buttonText.matches("[1-9]") 
                   && !(stringForUser.startsWith("0") 
                   && !stringForUser.contains("."))) {
            stringForUser = stringForUser + buttonText;
        } else if (buttonText.equals("CA")) {
            stringForUser = "";
            infixExpression = "";
        } else if (buttonText.equals("\u2190")) {
            if (!stringForUser.isEmpty()) {
                stringForUser = stringForUser.substring(0, stringForUser.length() - 1);
            }
        } else if (buttonText.equals(".")) {
            if (!stringForUser.isEmpty()
                    && !stringForUser.contains(".") 
                    && !stringForUser.endsWith("+") 
                    && !stringForUser.endsWith("-") 
                    && !stringForUser.endsWith("*") 
                    && !stringForUser.endsWith("/")) {
                stringForUser = stringForUser + buttonText;
            }
        } else if (buttonText.equals("+") 
                   || buttonText.equals("-") 
                   || buttonText.equals("*") 
                   || buttonText.equals("/")) {
            if (!(stringForUser.endsWith("+") 
                    || stringForUser.endsWith("-") 
                    || stringForUser.endsWith("*") 
                    || stringForUser.endsWith("/"))) {
                if (!stringForUser.isEmpty()
                        && !stringForUser.endsWith(".")) {
                    infixExpression = infixExpression + stringForUser + buttonText;
                    stringForUser = "";
                }
            }
        } else if (buttonText.equals("=")) {
            infixExpression = infixExpression + stringForUser;
            double result = rpn.evaluate(infixExpression);

            if (result == Double.POSITIVE_INFINITY) {
                stringForUser = "\u221e";
                viewer.updateTextField(stringForUser);
                return;
            }

            DecimalFormat df = new DecimalFormat("0.000000000");
            String resultFromRPN =  df.format(result);
            int endIndex = findIndexOfLastNonZeroDigit(resultFromRPN);

            infixExpression = resultFromRPN.substring(0, endIndex);
            stringForUser = infixExpression;
            infixExpression = "";

            viewer.updateTextField(stringForUser);

            return;
        }
        
        viewer.updateTextField(stringForUser);
    }
}
