#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
  
  class Jugador
    attr_accessor :encarcelado, :casillaActual, :saldo, :propiedades
    attr_writer :cartaLibertad
    def initialize n
      @encarcelado = false
      @nombre = n
      @saldo = 0
      @cartaLibertad = nil
      @casillaActual = nil
      @propiedades = []
    end
    
    def cuantas_casas_hoteles_tengo
      n = 0
      
      for prop in @propiedades
        aux = prop.casilla
        n = n + aux.numCasas + aux.numHoteles
      end
      
      n
    end
    
    def devolver_carta_libertad
      aux = @cartaLibertad
      @cartaLibertad = null
      
      aux
    end
    
    def actualizar_posicion casilla
      if(casilla.numeroCasilla < @casillaActual.numeroCasilla)
        modificar_saldo(1000)#Qytetet::SALDO_SALIDA
      end

      @casillaActual = casilla

      if(@casillaActual.soy_edificable())
        tengoPropietario = @casillaActual.tengo_propietario()

        if(tengoPropietario)
          encarcelado = @casillaActual.propietario_encarcelado()

          if(!encarcelado)
            costeAlquiler = @casillaActual.cobrar_alquiler()
          end

          modificar_saldo( -costeAlquiler)
        end
        
      elsif(@casillaActual.tipo == TipoCasilla::IMPUESTO)
        coste = @casillaActual.coste
        modificar_saldo(-coste)
      end
      tengoPropietario
    end
    
    def puedo_hipotecar casilla
      es_de_mi_propiedad(casilla)
    end
    
    def puedo_pagar_hipoteca casilla
      tengo_saldo = false
      
        hipotecadas = obtener_propiedades_hipotecadas(true)
        if(hipotecadas.index(casilla.titulo) >= 0 && hipotecadas.index(casilla.titulo) < hipotecadas.size)
        
            coste_hipoteca = casilla.get_coste_hipoteca()
            tengo_saldo = tengo_saldo(coste_hipoteca)
        end
        
        return tengo_saldo
    end
    
    def pagar_libertad cantidad
      tengo_saldo = tengo_saldo(cantidad)
      if(tengo_saldo)
        modificar_saldo(cantidad)
      end
      tengo_saldo
    end
    
    def pagar_cobrar_por_casa_hotel cantidad
      numero_total = cuantas_casas_hoteles_tengo()
      modificar_saldo(numero_total*cantidad)
    end
    
    def puedo_vender_propiedad casilla
      es_de_mi_propiedad(casilla) && !casilla.esta_hipotecada()
    end
    
    def puedo_edificar_casa casilla
      es_de_mi_propiedad(casilla) && tengo_saldo(casilla.titulo.precioEdificar)
    end
    
    def puedo_edificar_hotel casilla
      es_de_mi_propiedad(casilla) && tengo_saldo(casilla.titulo.precioEdificar)
    end
    
    def vender_propiedad casilla
      precio_venta = casilla.vender_titulo()
      modificar_saldo(precio_venta)
      eliminar_de_mis_propiedades(casilla)
    end
    
    def comprar_titulo
      puedo_comprar = false
      if( @casillaActual.soy_edificable() )
        tengo_propietario = @casillaActual.tengo_propietario()
        
        if (!tengo_propietario)
          coste_compra = @casillaActual.coste
          
          if(tengo_saldo(coste_compra))
            titulo = @casillaActual.asignar_propietario(self)
            @propiedades << titulo
            modificar_saldo(-coste_compra)
            puedo_comprar = true
          end
        end
      end
      puedo_comprar
    end
    
    def obtener_capital
      capital = @saldo
      
      for prop in @propiedades
        aux = prop.casilla
        
        capital = capital + aux.coste + ( (aux.numCasas + aux.numHoteles) * prop.precioEdificar )
        
        if (prop.hipotecada)
          capital = capital - prop.hipotecaBase
        end
      end
      
      capital
    end
    
    def tengo_carta_libertad
      @cartaLibertad != nil
    end
    
    def ir_a_carcel casilla
      @casillaActual = casilla
      @encarcelado = true
    end
    
    def es_de_mi_propiedad casilla
      i = 0
      seguir = true
      
      while (i < @propiedades.size && seguir)
        if (@propiedades[i].casilla == casilla)
          seguir = false
        end
      end
      
      !seguir
    end
    
    def puedo_vender_propiedad casilla
      es_de_mi_propiedad(casilla) && !casilla.esta_hipotecada
    end
    
    def eliminar_de_mis_propiedades casilla
      
      for prop in @propiedades
        if(prop.casilla == casilla)
          @propiedades.delete(prop)
        end
      end
      
    end
    
    def modificar_saldo cantidad
      @saldo += cantidad
    end
    
    def obtener_propiedades_hipotecadas hipotecadas
      titulos = []
      for prop in @propiedades
        if (prop.hipotecada == hipotecadas)
          titulos << prop
        end
      end
      titulos
    end
    
    def propiedades_hipotecadas(hipotecadas)
      casillas = Array.new()
      for p in @propiedades
        if p.hipotecada == hipotecadas
          casillas << p.casilla
        end
      end
      casillas
    end
    
    def tengo_propiedades
      @propiedades.size > 0
    end
    
    def tengo_saldo cantidad
      @saldo >= cantidad
    end
    
    def to_s
      "\n      nombre: #{@nombre} \n      saldo: #{@saldo} \n      encarcelado: #{@encarcelado} \n      cartaLibertad: #{@cartaLibertad} \n      casillaActual: #{@casillaActual}           propiedades: #{@propiedades}\n"
    end
    
    public :encarcelado, :casillaActual
  end
  
end