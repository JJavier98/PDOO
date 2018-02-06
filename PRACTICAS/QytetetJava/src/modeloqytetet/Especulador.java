/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.Scanner;

/**
 *
 * @author jotajota98
 */
public class Especulador extends Jugador {
    static int FactorEspeculador = 2;
    private int fianza;
    
    protected Especulador(Jugador jug, int f)
    {
        super(jug);
        fianza = f;
    }
    
    public int getFianza()
    {
        return fianza;
    }
    
    private boolean pagarFianza(int cantidad)
    {
        if ( tengoSaldo(cantidad) )
        {
            modificarSaldo(-cantidad);
            return true;
        }
        else
            return false;
    }
    
    @Override
    protected void pagarImpuestos(int cantidad)
    {
        modificarSaldo(-cantidad/2);
    }
    
    @Override
    protected void irACarcel(Casilla casilla)
    {
        Scanner scan = new Scanner(System.in);
        int opcion;
        
        do{
            System.out.println("Quieres pagar una fianza para evitar ir a la carcel? <0, SI> <1, NO>\n");
            opcion = scan.nextInt();
        }while (opcion != 0 && opcion != 1);
        
        if (opcion == 0 && pagarFianza(fianza))
        {
            System.out.println("Has pagado para no entrar en la carcel. Seguirás en la misma casilla\n");
        }
        else
        {
            setCasillaActual(casilla);
            setEncarcelado(true);
        }
    }
    
    @Override
    protected Especulador convertirme(int fianza)
    {
        return this;
    }
    
    @Override
    public String toString() {
        String resultado = "\n      Jugador tipo ESPECULADOR.\n      factorEspeculador:" + FactorEspeculador + "\n      Fianza: " + fianza + "\n";
        resultado += super.toString();
        return resultado;
    }
}
