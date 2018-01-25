#encoding UTF-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet

    class TituloPropiedad
      attr_reader :nombre, :alquilerBase, :factorRevalorizacion, :hipotecaBase, :precioEdificar
      attr_accessor :hipotecado, :casilla, :propietario 

      #Constructor
      def initialize(n,ab,fr,hb,pe)
        @nombre = n
        @hipotecado = false
        @alquilerBase = ab
        @factorRevalorizacion = fr
        @hipotecaBase = hb
        @precioEdificar = pe
        @propietario = nil
        @casilla = nil
        
        
      end

      #Metodo to_String()
      def to_s
      "Nombre: #{@nombre}\n Hipotecada: #{@hipotecada}\n Alquiler Base: #{@alquilerBase}\n 
      Factor Revalorización: #{@factorRevalorizacion}\n Hipoteca Base: #{@hipotecaBase}\n 
      Precio Edificar: #{@precioEdificar}\n Propietario: " + (@propietario == nil ? "" : @propietario.nombre)    
      end

      #Metodos de instancia
      def cobrar_alquiler(coste)
        @propietario.modificar_saldo(-coste)
      end

      def propietario_encarcelado
        return @propietario.encarcelado
      end

      def tengo_propietario
        @propietario != nil
      end


    end
end
