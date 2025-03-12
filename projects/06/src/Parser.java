import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Parser {
    // Constructor: Initializes the parser with the source file.
    public int counter = -1;
    public String currInstuc;
    private static Scanner forTranslate;
    private enum instruction_Type{
        A_Instruction,
        C_Instruction,
        L_Instruction,
    }

    public Parser(String inputFile) throws IOException {
        // Open the input file and prepare for parsing
       forTranslate = new Scanner(new File(inputFile));
    }

    // Checks if there are more lines to process.
    public boolean hasMoreLines() {
        return forTranslate.hasNextLine(); // Placeholder
    }

    // Advances to the next line and makes it the current instruction.
    public void advance() throws IOException {
        // Logic to move to the next instruction
        while (hasMoreLines()){
            currInstuc = forTranslate.nextLine();
            currInstuc = currInstuc.trim();
            if (!currInstuc.startsWith("//") && !currInstuc.isEmpty()){
                counter ++;
                break;
            }
        }
    }

    // Returns the type of the current instruction: A_INSTRUCTION, C_INSTRUCTION, L_INSTRUCTION.
    public String instructionType() {
        if(currInstuc.startsWith("@")){
            return instruction_Type.A_Instruction.name();
        } else if (currInstuc.startsWith("(")) {
            return instruction_Type.L_Instruction.name();
        }
        else {
            return instruction_Type.C_Instruction.name();
        }
    }

    // Returns the symbol for A_INSTRUCTION or L_INSTRUCTION.
    public String symbol() {
        if(Objects.equals(instructionType(), instruction_Type.A_Instruction.name())){
            return currInstuc.substring(1);
        }
        else {
            return currInstuc.substring(1, currInstuc.length()-1);
        }
    }

    // Returns the `dest` part of a C-instruction.
    public String dest() {
        String destination = "";
        if (Objects.equals(instructionType(), instruction_Type.C_Instruction.name())) { // Placeholder
            destination = currInstuc.split("=")[0];
        }
        if (destination.isEmpty())
        {
            return null;
        }
        return destination;
    }

    // Returns the `comp` part of a C-instruction.
    public String comp() {
        String comp = "";
        if (Objects.equals(instructionType(), instruction_Type.C_Instruction.name())) {
            if (currInstuc.contains(";")) {
                if (currInstuc.contains("=")){
                    comp = currInstuc.split("=")[1].split(";")[0];
                } else {
                    comp = currInstuc.split(";")[0];
                }
            }
            else {
                comp = currInstuc.split("=")[1];
            }
        }
        return comp; // Placeholder
    }

    // Returns the `jump` part of a C-instruction.
    public String jump() {
        String jump = "";
        if (Objects.equals(instructionType(), instruction_Type.C_Instruction.name())) {
            if (currInstuc.contains(";")){
                jump = currInstuc.split(";")[1];
            }
        }
        if (jump.isEmpty())
        {
            return null;
        }
        return jump;
    }
}
