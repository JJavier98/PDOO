#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
  
  class Casilla
    attr_accessor :numCasas, :numHoteles, :titulo
    attr_reader :tipo, :coste, :numeroCasilla 
    
    def initialize( numCas, cost, tipo, titulo )
      @numeroCasilla = numCas
      @coste = cost
      @numCasas = 0
      @numHoteles = 0
      @tipo = tipo
      @titulo = titulo
    end

    # NO CALLE
    def self.constructor_1(numCas, tipo)
      self.new(numCas, 0, tipo, nil)
    end
    #CALLE
    def self.constructor_2(numCas, cost, titulo)
      self.new(numCas, cost, TipoCasilla::CALLE, titulo)
    end

    def to_s
      "\n            Número de Casilla: #{@numeroCasilla} \n            Coste: #{@coste} \n            Número de Casas: #{@numCasas} \n            Número de Hoteles: #{@numHoteles} \n            Tipo: #{@tipo} \n            Título: \n #{@titulo}"
    end
    
    public :tipo, :titulo
    private :titulo=

  end

end