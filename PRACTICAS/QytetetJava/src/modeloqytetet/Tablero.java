/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;

/**
 *
 * @author jjavier98
 */
public class Tablero {
    // ATRIBUTOS
    private ArrayList<Casilla> casillas = new ArrayList<>();
    private Casilla carcel;

//______________________________________________________________________________
    
    // CONSTRUCTOR
    Tablero()
    {
        inicializarCasillas();
    }
    
    // INICIALIZADOR
    private void inicializarCasillas()
    {
        // LADO - 1
            // CASILLA 1 - SALIDA
            casillas.add(new OtraCasilla(0, TipoCasilla.SALIDA));

            // CASILLA 2 - CALLE
            TituloPropiedad titulo_1 = new TituloPropiedad("Santuario Elemental", 10, (float) 0.1, 150, 250);
            casillas.add(new Calle(1, 300, titulo_1));

            // CASILLA 3 - CALLE
            TituloPropiedad titulo_2 = new TituloPropiedad("Mansión de los vientos", 30, (float) 0.11, 250, 250);
            casillas.add(new Calle(2, 500, titulo_2));

            // CASILLA 4 - IMPUESTO
            casillas.add(new OtraCasilla(3, TipoCasilla.IMPUESTO)); 

            // CASILLA 5 - CALLE
            TituloPropiedad titulo_3 = new TituloPropiedad("Castillo de Lorule", 40, (float) 0.11, 300, 250);
            casillas.add(new Calle(4, 600, titulo_3)); 

            
        // LADO - 2
            // CASILLA 6 - CÁRCEL
            casillas.add(new OtraCasilla(5, TipoCasilla.CARCEL));
            carcel = casillas.get(5);

            // CASILLA 7 - CALLE
            TituloPropiedad titulo_4 = new TituloPropiedad("Torre de los Dioses", 50, (float) 0.12, 350, 500);
            casillas.add(new Calle(6, 700, titulo_4));

            // CASILLA 8 - SORPRESA
            casillas.add(new OtraCasilla(7, TipoCasilla.SORPRESA));

            // CASILLA 9 - CALLE
            TituloPropiedad titulo_5 = new TituloPropiedad("Montaña de la Muerte", 70, (float) 0.13, 450, 500);
            casillas.add(new Calle(8, 900, titulo_5));
            
            // CASILLA 10 - CALLE
            TituloPropiedad titulo_6 = new TituloPropiedad("Palacio de Vaati", 80, (float) 0.13, 500, 500);
            casillas.add(new Calle(9, 1000, titulo_6));
        
        // LADO - 3
            // CASILLA 11 - PARKING
            casillas.add(new OtraCasilla(10, TipoCasilla.PARKING));
            
            // CASILLA 12 - CALLE
            TituloPropiedad titulo_7 = new TituloPropiedad("Región de los Zora", 90, (float) 0.14, 550, 750);
            casillas.add(new Calle(11, 1100, titulo_7));
            
            // CASILLA 13 - SORPRESA
            casillas.add(new OtraCasilla(12, TipoCasilla.SORPRESA));
            
            // CASILLA 14 - CALLE
            TituloPropiedad titulo_8 = new TituloPropiedad("Reino del Crepúsculo", 100, (float) 0.15, 600, 750);
            casillas.add(new Calle(13, 1200, titulo_8));
            
            // CASILLA 15 - CALLE
            TituloPropiedad titulo_9 = new TituloPropiedad("Ciudad Reloj", 120, (float) 0.16, 700, 750);
            casillas.add(new Calle(14, 1400, titulo_9));
            
        // LADO - 4
            // CASILLA 16 - JUEZ
            casillas.add(new OtraCasilla(15, TipoCasilla.JUEZ));
            
            // CASILLA 17 - CALLE
            TituloPropiedad titulo_10 = new TituloPropiedad("Bosque de Farone", 130, (float) 0.18, 750, 1000);
            casillas.add(new Calle(16, 1500, titulo_10));
            
            // CASILLA 18 - CALLE
            TituloPropiedad titulo_11 = new TituloPropiedad("Altárea", 140, (float) 0.19, 800, 1000);
            casillas.add(new Calle(17, 1600, titulo_11));
            
            // CASILLA 19 - SORPRESA
            casillas.add(new OtraCasilla(18, TipoCasilla.SORPRESA));
            
            // CASILLA 20 - CALLE
            TituloPropiedad titulo_12 = new TituloPropiedad("Castillo de Hyrule", 250, (float) 0.2, 1000, 1000);
            casillas.add(new Calle(19, 2000, titulo_12));
            
        for (Casilla cas : casillas) {
            if(cas.getTipo() == TipoCasilla.CALLE)
                ((Calle)cas).getTitulo().setCasilla(((Calle)cas));
        }
    }
    
    // OBTENER CASILLAS
    Casilla obtenerCasillaNumero(int numeroCasilla)
    {
        if(numeroCasilla >= 0 && numeroCasilla < casillas.size())
            return casillas.get(numeroCasilla);
        else
            return null;
    }
    
    Casilla obtenerNuevaCasilla(Casilla casilla, int desplazamiento)
    {
        int casillaActual = casilla.getNumeroCasilla();
        int posicionADevolver = (casillaActual + desplazamiento) % casillas.size();
        
        return casillas.get(posicionADevolver);
    }
    
    // CONSULTOR
    Casilla getCarcel()
    {
        return carcel;
    }
    
    boolean esCasillaCarcel(int numeroCasilla)
    {
        return numeroCasilla == getCarcel().getNumeroCasilla();
    }
    
    // MÉTODOS SOBRESCRITOS
    @Override
    public String toString()
    {
        String mensaje = "";
        for (Casilla cas : casillas) {
            mensaje += cas.toString() + "\n\n               ---------------------------\n\n";
        }
        mensaje += "}";
        return mensaje;
    }
}
