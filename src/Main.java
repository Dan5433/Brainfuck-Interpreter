import java.io.*;

public final class Main {
    public static final String FILE_EXTENSION = ".bf";

    public static final BufferedReader CONSOLE_READER = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedReader fileReader;

    public static void main(String[] args) throws IOException {
        System.out.println("Brainfuck Interpreter by Abtu\n");

        Interpreter interpreter = new Interpreter(createFileReader());

        System.out.println("File loaded successfully, starting execution...");
        int exitCode = interpreter.interpret();

        System.out.println("\nBrainfuck program finished with exit code " + exitCode);
    }

    private static BufferedReader createFileReader() throws IOException {
        System.out.println("Enter the path of the brainfuck file you want to run:");

        File file;
        while (true) {
            file = new File(CONSOLE_READER.readLine());

            if (!file.exists()) {
                System.out.println("Path does not exist, try again:");
            } else if (!file.isFile()) {
                System.out.println("Path is not a file, try again:");
            } else if (!file.getName().endsWith(FILE_EXTENSION)) {
                System.out.println("File is not a brainfuck (.bf) file, try again:");
            } else {
                break;
            }
        }

        return new BufferedReader(new FileReader(file));
    }
}