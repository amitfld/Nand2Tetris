import java.util.HashMap;

public class SymbolTable {

    public int nextAvailableAddress = 16;
    private HashMap<String, Integer> table;

    public SymbolTable() {
        table = new HashMap<>();
        for(int i = 0; i<=15; i++){
            table.put("R" + i, i);
        }
        table.put("SCREEN", 16384);
        table.put("KBD", 24576);
        table.put("SP", 0);
        table.put("LCL", 1);
        table.put("ARG", 2);
        table.put("THIS", 3);
        table.put("THAT", 4);
    }

    public void addEntry(String symbol, int address) {
        table.put(symbol, address);
        if (address == nextAvailableAddress){
            nextAvailableAddress ++;
        }
    }

    public Boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    public int getAddress(String symbol){
        return table.get(symbol);
    }
}
