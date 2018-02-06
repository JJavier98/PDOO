#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
  
	class Casilla
		attr_reader :tipo, :coste, :numeroCasilla 
    
		def initialize( numCas, cost = 0, tipo)
			if(tipo == TipoCasilla::IMPUESTO)
				@coste = 1000
			else
				@coste = cost
			end
			@numeroCasilla = numCas
			@tipo = tipo
		end
		
		def soy_edificable
			return @tipo==TipoCasilla::CALLE
		end
		
		def tengo_propietario
			false
		end
    
		def to_s
			"\n            *Número de Casilla: #{@numeroCasilla} \n            *Tipo: #{@tipo} \n"
		end

	end

end