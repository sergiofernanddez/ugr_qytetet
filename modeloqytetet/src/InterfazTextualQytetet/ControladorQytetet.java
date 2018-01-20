/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfazTextualQytetet;
import GUIQytetet.Dado;
import ModeloQytetet.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ControladorQytetet {
    //Variables de instnacia
    Qytetet juego;
    Jugador jugador;
    Casilla casilla;
    VistaTextualQytetet vista;
    Dado dado = GUIQytetet.Dado.getInstance();

    
    public Casilla elegirPropiedad(ArrayList<Casilla> propiedades){ 
    // //este metodo toma una lista de propiedades y genera una lista de strings, con el numero y nombre de las propiedades
    // //luego llama a la vista para que el usuario pueda elegir.
        vista.mostrar("\tCasilla\tTitulo");
        int seleccion;
        ArrayList<String> listaPropiedades= new ArrayList();
    
        for(Casilla casilla : propiedades){
            listaPropiedades.add("\t"+((Calle)casilla).getNumeroCasilla()+"\t"+((Calle)casilla).getTitulo().getNombre());
        }
        seleccion=vista.menuElegirPropiedad(listaPropiedades);  
         return propiedades.get(seleccion);
}
    
    void inicializacionJuego(){
        juego = Qytetet.getInstance();
        ArrayList<String> nombres = new ArrayList();
        
        nombres = vista.obtenerNombreJugadores();
        
        juego.inicializarJuego(nombres);
        
        jugador = juego.getJugadorActual();
        casilla = jugador.getCasillaActual();
        
        vista.pausa();
        System.out.println(juego.toString());
    }
      
    
     void desarrolloJuego(){
        
        do{
        //MOVIMIENTO. Funciona
        System.out.println("\nTurno de: ");
        System.out.println(jugador.getNombre());
        
        
        if(jugador.getEncarcelado() == true){
           int opcion =  vista.menuSalirCarcel();
           
           if(opcion == 0)
               juego.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
           
           if(opcion == 1)
               juego.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
        }
        
        if(jugador.getEncarcelado() == false){
           
            vista.mostrar("Tira el dado");
            int num_dado = dado.nextNumber();
            System.out.println("Ha salido "+num_dado);
            vista.pausa();
            
            casilla = jugador.getCasillaActual();
            
            jugador.setCasillaActual(juego.getTablero().obtenerNuevaCasilla(casilla, num_dado));
            
            casilla = jugador.getCasillaActual();

            if(casilla.getTipo() == TipoCasilla.SORPRESA){
                vista.mostrar(juego.getCartaMazo(0).toString());
                juego.setCartaActual(juego.getCartaMazo(0));
                juego.aplicarSorpresa();
            }
            
            if(casilla.getTipo() == TipoCasilla.PARKING)
                vista.mostrar("Has entrado en el parking");
            
            if(casilla.getTipo() == TipoCasilla.CARCEL){
                vista.mostrar("Ha caido en la carcel");
                jugador.setEncarcelado(true);
            }
            
            if(casilla.getTipo() == TipoCasilla.CALLE){
                vista.mostrar("Informacion sobre la calle");
                vista.mostrar(casilla.toString());
                
                vista.pausa();
                                
                if(((Calle)casilla).getTitulo().getPropietario() == null){
                    boolean decision = vista.elegirQuieroComprar();
                    
                    if(decision == true){
                        boolean puedeComprar = jugador.comprarTituloPropiedad();
                        if(puedeComprar)
                            ((Calle)casilla).asignarPropietario(jugador);
                        else
                            vista.mostrar("Saldo insuficiente");
                    }
                }else{
                    vista.mostrar("Esta calle ya tiene propietario");
                    int coste = ((Calle)casilla).cobrarAlquiler();
                    jugador.modificarSaldo(-coste);
                }
            }
        }
        
        //ADMINISTRACION INMOBILIARIA
        if(jugador.getEncarcelado() == false && jugador.getSaldo()>0 && jugador.tengoPropiedades() == true)
        {  
            int eleccion = -1;
            
            while(eleccion != 0){
            
            Casilla calle = this.elegirPropiedad(jugador.getPropiedades());
            
            eleccion = vista.menuGestionInmobiliaria();            
            
            if(eleccion == 0){
                vista.mostrarSituacionJugador(jugador);
                jugador = juego.siguienteJugador();
            }
            
            if(eleccion == 1){
                int coste = ((Calle)calle).edificarCasa();
                jugador.modificarSaldo(-coste);
                vista.mostrarSituacionJugador(jugador);
            }
            
            if(eleccion == 2){
                if(((Calle)calle).getNumCasas()<4)
                    vista.mostrar("Se necesitan al menos 4 casas para construir un hotel");
                else{
                    int coste = ((Calle)calle).edificarHotel();
                    jugador.modificarSaldo(-coste);
                }
                vista.mostrarSituacionJugador(jugador);
            }
            
            if(eleccion == 3){
                jugador.venderPropiedad(casilla);
                vista.mostrarSituacionJugador(jugador);
            }
            
            if(eleccion == 4){
                int hipoteca = ((Calle)calle).hipotecar();
                jugador.modificarSaldo(hipoteca);
                vista.mostrarSituacionJugador(jugador);
            }
            
            if(eleccion == 5){
                int cancelar = ((Calle)calle).cancelarHipoteca();
                jugador.modificarSaldo(-cancelar);
                vista.mostrarSituacionJugador(jugador);
            }
        }
        }else{
            //Cambio de turno
            vista.mostrarSituacionJugador(jugador);
            jugador = juego.siguienteJugador();
        }
        
        
        }while(jugador.getSaldo() >= 0);
        
        //Fin del juego
        if(jugador.getSaldo() < 0){
            
            vista.mostrar("Fin del juego");
            HashMap<Integer, String> ranking = juego.obtenerRanking();
            System.out.println(ranking.toString());
            
        }
            
}
    
   public static void main(String[] args){
       
       ControladorQytetet j = new ControladorQytetet();

       j.inicializacionJuego();
       j.desarrolloJuego();
    }
    
}


/*Cosas que funcionan

    -Pasar de turno
    -Comprar pripiedad
    -Salir de la carcel pagando
    -Salir de la carcel tirando dado
    -Cobrar alquiler de una casilla
    -Construir casa
    -Construir hotel
    -Vender propiedad
    -Hipotecar propiedad
    -Cancelar hipoteca
*/
