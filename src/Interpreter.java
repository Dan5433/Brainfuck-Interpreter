import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public final class Interpreter {
    private final BufferedReader fileReader;

    private final String input;
    private int inputPointer = 0;

    private final byte[] memory = new byte[Short.MAX_VALUE];
    private int memoryPointer = 0;

    private boolean isInLoop = false;
    private int loopCounter = -1;
    private int loopPointer = 0;
    private final ArrayList<StringBuilder> loops = new ArrayList<>();

    public int interpret() throws IOException {
        while (true) {
            char command;
            if (isInLoop) {
                command = loops.get(loopCounter).charAt(loopPointer);
            } else {
                int input = fileReader.read();
                if (input == -1)
                    return 0;
                command = (char) input;
            }

            int code = executeCommand(command);
            if (code != 0)
                return code;
        }
    }

    private int executeCommand(char command) throws IOException {
        switch (command) {
            case '>':
                memoryPointer++;
                if (memoryPointer == Short.MAX_VALUE)
                    return 1;
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
                System.out.print((char) ((short) memory[memoryPointer] - Byte.MIN_VALUE));
                break;
            case ',':
                memory[memoryPointer] = (byte) (input.charAt(inputPointer) + Byte.MIN_VALUE);
                inputPointer++;
                break;
            case '[':
                loopCounter++;
                loopPointer = 0;
                loops.add(new StringBuilder());
                return 0;
            case ']':
                if (loopCounter < 0)
                    return 1;

                if (memory[memoryPointer] == Byte.MIN_VALUE) {
                    isInLoop = false;
                    loops.remove(loopCounter);
                    loopCounter--;
                } else {
                    if (!isInLoop)
                        AddCommandToLoop(command);
                    isInLoop = true;
                    loopPointer = 0;
                }
                return 0;
            default:
                return 0;
        }

        if (loopCounter >= 0) {
            if (!isInLoop)
                AddCommandToLoop(command);
            loopPointer++;
        }
        return 0;
    }

    private void AddCommandToLoop(char command) {
        for (int i = 0; i <= loopCounter; i++) {
            loops.get(i).append(command);
        }
    }

    public Interpreter(BufferedReader fileReader, String input) {
        this.fileReader = fileReader;
        this.input = input;

        Arrays.fill(memory, Byte.MIN_VALUE);
    }
}
