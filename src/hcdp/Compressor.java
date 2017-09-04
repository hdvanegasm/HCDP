
package hcdp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 *
 * @author Admin
 */
public class Compressor {

    private final char[] CHARACTERS = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
        50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76,
        77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 97, 98, 99, 100, 101, 102, 103,
        104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124,
        125, 126, 161, 172, 176, 191, 193, 201, 205, 209, 211, 218, 220, 225, 233, 237, 241, 243, 250, 252, '�',
        '�', '�', '`', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '\n'};

    public HashMap<String, Integer> countFrequencies(String filePath) throws IOException {
        FileReader lectorArchivo = new FileReader(filePath);
        BufferedReader lineReader = new BufferedReader(lectorArchivo);
        HashMap<String, Integer> frequencies = new HashMap<>();

        String line = "";

        while ((line = lineReader.readLine()) != null) {

            for (int i = 1; i <= 6; i++) {
                for (int j = 0; j <= line.length() - i; j++) {
                    String substring = line.substring(j, j + i);
                    if (frequencies.containsKey(substring)) {
                        frequencies.put(substring, frequencies.get(substring) + 1);
                    } else {
                        frequencies.put(substring, 1);
                    }
                }
            }

        }

        return frequencies;
    }

    public HuffmanNode generateTree(HashMap<String, Integer> frequencies) {
        Object[] keys = frequencies.keySet().toArray();
        PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>();
        for (int i = 0; i < keys.length; i++) {

            HuffmanNode node = new HuffmanNode(null, null, keys[i].toString(), frequencies.get(keys[i].toString()));
            nodes.add(node);
        }

        for (int i = 0; i < keys.length - 1; i++) {
            HuffmanNode leftNode = nodes.poll();
            HuffmanNode rightNode = nodes.poll();
            HuffmanNode nuevoNodo = new HuffmanNode(leftNode, rightNode,
                    leftNode.getString().concat(rightNode.getString()),
                    leftNode.getFrequency() + rightNode.getFrequency());
            nodes.add(nuevoNodo);
        }

        return nodes.poll();
    }

    public HashMap<String, String> generateBinaryTable(HuffmanNode tree, String binaryString, HashMap<String, String> binaryTable) {
        if (tree.getLeft() == null && tree.getRight() == null) {
            binaryTable.put(tree.getString(), binaryString);
        } else {
            if (tree.getLeft() != null) {
                generateBinaryTable(tree.getLeft(), binaryString + "0", binaryTable);
            }
            if (tree.getRight() != null) {
                generateBinaryTable(tree.getRight(), binaryString + "1", binaryTable);
            }
        }
        return binaryTable;
    }
    
    public void backtrack(int cuts[][], LinkedList<Integer> positions, int i, int j) {
        if (i == j - 1) {
            if (cuts[i][j] != j) {
                positions.add(cuts[i][j]);
            }
        } else {
            if (cuts[i][j] != j) {
                positions.add(cuts[i][j]);

                backtrack(cuts, positions, i, cuts[i][j]);
                if (cuts[i][j] + 1 < cuts.length) {
                    backtrack(cuts, positions, cuts[i][j] + 1, j);
                }
            }
        }
    }
    
    public Code encode(String text, HashMap<String, String> table) {

        // --------------- EXTRACCION OPTIMA ------------------------------
        int optimization[][] = new int[text.length()][text.length()];
        int optimalCuts[][] = new int[text.length()][text.length()];
        for (int i = 0; i < text.length(); i++) {
            optimization[i][i] = table.get(String.valueOf(text.charAt(i))).length();
            optimalCuts[i][i] = i;
        }
        for (int l = 2; l <= text.length(); l++) {
            for (int i = 0; i <= text.length() - l; i++) {
                int j = i + l - 1;
                String substring = text.substring(i, j + 1);
                if (table.containsKey(substring)) {
                    optimization[i][j] = table.get(substring).length();
                } else {
                    optimization[i][j] = Integer.MAX_VALUE;
                }
                optimalCuts[i][j] = j;
                for (int k = j - 1; k >= i; k--) {
                    int q = optimization[i][k] + optimization[k + 1][j];
                    if (q < optimization[i][j]) {
                        optimization[i][j] = q;
                        optimalCuts[i][j] = k;
                    }
                }
            }
        }
        LinkedList<Integer> cuts = new LinkedList<>();
        cuts.add(text.length() - 1);
        backtrack(optimalCuts, cuts, 0, text.length() - 1);

        // -----------------------------------------------------------------
        StringBuilder code = new StringBuilder();
        Iterator<Integer> it = cuts.listIterator();
        PriorityQueue<Integer> order = new PriorityQueue<>();
        while (it.hasNext()) {
            order.add(it.next());
        }
        int previous = 0;
        while (order.size() > 0) {
            int cut = order.poll();
            String string = text.substring(previous, cut + 1);
            code.append(table.get(string));
            previous = cut + 1;
        }
        

        String codeToCharArray = code.toString();

        // El codigo quedara invertido
        Code codeObj = new Code(codeToCharArray.length());
        int codeCursor = 0;
        int arrayCursor = codeObj.getData().length - 1;
        int cursorBit = 0;
        while (codeCursor < codeToCharArray.length()) {
            if (code.charAt(codeCursor) == '1') {
                codeObj.getData()[arrayCursor] |= (1 << cursorBit);
                cursorBit++;
            } else {
                codeObj.getData()[arrayCursor] &= ~(1 << cursorBit);
                cursorBit++;
            }
            if (cursorBit > 7) {
                cursorBit = 0;
                arrayCursor--;
            }
            codeCursor++;
        }
        return codeObj;
    }
    
     public HashMap<String, Integer> filterFrequencies(HashMap<String, Integer> frequencies, int mapNumber) {

        PriorityQueue<TupleFrequency> moreFrequent = new PriorityQueue();
        HashMap<String, Integer> filtered = new HashMap<>();
        for (char i : CHARACTERS) {
            if (frequencies.containsKey(String.valueOf(i))) {
                filtered.put(String.valueOf(i), frequencies.get(String.valueOf(i)));
                frequencies.remove(String.valueOf(i));
            } else {
                filtered.put(String.valueOf(i), 0);
            }

        }
        Iterator<String> frecs = frequencies.keySet().iterator();
        while (frecs.hasNext()) {
            String string = frecs.next();
            moreFrequent.add(new TupleFrequency(string, frequencies.get(string)));
        }

        for (int i = 0; i < mapNumber - CHARACTERS.length && moreFrequent.size() > 0; i++) {
            filtered.put(moreFrequent.peek().getString(), moreFrequent.peek().getFrequency());
            moreFrequent.poll();
        }

        return filtered;
    }
     
    

}
