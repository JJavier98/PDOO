#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tipo_sorpresa"
require_relative "sorpresa"
require_relative "tipo_casilla"
require_relative "casilla"
require_relative "titulo_propiedad"
require_relative "tablero"

module ModeloQytetet
  
  class PruebaQytetet
    
    #@@mazo = Array.new
    @@mazo = []
    @tablero = Tablero.new  
    
    def self.inicializar_sorpresa
      @@mazo << Sorpresa.new("Te hemos pillado con chanclas y calcetines. Lo sentimos, ¡debes ir a la carcel!", @tablero.carcel.numeroCasilla, TipoSorpresa::IRACASILLA)
      @@mazo << Sorpresa.new("¡Un OVNI te ha abducido y te ha llevado hasta la casilla de SALIDA! Cobra tu recompensa por sobrevivir.",1000, TipoSorpresa::IRACASILLA)
      @@mazo << Sorpresa.new("Un fan anónimo ha pagado tu fianza. Sales de la cárcel", 0, TipoSorpresa::SALIRCARCEL)
      @@mazo << Sorpresa.new("Gracias a tus poderosos contactos quedas libre de la cárcel", 0, TipoSorpresa::SALIRCARCEL)
      @@mazo << Sorpresa.new("¡Levantas una piedra y encuentras dinero!", 200, TipoSorpresa::PAGARCOBRAR)
      @@mazo << Sorpresa.new("Por no recoger los excrementos de tu perro pagas a cada jugador por las molestias", 100, TipoSorpresa::PAGARCOBRAR)
      @@mazo << Sorpresa.new("Eres un químico muy patoso y provocas una explosión que afecta a todo el vecindario. Pagas por cada casa/hotel que haya en tu misma arista del tablero", 100, TipoSorpresa::PORCASAHOTEL)
      @@mazo << Sorpresa.new("Recibes el pago mensual por alquiler de casas y reservas de hotel. Recibes:", 150, TipoSorpresa::PORCASAHOTEL)
      @@mazo << Sorpresa.new("Haces una fiesta privada y asisten todos los jugadores. El precio de la entrada es:", 150, TipoSorpresa::PORJUGADOR)
      @@mazo << Sorpresa.new("¡Se te va de las manos! Por escándalo público pagas: ", 150, TipoSorpresa::PORJUGADOR)
    end
    
    def self.sorpresas_con_valor
      con_valor = []
      for value in @@mazo
        if value.valor > 0
          con_valor << value
        end
      end
      con_valor
    end
    
    def self.sorpresas_ir_a_casilla
      de_casilla = []
      for cas in @@mazo
        if cas.tipo == TipoSorpresa::IRACASILLA
          de_casilla << cas
        end
      end
      de_casilla
    end
    
    def self.sorpresas_tipo_x abc
      tipo_x = []
      for tipe in @@mazo
        if tipe.tipo == abc
          tipo_x << tipe
        end
      end
      tipo_x
    end
    
    def self.main
      inicializar_sorpresa
      #puts @@mazo.to_s
      
      con_valor = sorpresas_con_valor
      #puts con_valor
      
      ir_a_casilla = sorpresas_ir_a_casilla
      #puts ir_a_casilla
      
      tipo_x = sorpresas_tipo_x(TipoSorpresa::IRACASILLA)
      #puts tipo_x
      
      nombres = []
      nombres << "pablo" << "juan" << "rodolfa" << "pepita"
      
      nuevo = Qytetet.instance
      nuevo.inicializar_juego(nombres)
      
      puts nuevo
      
    end
    
  end
  
  PruebaQytetet.main
end