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
public class TupleFrequency implements Comparable<TupleFrequency>{

    private String string;
    private int frequency;

    public TupleFrequency(String string, int frequency) {
        this.string = string;
        this.frequency = frequency;
    }

    public void incrFrec() {
        this.frequency++;
    }

    @Override
    public String toString() {
        return "TuplaFrec [cadena=" + string + ", frecuencia=" + frequency + "]";
    }

    public void setFrecuency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }

    @Override
    public int compareTo(TupleFrequency arg) {
        return -Integer.compare(this.getFrequency(), arg.getFrequency());
    }
}
