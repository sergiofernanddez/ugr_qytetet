/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloQytetet;

/**
 *
 * @author paquejo
 */
public abstract class Casilla {
    private int  numeroCasilla;
    TipoCasilla tipo;
    
    public Casilla(int numero, TipoCasilla tipo){
        this.numeroCasilla = numero;
        this.tipo = tipo;
    }
    
    protected Casilla(Casilla nueva){
        this.numeroCasilla = nueva.numeroCasilla;
        this.tipo = nueva.tipo;
    }
    
    
    public int getNumeroCasilla() {
        return numeroCasilla;
    }

    public TipoCasilla getTipo() {
        return tipo;
    }
    
    abstract boolean soyEdificable();
    
}
