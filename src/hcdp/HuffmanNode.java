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

    public HuffmanNode(HuffmanNode left, HuffmanNode right, String string, int frequency) {
        this.left = left;
        this.right = right;
        this.string = string;
        this.frequency = frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setIzquierda(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrecuencia(int frequency) {
        this.frequency = frequency;
    }

    public int compareTo(HuffmanNode arg) {
        return Integer.compare(this.getFrequency(), arg.getFrequency());
    }
}
