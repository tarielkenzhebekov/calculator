class Token {
    private Tag tag;              
    private String value;        
    private int precedence;      
    private char associativity; 

    public Token(Tag tag, String value) {
        this.tag = tag;
        this.value = value;
        this.precedence = 0;
        this.associativity = 'n';
    }

    public Token(Tag tag, 
                 String value, 
                 int precedence, 
                 char associativity) {
        this.tag = tag;
        this.value = value;
        this.precedence = precedence;
        this.associativity = associativity;
    }

    public Tag getTag() {
        return tag;
    }

    public String getValue() {
        return value;
    }

    public int getPrecedence() {
        return precedence;
    }

    public char getAssociativity() {
        return associativity;
    }
}