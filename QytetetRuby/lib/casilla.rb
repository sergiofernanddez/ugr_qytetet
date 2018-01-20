#encoding utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


module ModeloQytetet
  
require_relative 'tipo_casilla'

class Casilla
   
  #Consultores y modificadores basicos
  attr_reader :numeroCasilla, :coste, :tipo
  
 
  def initialize(nc,c,t)
    @numeroCasilla = nc
    @coste = c
    @tipo = t
  end
  
  public
  
  #Métodos de clase que son los nuevos constructores
  #Constructor casilla no calle
  #def self.new_casilla(num_cas,tipo)  
    # asigna memoria para el objeto
      #nuevo_obj=allocate      
    
    # inicializa el objeto, invocando al método initialize
      #nuevo_obj.send(:initialize,num_cas,0,tipo,nil,0,0)     
    
    # devuelve el objeto creado e inicializado
      #nuevo_obj 
  #end
  
  #Constructor casillas calle
  #def self.new_calle(num_cas,coste,titulo)
      #new_obj = allocate
      #new_obj.send(:initialize,num_cas,coste,TipoCasilla::CALLE,titulo,0,0)
      #new_obj
  #end    

  #Eliminamos el metodo new de la clase 
  #Anula el método constructor new para que no podamos usarlo tal cual
  #Casilla.instance_eval { undef :new }

  def soy_edificable
    return false
  end
  
  def to_s
    "Número casilla: #{@numeroCasilla}\n Tipo: #{@tipo}" + (@tipo == TipoCasilla::IMPUESTO ? "\n Coste: #{@coste}" : "")
    end
  
end

end