#encoding: UTF-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet

  class Sorpresa
      
    attr_reader :descripcion, :tipo, :valor
  
    def initialize(descripcion,valor,tipo)
      @descripcion = descripcion
      @valor = valor
      @tipo = tipo
    end
      
    def to_s
      "Texto: #{@descripcion}\n  Valor: #{@valor}\n  Tipo: #{@tipo}"      
    end      
  end
end