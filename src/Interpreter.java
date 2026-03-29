import java.io.BufferedReader;
import java.io.IOException;

public final class Interpreter {
    private BufferedReader fileReader;

    private byte[] memory = new byte[Short.MAX_VALUE];
    private int dataPointer = 0;

    public Interpreter(BufferedReader fileReader) {
        this.fileReader = fileReader;
    }

    public void interpret() throws IOException {
        int dataPointer = 0;

        while (true) {
            int input = fileReader.read();
            if (input == -1)
                return;

            char command = (char) input;
            switch (command) {
                case '>':
                    dataPointer++;
                    break;
                case '<':
                    dataPointer--;
                    break;
                case '+':
                    memory[dataPointer]++;
                    break;
                case '-':
                    memory[dataPointer]--;
                    break;
                case '.':
                    System.out.print((char) memory[dataPointer]);
                    break;
                case ',':
                    memory[dataPointer] = (byte) System.in.read();
                    break;
            }
        }
    }
}
