# encoding: UTF-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet

class Jugador
  
  #Mdificadores y consultores basicos
  attr_accessor :casilla_actual, :encarcelado
  attr_reader :carta_libertad, :propiedades, :nombre, :saldo, :factor_especulador
  attr_writer :nombre, :saldo, :propiedades, :carta_libertad
  
  #Constructor
  def initialize(nombre)
    @encarcelado = false
    @nombre = nombre
    @saldo = 7500
    @carta_libertad = nil
    @casilla_actual = Casilla.new(0,0, TipoCasilla::SALIDA)
    @propiedades = []
    @factor_especulador = 1
  end
  
  
  #Metodo to string
  def to_s
      " Encarcelado #{@encarcelado} \n Nombre: #{@nombre} \n Saldo: #{@saldo}\n 
      Carta Liberdad: #{@carta_libertad}  \n Casilla actual: #{@casilla_actual}\n Propiedades #{@propiedades} "
  end
  
  #Metodos de instancia
  def tengo_propiedades
    ret = false
      
    if(@propiedades.size() > 0)
      ret = true
    end
    return ret   
  end
  
  def actualizar_posicion_casilla(casilla)
    
    if(casilla.numeroCasilla < @casilla_actual.numeroCasilla)
      modificar_saldo(Qytetet.saldo_salida)     
    end
    
    tengo_propietario = false
    
    @casilla_actual = casilla
    
    if(casilla.soy_edificable)
      tengo_propietario = casilla.tengo_propietario
      
      if(casilla.tengo_propietario)
        self.encarcelado = casilla.propietario_encarcelado
        
        if(!encarcelado)
          coste_alquiler = casilla.cobrar_alquiler
          modificar_saldo(-coste_alquiler)
        end
      end
    end
    
    if(casilla.tipo == TipoCasilla::IMPUESTO)
      coste = casilla.coste
      pagar_impuestos(coste)
    end
    
    tengo_propietario
  end
  
  def comprar_titulo_propiedad
   puedo_comprar = false;
    
    if @casilla_actual.soy_edificable
      tengo_propietario = @casilla_actual.tengo_propietario
      
      if !tengo_propietario
        coste_compra = @casilla_actual.coste
        
        if coste_compra <= @saldo
          titulo = @casilla_actual.asignar_propietario(self)
          @propiedades << titulo
          self.modificar_saldo(-coste_compra)
          puedo_comprar = true
        end
      end
    end
    
    return puedo_comprar
  end
  
  def devolver_carta_libertad
    cartaAux = Sorpresa.new(@carta_libertad.texto, @carta_libertad.valor, @carta_libertad.tipo)
       @carta_libertad = nil
       
      return cartaAux 
  end
  
  def ir_a_carcel(casilla)
    @casilla_actual = casilla
    @encarcelado = true    
  end
  
  def modificar_saldo(cantidad)
    @saldo += cantidad
  end
  
  def obtener_capital
    
    capital = 0
    
    capital += @saldo
    
    for i in @propiedades
      capital += i.casilla_titulo.coste
      capital += (o.casilla_titulo.numCasas+
                          i.casilla_titulo.numHoteles)*
                          i.casilla_titulo.get_precio_edificar    
      if(i.hipotecado == true)        
        capital -= i.hipotecaBase        
      end    
    end    
    return capital
  end
  
  def obtener_propiedades_hipotecadas(hipotecada)
    
    hipoteca = Array.new
    
    for i in @propiedades
      hipoteca << i if (i.hipotecado == hipotecada)
    end
    
    return hipoteca
  end
  
  def pagar_cobrar_por_casa_y_hotel(cantidad)  
    numero_total = cuantas_casas_hoteles_tengo
    modificar_saldo(numero_total*cantidad)    
  end
    
  def pagar_libertad(cantidad)    
    tengo_saldo = tengo_saldo(cantidad)
    
    if(tengo_saldo)
      modificar_saldo(-cantidad)
    end    
    return tengo_saldo
  end
    
  def puedo_edificar_casa(casilla)    
    es_mia = es_de_mi_propiedad(casilla)
    tengo_saldo = false
    
    if(es_mia)
      coste_edificar_casa = casilla.titulo.precioEdificar
      tengo_saldo = tengo_saldo(coste_edificar_casa)
    end    
    return es_mia && tengo_saldo    
  end
    
  def puedo_edificar_hotel(casilla)
    tengo_saldo = false
    es_mia = es_de_mi_propiedad(casilla)
      
    if(es_mia)
      coste_edificar = casilla.titulo.precioEdificar
      tengo_saldo = tengo_saldo(coste_edificar)
    end
      
    return es_mia && tengo_saldo
  end
    
  def puedo_hipotecar(casilla)
    es_mia = es_de_mi_propiedad(casilla)
    return es_mia
  end
    
  def  puedo_pagar_hipoteca(casilla)
    hipoteca = casilla.calcular_valor_hipoteca * 1.1
    puedo_pagar = false
    if(hipoteca < @saldo)
      puedo_pagar = true
    end
    return puedo_pagar
  end
    
  def puedo_vender_propiedad(casilla)
    es_mia = es_de_mi_propiedad(casilla)
    hipotecada = casilla.esta_hipotecada
      
    return es_mia && !hipotecada
  end
  
  def tengo_carta_libertad
    return @carta_libertad != nil
  end   
  
  def vender_propiedad(casilla)
    
    precio_venta = casilla.vender_titulo
    modificar_saldo(precio_venta)
    eliminar_de_mis_propiedades(casilla)
    
  end
  
  def cuantas_casas_hoteles_tengo      
    for i in @propiedades      
      n_casas += i.casilla_titulo.numCasas
      n_hoteles += i.casilla_titulo.numHoteles      
    end    
    return n_casas + n_hoteles      
  end
  
  def eliminar_de_mis_propiedades(casilla)
    for i in @propiedades
      if(i.casilla == casilla)
        @propiedades.delete(i)
      end
    end
  end
  
  def es_de_mi_propiedad(casilla)
    mio = false;
    for i in @propiedades
      if(i.casilla == casilla)
        mio = true
      end
    end
    return mio
  end
        
  def tengo_saldo(cantidad)
    return  (@saldo > cantidad)
  end
  
  def pagar_impuestos(cantidad)
      modificar_saldo(-cantidad)
  end
  
  def convertirme(fianza)
      espec = Especulador.new(self, fianza)
      espec.propiedades.each do |prop|
          prop.propietario = espec
      end
      return espec
  end
   
  end
end