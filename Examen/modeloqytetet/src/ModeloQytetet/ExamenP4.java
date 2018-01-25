/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloQytetet;

/**
 *
 * @author sergioff98
 */
public class ExamenP4 {
    
    public static void main(String[] args){
        Jugador jugador_1 = new Jugador("Pepe");
        Tramposo jugador_2 = new Tramposo(jugador_1);
        
        System.out.println(jugador_1.toString());
        System.out.println(jugador_2.toString());
        
        for (int i=0; i<8; i++)
        {
            jugador_1.modificarSaldo(-9850);
            jugador_2.modificarSaldo(-9875);
        }
        
        System.out.println(jugador_1.toString());
        System.out.println(jugador_2.toString());
        
        jugador_2.perdonar();
        System.out.println(jugador_2.toString());
        
        
    }
}
