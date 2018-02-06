/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author jjavier98
 */
public abstract class Casilla {
    // ATRIBUTOS
    protected final int numeroCasilla;
    protected final int coste;
    protected final TipoCasilla tipo;
    
//______________________________________________________________________________
    
    // CONSTRUCTORES
    Casilla (int numeroCasilla, int coste, TipoCasilla tipo)
    {
        if(tipo == TipoCasilla.IMPUESTO)
            this.coste = 1000;
        else
            this.coste = coste;
        
        this.numeroCasilla = numeroCasilla;
        this.tipo = tipo;
    }
    
//______________________________________________________________________________
    
    // MÉTODOS CONSULTORES
    public int getNumeroCasilla()
    {
        return numeroCasilla;
    }
    
    public int getCoste()
    {
        return coste;
    }
    
    public TipoCasilla getTipo()
    {
        return tipo;
    }
    
    public boolean soyEdificable()
    {
        return tipo == TipoCasilla.CALLE;
    }
    
//______________________________________________________________________________
    
    protected abstract boolean tengoPropietario();
}