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

    public String decodificarCodigoString(String codigo, HashMap<String, String> tabla) {
        StringBuilder resultado = new StringBuilder();
        int limiteInicial = 0;
        while (limiteInicial < codigo.length()) {
            for (int i = 1; i <= codigo.length(); i++) {
                String fraccionCodigo = codigo.substring(limiteInicial, limiteInicial + i);
                if (tabla.containsKey(fraccionCodigo)) {
                    resultado.append(tabla.get(fraccionCodigo));
                    limiteInicial += i;
                    break;
                }
            }
        }
        return resultado.toString();
    }

    public String decodificar(Code codigo, HashMap<String, String> tabla) {
        StringBuilder codigoStr = new StringBuilder();
        int cursorCodigo = 0;
        int cursorArreglo = codigo.getDatos().length - 1;
        int cursorBit = 0;
        while (cursorCodigo < codigo.getNumeroBits()) {
            if ((codigo.getDatos()[cursorArreglo] & (1 << cursorBit)) != 0) {
                codigoStr.append("1");
                cursorBit++;
            } else {
                codigoStr.append("0");
                cursorBit++;
            }
            if (cursorBit > 7) {
                cursorBit = 0;
                cursorArreglo--;
            }
            cursorCodigo++;
        }
        return decodificarCodigoString(codigoStr.toString(), tabla);
    }
    
    private static HashMap<String, String> generarTablaDecodificacion(HashMap<String, String> tablaBinariaCodificar) {

        // Genera la tabla para la decodificacion (Tomar en cuenta que para una
        // palabra solo hay un codigo, es decir, hay una
        // biyeccion entre el conjunto de palabras y el conjunto de codigos)
        HashMap<String, String> tablaDecodificacion = new HashMap<String, String>();
        Iterator<String> iterador = tablaBinariaCodificar.keySet().iterator();
        while (iterador.hasNext()) {
            String palabra = iterador.next();
            String codigo = tablaBinariaCodificar.get(palabra);
            tablaDecodificacion.put(codigo, palabra);
        }
        return tablaDecodificacion;
    }
}
