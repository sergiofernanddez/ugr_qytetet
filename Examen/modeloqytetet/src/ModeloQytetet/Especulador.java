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
public class Especulador extends Jugador{

        static int FactorEspeculador = 2;
        int fianza;
        
        protected Especulador(Jugador jugador, int fianza){
            super(jugador);
            this.fianza = fianza;
        }

        public static int getFactorEspeculador() {
            return FactorEspeculador;
        }      
        
        protected void PagarImpuestos(int cantidad){
            cantidad /= 2;
            this.modificarSaldo(-cantidad);
        }
        
        @Override
        public void irACarcel(Casilla casilla){
            
            boolean puedoPagar = this.pagarFianza(fianza);
            
            if(puedoPagar)
                this.setEncarcelado(false);
            else
                this.irACarcel(casilla);
            
        }
        
        protected Especulador Convertirme(int fianza){
            this.fianza = fianza;
            return this;
        }
        
        private boolean pagarFianza(int cantidad){
            
            boolean pagar;
            pagar = this.tengoSaldo(cantidad);
            
            if(pagar) 
                this.PagarImpuestos(cantidad);
            
            return pagar;
        }
        
        
}
