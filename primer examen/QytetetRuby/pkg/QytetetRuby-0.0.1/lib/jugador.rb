#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
  
  class Jugador
    attr_accessor :encarcelado, :casillaActual
    attr_writer :cartaLibertad
    def initialize n, ca
      @encarcelado = false
      @nombre = n
      @saldo = 7500
      @cartaLibertad
      @casillaActual = ca
      @propiedades = nil
    end
    
    def to_s
      resultado="\n      nombre: #{@nombre} \n      encarcelado: #{@encarcelado} \n      cartaLibertad: #{@cartaLibertad} \n      casillaActual: #{@casillaActual}           propiedades: #{@propiedades}\n"
    end
    
    public :encarcelado, :casillaActual
  end
  
end