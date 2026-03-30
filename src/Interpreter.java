import java.io.BufferedReader;
import java.io.IOException;

public final class Interpreter {
    private final BufferedReader fileReader;

    private final byte[] memory = new byte[Short.MAX_VALUE];
    private int memoryPointer = 0;

    public int interpret() throws IOException {
        while (true) {
            int input = fileReader.read();
            if (input == -1)
                return 0;

            int code = executeCommand((char) input);
            if (code != 0)
                return code;
        }
    }

    private int executeCommand(char command) throws IOException {
        switch (command) {
            case '>':
                if (memoryPointer == Short.MAX_VALUE)
                    return 1;
                memoryPointer++;
                break;
            case '<':
                if (memoryPointer == 0)
                    return 1;
                memoryPointer--;
                break;
            case '+':
                memory[memoryPointer]++;
                break;
            case '-':
                memory[memoryPointer]--;
                break;
            case '.':
                System.out.print((char) memory[memoryPointer]);
                break;
            case ',':
                memory[memoryPointer] = (byte) System.in.read();
                break;
        }

        return 0;
    }

    public Interpreter(BufferedReader fileReader) {
        this.fileReader = fileReader;
    }
}
