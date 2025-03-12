import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 1){
            throw new IOException("Not one argument");
        }

        String fileName = args[0].split("\\.")[0] + ".hack";
        FileWriter file = new FileWriter(new File(fileName));
        Parser firstParser = new Parser(args[0]);
        Code code = new Code();
        SymbolTable symbolTable = new SymbolTable();

        while (firstParser.hasMoreLines()){
            firstParser.advance();
            String instructionType1 = firstParser.instructionType();
            if (Objects.equals(instructionType1, "L_Instruction")){
                String toAdd = firstParser.currInstuc.split("\\(")[1].split("\\)")[0];
                symbolTable.addEntry(toAdd, firstParser.counter);
                firstParser.counter --;
            }
        }

        Parser parser = new Parser(args[0]);
        while (parser.hasMoreLines()){

            parser.advance();
            StringBuilder line = new StringBuilder();
            String instructionType = parser.instructionType();
            if (parser.currInstuc.startsWith("(")){
                continue;
            }

            if (Objects.equals(instructionType, "A_Instruction")){
                if (parser.currInstuc.startsWith("@")){
                    String var = parser.currInstuc.split("@")[1];
                    if (!symbolTable.contains(var)){
                        try {
                            int putIn = Integer.parseInt(var);
                            String binaryNum = String.format("%016d", Long.parseLong(Integer.toBinaryString(putIn)));
                            line.append(binaryNum);
                            if (parser.hasMoreLines()){
                                file.write(line + "\n");
                                file.flush();
                            }else {
                                file.write(line.toString());
                                file.flush();
                            }
                            continue;
                        } catch (NumberFormatException e){
                            symbolTable.addEntry(var, symbolTable.nextAvailableAddress);
                        }
                    }
                    String loc = parser.currInstuc.split("@")[1];
                    int address = symbolTable.getAddress(loc);
                    String binaryStr = String.format("%016d", Long.parseLong(Integer.toBinaryString(address)));
                    line.append(binaryStr);
                }
            } else if (Objects.equals(instructionType, "C_Instruction")){
                line.append("111");
                line.append(code.comp(parser.comp()));
                line.append(code.dest(parser.dest()));
                line.append(code.jump(parser.jump()));
            }
            if (parser.hasMoreLines()){
                file.write(line + "\n");
                file.flush();
            }else {
                file.write(line.toString());
                file.flush();
            }
        }

        file.close();
    }
}
