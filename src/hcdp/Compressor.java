
package hcdp;

import java.io.BufferedReader;
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
        '�', '�', '`', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�'};

    public HashMap<String, Integer> countFrequency(BufferedReader lectorLinea) throws IOException {
        HashMap<String, Integer> frecuencias = new HashMap<>();

        String linea = "";

        while ((linea = lectorLinea.readLine()) != null) {

            for (int i = 1; i <= 6; i++) {
                for (int j = 0; j <= linea.length() - i; j++) {
                    String substring = linea.substring(j, j + i);
                    if (frecuencias.containsKey(substring)) {
                        frecuencias.put(substring, frecuencias.get(substring) + 1);
                    } else {
                        frecuencias.put(substring, 1);
                    }
                }
            }

        }

        return frecuencias;
    }

    public HuffmanNode generarArbol(HashMap<String, Integer> frequencies) {
        Object[] keys = frequencies.keySet().toArray();
        PriorityQueue<HuffmanNode> nodos = new PriorityQueue<>();
        for (int i = 0; i < keys.length; i++) {

            HuffmanNode node = new HuffmanNode(null, null, keys[i].toString(), frequencies.get(keys[i].toString()));
            nodos.add(node);
        }

        for (int i = 0; i < keys.length - 1; i++) {
            HuffmanNode leftNode = nodos.poll();
            HuffmanNode nodoDerecha = nodos.poll();
            HuffmanNode nuevoNodo = new HuffmanNode(leftNode, nodoDerecha,
                    leftNode.getCadena().concat(nodoDerecha.getCadena()),
                    leftNode.getFrecuencia() + nodoDerecha.getFrecuencia());
            nodos.add(nuevoNodo);
        }

        return nodos.poll();
    }

    public HashMap<String, String> generateTree(HuffmanNode arbol, String cadenaBinaria, HashMap<String, String> tablaBinaria) {
        if (arbol.getIzquierda() == null && arbol.getDerecha() == null) {
            tablaBinaria.put(arbol.getCadena(), cadenaBinaria);
        } else {
            if (arbol.getIzquierda() != null) {
                generateTree(arbol.getIzquierda(), cadenaBinaria + "0", tablaBinaria);
            }
            if (arbol.getDerecha() != null) {
                generateTree(arbol.getDerecha(), cadenaBinaria + "1", tablaBinaria);
            }
        }
        return tablaBinaria;
    }
    
    public void backtrack(int cortes[][], LinkedList<Integer> posiciones, int i, int j) {
        if (i == j - 1) {
            if (cortes[i][j] != j) {
                posiciones.add(cortes[i][j]);
            }
        } else {
            if (cortes[i][j] != j) {
                posiciones.add(cortes[i][j]);

                backtrack(cortes, posiciones, i, cortes[i][j]);
                if (cortes[i][j] + 1 < cortes.length) {
                    backtrack(cortes, posiciones, cortes[i][j] + 1, j);
                }
            }
        }
    }
    
    public Code encode(String texto, HashMap<String, String> tabla) {

        // --------------- EXTRACCION OPTIMA ------------------------------
        int optimizacion[][] = new int[texto.length()][texto.length()];
        int cortesOptimos[][] = new int[texto.length()][texto.length()];
        for (int i = 0; i < texto.length(); i++) {
            optimizacion[i][i] = tabla.get(String.valueOf(texto.charAt(i))).length();
            cortesOptimos[i][i] = i;
        }
        for (int l = 2; l <= texto.length(); l++) {
            for (int i = 0; i <= texto.length() - l; i++) {
                int j = i + l - 1;
                String subcadena = texto.substring(i, j + 1);
                if (tabla.containsKey(subcadena)) {
                    optimizacion[i][j] = tabla.get(subcadena).length();
                } else {
                    optimizacion[i][j] = Integer.MAX_VALUE;
                }
                cortesOptimos[i][j] = j;
                for (int k = j - 1; k >= i; k--) {
                    int q = optimizacion[i][k] + optimizacion[k + 1][j];
                    if (q < optimizacion[i][j]) {
                        optimizacion[i][j] = q;
                        cortesOptimos[i][j] = k;
                    }
                }
            }
        }
        LinkedList<Integer> cortes = new LinkedList<>();
        cortes.add(texto.length() - 1);
        backtrack(cortesOptimos, cortes, 0, texto.length() - 1);

        // -----------------------------------------------------------------
        StringBuilder codigo = new StringBuilder();
        Iterator<Integer> it = cortes.listIterator();
        PriorityQueue<Integer> ordenar = new PriorityQueue<>();
        while (it.hasNext()) {
            ordenar.add(it.next());
        }
        int anterior = 0;
        while (ordenar.size() > 0) {
            int corte = ordenar.poll();
            String cadena = texto.substring(anterior, corte + 1);
            codigo.append(tabla.get(cadena));
            anterior = corte + 1;
        }
        // StringBuilder codigo = new StringBuilder();
        // for(int i = 0; i < texto.length(); i++) {
        // codigo.append(tabla.get(String.valueOf(texto.charAt(i))));
        // }

        String codigoToCharArray = codigo.toString();

        // El codigo quedara invertido
        Code codigoObj = new Code(codigoToCharArray.length());
        int cursorCodigo = 0;
        int cursorArreglo = codigoObj.getDatos().length - 1;
        int cursorBit = 0;
        while (cursorCodigo < codigoToCharArray.length()) {
            if (codigo.charAt(cursorCodigo) == '1') {
                codigoObj.getDatos()[cursorArreglo] |= (1 << cursorBit);
                cursorBit++;
            } else {
                codigoObj.getDatos()[cursorArreglo] &= ~(1 << cursorBit);
                cursorBit++;
            }
            if (cursorBit > 7) {
                cursorBit = 0;
                cursorArreglo--;
            }
            cursorCodigo++;
        }
        return codigoObj;
    }
    
     public HashMap<String, Integer> filtrarFrecuencias(HashMap<String, Integer> frecuencias, int numeroMapeos) {

        PriorityQueue<TupleFrequency> masFrecuentes = new PriorityQueue();
        HashMap<String, Integer> filtrado = new HashMap<>();
        for (char i : CHARACTERS) {
            if (frecuencias.containsKey(String.valueOf(i))) {
                filtrado.put(String.valueOf(i), frecuencias.get(String.valueOf(i)));
                frecuencias.remove(String.valueOf(i));
            } else {
                filtrado.put(String.valueOf(i), 0);
            }

        }
        Iterator<String> frecs = frecuencias.keySet().iterator();
        while (frecs.hasNext()) {
            String cadena = frecs.next();
            masFrecuentes.add(new TupleFrequency(cadena, frecuencias.get(cadena)));
        }

        for (int i = 0; i < numeroMapeos - CHARACTERS.length && masFrecuentes.size() > 0; i++) {
            filtrado.put(masFrecuentes.peek().getCadena(), masFrecuentes.peek().getFrecuencia());
            masFrecuentes.poll();
        }

        return filtrado;
    }
     
    

}
