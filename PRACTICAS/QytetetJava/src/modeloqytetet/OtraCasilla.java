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
public class OtraCasilla extends Casilla {
    
    OtraCasilla(int numeroCasilla, TipoCasilla tipo)
    {
        super(numeroCasilla, 0, tipo);
    }
    
    @Override
    public String toString()
    {
        return "\n            *Número de Casilla:" + numeroCasilla + "\n            *TipoCasilla:" + tipo;
    }
    
    @Override
    public boolean tengoPropietario(){
        return false;
    }
}
