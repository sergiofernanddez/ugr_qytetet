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
public class TituloPropiedad {
    //Atributos de instancia
    private String nombre;
    private boolean hipotecada;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    public Jugador propietario;
    private Casilla casillaTitulo;
    
    //Constructor de la clase
    public TituloPropiedad(String nombre, int alquilerBase, float factorRevalorizacion, int hipotecaBase, 
            int precioEdificar){
        this.nombre = nombre;
        hipotecada = false;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;        
    }
    
    //Consultores y modificadores

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }
    
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public boolean getHipotecada(){
        return hipotecada;
    }
    
    public void setHipotecada(boolean hipotecada){
        this.hipotecada = hipotecada;
    }
    
    public int getAlquilerBase(){
        return alquilerBase;
    }
    
    public void setAlquilerBase(int alquilerBase){
        this.alquilerBase = alquilerBase;
    }
    
    public float getFactorRevalorizacion(){
        return factorRevalorizacion;
    }
    
    public void setFactorRevalorizacion(float factorRevalorizacion){
        this.factorRevalorizacion = factorRevalorizacion;
    }
    
    public int getHipotecaBase(){
        return hipotecaBase;
    }

    public Casilla getCasillaTitulo() {
        return casillaTitulo;
    }

    public void setCasillaTitulo(Casilla casillaTitulo) {
        this.casillaTitulo = casillaTitulo;
    }
    
       
    public void setHipotecaBase(int hipotecaBase){
        this.hipotecaBase = hipotecaBase;
    }
    
    public int getPrecioEdificar(){
        return precioEdificar;
    }
    
    public void setPrecioEdificar(int precioEdificar){
        this.precioEdificar = precioEdificar;
    }
    
    
    //Metodos de instancia
    void cobrarAlquiler(int coste){
        propietario.modificarSaldo(coste);
    }
    
    boolean propietarioEncarcelado(){
        return propietario.getEncarcelado();        
    }
    
    boolean tengoPropietario(){
        
        return propietario != null;
        
    }
    
    @Override
    public String toString(){
        return "Nombre: " + nombre + "\nHipotecada: " + hipotecada +
                "\nCoste alquiler: " + alquilerBase +
                "\nCoste hipoteca: " + hipotecaBase + "\nPrecio edificar: " + precioEdificar+"\n";
    }
    
    TituloPropiedad asignarPropietario(Jugador jugador){return null;}
    
    int calcularValorHipoteca(){return 0;}
    
    int cancelarHipoteca(){return 0;}
    
    int cobrarAlquiler(){return 0;}
    
    int edificarCasa(){return 0;}
    
    int edificarHotel(){return 0;}
    
    boolean estaHipotecada(){return false;}
    
    int getCosteHipoteca(){return 0;}
    
    int hipotecar(){return 0;}
    
    int precioTotalComprar(){return 0;}
    
    boolean sePuedeEdificarCasa(){return false;}
    
    boolean sePuedeEdificarHotel(){return false;}
    
    boolean soyEdificable(){return false;}
    
    int venderTitulo(){return 0;}
    
    void asignarTituloPropiedad(){}
}
