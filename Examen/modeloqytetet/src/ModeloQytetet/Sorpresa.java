/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloQytetet;

/**
 *
 * @author paco1998
 */
public class Sorpresa {

        
        private String descripcion;
        private TipoSorpresa tipo;
        private int valor;
        
        public Sorpresa(String texto, int valor, TipoSorpresa tipo){
            descripcion=texto;
            this.tipo=tipo;
            this.valor=valor;
        }
        
        String getDescripcion(){
            return descripcion;
        }
        
        public TipoSorpresa getTipo(){
            return tipo;
        }
        
        public int getValor(){
            return valor;
        }
        
        @Override
        public String toString() {
            return descripcion;
        }
}
