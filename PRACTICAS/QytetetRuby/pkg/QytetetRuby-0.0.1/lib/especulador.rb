#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "jugador"
module ModeloQytetet

	class Especulador < Jugador
  
		def initialize jugador, f
			super(jugador.nombre, jugador.encarcelado, jugador.saldo, jugador.cartaLibertad, jugador.casillaActual, jugador.propiedades)
			@fianza = f
		end
  
		def get_factor_especulador
			return 2
		end
	
		def convertirme fianza
			self
		end
	
		def pagar_impuestos cantidad
			puts "PAGAS #{cantidad/2}************************************************************"
			modificar_saldo(-cantidad/2);
		end
	
		def pagar_fianza f
			if(tengo_saldo(f))
				modificar_saldo(-f)
				return true
			else
				return false
			end
		end
	
		def ir_a_carcel casilla
			begin
				puts "Quieres pagar una fianza para evitar ir a la carcel? <0, SI> <1, NO>\n"
				eleccion = gets.chomp
				numero = eleccion.to_i
			end while(numero!=0 && numero!=1)
		
			if (numero == 0 && pagar_fianza(@fianza))
				puts "Has pagado para no entrar en la carcel. Seguirás en la misma casilla\n"
			else
				@casillaActual = casilla
				@encarcelado = true
			end
		end
	
		def to_s
			s = "\n      Jugador tipo ESPECULADOR.\n      factorEspeculador: #{get_factor_especulador()}\n      Fianza: #{@fianza} \n      nombre: #{@nombre} \n      saldo: #{@saldo} \n      encarcelado: #{@encarcelado} \n      cartaLibertad: #{@cartaLibertad} \n      casillaActual: #{@casillaActual}      propiedades: \n "
			for prop in @propiedades
				s += prop.to_s
				s += "\n"
			end
			s
		end
    
	end

end