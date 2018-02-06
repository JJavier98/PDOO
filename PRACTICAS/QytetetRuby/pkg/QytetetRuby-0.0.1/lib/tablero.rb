#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


require_relative "casilla"
require_relative "qytetet"
module ModeloQytetet
  
  class Tablero
    attr_reader :carcel
    def initialize
     @casillas = []
     inicializar_casillas()
     @carcel = @casillas[5]
    end
    
    def inicializar_casillas
       # LADO - 1
            # CASILLA 1 - SALIDA
            @casillas << Casilla.new(0, TipoCasilla::SALIDA)

            # CASILLA 2 - CALLE
            titulo_1 = TituloPropiedad.new("Santuario Elemental", 10, 0.1, 150, 250)
            @casillas << Calle.new(1, 300, titulo_1)

            # CASILLA 3 - CALLE
            titulo_2 = TituloPropiedad.new("Mansión de los vientos", 30, 0.11, 250, 250)
            @casillas << Calle.new(2, 500, titulo_2)

            # CASILLA 4 - IMPUESTO
            @casillas << Casilla.new(3, TipoCasilla::IMPUESTO)

            # CASILLA 5 - CALLE
            titulo_3 = TituloPropiedad.new("Castillo de Lorule", 40, 0.11, 300, 250)
            @casillas << Calle.new(4, 600, titulo_3)

            
        # LADO - 2
            # CASILLA 6 - CÁRCEL
            @casillas << Casilla.new(5, TipoCasilla::CARCEL)

            # CASILLA 7 - CALLE
            titulo_4 = TituloPropiedad.new("Torre de los Dioses", 50, 0.12, 350, 500)
            @casillas << Calle.new(6, 700, titulo_4)

            # CASILLA 8 - SORPRESA
            @casillas << Casilla.new(7, TipoCasilla::SORPRESA)

            # CASILLA 9 - CALLE
            titulo_5 = TituloPropiedad.new("Montaña de la Muerte", 70, 0.13, 450, 500)
            @casillas << Calle.new(8, 900, titulo_5)
            
            # CASILLA 10 - CALLE
            titulo_6 = TituloPropiedad.new("Palacio de Vaati", 80, 0.13, 500, 500)
            @casillas << Calle.new(9, 1000, titulo_6)
        
        # LADO - 3
            # CASILLA 11 - PARKING
            @casillas << Casilla.new(10, TipoCasilla::PARKING)
            
            # CASILLA 12 - CALLE
            titulo_7 = TituloPropiedad.new("Región de los Zora", 90, 0.14, 550, 750)
            @casillas << Calle.new(11, 1100, titulo_7)
            
            # CASILLA 13 - SORPRESA
            @casillas << Casilla.new(12, TipoCasilla::SORPRESA)
            
            # CASILLA 14 - CALLE
            titulo_8 = TituloPropiedad.new("Reino del Crepúsculo", 100, 0.15, 600, 750)
            @casillas << Calle.new(13, 1200, titulo_8)
            
            # CASILLA 15 - CALLE
            titulo_9 = TituloPropiedad.new("Ciudad Reloj", 120, 0.16, 700, 750)
            @casillas << Calle.new(14, 1400, titulo_9)
            
        # LADO - 4
            # CASILLA 16 - JUEZ
            @casillas << Casilla.new(15, TipoCasilla::JUEZ)
            
            # CASILLA 17 - CALLE
            titulo_10 = TituloPropiedad.new("Bosque de Farone", 130, 0.18, 750, 1000)
            @casillas << Calle.new(16, 1500, titulo_10)
            
            # CASILLA 18 - CALLE
            titulo_11 = TituloPropiedad.new("Altárea", 140, 0.19, 800, 1000)
            @casillas << Calle.new(17, 1600, titulo_11)
            
            # CASILLA 19 - SORPRESA
            @casillas << Casilla.new(18, TipoCasilla::SORPRESA)
            
            # CASILLA 20 - CALLE
            titulo_12 = TituloPropiedad.new("Castillo de Hyrule", 250, 0.2, 1000, 1000)
            @casillas << Calle.new(19, 2000, titulo_12)
            
      for cas in @casillas
            if(cas.tipo == TipoCasilla::CALLE)
                cas.titulo.casilla = cas
            end
      end
    end
    
    def obtener_casilla_numero(n)
      @casillas[n]
    end
    
    def es_casilla_carcel(n)
      @carcel.numeroCasilla == n
    end
    
    def obtener_nueva_casilla cas, despl
      casilla_actual = cas.numeroCasilla;
      posicion_a_devolver = ( (casilla_actual + despl) % @casillas.size )
        
      @casillas[posicion_a_devolver]
    end
    
    def to_s
      resultado = "Casillas: "
      for c in @casillas
        resultado += c.to_s
      end
      resultado += "\n Carcel: #{@carcel}\n\n"
    end
    
    private :inicializar_casillas
    
  end

end