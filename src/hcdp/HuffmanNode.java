/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcdp;

/**
 *
 * @author Admin
 */
public class HuffmanNode {

    private HuffmanNode left;
    private HuffmanNode right;
    private String string;
    private int frequency;

    public HuffmanNode(HuffmanNode izquierda, HuffmanNode derecha, String cadena, int frecuencia) {
        this.left = izquierda;
        this.right = derecha;
        this.string = cadena;
        this.frequency = frecuencia;
    }

    public HuffmanNode getIzquierda() {
        return left;
    }

    public void setIzquierda(HuffmanNode izquierda) {
        this.left = izquierda;
    }

    public HuffmanNode getDerecha() {
        return right;
    }

    public void setDerecha(HuffmanNode derecha) {
        this.right = derecha;
    }

    public String getCadena() {
        return string;
    }

    public void setCadena(String cadena) {
        this.string = cadena;
    }

    public int getFrecuencia() {
        return frequency;
    }

    public void setFrecuencia(int frecuencia) {
        this.frequency = frecuencia;
    }

    public int compareTo(HuffmanNode arg) {
        return Integer.compare(this.getFrecuencia(), arg.getFrecuencia());
    }

    @Override
    public String toString() {
        return "NodoHuffman [izquierda=" + left + ", derecha=" + right + ", cadena=" + string + ", frecuencia="
                + frequency + "]";
    }

}
