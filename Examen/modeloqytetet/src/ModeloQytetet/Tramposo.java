/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//EXAMEN
package ModeloQytetet;

import java.util.Random;

/**
 *
 * @author sergioff98
 */
public class Tramposo extends Jugador {
    int vecesTrampa;
    double importeFraudulento;
    
    protected Tramposo(Jugador jugador)
    {
        super(jugador);
        vecesTrampa = 0;
        importeFraudulento = 0;
    }
    
    @Override
    public void modificarSaldo(int cantidad){
        Random generator=new Random();
        int numero = generator.nextInt(2);
        
        if(numero==0){
            super.modificarSaldo(cantidad/2);
            vecesTrampa = vecesTrampa + 1;
            importeFraudulento = importeFraudulento + cantidad/2;
        }
        else if(numero==1)
            super.modificarSaldo(cantidad);
    }
    
    public void perdonar()
    {
        vecesTrampa=0;
        importeFraudulento=0;
    }

    @Override
    public String toString() {
        return (super.toString() + "Tramposo{" + "vecesTrampa=" + vecesTrampa + ", importeFraudulento=" + importeFraudulento + '}');
    }
    
    
     
}
