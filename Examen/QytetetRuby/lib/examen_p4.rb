# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tipo_sorpresa"
  require_relative "sorpresa"
  require_relative "casilla"
  require_relative "tablero"
  require_relative "titulo_propiedad"
  require_relative "tipo_casilla"
  require_relative "qytetet"
  require_relative "metodo_salir_carcel"
  require_relative "jugador"
  require_relative "dado"
  require_relative "vista_textual_qytetet"
  require_relative "calle"
  require_relative "tramposo"
  
module ModeloQytetet
class ExamenP4
    def initialize
    
    end
    
    def self.main
        jugador_1 = Jugador.new("Pepe")
        jugador_2 = Tramposo.new(jugador_1)
        
        puts jugador_1.to_s
        puts jugador_2.to_s
        
        i=0
        while(i<8)
            jugador_1.modificar_saldo(-1438)
            jugador_2.modificar_saldo(-1438)
            i = i+1
        end
        
        puts jugador_1.to_s
        puts jugador_2.to_s
        
        jugador_2.perdonar
        
        puts jugador_2.to_s
        
    end
    
    ExamenP4.main
end
end
