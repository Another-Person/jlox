/**
 * A Token represents the smallest useful item in a line of Lox code. It carries
 * information such as the string form from source (also known as a lexeme),
 * the line number in the source, the type of the token, and the actualization
 * of the Token as a Java object.
 *
 * @author Robert Nystrom
 * @author Joey Sachtleben
 */
public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    /**
     * The basic constructor for a Token. Takes each parameter and stores it in
     * the token without any validation or logic.
     *
     * @param type the TokenType of the token
     * @param lexeme the exact String from source
     * @param literal a Java Object serving as the realization of the Token
     * @param line the line in the source the token is found
     */
    Token(TokenType type, String lexeme, Object literal, int line)
    {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString()
    {
        return type + " " + lexeme + " " + literal;
    }
}
