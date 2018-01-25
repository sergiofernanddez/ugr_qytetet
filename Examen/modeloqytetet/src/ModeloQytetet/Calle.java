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
public class Calle extends Casilla{

    private int coste;
    private int numCasas;
    private int numHoteles;
    private TituloPropiedad titulo;

    public Calle(int numero,int cos,TituloPropiedad titulo) {
        super(numero, TipoCasilla.CALLE);
        this.coste = cos;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.titulo = titulo;
    }
    
    @Override
    public String toString(){
        return "Número: "+super.getNumeroCasilla()+
                "\nNombre: "+titulo.getNombre()+"\nCoste: "+coste+
                "\nNº Casas: "+numCasas+"\nNº Hoteles: "+numHoteles+
                "\nPrecio edificar: "+titulo.getPrecioEdificar()+
                "\nValor hipoteca: "+titulo.getHipotecaBase()+"\n";
    }

    //Consultores
    public int getCoste() {
        return coste;
    }

    public int getNumCasas() {
        return numCasas;
    }

    public int getNumHoteles() {
        return numHoteles;
    }

    public TituloPropiedad getTitulo() {
        return titulo;
    }

    //Modificadores
    public void setNumCasas(int numCasas) {
        this.numCasas = numCasas;
    }

    public void setNumHoteles(int numHoteles) {
        this.numHoteles = numHoteles;
    }
    
    //metodos de instancia
    public void asignarPropietario(Jugador jugador){
        titulo.setPropietario(jugador);
    }
    
    public int calcularValorHipoteca(){
        int hipotecaBase = titulo.getHipotecaBase();
        int cantidadRecibida = (int) (hipotecaBase + (numCasas*0.5) + (numHoteles*2));
        return cantidadRecibida;
    }
    
    public int cancelarHipoteca(){
        titulo.setHipotecada(false);
        int cantidadDar = this.calcularValorHipoteca();
        return cantidadDar;
    }
    
    public int cobrarAlquiler(){
        int costeAlquilerBase = titulo.getAlquilerBase();
        int costeAlquiler = (int) (costeAlquilerBase + (numCasas*0.5) +( numHoteles*2));
        titulo.cobrarAlquiler(costeAlquiler);
        return costeAlquiler;
    }
    
    boolean sePuedeEdificarCasa(){    
        return numCasas < 4*Jugador.getFactorEspeculador();
    }
    
    boolean sePuedeEdificarHotel(){   
        boolean sePuede;
        sePuede = (!this.sePuedeEdificarCasa() && numHoteles <= Jugador.getFactorEspeculador());
        return sePuede;
    }
        
    public int edificarCasa(){
        int costeEdificarCasa = 0;
        
         if(this.sePuedeEdificarCasa()){   
            this.setNumCasas(numCasas+1);
            costeEdificarCasa = titulo.getPrecioEdificar();
         }
        
        return costeEdificarCasa;
    }
    
    public int edificarHotel(){
        
        int costeEdificarHotel = 0;
        
        if(this.sePuedeEdificarHotel()){
            this.setNumHoteles(numHoteles+1);
            costeEdificarHotel = titulo.getPrecioEdificar();
        }
        return costeEdificarHotel;
    }
    
    boolean estaHipotecada(){
        return titulo.getHipotecada();
    }
    
    public boolean tengoPropietario(){
       return titulo.tengoPropietario() == true;
    }
    
    int getPrecioEdificar(){
        return titulo.getPrecioEdificar();
    }
    
    int venderTitulo(){
    
        titulo.setPropietario(null);
        this.setNumHoteles(0);
        this.setNumCasas(0);
        
        int precioCompra = this.getCoste()+(this.getNumCasas()+this.getNumHoteles())*titulo.getPrecioEdificar();
        int precioVenta = (int) (precioCompra+titulo.getFactorRevalorizacion()*precioCompra);
        
        return precioVenta;
    }
    
    public int hipotecar(){
        titulo.setHipotecada(true);
        int cantidadRecibida = this.calcularValorHipoteca();
        return cantidadRecibida;
    }

    @Override
    boolean soyEdificable() {
        return tipo == TipoCasilla.CALLE;
    }    

    boolean propietarioEncarcelado() {
        return titulo.getPropietario().getEncarcelado();
    }
}
