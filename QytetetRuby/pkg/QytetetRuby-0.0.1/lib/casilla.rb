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
    
    def soy_edificable
      return @tipo==TipoCasilla::CALLE
    end
    
    def esta_hipotecada
      @titulo.hipotecada
    end
    
    def tengo_propietario
      @titulo.tengo_propietario()
    end
    
    def se_puede_edificar_casa
      @numCasas  < 4 && @numHoteles < 4
    end
    
    def se_puede_edificar_hotel
      @numCasas == 4 && @numHoteles < 4
    end
    
    def set_num_casas num
      @numCasas = num
    end
    
    def set_num_hoteles num
      @numHoteles = num
    end
    
    def calcular_valor_hipoteca
      @titulo.hipotecaBase + (@numCasas*0.5*@titulo.hipotecaBase + @numHoteles*@titulo.hipotecaBase).to_i
    end
    
    def hipotecar
      @titulo.hipotecada = true;
      calcular_valor_hipoteca()
    end
    
    def cancelar_hipoteca
      @titulo.hipotecada = false
      return get_coste_hipoteca()
    end
    
    def get_coste_hipoteca
      return (calcular_valor_hipoteca()*1.1).to_i
    end
    
    def edificar_casa
      set_num_casas(@numCasas+1)
      @titulo.precioEdificar
    end
    
    def edificar_hotel
      set_num_casas(0)
      set_num_hoteles(@numHoteles+1)
      @titulo.precioEdificar
    end
    
    def propietario_encarcelado
      @titulo.propietario_encarcelado()
    end
    
    def cobrar_alquiler
      costeAlquilerBase = @titulo.alquilerBase
      costeAlquiler = costeAlquilerBase + (@numCasas*0.5 + @numHoteles*2).to_i
      @titulo.cobrar_alquiler(costeAlquiler)
    end
    
    def asignar_propietario jugador
      @titulo.propietario = jugador
      @titulo
    end
    
    def vender_titulo
      precio_compra = @coste + (@numCasas + @numHoteles)*@titulo.precioEdificar
      precio_venta = precio_compra + precio_compra*@titulo.factorRevalorizacion
      
      @titulo.propietario = nil
      @numCasas = 0
      @numHoteles = 0
      
      precio_venta
    end
    
    def to_s
      if @tipo == TipoCasilla::CALLE
        "\n            *Número de Casilla: #{@numeroCasilla} \n            *Coste: #{@coste} \n            *Número de Casas: #{@numCasas} \n            *Número de Hoteles: #{@numHoteles} \n            *Tipo: #{@tipo} \n            *Título: \n #{@titulo}"
      else
        "\n            *Número de Casilla: #{@numeroCasilla} \n            *Tipo: #{@tipo} \n"
      end
    end
    
    public :tipo, :titulo
    private :titulo=

  end

end