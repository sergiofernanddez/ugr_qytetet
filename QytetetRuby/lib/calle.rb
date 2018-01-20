# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
  
  require_relative 'tipo_casilla'
  require_relative'titulo_propiedad'
  class Calle < Casilla
    attr_accessor :numCasas, :numHoteles, :titulo
    def initialize(num_cas,coste,titulo)
      super(num_cas,coste,TipoCasilla::CALLE)
      @numCasas = 0
      @numHoteles = 0
      @titulo = titulo
      
      if @titulo != nil
        @titulo.casilla = self
      end
    end
    
  def asignar_propietario(jugador) 
    @titulo.propietario = jugador
    return @titulo
  end
  
  def calcular_valor_hipoteca
    hipoteca_base = @titulo.hipotecaBase
    cantidad_recibida = hipoteca_base + (@numCasas*0.5) + (@numHoteles*2.0)
    cantidad_recibida;
  end
    
  def cancelar_hipoteca
    @titulo.hipotecado = false
    cantidad_pagar = calcular_valor_hipoteca * 1.1      
    return cantidad_pagar
    
  end

  def cobrar_alquiler    
    coste_alquiler_base = @titulo.alquilerBase
    coste_alquiler = coste_alquiler_base + (@numCasas*0.5)+(@numHoteles*2.0)
    @titulo.cobrar_alquiler(coste_alquiler)
    
    return coste_alquiler    
  end
    
  def edificar_casa
      @numCasas +=1
    
    coste_edificar_casa = @titulo.precioEdificar
    
    return coste_edificar_casa    
  end

  def edificar_hotel
    nuevo_num = @numHoteles + 1
    @numHoteles = nuevo_num
    @numCasas = 0
    coste_edificar_hotel = @titulo.precioEdificar
      
    return coste_edificar_hotel
  end

  def esta_hipotecada
    @titulo.hipotecado
  end

  def get_precio_edificar
    coste_edificar_casa = @titulo.precioEdificar
      
    return coste_edificar_casa
  end

  def hipotecar
    @titulo.hipotecado = true
    cantidad_recibida = calcular_valor_hipoteca
    return cantidad_recibida
  end

  def precio_total_comprar
      precio_compra = @coste + (@numCasas + @numHoteles) * @titulo.precioEdificar
    
    return precio_compra
  end

  def propietario_encarcelado
      encarcelado = @titulo.propietario_encarcelado
    return encarcelado    
  end

  def se_puede_edificar_casa(factor)
    return @numCasas < (4 * factor)
  end

  def se_puede_edificar_hotel(factor)
    return @numCasas == (4*factor) && @numHoteles < (4*factor)
  end

  def soy_edificable
    return true
  end

  def tengo_propietario
    tengo_propietario = @titulo.tengo_propietario
    return tengo_propietario
  end

  def vender_titulo    
    precio_compra = self.precio_total_comprar
    precio_venta = (precio_compra + @titulo.precioEdificar*precio_compra);
        
    @numCasas = 0
    @numHoteles = 0        
    @titulo.propietario= nil        
        
    return precio_venta;    
  end

  def asignar_titulo_propiedad
    
  end
  
  def to_s
        "Número casilla: #{@numeroCasilla}\n Coste: #{@coste}\n Número Hoteles: #{@numHoteles}\n Número Casas: #{@numCasas}\n Tipo: #{@tipo}\n Título propiedad: #{@titulo}"
    end
  
end
end
  

