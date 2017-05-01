/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcdp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Admin
 */
public class Decompressor {

    public String decodeStringCode(String code, HashMap<String, String> table) {
        StringBuilder result = new StringBuilder();
        int initialLimit = 0;
        while (initialLimit < code.length()) {
            for (int i = 1; i <= code.length(); i++) {
                String codeFraction = code.substring(initialLimit, initialLimit + i);
                if (table.containsKey(codeFraction)) {
                    result.append(table.get(codeFraction));
                    initialLimit += i;
                    break;
                }
            }
        }
        return result.toString();
    }

    public String decode(Code code, HashMap<String, String> table) {
        StringBuilder codeStr = new StringBuilder();
        int codeCursor = 0;
        int arrayCursor = code.getData().length - 1;
        int cursorBit = 0;
        while (codeCursor < code.getNumeroBits()) {
            if ((code.getData()[arrayCursor] & (1 << cursorBit)) != 0) {
                codeStr.append("1");
                cursorBit++;
            } else {
                codeStr.append("0");
                cursorBit++;
            }
            if (cursorBit > 7) {
                cursorBit = 0;
                arrayCursor--;
            }
            codeCursor++;
        }
        return decodeStringCode(codeStr.toString(), table);
    }
    
    public static HashMap<String, String> generateDecodeTable(HashMap<String, String> binaryTableEncode) {

        // Genera la tabla para la decodificacion (Tomar en cuenta que para una
        // palabra solo hay un codigo, es decir, hay una
        // biyeccion entre el conjunto de palabras y el conjunto de codigos)
        HashMap<String, String> decodificationTable = new HashMap<String, String>();
        Iterator<String> iterator = binaryTableEncode.keySet().iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            String code = binaryTableEncode.get(word);
            decodificationTable.put(code, word);
        }
        return decodificationTable;
    }
}
