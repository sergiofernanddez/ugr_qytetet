#encoding: UTF-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


module ModeloQytetet
class Tablero
 
  @@casillas = Array.new
  @@carcel = Casilla.new(5,0,TipoCasilla::CARCEL)

  def self.carcel
    @@carcel
  end
  
  #Constructor
  def initialize
      inicializar_casillas
  end
  
  #Metodo to_String()
  def to_s
    @@casillas.to_s
  end
  
  def inicializar_casillas
    @@casillas <<Casilla.new(0,0,TipoCasilla::SALIDA)
    @@casillas <<Calle.new(1,6000,(TituloPropiedad.new("Carretera Málaga",50,0.1,150,250)))
    @@casillas <<Calle.new(2,8000,(TituloPropiedad.new("Camino de Alfacar",50,0.1,150,250)))
    @@casillas <<Casilla.new(3,0,TipoCasilla::SORPRESA)
    @@casillas <<Calle.new(4,10000,(TituloPropiedad.new("Calle Santa Adela,Zaidin",50,0.1,150,250)))
    @@casillas << @@carcel
    @@casillas <<Calle.new(6,12000,(TituloPropiedad.new("Avda. Andalucia",60,0.12,300,400)))
    @@casillas <<Calle.new(7,14000,(TituloPropiedad.new("Plaza Santa Ana",60,0.12,300,400)))
    @@casillas <<Casilla.new(8,0,TipoCasilla::SORPRESA)
    @@casillas <<Calle.new(9,18000,(TituloPropiedad.new("Gonzalo Gallas",60,0.12,300,400)))
    @@casillas <<Casilla.new(10,0,TipoCasilla::PARKING)
    @@casillas <<Calle.new(11,22000,(TituloPropiedad.new("Pedro Antonio",80,0.15,500,600)))
    @@casillas <<Calle.new(12,26000,(TituloPropiedad.new("Camino de Ronda",80,0.15,500,600)))
    @@casillas <<Casilla.new(13,0,TipoCasilla::SORPRESA)
    @@casillas <<Calle.new(14,30000,(TituloPropiedad.new("Paseo de los Tristes",80,0.15,500,600)))
    @@casillas <<Casilla.new(15,0,TipoCasilla::JUEZ)
    @@casillas <<Calle.new(16,35000,(TituloPropiedad.new("Constitucion",90,0.18,700,700)))
    @@casillas <<Calle.new(17,40000,(TituloPropiedad.new("Recogidas",90,0.18,700,700)))
    @@casillas <<Casilla.new(18,200,TipoCasilla::IMPUESTO)
    @@casillas <<Calle.new(19,50000,(TituloPropiedad.new("Gran Via",100,0.2,1000,750)))
  end
  
  def self.es_casilla_carcel(numero_casilla)
    
    return numero_casilla == 5
    
  end
    
  def self.obtener_casilla_numero(numero_casilla)
    
    return @@casillas.at(numero_casilla)
    
  end

  def self.obtener_nueva_casilla(casilla,desplazamiento)
    numero_casilla = casilla.numeroCasilla
    numero_casilla = (numero_casilla + desplazamiento) % @@casillas.length
      
    return @@casillas.at(numero_casilla)     
  end
  
end
end
