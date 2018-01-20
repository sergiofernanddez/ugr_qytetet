#encoding: UTF-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
  require 'singleton'

class Qytetet
  
  include Singleton
  
  attr_accessor :Carta_actual, :Jugador_actual, :Jugadores
  
  @@MAX_JUGADORES = 4
  @@MAX_CARTAS = 10
  @@MAX_CASILLAS = 20
  @@PRECIO_LIBERTAD = 200
  @@SALDO_SALIDA = 100000
  
  def initialize
    @Carta_actual = nil
    @Jugador_actual = nil
    @Jugadores = Array.new
    @Tablero = Tablero.new
    @Mazo = Array.new
    @Dado = Dado.instance
  end
  
    #Metodo de clase
    def self.saldo_salida
      @@SALDO_SALIDA
    end
    
    def carta_mazo(indice)
        @Mazo[indice]
    end
    
    
    def to_s
      "QYTETET\n Carta Actual: #{@cartaActual}\n Mazo: #{@mazo.to_s}\n Jugadores: " + @jugadores.to_s + "\n 
      Jugador Actual: #{@jugadorActual.to_s}\n Tablero #{@tablero}\n Dado #{@dado.to_s}"
    end
    
    
    public
    def aplicar_sorpresa
      
      if(@Carta_actual.tipo == TipoSorpresa::PAGARCOBRAR)
        
        @Jugador_actual.modificar_saldo(@Carta_actual.valor)
       
      elsif(@Carta_actual.tipo == TipoSorpresa::IRACASILLA)
        
        es_carcel = Tablero.es_casilla_carcel(@Carta_actual.valor)
        
        if(es_carcel)
          encarcelar_jugador();
        end
        
        nueva_casilla = Tablero.obtener_casilla_numero(@Carta_actual.valor)
      
        tiene_propietario = @Jugador_actual.actualizar_posicion_casilla(nueva_casilla)
      
      if(@Carta_actual.tipo == TipoSorpresa::PORCASAHOTEL)        
          @Jugador_actual.pagar_cobrar_por_casa_y_hotel(@Carta_actual.valor)
      end
      
      if(@Carta_actual.tipo == TipoSorpresa::PORJUGADOR)
        
        for i in 0..@@MAX_JUGADORES-1
          
          jugador = @jugadores[i+1]
          
          if(jugador != @Jugador_actual)
            
            jugador.modificar_saldo(@Carta_actual.valor)
            @Jugador_actual.modificar_saldo(-@Carta_actual.valor)
            
          end
        end 
      end
      
      if (@Carta_actual.tipo == TipoSorpresa::CONVERTIRME)
          @Jugador_actual = 
            @Jugadores[@jugadores.find_index(@Jugador_actual)] = @Jugador_actual.convertirme(@Carta_actual.valor)
      end
      
      if(@Carta_actual.tipo == TipoSorpresa::SALIRCARCEL)
        
        @Jugador_actual.carta_libertad = @Carta_Actual
      end
        
      else
        @Mazo << @Carta_actual
      end
      
      tiene_propietario
    end
    
    def cancelar_hipoteca(casilla)
      puedo_cancelar = false
      
      if (casilla.soy_edificable)
        se_puede_cancelar = casilla.esta_hipotecada

        
        if (se_puede_cancelar)
          puedo_cancelar = @Jugador_actual.puedo_pagar_hipoteca(casilla)

          if (puedo_cancelar)
            coste_cancelar = casilla.cancelar_hipoteca
            @Jugador_actual.modificar_saldo(-coste_cancelar)
          end
        end
      end
      return puedo_cancelar
    end
    
    def comprar_titulo_propiedad(casilla)      
     return @Jugador_actual.comprar_titulo_propiedad      
    end
    
    def edificar_casa(casilla)
      
      puedo_edificar = false
      
      if(casilla.soy_edificable)
        
        se_puede_edificar = casilla.se_puede_edificar_casa(@Jugador_actual.factor_especulador)
        
        if(se_puede_edificar)
          
          puedo_edificar = @Jugador_actual.puedo_edificar_casa(casilla)
          
          if(puedo_edificar)
            
            coste_edificar_casa = casilla.edificar_casa
            @Jugador_actual.modificar_saldo(- coste_edificar_casa)
            
          end
        end
      end
      return puedo_edificar
    end
    
    def edificar_hotel(casilla)
      puedo_edificar = false
      soy_edificable = casilla.soy_edificable
      
      if (soy_edificable)
        se_puede_edificar = casilla.se_puede_edificar_hotel(@Jugador_actual.factor_especulador)
        
        if (se_puede_edificar)
          puedo_edificar = @JugadorActual.puedo_edificar_hotel(casilla)
          
          if (puedo_edificar)
            coste_edificar_hotel = casilla.edificar_hotel
            @JugadorActual.modificar_saldo(-coste_edificar_hotel)
          end
        end
      end
      
      return puedo_edificar
    end
        
    def hipotecar_propiedad(casilla)
      
      if(casilla.soy_edificable)
        se_puede_hipotecar = !casilla.esta_hipotecada
        
        if(se_puede_hipotecar)
          puedo_hipotecar = @Jugador_actual.puedo_hipotecar(casilla)
          
          if(puedo_hipotecar)
            cantidad_recibida = casilla.hipotecar
            @Jugador_actual.modificar_saldo(cantidad_recibida)
          end
        end
      end
      puedo_hipotecar      
    end
    
    def inicializar_juego(nombres)
      
      inicializar_jugadores(nombres)
      inicializar_cartas_sorpresa
      inicializar_tablero
      salida_jugadores
      
    end
    
    public
    def intentar_salir_carcel(metodo)
        libre = false
      
      if(metodo == MetodoSalirCarcel::TIRANDODADO)
        
        valor_dado = @Dado.tirar
        
        if(valor_dado > 5)
          libre = true
        end
      end
      
      if(metodo == MetodoSalirCarcel::PAGANDOLIBERTAD)
        
        tengo_saldo = @Jugador_actual.pagar_libertad(-200)
        libre = tengo_saldo
      end
      
      if(libre)
        @Jugador_actual.encarcelado = libre
      end
      
      return libre
    end
    
    def jugar
      
      valor_dado = @Dado.tirar
      casilla_posicion = @Jugador_actual.casilla_actual;
      nueva_casilla = Tablero.obtener_nueva_casilla(casilla_posicion, valor_dado)
      tiene_propietario = @Jugador_actual.actualizar_posicion_casilla(nueva_casilla)
      
      if(!nueva_casilla.soy_edificable)
        
        if(nueva_casilla.tipo == TipoCasilla::JUEZ)
          
          encarcelar_jugador;
          
        elsif(nueva_casilla.tipo == TipoCasilla::SORPRESA)
          
          @Carta_actual = @Mazo[0]
        end
      end
      
      tiene_propietario
    end
    
    def obtener_ranking
      ranking = {}
      
      jugadores = @Jugadores.sort { |j1,j2| j2.obtener_capital - j1.obtener_capital }
      
      jugadores.each do |jugador|
        capital = jugador.obtener_capital
        ranking[jugador.nombre] = capital
      end
      
      return ranking      
    end 
    
    def propiedades_hipotecadas_jugador(hipotecadas)
      casillas = Array.new
      propiedades = @jugadorActual.obtener_propiedades_hipotecadas(hipotecadas)
      
      for item in @tablero.casillas
        for aux in propiedades
          if (aux == item.titulo)
            casillas << item
          end
        end
      end
      
      return casillas      
    end
    
    def siguiente_jugador
      @Jugador_actual = @Jugadores[(@Jugadores.find_index(@Jugador_actual) + 1) % @Jugadores.size]
    end
    
    
    def vender_propiedad(casilla)
      
      puedo_vender = false
      
      if(casilla.soy_edificable)
        puedo_vender = @Jugador_actual.puedo_vender_propiedad(casilla)
        
        if(puedo_vender)
          @Jugador_actual.vender_propiedad(casilla)
        end
        
      end
      return puedo_vender
    end
    
    
    def encarcelar_jugador
      
      if(!@Jugador_actual.tengo_carta_libertad)
        
        casilla_carcel = Tablero.carcel
        @Jugador_actual.ir_a_carcel(casilla_carcel)
        
      else
        
        carta = @Jugador_actual.devolver_carta_libertad
        @Mazo << carta
        
      end
      
    end
    
    private
    def inicializar_cartas_sorpresa
      @Mazo << Sorpresa.new("Te han pillado hackeando el banco, vas a la cárcel",5 , TipoSorpresa::IRACASILLA)
      @Mazo << Sorpresa.new("Vas a la casilla 7", 7, TipoSorpresa::IRACASILLA);
      @Mazo << Sorpresa.new("Vas a la casilla 14", 14, TipoSorpresa::IRACASILLA);

      @Mazo << Sorpresa.new("Te ha tocado el Euromillón, recibes 2000", 
              2000, TipoSorpresa::PAGARCOBRAR);
      @Mazo << Sorpresa.new("Te han puesto una multa, pagas 500 y los 6 puntos del carné", 
              500, TipoSorpresa::PAGARCOBRAR);

      @Mazo << Sorpresa.new("Pagas por casa 200 y doble por hotel", 
              200, TipoSorpresa::PORCASAHOTEL);
      @Mazo << Sorpresa.new("Cobras por casa 200 y doble por hotel", 
              200, TipoSorpresa::PORCASAHOTEL);

      @Mazo << Sorpresa.new("Recibes por tu cumpleaños 300 de cada jugador", 
              300, TipoSorpresa::PORJUGADOR);
      @Mazo << Sorpresa.new("Invita a una cena al resto de jugadores. Pagas 300 a cada jugador", 
              300, TipoSorpresa::PORJUGADOR);

      @Mazo << Sorpresa.new("Han sobornado al juez y puedes salir de la cárcel", 
              0, TipoSorpresa::SALIRCARCEL);
          
      @Mazo << Sorpresa.new("Enhorabuena, pasas a ser especulador", 3000, TipoSorpresa::CONVERTIRME)
      @Mazo << Sorpresa.new("Pasas a pertenecer al gobierno de granada", 5000, TipoSorpresa::CONVERTIRME)
          
      @Mazo.shuffle!
            
    end
    
    def inicializar_jugadores(nombres)
      
     for i in nombres
       @Jugadores << Jugador.new(i)
     end
    end
    
    def inicializar_tablero
        @tablero = Tablero.new
    end
    
    def salida_jugadores
      
      for i in @Jugadores
        i.casilla_actual = Tablero.obtener_casilla_numero(0)
        i.saldo = 100000
      end
      
      numero = rand(@Jugadores.size)
      
      @Jugador_actual = @Jugadores[numero]
      
    end
    
    
end
end
