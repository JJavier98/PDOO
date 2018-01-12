#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "casilla"
require_relative "dado"
require_relative "jugador"
require_relative "metodo_salir_carcel"
require_relative "qytetet"
require_relative "sorpresa"
require_relative "tablero"
require_relative "tipo_casilla"
require_relative "tipo_sorpresa"
require_relative "titulo_propiedad"
require_relative "vista_textual_qytetet"



module Interfaz_textual_qytetet
    
  class ControladorQytetet
    
    include ModeloQytetet
    
    def inicializacion_juego
    
      @juego = ModeloQytetet::Qytetet.instance
      @vista = VistaTextualQytetet.new
      @juego.inicializar_juego(@vista.obtener_nombre_jugadores())

      @jugador = @juego.jugadorActual
      @casilla = @jugador.casillaActual
      
      puts @juego
      @vista.mostrar( "\n\n\n**Para empezar a jugar pulsa INTRO** ")
      gets.chomp
    end
    
    def desarrollo_del_juego()
      
        fin = false
        
        while(!fin)

            @vista.mostrar("\n\n\n            ****** Es el turno de: ******           \n#{@jugador}")

            if(@jugador.encarcelado)
            
                metodo_salir = @vista.menu_salir_carcel()

                if(metodo_salir == 1)
                
                    m = MetodoSalirCarcel::PAGANDOLIBERTAD
                
                else
                
                    m = MetodoSalirCarcel::TIRANDODADO
                end

                libre = @juego.intentar_salir_carcel(m)

                if(libre)
                    @vista.mostrar("Has quedado libre de la carcel.\n")
                else
                    @vista.mostrar("Sigues encarcelado.\n")
                end
            end

            if(!@jugador.encarcelado)
            
                tiene_propietario = @juego.jugar();
                @casilla = @jugador.casillaActual;

                if(@jugador.saldo > 0 && !@jugador.encarcelado) # NO ESTÁ EN BANCARROTA NI ESTÁ ENCARCELADO
                

                    if(@casilla.tipo == TipoCasilla::CALLE)
                                            
                        if(!tiene_propietario)
                        
                            @vista.mostrar("            ****** Estás en: ******             \n #{@casilla}" )
                            comprar = @vista.elegir_quiero_comprar()
                            if(comprar)
                                if(@jugador.comprar_titulo())
                                    @vista.mostrar("\n\nHas comprado la casilla.\n")
                                else
                                    @vista.mostrar("\n\nNo puedes comprar la casilla.\n")
                                end
                            else
                                @vista.mostrar("\n\nNo has comprado la casilla.\n")
                            end

                        end
                    
                    elsif(@casilla.tipo == TipoCasilla::SORPRESA)
                    
                        tiene_propietario = @juego.aplicar_sorpresa()
                        casilla = @jugador.casillaActual

                        if(!@jugador.encarcelado && @jugador.saldo > 0 && @casilla.tipo == TipoCasilla::CALLE && !tiene_propietario)
                        
                            @vista.mostrar("            ****** Estás en: ******             \n" + casilla )
                            if(@vista.elegir_quiero_comprar())
                              
                                if(@jugador.comprar_titulo())
                                    @vista.mostrar("\n\nHas comprado la casilla.\n")
                                else
                                    @vista.mostrar("\n\nNo puedes comprar la casilla.\n")
                                end
                                
                            else
                                @vista.mostrar("\n\nNo has comprado la casilla.\n")
                            end
                        end
                    end
                    
                    if(!@jugador.encarcelado && @jugador.saldo > 0 && @jugador.tengo_propiedades())
                    
                        opcion = @vista.menu_gestion_inmobiliaria();

                        while(opcion != 0 && !@jugador.encarcelado && @jugador.saldo > 0 && @jugador.tengo_propiedades())
                        
                            #if(opcion != 5 && !@juego.propiedades_hipotecadas_jugador(false).empty?)
                            #  propiedades_prueba = @juego.propiedades_hipotecadas_jugador(false)
                            #elsif(!@juego.propiedades_hipotecadas_jugador(true).empty?)
                            #  propiedades_prueba = @juego.propiedades_hipotecadas_jugador(true)
                            #end
                            casillas = []
                            puts "+++++++++++"
                            puts @jugador.propiedades[0].nombre
                            for prop in @jugador.propiedades
                              casillas << prop.casilla
                            end
                            
                            casilla_elegida = elegir_propiedad(casillas)

                            case opcion
                            
                                when 1
                                  if(@juego.edificar_casa(casilla_elegida))
                                      @vista.mostrar("\n--> Número de casas en " + casilla_elegida.titulo.nombre + " es:  #{casilla_elegida.numCasas}" + "\n")
                                  else
                                      @vista.mostrar("\n--> No puedes construir casa"+ "\n")
                                  end
                                      
                                when 2
                                  if(@juego.edificar_hotel(casilla_elegida))
                                      @vista.mostrar("\n--> Número de hoteles en " + casilla_elegida.titulo.nombre + " es:  #{casilla_elegida.numHoteles}" + "\n")
                                  else
                                      @vista.mostrar("\n--> No puedes construir hotel"+ "\n")
                                  end
                                  
                                when 3
                                  if(@juego.vender_propiedad(casilla_elegida))
                                      @vista.mostrar("\n--> Has vendido la propiedad \n")
                                  else
                                      @vista.mostrar("\n--> No puedes vender tu propiedad\n")
                                  end
                                  
                                when 4
                                  if(@juego.hipotecar_propiedad(casilla_elegida))
                                      @vista.mostrar("\n--> Has hipotecado la propiedad \n")
                                  else
                                      @vista.mostrar("\n--> No puedes hipotecar tu propiedad\n")
                                  end
                                  
                                when 5
                                  if(@juego.cancelar_hipoteca(casilla_elegida))
                                      @vista.mostrar("\n--> Has cancelado la hipoteca de la propiedad \n")
                                  else
                                      @vista.mostrar("\n--> No puedes cancelar la hipoteca de tu propiedad\n")
                                  end
                            end
                            
                            if(@jugador.propiedades.size > 0)
                                opcion = @vista.menu_gestion_inmobiliaria();
                            end
                        end
                    end
                end
            end

            @vista.mostrar("\n\n\n            ****** El estado final del jugador es: ******           \n#{@jugador}")

            fin = @jugador.saldo <= 0

            if(!fin)
                @juego.siguiente_jugador()
                @jugador = @juego.jugadorActual
                @casilla = @jugador.casillaActual
                fin = @jugador.saldo <= 0
            end

            if(fin)
                @vista.mostrar("ranking" + @juego.obtener_ranking())
            end
        end
    end
    
=begin    //metodo que muestra en pantalla el string que recibe como argumento
    public void mostrar(String texto){
         
        @vista.mostrar(texto);
=end    }
    
    def elegir_propiedad propiedades 
        @vista.mostrar("\tCasilla\tTitulo")
        
        lista_propiedades= Array.new
        for prop in propiedades  # crea una lista de strings con numeros y nombres de propiedades
                prop_string= prop.numeroCasilla.to_s+' '+prop.titulo.nombre
                lista_propiedades<<prop_string
        end
        seleccion=@vista.menu_elegir_propiedad(lista_propiedades)  # elige de esa lista del menu
        propiedades.at(seleccion)
    end

    
    def self.main
      controlador = ControladorQytetet.new
      controlador.inicializacion_juego()
      controlador.desarrollo_del_juego()
    end
    

  end
  ControladorQytetet.main
end
