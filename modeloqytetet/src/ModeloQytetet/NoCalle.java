/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModeloQytetet;

/**
 * 
 * @author pakejo
 */
public class NoCalle extends Casilla {
    
    public NoCalle(int numero, TipoCasilla tipo) {
        super(numero, tipo);
    }
    
     //Metodo to_string()
    @Override
    public String toString() {
        return tipo+"";
    }

    @Override
    boolean soyEdificable() {
        return tipo == TipoCasilla.CALLE;
    }

}
