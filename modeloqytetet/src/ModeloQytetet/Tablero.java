/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloQytetet;

import java.util.ArrayList;

/**
 *
 * @author paquejo
 */
public class Tablero {
    
    private ArrayList<Casilla> casillas = new ArrayList();
    private Casilla carcel;
    
    //Constructor
    public Tablero(){
        carcel = new NoCalle(5,TipoCasilla.CARCEL);
    }
    
    //Consultores basicos
    public Casilla getCarcel() {
        return carcel;
    }
    //Metodo to_string()
    @Override
    public String toString() {
        return casillas.toString();
    }
    
    void inicializar() {
        casillas.add(new NoCalle(0,TipoCasilla.SALIDA));
        casillas.add(new Calle(1,6000,(new TituloPropiedad("Carretera Málaga",50,(float) 0.1,150,250))));
        casillas.add(new Calle(2,8000,(new TituloPropiedad("Camino de Alfacar",50,(float) 0.1,150,250))));
        casillas.add(new NoCalle(3,TipoCasilla.SORPRESA));
        casillas.add(new Calle(4,10000,(new TituloPropiedad("Calle Santa Adela,Zaidin",50,(float) 0.1,150,250))));
        casillas.add(carcel);
        casillas.add(new Calle(6,12000,(new TituloPropiedad("Avda. Andalucia",60,(float) 0.12,300,400))));
        casillas.add(new Calle(7,14000,(new TituloPropiedad("Plaza Santa Ana",60,(float) 0.12,300,400))));
        casillas.add(new NoCalle(8,TipoCasilla.SORPRESA));
        casillas.add(new Calle(9,18000,(new TituloPropiedad("Gonzalo Gallas",60,(float) 0.12,300,400))));
        casillas.add(new NoCalle(10,TipoCasilla.PARKING));
        casillas.add(new Calle(11,22000,(new TituloPropiedad("Pedro Antonio",80,(float) 0.15,500,600))));
        casillas.add(new Calle(12,26000,(new TituloPropiedad("Camino de Ronda",80,(float) 0.15,500,600))));
        casillas.add(new NoCalle(13,TipoCasilla.SORPRESA));
        casillas.add(new Calle(14,30000,(new TituloPropiedad("Paseo de los Tristes",80,(float) 0.15,500,600))));
        casillas.add(new NoCalle(15,TipoCasilla.JUEZ));
        casillas.add(new Calle(16,35000,(new TituloPropiedad("Constitucion",90,(float) 0.18,700,700))));
        casillas.add(new Calle(17,40000,(new TituloPropiedad("Recogidas",90,(float) 0.18,700,700))));
        casillas.add(new NoCalle(18,TipoCasilla.IMPUESTO));
        casillas.add(new Calle(19,50000,(new TituloPropiedad("Gran Via",100,(float) 0.2,1000,750))));
    }
    
    public boolean esCasillaCarcel(int numeroCasilla){
        
        return numeroCasilla == 5;
    
    }
    
    public Casilla obtenerCasillaNumero(int numeroCasilla){
    
        if( (numeroCasilla >= 0)  && (numeroCasilla  < 20) )
            return casillas.get(numeroCasilla);
        
        return null;
    }
    
    public Casilla obtenerNuevaCasilla(Casilla casilla, int desplazamiento){
    
        int posicionFinal = casilla.getNumeroCasilla() + desplazamiento;
        
        if(posicionFinal < 20){
            return casillas.get(posicionFinal);
            
        }else
            return casillas.get(posicionFinal - 20);
    }
    
}
