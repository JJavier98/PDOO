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
public class Dado {
    
    private static Dado instancia=new Dado();
    
    public static Dado getInstance(){
        return instancia;
    }
    
    //constructor privado para que no se pueda instanciar de otras clases
    private Dado(){
        //constructor vacío
    }
    
    int tirar(){
        int numero = (int) (Math.random() * 6) + 1;
        return numero;
    }
}
