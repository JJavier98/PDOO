/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;

/**
 *
 * @author jotajota98
 */
public class PruebaQytetet {
    private static ArrayList<Sorpresa> mazo = new ArrayList<>();
    private static Tablero tablero = new Tablero();
    
    private static void inicializarSorpresas() {
        mazo.add(new Sorpresa("Te hemos pillado con chanclas y calcetines. Lo sentimos, ¡debes ir a la carcel!",TipoSorpresa.IRACASILLA, tablero.getCarcel().getNumeroCasilla()));
        mazo.add(new Sorpresa ("¡Un OVNI te ha abducido y te ha llevado hasta la casilla de SALIDA! Cobra tu recompensa por sobrevivir.", TipoSorpresa.IRACASILLA, 0));
        mazo.add(new Sorpresa ("Un fan anónimo ha pagado tu fianza. Sales de la cárcel", TipoSorpresa.SALIRCARCEL, 0));
        mazo.add(new Sorpresa ("Gracias a tus poderosos contactos quedas libre de la cárcel", TipoSorpresa.SALIRCARCEL, 0));
        mazo.add(new Sorpresa ("¡Levantas una piedra y encuentras dinero!", TipoSorpresa.PAGARCOBRAR, 300));
        mazo.add(new Sorpresa("Por no recoger los excrementos de tu perro pagas a cada jugador por las molestias", TipoSorpresa.PAGARCOBRAR, 100));
        mazo.add(new Sorpresa("Eres un químico muy patoso y provocas una explosión que afecta a todo el vecindario. Pagas por cada casa/hotel que haya en tu misma arista del tablero", TipoSorpresa.PORCASAHOTEL, 100));
        mazo.add(new Sorpresa("Recibes el pago mensual por alquiler de casas y reservas de hotel. Recibes:", TipoSorpresa.PORCASAHOTEL, 150));
        mazo.add(new Sorpresa("Haces una fiesta privada y asisten todos los jugadores. El precio de la entrada es:", TipoSorpresa.PORJUGADOR, 150));
        mazo.add(new Sorpresa("¡Se te va de las manos! Por escándalo público pagas: ", TipoSorpresa.PORJUGADOR, 150));
    }
    
    private static ArrayList sorpresasConValor(){
        
        ArrayList<Sorpresa> sorpresa_con_valor = new ArrayList<>();
        for (Sorpresa sorpresa : mazo) {
            if (sorpresa.getValor() > 0) {
                sorpresa_con_valor.add(sorpresa);                
            }            
        }
        return sorpresa_con_valor;
    }
    
    private static ArrayList sorpresasIrACasilla(){
        
        ArrayList<Sorpresa> sorpresa_ir_a_casilla = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            if (mazo.get(i).getTipo() == TipoSorpresa.IRACASILLA) {
                sorpresa_ir_a_casilla.add(mazo.get(i));                
            }            
        }
        return sorpresa_ir_a_casilla;
    }
    
    private static ArrayList sorpresasTipoX(Sorpresa ref){
        ArrayList<Sorpresa> sorpresa_tipo_x = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            if (mazo.get(i).getTipo() == ref.getTipo()) {
                sorpresa_tipo_x.add(mazo.get(i));                
            }            
        }
        return sorpresa_tipo_x;
    }

    /**
     * @param args the command line arguments
     */
   /* public static void main(String[] args) {
        
        /*inicializarSorpresas();
        for (Sorpresa sorpresa : mazo) {
            System.out.println(sorpresa.toString());
        }
        
        ArrayList<Sorpresa> sorpresa_valor = sorpresasConValor();
        for (Sorpresa mayor_0 : sorpresa_valor) {
            System.out.println(mayor_0.toString());
        }
        
        ArrayList<Sorpresa> sorpresa_casilla = sorpresasIrACasilla();
        for(Sorpresa ir_casilla : sorpresa_casilla){
            System.out.println(ir_casilla.toString());
        }
        
        ArrayList<Sorpresa> sorpresa_tipo_x = sorpresasTipoX(mazo.get(2));
        for(Sorpresa tipo_x : sorpresa_tipo_x){
            System.out.println(tipo_x.toString());
        }
        
        Qytetet hola = Qytetet.getInstance();
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Jota");
        nombres.add("Angy");
        nombres.add("Toni");
        nombres.add("Laura");
        hola.inicializarJuego(nombres);
        System.out.println(hola);
    }*/
}