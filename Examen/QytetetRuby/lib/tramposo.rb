# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
    attr_accessor :veces_trampa, :importe_fraudulento
    class Tramposo < Jugador
        def initialize(jugador)
            @encarcelado = jugador.encarcelado
            @nombre = jugador.nombre
            @saldo = jugador.saldo
            @carta_libertad = jugador.carta_libertad
            @casilla_actual = jugador.casilla_actual
            @propiedades = jugador.propiedades
            @veces_trampa = 0
            @importe_fraudulento = 0
        end
        
        def modificar_saldo(cantidad)
            valor = rand(2)
            
            if valor == 0
                super(cantidad/2)
                @veces_trampa = @veces_trampa + 1
                @importe_fraudulento = @importe_fraudulento + cantidad/2
           
            else if valor == 1
                 super(cantidad)   
            end
            end
        end
        
        def perdonar
            @veces_trampa = 0
            @importe_fraudulento = 0
        end
        
        def to_s
            super + "Tramposo { veces_trampa: #{@veces_trampa}, importe_fraudulento: #{@importe_fraudulento}"
        end
    end
end
