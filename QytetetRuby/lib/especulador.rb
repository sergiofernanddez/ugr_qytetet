# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
    class Especulador < Jugador
        attr_reader :fianza
        def initialize(jugador, fianza)
            super()
            @fianza = fianza
        end
        
        def convertirme(_)
            return self
        end
        
        def pagar_impuestos(cantidad)
            super(cantidad/2)
        end
        
        def ir_a_carcel(casilla)
            evitar_carcel = pagar_fianza(@fianza)
            
            if(!evitar_carcel)
                @casilla_actual = casilla
                @encarcelado = true
            end
        end
        
        def pagar_fianza(cantidad)
            puede_pagar = false
      
            if (@saldo > cantidad)
                puede_pagar = true
                modificar_saldo(-cantidad)
            end
            return puede_pagar
        end
        
        def to_s
            super + " (Especulador)"
        end
        
        protected :pagar_impuestos
        private :pagar_fianza

        
        
        
    end
end
