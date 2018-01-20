/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloQytetet;

import GUIQytetet.Dado;
import java.util.*;
import java.util.HashMap;

/**
 *
 * @author paquejo
 */
public class Qytetet {
    //Variables de clase e instancia
    public static final int MAX_JUGADORES = 4;
    static final int MAX_CARTAS = 10;
    static final int MAX_CASILLAS = 20;
    static final int PRECIO_LIBERTAD = 200;
    static final int SALDO_SALIDA = 1000;
    Sorpresa cartaActual ;
    Jugador jugadorActual;
    ArrayList<Jugador> jugadores;
    Tablero tablero;
    ArrayList<Sorpresa> mazo;
    static Qytetet instance = new Qytetet();
    Dado dado = GUIQytetet.Dado.getInstance();
    
    
    //Constructor
    public Qytetet(){
        
         cartaActual = null;
         jugadorActual = null;
         jugadores = new ArrayList();
         tablero = new Tablero();
         mazo = new ArrayList();
}

    //Consultores basicos

    public void setCartaActual(Sorpresa cartaActual) {
        this.cartaActual = cartaActual;
    }
    
    public Tablero getTablero() {
        return tablero;
    }
    
    public static Qytetet getInstance() {
        return instance;
    }

    public Sorpresa getCartaActual() {
        return cartaActual;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public Sorpresa getCartaMazo(int carta) {
        return mazo.get(carta);
        
    }
    
    
    
    //Metodos de instancia
    @Override
    public String  toString(){
        
        return "Jugadores\n" + jugadores.toString() + "\n\nTablero\n"+ tablero.toString() + 
                "\n\nCartas Sorpresa\n" + mazo.toString();
    }
    
    
    
    public boolean aplicarSorpresa(){
    
    boolean tienePropietario = false;
    
    if( null != cartaActual.getTipo())switch (cartaActual.getTipo()) {
           
        case PAGARCOBRAR:
                jugadorActual.modificarSaldo(cartaActual.getValor());
                break;
        
        case IRACASILLA:
                boolean esCarcel = tablero.esCasillaCarcel(cartaActual.getValor());
                if(esCarcel){
                    
                    this.encarcelarJugador();
                }       
                Casilla nuevaCasilla = tablero.obtenerCasillaNumero(cartaActual.getValor());
                tienePropietario = jugadorActual.actualizarPosicionCasilla(nuevaCasilla);
                break;
        
        case PORCASAHOTEL:
                jugadorActual.pagarCobrarPorCasaYHotel(cartaActual.getValor());
                break;
        
        case PORJUGADOR:
                for(int i=0; i<MAX_JUGADORES; i++){
                    
                    Jugador jugador = jugadores.get(i+1);
                    
                    if(jugador != jugadorActual){
                        
                        jugador.modificarSaldo(cartaActual.getValor());
                        jugadorActual.modificarSaldo(-cartaActual.getValor());
                    }
                }       break;
        
                
        case CONVERTIRME:
                jugadorActual.convertirme(cartaActual.getValor());
                break;
            
                
        default:
                break;
        }
    
    if(cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL){
        
        jugadorActual.setCartaLibertad(cartaActual);
        
    }else{
        mazo.add(cartaActual);
        mazo.remove(0);
    }
    return tienePropietario;
    
    }
    
    public boolean cancelarHipoteca(Casilla casilla){return false;}
    
    public boolean comprarTituloPropiedad(){
    
          return  jugadorActual.comprarTituloPropiedad();
    
    }
    
    public boolean edificarCasa(Casilla casilla){
    
        boolean puedoEdificar = false;
        
        if(casilla.soyEdificable()){
            boolean sePuedeEdificar = ((Calle)casilla).sePuedeEdificarCasa();
            
            if(sePuedeEdificar){
               puedoEdificar = jugadorActual.puedoEdificarCasa(casilla);
               
               if(puedoEdificar){
                   int costeEdificarCasa = ((Calle)casilla).edificarCasa();
                   jugadorActual.modificarSaldo(-costeEdificarCasa);
               }
            }
        }
        return puedoEdificar;
    }
    
    public boolean edificarHotel(Casilla casilla){return false;}
    
    boolean puedoHipotecar = false;
    
    boolean hipotecarPropiedad(Casilla casilla){
        if(casilla.soyEdificable()){
            boolean sePuedeHipotecar = !((Calle)casilla).estaHipotecada();
        
            if(sePuedeHipotecar){
                puedoHipotecar = jugadorActual.puedoHipotecar(casilla);
                
                if(puedoHipotecar){
                    int cantidadRecibida = ((Calle)casilla).hipotecar();
                    jugadorActual.modificarSaldo(cantidadRecibida);
                }
            }
        }
        return puedoHipotecar;
    }
    
    public void inicializarJuego(ArrayList<String> nombres){
        
        this.inicializarJugadores(nombres);
        this.inicializarCartasSorpresa();
        this.inicializarTablero();
        this.salidaJugadores();
    }
    
    public boolean  intentarSalirCarcel(MetodoSalirCarcel metodo){
    
        boolean libre = false;
        
        if(metodo == MetodoSalirCarcel.TIRANDODADO){
            
            int valorDado = dado.nextNumber();
            System.out.println("Ha salido "+valorDado);
            
            if(valorDado > 5)
                libre = true;    
        }
        
        if(metodo == MetodoSalirCarcel.PAGANDOLIBERTAD){
            
            boolean tengoSaldo = jugadorActual.pagarLibertad(-200);
            libre = tengoSaldo;
        }
        
        if(libre)
            jugadorActual.setEncarcelado(false);
        
        return libre;
    }
    
    public boolean jugar(){
    
        int valorDado = dado.nextNumber();
        Casilla casillaPosicion = jugadorActual.getCasillaActual();
        Casilla nuevaCasilla = tablero.obtenerNuevaCasilla(casillaPosicion, valorDado);
        boolean tienePropietario = jugadorActual.actualizarPosicionCasilla(nuevaCasilla);
        
        if(!nuevaCasilla.soyEdificable()){
            
            if(nuevaCasilla.getTipo() == TipoCasilla.JUEZ){
                
                this.encarcelarJugador();
                
            }else if(nuevaCasilla.getTipo() == TipoCasilla.SORPRESA)
                cartaActual = mazo.get(0);
        }
    
        return tienePropietario;
    }
    
    public HashMap<String, Integer> obtenerRanking(){
    
        HashMap<String, Integer> ranking = new HashMap();
        
        jugadores.forEach((j) -> {
            int capital = j.obtenerCapital();
            ranking.put(j.getNombre(), capital);
        });
        
        return ranking;
    }
    
    public ArrayList<Casilla> propiedadesHipotecadasJugador(boolean hipotecadas){
    
        ArrayList<Casilla> casillas = new ArrayList();
        
        for(int i=0; i<jugadorActual.getPropiedades().size(); i++){
            
            if(((Calle)jugadorActual.getPropiedades().get(i)).getTitulo().getHipotecada() == hipotecadas);
            
                casillas.add(jugadorActual.getPropiedades().get(i));
        }
    
          return casillas;
    }
    
    public Jugador siguienteJugador(){

        int indice = 0;

         for(int i=0; i<jugadores.size(); i++){
             if(jugadores.get(i) == jugadorActual)
                 indice = i;
         }
         
         if( (indice+1) < jugadores.size()){
             jugadorActual = jugadores.get(indice+1);
             return jugadores.get(indice+1);
             
         }else{
             jugadorActual = jugadores.get(0);
             return jugadores.get(0);
         }

    }
    
    public boolean venderPropiedad(Casilla casilla){
    
        boolean puedoVender = false;
        
        if(casilla.soyEdificable()){
            puedoVender = jugadorActual.puedoVenderPropiedad(casilla);
            
            if(puedoVender){
                jugadorActual.venderPropiedad(casilla);
            }
        }
        
        return puedoVender;
    }
    
    public void encarcelarJugador(){
        
        Sorpresa carta = null;
    
        if(!jugadorActual.tengoCartaLibertad()){
            
            Casilla casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
          
        }else
            
            carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);
      
    }
    
    private void inicializarCartasSorpresa(){
     
        mazo.add(new Sorpresa("Te han pillado hackeando el banco, vas a la cárcel",
               tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        
        mazo.add(new Sorpresa("Vas a la casilla 7", 7, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Vas a la casilla 14", 14, TipoSorpresa.IRACASILLA));
        
        mazo.add(new Sorpresa("Te ha tocado el Euromillón, recibes 2000", 
                2000, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Te han puesto una multa, pagas 500 y los 6 puntos del carné", 
                500, TipoSorpresa.PAGARCOBRAR));
        
        mazo.add(new Sorpresa("Pagas por casa 200 y doble por hotel", 
                200, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Cobras por casa 200 y doble por hotel", 
                200, TipoSorpresa.PORCASAHOTEL));
        
        mazo.add(new Sorpresa("Recibes por tu cumpleaños 300 de cada jugador", 
                300, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Invita a un jamon iberico al resto de jugadores. Pagas 300 a cada jugador", 
                300, TipoSorpresa.PORJUGADOR));
        
        mazo.add(new Sorpresa("Han sobornado al juez y puedes salir de la cárcel", 
                0, TipoSorpresa.SALIRCARCEL));
        
        mazo.add(new Sorpresa("Enhorabuena, pasas a convertirte en especulador",
                3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("Pasar a pertenecer al gobierno de Grandada, te conviertes en especulador",
                5000,TipoSorpresa.CONVERTIRME));
        
        Collections.shuffle(mazo);
    }   
    
    private void inicializarJugadores(ArrayList<String>nombres){
    
        for(int i=0; i<nombres.size(); i++){
            jugadores.add(new Jugador(nombres.get(i)));
        }
    }
    
    private void inicializarTablero(){
        
        tablero.inicializar();
    }
    
    private void salidaJugadores(){
    
        for(int i=0; i<jugadores.size(); i++){
            jugadores.get(i).setCasillaActual(tablero.obtenerCasillaNumero(0));
        }
        
        int numero = (int) (Math.random() * jugadores.size()) ;
        
        jugadorActual = jugadores.get(numero);

    }
    
    public ArrayList<Jugador> getJugadores(){
        
        return jugadores;
    }
                       
            
}

