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
    
    attr_reader :cartaActual, :jugadorActual
    
    @@MAX_JUGADORES = 4
    @@MAX_CARTAS = 10
    @@MAX_CASILLAS = 20
    @@PRECIO_LIBERTAD = 200
    @@SALDO_SALIDA = 1000
    
    def initialize
      @cartaActual = nil
      @mazo = []
      @jugadores = []
      @jugadorActual = nil
      @tableroActual = nil
      @dado = Dado.instance
      
    end
    
    def inicializar_cartas_sorpresa()
      @mazo << Sorpresa.new("Te hemos pillado con chanclas y calcetines. Lo sentimos, ¡debes ir a la carcel!", @tableroActual.carcel.numeroCasilla, TipoSorpresa::IRACASILLA)
      @mazo << Sorpresa.new("¡Un OVNI te ha abducido y te ha llevado hasta la casilla de SALIDA! Cobra tu recompensa por sobrevivir.",0, TipoSorpresa::IRACASILLA)
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
        @jugadores << Jugador.new(name)
      end
#      if (i<1 || i>MAX_JUGADORES-1)
#        raise UnsupportedOperationException, 'Sin implementar'
#      end
    end
    
    def inicializar_tablero
      @tableroActual = Tablero.new
    end
    
    def inicializar_juego nombres
      inicializar_tablero()
      inicializar_jugadores (nombres)
      inicializar_cartas_sorpresa()
      salida_jugadores()
    end
    
    def get_carta_actual
      return @cartaActual
    end
    
    def get_jugador_actual
      return @jugadorActual
    end
    
    def comprar_titulo_propiedad
      @jugadorActual.comprar_titulo()
    end
    
    def edificar_casa casilla
      puedo_edificar = false
      
      if(casilla.soy_edificable())
        se_puede_edificar = casilla.se_puede_edificar_casa()
        
        if(se_puede_edificar)
          puedo_edificar = @jugadorActual.puedo_edificar_casa(casilla)
          
          if(puedo_edificar)
            coste_edificar_casa = casilla.edificar_casa()
            @jugadorActual.modificar_saldo(-coste_edificar_casa)
          end
        end
      end
      
      puedo_edificar
    end
    
    def edificar_hotel casilla
      puedo_edificar = false
      
      if(casilla.soy_edificable())
        se_puede_edificar = casilla.se_puede_edificar_hotel()
        
        if(se_puede_edificar)
          puedo_edificar = @jugadorActual.puedo_edificar_hotel(casilla)
          
          if(puedo_edificar)
            coste_edificar_hotel = casilla.edificar_hotel()
            @jugadorActual.modificar_saldo(-coste_edificar_hotel)
          end
        end
      end
      
      puedo_edificar
    end
    
    def hipotecar_propiedad casilla
      puedo_hipotecar_propiedad = false
      
      if(casilla.soy_edificable())
        if(!casilla.esta_hipotecada())
          if(@jugadorActual.puedo_hipotecar(casilla))
            cantidad_recibida = casilla.hipotecar()
            @jugadorActual.modificar_saldo(cantidad_recibida)
            puedo_hipotecar_propiedad = true
          end
        end
      end
      
      puedo_hipotecar_propiedad
    end
    
    def cancelar_hipoteca casilla
      puedo_cancelar = @jugadorActual.puedo_pagar_hipoteca(casilla)
      if(puedo_cancelar)

          coste_hipoteca = casilla.cancelar_hipoteca()
          @jugadorActual.modificar_saldo(-coste_hipoteca)
      end

      puedo_cancelar
    end
    
    def vender_propiedad casilla
      puedo_vender = false
      if (casilla.soy_edificable())
        puedo_vender = @jugadorActual.puedo_vender_propiedad(casilla)
        
        if(puedo_vender)
          @jugadorActual.vender_propiedad(casilla)
        end
      end
      puedo_vender
    end
    
    def encarcelar_jugador
      if(!@jugadorActual.tengo_carta_libertad())
        
        casilla_carcel = @tableroActual.carcel
        
        @jugadorActual.ir_a_carcel(casilla_carcel)
        
      else
        
        carta = @jugadorActual.devolver_carta_libertad()
        mazo << carta
        
      end
    end
    
    def aplicar_sorpresa
      tienePropietario = false
      
      if (@cartaActual.tipo == TipoSorpresa::PAGARCOBRAR)
        
        @jugadorActual.modificar_saldo(@cartaActual.valor)
        
      elsif (@cartaActual.tipo == TipoSorpresa::IRACASILLA)
        
        es_carcel = @tableroActual.es_casilla_carcel(@cartaActual.valor)
        
        if(es_carcel)
          
          encarcelar_jugador()
          
        else
          
          nueva_casilla = @tableroActual.obtener_casilla_numero(@cartaActual.valor)
          tiene_propietario = @jugadorActual.actualizar_posicion(nueva_casilla)
          
        end
        
      elsif (@cartaActual.tipo == TipoSorpresa::PORCASAHOTEL)
        
        @jugadorActual.pagar_cobrar_por_casa_hotel(@cartaActual.valor)
        
      elsif(@cartaActual.tipo == TipoSorpresa::PORJUGADOR)
        
        for jug in @jugadores
          
          if (jug != @jugadorActual)
            
            jug.modificar_saldo(@cartaActual.valor)
            @jugadorActual.modificar_saldo(-@cartaActual.valor)
            
          end
          
        end
        
      end
      
      if(@cartaActual.tipo == TipoSorpresa::SALIRCARCEL)
        
        @jugadorActual.cartaLibertad = @cartaActual
      end  
        
      @mazo << @cartaActual  #SOLO SE DEVUELVE LA CARTA SI ES TIPO CACEL??
        
      
    end
    
    def intentar_salir_carcel metodo
      libre = false
      
      if(metodo == MetodoSalirCarcel::TIRANDODADO)
        valor_dado = @dado.tirar()
        libre = valor_dado > 5
        
      elsif (metodo == MetodoSalirCarcel::PAGANDOLIBERTAD)
        libre = @jugadorActual.pagar_libertad(-@@PRECIO_LIBERTAD)
      end
      
      if (libre)
        @jugadorActual.encarcelado = false
      end
      libre
    end
    
    def jugar
      tiene_propietario = false
      
      puts "\n\n\n****Pulsa Intro para tirar el dado****\n\n"
      gets.chomp
      valor_dado = @dado.tirar()
      puts "Has sacado #{valor_dado}\n\n\n"
      casilla_posicion = @jugadorActual.casillaActual
      nueva_casilla = @tableroActual.obtener_nueva_casilla(casilla_posicion, valor_dado)
      
      tiene_propietario = @jugadorActual.actualizar_posicion(nueva_casilla)
      
      if(!nueva_casilla.soy_edificable())
        
        if(nueva_casilla.tipo == TipoCasilla::JUEZ)
          
          encarcelar_jugador()
          
        elsif(nueva_casilla.tipo == TipoCasilla::SORPRESA)
          
          @cartaActual = @mazo[0]
          puts @cartaActual.texto
          @mazo.delete_at(0)
          
        end
        
      end
      
      
      tiene_propietario
    end
    
    def obtener_ranking
      ranking = []
      aux = @jugadores
      
      while(aux.size > 0)
        
        max = aux[0]
        
        for jug in aux
          
          if(jug.obtener_capital() > max.obtener_capital())
            
            max = jug
            
          end
          
          ranking << max
          aux.delete(max)
          
        end
        
      end
      
      ranking
      
    end
    
    def siguiente_jugador
      i = 0
      seguir = true
      
      while (i < @jugadores.size && seguir)
        
        if(@jugadores[i] == @jugadorActual)
          seguir = false
          @jugadorActual = @jugadores[ ( (i+1) % @jugadores.size ) ]
        end
        i = i+1
      end
    
    end
    
    def salida_jugadores
      for jug in @jugadores
        jug.saldo = 7500
        jug.casillaActual = @tableroActual.obtener_casilla_numero(0)
      end
      
      @jugadorActual = @jugadores[rand(@jugadores.size)]
    end
    
    def propiedades_hipotecadas_jugador hipotecada
=begin      devolver = []
      
      for jug in @jugadores
        if ( jug.propiedades.hipotecada == hipotecada )
          devolver << jug.propiedades.casilla
        end
      end
      
      devolver
=end
      @jugadorActual.propiedades_hipotecadas(hipotecada)
    end
    
    def get_jugadores
      @jugadores
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
      resultado += "\n\n      Tablero:\n         #{@tableroActual} \n JugadorActual: #{@jugadorActual} \n CartaSorpresaActual: #{@cartaActual}\n"
    end
    
    public :cartaActual, :jugadorActual
  end
  
end