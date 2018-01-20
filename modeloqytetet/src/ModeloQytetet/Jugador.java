/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloQytetet;

import java.util.ArrayList;

/**
 *
 * @author sergio
 */
public class Jugador {
    //Variables de instancia
    private boolean encarcelado;
    private String nombre;
    private int saldo = 75000;
    private Sorpresa cartaLibertad;
    private Casilla casillaActual;
    private ArrayList<Casilla> propiedades;
    static int FactorEspeculador = 1;

    //Constructor
    public Jugador(String nombre) {
        this.encarcelado = false;
        this.nombre = nombre;
        this.cartaLibertad = null;
        this.casillaActual = null;
        this.propiedades = new ArrayList();
    }

    protected Jugador(Jugador jugador){
      
        this.encarcelado = jugador.encarcelado;
        this.nombre = jugador.nombre;
        this.cartaLibertad = jugador.cartaLibertad;
        this.casillaActual = jugador.casillaActual;
        this.propiedades = jugador.propiedades;
        
        for(int i=0; i<propiedades.size(); i++)
            ((Calle)propiedades.get(i)).asignarPropietario(this);
        
    }
    
    //Consultores y modificadores   
    public String getNombre(){
        return nombre;
    }

    public Casilla getCasillaActual() {
        return casillaActual;
    }

    public boolean getEncarcelado() {
        return encarcelado;
    }

    public int getSaldo() {
        return saldo;
    }

    public Sorpresa getCartaLibertad() {
        return cartaLibertad;
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    public void setEncarcelado(boolean encarcelado) {
        this.encarcelado = encarcelado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void setCartaLibertad(Sorpresa cartaLibertad) {
        this.cartaLibertad = cartaLibertad;
    }

    public void setCasillaActual(Casilla casillaActual) {
        this.casillaActual = casillaActual;
    }

    public void setPropiedades(ArrayList propiedades) {
        this.propiedades = propiedades;
    }

    public static int getFactorEspeculador() {
        return FactorEspeculador;
    }      

      
    @Override
    public String toString() {
        return  "Nombre: "+nombre+"\nEncarcelado: "+encarcelado+
                "\nSaldo actual: "+saldo+
                "\n\n\tPropiedades obtenidas\n"+propiedades.toString();
    }
    
           
    //Metodos de instancia
    
    protected Especulador convertirme(int fianza){
        Especulador chorizo = new Especulador(this,fianza);
        return chorizo;
    }
    
    protected void pagarImpuestos(int cantidad){
        this.modificarSaldo(-cantidad);
    }
    
    
    public boolean tengoPropiedades(){
        
        return propiedades.isEmpty() != true;
    }
    
    public boolean actualizarPosicionCasilla(Casilla casilla){
        
       if(casilla.getNumeroCasilla() < casillaActual.getNumeroCasilla())
           
           this.modificarSaldo(Qytetet.SALDO_SALIDA);
           
        boolean tengoPropietario = false;

        this.setCasillaActual(casilla);
        
        if(casilla.soyEdificable()){
            
            tengoPropietario = ((Calle) casilla).tengoPropietario();
            
            if(((Calle)casilla).tengoPropietario()){
                
                boolean encarcelado = ((Calle)casilla).propietarioEncarcelado();
                
                if(!encarcelado){
                    
                    int costeAlquiler = ((Calle)casilla).cobrarAlquiler();
                    
                    this.modificarSaldo(-costeAlquiler);
                }
            }
            
            if(casilla.getTipo() == TipoCasilla.IMPUESTO){
                
               this.pagarImpuestos(((Calle)casilla).getCoste());
            }
        }
        
        return tengoPropietario;
    }
    
    public boolean comprarTituloPropiedad(){
        
        boolean puedoComprar = false;
        
        if(casillaActual.soyEdificable()){
            
            boolean tengoPropietario = ((Calle)casillaActual).tengoPropietario();
            
            if(!tengoPropietario){
                
                int costeCompra = ((Calle)casillaActual).getCoste();
                
                if(costeCompra <= saldo){
                    
                    Casilla titulo =  casillaActual;
                    ((Calle)casillaActual).getTitulo().asignarPropietario(this);
                    propiedades.add(titulo);
                    
                    this.modificarSaldo(-costeCompra);
                                        
                    puedoComprar = true;
                    
                    
                }
            }
        }
        
        return puedoComprar;
    }
    
    public Sorpresa devolverCartaLibertad(){
        
        Sorpresa sinCarta = null;
        
        return cartaLibertad = sinCarta;
        
    }
    
    public void irACarcel(Casilla casilla){
    
        this.setCasillaActual(casilla);
        this.setEncarcelado(true);
    }
    
    public void modificarSaldo(int cantidad){
        
        saldo += cantidad;
    }
    
    int obtenerCapital(){
        
        int capital = 0;
        
        capital += saldo;
        
        for(int i=0; i<propiedades.size(); i++){
            capital += ((Calle)propiedades.get(i)).getCoste();
            capital += ( ((Calle) propiedades.get(i)).getNumCasas()+
                                ((Calle)propiedades.get(i)).getNumHoteles())*
                                ((Calle)propiedades.get(i)).getPrecioEdificar();
            
            if(((Calle)propiedades.get(i)).getTitulo().getHipotecada() == true)
                capital -= ((Calle)propiedades.get(i)).getTitulo().getHipotecaBase();
            
        }
        return capital;
        
    }
    
    ArrayList<Casilla> obtenerPropiedadesHipotecadas(boolean hipotecada){
        
        ArrayList<Casilla> hipotecadas = new ArrayList();
        
        for(int i=0; i<propiedades.size(); i++)
            
            if(((Calle)propiedades.get(i)).getTitulo().getHipotecada() == hipotecada)
                
                hipotecadas.add(propiedades.get(i));
        
        return hipotecadas;
    }
    
    void pagarCobrarPorCasaYHotel(int cantidad){
    
        int numeroTotal = this.cuantasCasasHotelesTengo();
        this.modificarSaldo(numeroTotal*cantidad);
    
    }
    
    boolean pagarLibertad(int cantidad){
        boolean tengoSaldo = tengoSaldo(cantidad);
        
        if(tengoSaldo)
            this.modificarSaldo(cantidad);
        
        return tengoSaldo;
    }
    
    boolean puedoEdificarCasa(Casilla casilla){
        boolean esMia = esDeMiPropiedad(casilla);
        boolean tengoSaldo = false;
        
        if(esMia){
            int costeEdificarCasa = ((Calle)casilla).getPrecioEdificar();
            tengoSaldo = tengoSaldo(costeEdificarCasa);
        }
        
        return esMia && tengoSaldo;
    }
    
    boolean puedoEdificarHotel(Casilla casilla){
        return false;
    }
    
    boolean puedoHipotecar(Casilla casilla){
        boolean esMia = this.esDeMiPropiedad(casilla);
        return esMia;
    }
    
    boolean puedoPagarHipoteca(Casilla casilla){
        return false;
    }
    
    boolean puedoVenderPropiedad(Casilla casilla){
          
        boolean hipotecada = false;
        
        for(int i=0; i<propiedades.size(); i++)
              
                if(propiedades.get(i) == casilla)
                
                    hipotecada = ((Calle)propiedades.get(i)).getTitulo().getHipotecada();
        

        return esDeMiPropiedad(casilla) == true && hipotecada == true;
            
    }
    
    boolean tengoCartaLibertad(){
        
        return cartaLibertad != null;
        
    }
    
    public void venderPropiedad(Casilla casilla){
    
        int precioVenta = ((Calle)casilla).venderTitulo();
        this.modificarSaldo(precioVenta);
        this.eliminarDeMisPropiedades(casilla);
        
    }
    
    public int cuantasCasasHotelesTengo(){
        
        int nCasas = 0;
        int nHoteles = 0;
        
        for(int i=0; i<propiedades.size(); i++){
            
            nCasas += ((Calle)propiedades.get(i)).getNumCasas();
            nHoteles +=((Calle)propiedades.get(i)).getNumHoteles();
        }
        
        return nCasas + nHoteles;
    }
    
    public void eliminarDeMisPropiedades(Casilla casilla){
 
        for(int i=0; i<propiedades.size(); i++)
              
                if(propiedades.get(i) == casilla)
                    
                    propiedades.remove(i);
    }
    
    public boolean esDeMiPropiedad(Casilla casilla){
        
        for(int i=0; i<propiedades.size(); i++){
            
            if(propiedades.get(i) == casilla )
                
                return true;
        }
        
        return false;
    }
    
    public boolean tengoSaldo(int cantidad){
       
        return saldo >= cantidad;
    }
      
}
