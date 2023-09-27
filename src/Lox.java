import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Lox is the class providing access to an interpreter for the Lox language,
 * as defined by <a href="https://craftinginterpreters.com/the-lox-language.html" target="_top">Crafting Interpreters</a>.
 * It provides a mechanism for both reading from a provided source file along
 * with an interactive REPL.
 *
 * @see <a href="https://craftinginterpreters.com" target="_top">Crafting Interpreters</a>
 * @author Robert Nystrom
 * @author Joey Sachtleben
 */
public class Lox {

    static boolean hadError;

    /**
     * The entry point to the interpreter. Delegates to one of several helper
     * functions to run the code. If there are excessive parameters, a usage
     * message is displayed.
     *
     * @param args The path to a text file containing Lox code (optional)
     * @throws IOException if there are errors resulting from reading the source
     * file or the basic operation of the REPL.
     */
    public static void main(String[] args) throws IOException
    {
        if (args.length > 1)
        {
            System.out.println("Usage: jlox [script]");
            System.exit(64); // sysexits.h exit code for EX_USAGE
        }
        else if (args.length == 1)
        {
            runFile(args[0]);

        }
        else
        {
            runPrompt();
        }
    }

    /**
     * Reads in the entire provided text file and directly passes it to the
     * interpreter to be executed.
     *
     * @param path The path to a text file containing Lox code
     * @throws IOException if there are errors resulting from opening & reading the file
     */
    private static void runFile(String path) throws IOException
    {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError)
            System.exit(65);
    }

    /**
     * Provides the interactive REPL environment. Displays a prompt and reads
     * user input line-by-line, directly passing each line to the core
     * interpreter.
     *
     * @throws IOException if there are errors reading a line
     */
    private static void runPrompt() throws IOException
    {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        while (true)
        {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            run(line);
            hadError = false;
        }
    }

    /**
     * Takes in the provided Lox code and runs it through the interpreter.
     *
     * @param source The code to be executed.
     */
    private static void run(String source)
    {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        // For now, just print out the tokens
        for (Token token : tokens)
        {
            System.out.println(token);
        }
    }

    /**
     * Provides a way to report an error in Lox code.
     *
     * @param line the line of code where the error occurred
     * @param message String describing what the error is
     */
    static void error(int line, String message)
    {
        report(line, "", message);
    }

    /**
     * Displays the reported error to the system error stream.
     * Sets a field in the Lox class to note that an error has occurred to prevent running any code.
     *
     * @param line the line of code where the error occurred
     * @param where Further information about where the error occurred
     * @param message String describing what the error is
     */
    private static void report(int line, String where, String message)
    {
        System.err.println("[line " + line + "] Error " + where + ": " + message);
        hadError = true;
    }
}
