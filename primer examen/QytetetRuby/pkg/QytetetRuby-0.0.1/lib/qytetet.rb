#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require "singleton"
require_relative "dado"
require_relative "tablero"
require_relative "sorpresa"
require_relative "casilla"
require_relative "jugador"
require_relative "titulo_propiedad"

module ModeloQytetet

  class Qytetet
    include Singleton
    
    attr_reader :cartaActual, :jugadorActual, :tableroActual#EXAMEN-inicio #EXAMEN-fin
    
    @@MAX_JUGADORES = 4
    @@MAX_CARTAS = 10
    @@MAX_CASILLAS = 20
    @@PRECIO_LIBERTAD = 200
    @@SALDO_SALIDA = 1000
    
    def initialize
      @cartaActual = nil
      @mazo = []
      @jugadores = []
      @jugadorActual = @jugadores[0]
      @tableroActual
      @dado = nil
      
      inicializar_tablero()
      inicializar_cartas_sorpresa()
      
    end
    
    def inicializar_cartas_sorpresa()
      @mazo << Sorpresa.new("Te hemos pillado con chanclas y calcetines. Lo sentimos, ¡debes ir a la carcel!", @tableroActual.carcel.numeroCasilla, TipoSorpresa::IRACASILLA)
      @mazo << Sorpresa.new("¡Un OVNI te ha abducido y te ha llevado hasta la casilla de SALIDA! Cobra tu recompensa por sobrevivir.",1000, TipoSorpresa::IRACASILLA)
      @mazo << Sorpresa.new("Un fan anónimo ha pagado tu fianza. Sales de la cárcel", 0, TipoSorpresa::SALIRCARCEL)
      @mazo << Sorpresa.new("Gracias a tus poderosos contactos quedas libre de la cárcel", 0, TipoSorpresa::SALIRCARCEL)
      @mazo << Sorpresa.new("¡Levantas una piedra y encuentras dinero!", 200, TipoSorpresa::PAGARCOBRAR)
      @mazo << Sorpresa.new("Por no recoger los excrementos de tu perro pagas a cada jugador por las molestias", 100, TipoSorpresa::PAGARCOBRAR)
      @mazo << Sorpresa.new("Eres un químico muy patoso y provocas una explosión que afecta a todo el vecindario. Pagas por cada casa/hotel que haya en tu misma arista del tablero", 100, TipoSorpresa::PORCASAHOTEL)
      @mazo << Sorpresa.new("Recibes el pago mensual por alquiler de casas y reservas de hotel. Recibes:", 150, TipoSorpresa::PORCASAHOTEL)
      @mazo << Sorpresa.new("Haces una fiesta privada y asisten todos los jugadores. El precio de la entrada es:", 150, TipoSorpresa::PORJUGADOR)
      @mazo << Sorpresa.new("¡Se te va de las manos! Por escándalo público pagas: ", 150, TipoSorpresa::PORJUGADOR)
      
      @mazo.shuffle
    end
    
    def inicializar_jugadores nombres
      for name in nombres
        @jugadores << Jugador.new(name, @tableroActual.obtener_casilla_numero(0))
      end
#      if (i<1 || i>MAX_JUGADORES-1)
#        raise UnsupportedOperationException, 'Sin implementar'
#      end
    end
    
    def inicializar_tablero
      @tableroActual = Tablero.new
    end
    
    def get_carta_actual
      return @cartaActual
    end
    
    def get_jugador_actual
      return @jugadorActual
    end
    
    def to_s
      resultado = "Qytetet!! \n\n Jugadores: "
      for j in @jugadores
        resultado += j.to_s
      end
      resultado += "\n\n Mazo: \n"
      for m in @mazo
        resultado += m.to_s
      end
      resultado += "\n\n      Tablero #{@tableroActual} \n JugadorActual: #{@jugadorActual} \n CartaSorpresaActual: #{@cartaActual}\n"
    end
    
    public :cartaActual, :jugadorActual
  end
  
end