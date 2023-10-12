class TokensStack {
    private Token tokens[]; 
    private int size;       
    private int top;        

    public TokensStack() {
        size = 10;
        tokens = new Token[size];
        top = -1;
    }

    public void push(Token token) {
        if (top >= size) {
            growSize();
        }
        tokens[++top] = token;
    }

    public Token pop() {
        return tokens[top--];
    }

    public Token peek() {
        return tokens[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void clear() {
        top = -1;
        size = 10;
        tokens = new Token[size];
    }

    private void growSize() {
        Token temp[] = new Token[size * 2];

        for (int i = 0; i < size; i++) {
            temp[i] = tokens[i];
        }

        size = size * 2;
        tokens = temp;
    }
}