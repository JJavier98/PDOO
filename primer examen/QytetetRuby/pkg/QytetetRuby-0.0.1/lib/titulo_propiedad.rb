#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
  
  class TituloPropiedad
    attr_accessor :hipotecada
    attr_reader :nombre, :alquilerBase, :factorRevalorizacion,
      :hipotecaBase, :precioEdificar

    attr_writer :casilla, :propietario

    def initialize( nom, alq, fac_r, hipt_b, prec_ed)
      @nombre = nom
      @hipotecada = false
      @alquilerBase = alq
      @factorRevalorizacion = fac_r
      @hipotecaBase = hipt_b
      @precioEdificar = prec_ed
      @casilla = nil
      @propietario = nil
    end

    def to_s
        "   Nombre: #{@nombre} \n    Hipotecada: #{@hipotecada} \n    Alquiler Base: #{@alquilerBase} \n    Factor de Revalorización: #{@factorRevalorizacion} \n    Hipoteca Base: #{@hipotecaBase} \n    Precio para Edificar: #{@precioEdificar}\n\n"
    end

  end
  
end