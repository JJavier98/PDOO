#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#EXAMEN-inicio

require_relative "tablero"
require_relative "tipo_casilla"
require_relative "casilla"

module Obras
    class Constructora
      include ModeloQytetet
      attr_accessor :direccion
      attr_reader :nombre, :CIF
      @@CIF = "Q2467896F"
      def initialize tab, nom, dir
        @tablero = tab
        @nombre = nom
        @direccion = dir
      end
      
      def self.constructor2 tab,nom
        salir = false
        i = 0
        
        while !salir do
          casilla = tab.obtener_casilla_numero(i)
          
          if (casilla.titulo) then
            titl = casilla.titulo
            salir = true
          end
          i = i+1
        end
        dir = titl.nombre
        
        self.new(tab, nom, dir)
      end
      
      def self.set_organo_colegiado nuevo_cif
        @@CIF = nuevo_cif
      end
      
      def edificar
        for cas in @tablero.casillas
          if(cas.tipo == TipoCasilla::CALLE && cas.numCasas < 4 && cas.numHoteles == 0)
            cas.numCasas = cas.numCasas+1;
          end
        end
      end
      
      def to_s
        " Constructora del tablero: #{@tablero}\n CIF: #{@@CIF}\n Nombre: #{@nombre}\n Dirección: #{@direccion}\n"
      end
      
    end
end
#EXAMEN-fin