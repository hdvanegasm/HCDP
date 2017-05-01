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
public class TupleFrequency {

    private String cadena;
    private int frecuencia;

    public TupleFrequency(String cadena, int frecuencia) {
        this.cadena = cadena;
        this.frecuencia = frecuencia;
    }

    public void incrFrec() {
        this.frecuencia++;
    }

    @Override
    public String toString() {
        return "TuplaFrec [cadena=" + cadena + ", frecuencia=" + frecuencia + "]";
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getFrequency() {
        return this.frecuencia;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getString() {
        return this.cadena;
    }

    public int compareTo(TupleFrequency arg) {
        return -Integer.compare(this.getFrequency(), arg.getFrequency());
    }
}
