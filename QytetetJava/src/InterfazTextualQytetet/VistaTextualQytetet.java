/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazTextualQytetet;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import modeloqytetet.Casilla;

/**
 *
 * @author jjavier98
 */
public class VistaTextualQytetet {
    
private static final Scanner in = new Scanner (System.in);
    
public int menuGestionInmobiliaria(){ //ejemplo de menu
     
    this.mostrar("Elige la gestion inmobiliaria que deseas hacer");
    Map<Integer, String> menuGI = new TreeMap<>();
    menuGI.put(0, "Siguiente Jugador"); 
    menuGI.put(1, "Edificar casa");
    menuGI.put(2, "Edificar Hotel"); 
    menuGI.put(3, "Vender propiedad ");  	
    menuGI.put(4, "Hipotecar Propiedad"); 
    menuGI.put(5, "Cancelar Hipoteca");
    int salida=this.seleccionMenu(menuGI); // Metodo para controlar la eleccion correcta en el menu 
    return salida;
 }

    public int menuSalirCarcel(){
        int metodo_salir;
        Map<Integer, String> menuEP = new TreeMap<>();
        
        menuEP.put(0, "PagarLibertad");
        menuEP.put(1, "TirarDado");
        
        System.out.println("Estás encarcelado. Escoge un método para intentar salir de la cárcel\n");
        metodo_salir = this.seleccionMenu(menuEP);
        return metodo_salir;
    }
  
    public boolean elegirQuieroComprar(){
        Map<Integer, String> menuEP = new TreeMap<>();
        
        menuEP.put(0, "SI");
        menuEP.put(1, "NO");
        
        System.out.println("Quieres comprar la casilla en la que te encuentras?" );
        
        int salida = this.seleccionMenu(menuEP);
        return salida == 0;
    }
 
        
 public int menuElegirPropiedad(ArrayList<String> listaPropiedades){  //numero y nombre de propiedades            
    Map<Integer, String> menuEP = new TreeMap<>();
    int numeroOpcion=0;
    for(String prop: listaPropiedades) {
        menuEP.put(numeroOpcion, prop); //opcion de menu, numero y nombre de propiedad
        numeroOpcion=numeroOpcion+1;
    }
    int salida=this.seleccionMenu(menuEP); // Método para controlar la elección correcta en el menú 
    return salida;
 
   }   

private int seleccionMenu(Map<Integer,String> menu) 
//Método para controlar la elección correcta de una opción en el menú que recibe como argumento   
{   boolean valido = true; 
    int numero;
    String lectura;
    this.mostrar("\n Elige una opción: ");
    do { // Hasta que se hace una selección válida
      for(Map.Entry<Integer, String> fila : menu.entrySet()) {
            numero = fila.getKey();
            String texto = fila.getValue();
            this.mostrar(numero + " : " + texto);  // número de opción y texto
      }
      
      lectura = in.nextLine();  //lectura de teclado
      valido=this.comprobarOpcion(lectura, 0, menu.size()-1); //método para comprobar la elección correcta
    } while (!valido);
    return Integer.parseInt(lectura);
}

public ArrayList<String> obtenerNombreJugadores() { //metodo para pedir el nombre de los jugadores
    boolean valido; 
    String lectura;
    ArrayList<String> nombres = new ArrayList<>();
    do{ //repetir mientras que el usuario no escriba un numero correcto 
        this.mostrar("Escribe el numero de jugadores: (de 2 a 4):");
        lectura = in.nextLine();  //lectura de teclado
        valido=this.comprobarOpcion(lectura, 2, 4); //metodo para comprobar la elección correcta
    }while (!valido);
    
    for (int i = 1; i <= Integer.parseInt(lectura); i++) { //solicitud del nombre de cada jugador
      this.mostrar("Nombre del jugador " + i + ": ");
      nombres.add (in.nextLine());
    }
    return nombres;
  }

 private boolean comprobarOpcion(String lectura, int min, int max){ 
//método para comprobar que se introduce un entero correcto, usado por seleccion_menu
     boolean valido=true;   
     int opcion;
     try {  
          opcion =Integer.parseInt(lectura);
          if (opcion<min || opcion>max) { // No es un entero entre los válidos
               this.mostrar("el numero debe estar entre min y max");
                valido = false;}
        
      } catch (NumberFormatException e) { // No se ha introducido un entero
              this.mostrar("debes introducir un numero");
              valido = false;  
      }
      if (!valido) {
        this.mostrar("\n\n Seleccion erronea. Intentalo de nuevo.\n\n");
      }
      return valido;
   }

 public void mostrar(String texto){ //método que muestra en pantalla el string que recibe como argumento
         
    System.out.println(texto);
}

    void mostrar(ArrayList<Casilla> propiedades) {
        System.out.println(propiedades);
    }

    private void mostrar(int salida) {
        System.out.println(salida);
    }
 

}
 
// añadir el siguiente método a ControladorQytetet
//     public Casilla elegirPropiedad(ArrayList<Casilla> propiedades){ 
// //este metodo toma una lista de propiedades y genera una lista de strings, con el numero y nombre de las propiedades
// //luego llama a la vista para que el usuario pueda elegir.
//        vista.mostrar("\tCasilla\tTitulo");
//        int seleccion;
//        ArrayList<String> listaPropiedades= new ArrayList();
//        for ( Casilla casilla: propiedades) {
//                listaPropiedades.add( "\t"+casilla.getNumeroCasilla()+"\t"+casilla.getTituloPropiedad().getNombre()); 
//        }
//        seleccion=vista.menuElegirPropiedad(listaPropiedades);  
//        return propiedades.get(seleccion);
//         }
// 
//}
