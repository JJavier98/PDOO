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
public class Jugador {
    private boolean encarcelado;
    private String nombre;
    private int saldo;
    private Sorpresa cartaLibertad;
    private Casilla casillaActual;
    private ArrayList<TituloPropiedad> propiedades;
    
    Jugador(String n){
        encarcelado = false;
        nombre = n;
        saldo = 0;
        cartaLibertad = null;
        casillaActual = null;
        propiedades = new ArrayList<>();        
    }

    @Override
    public String toString() {
        return "\n      nombre:" + nombre + "\n      saldo:" + saldo + "\n      encarcelado:" + encarcelado + "\n      cartaLibertad:" + cartaLibertad + "\n      casillaActual:" + casillaActual + "\n      propiedades:" + propiedades + "\n";
    }
    

    public boolean isEncarcelado() {
        return encarcelado;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSaldo() {
        return saldo;
    }

    public Sorpresa getCartaLibertad() {
        return cartaLibertad;
    }

    public Casilla getCasillaActual() {
        return casillaActual;
    }

    public ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }

    public void setEncarcelado(boolean encarcelado) {
        this.encarcelado = encarcelado;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void setCartaLibertad(Sorpresa cartaLibertad) {
        this.cartaLibertad = cartaLibertad;
    }

    public void setCasillaActual(Casilla casillaActual) {
        this.casillaActual = casillaActual;
    }
    
//______________________________________________________________________________
    
    // MÉTODOS SIN IMPLEMENTAR
    public boolean tengoPropiedades (){
        return propiedades.size() > 0;
    }
    
    public boolean actualizarPosicion (Casilla casilla){
        if(casilla.getNumeroCasilla() < casillaActual.getNumeroCasilla())
            modificarSaldo(Qytetet.SALDO_SALIDA);
        
        
        setCasillaActual(casilla);
        
        if(casillaActual.soyEdificable())
        {
            boolean tengoPropietario = casillaActual.tengoPropietario();
            
            if (tengoPropietario)
            {
                boolean encarcelado = casillaActual.propietarioEncarcelado();
                
                if(!encarcelado)
                {
                    int costeAlquiler = casillaActual.cobrarAlquiler();
                    
                    modificarSaldo(-costeAlquiler);
                }
            }
        }
        else    
            if(casilla.getTipo() == TipoCasilla.IMPUESTO) /* NO IRIA FUERA DE "SOY EDIFICABLE" ¿?¿? */ /* VA EN UN ELSE¿?¿?¿?*/
            {
                int coste = casilla.getCoste();
                modificarSaldo(-coste);
            }
        return casillaActual.tengoPropietario();
    }
    
    public boolean comprarTitulo()
    {
        boolean puedoComprar = false;
        if(casillaActual.soyEdificable())
        {
            boolean tengoPropietario = casillaActual.tengoPropietario();
            
            if(!tengoPropietario)
            {
                int costeCompra = casillaActual.getCoste();
                
                if(tengoSaldo(costeCompra))
                {
                    TituloPropiedad titulo = casillaActual.asignarPropietario(this);
                    propiedades.add(titulo);
                    modificarSaldo(-costeCompra);
                    
                    puedoComprar = true;
                }
            }
        }
        
        return puedoComprar;
    }
    
    public boolean puedoPagarHipoteca(Casilla casilla)
    {
        boolean tengoSaldo = false;
        
        ArrayList<TituloPropiedad> hipotecadas = obtenerPropiedadesHipotecadas(true);
        if(hipotecadas.contains(casilla.getTitulo()))
        {
            int costeHipoteca = casilla.getCosteHipoteca();
            tengoSaldo = tengoSaldo(costeHipoteca);
        }
        
        return tengoSaldo;
    }
        
    public Sorpresa devolverCartaLibertad (){
        Sorpresa carta_a_devolver = cartaLibertad;
        cartaLibertad = null;
        return carta_a_devolver;
    }
    
    public void irACarcel (Casilla casilla){
        setCasillaActual(casilla);
        setEncarcelado(true);
    }
    
    public void modificarSaldo (int cantidad){
        saldo += cantidad;
    }
    
    public int obtenerCapital (){
        int devolver = saldo;
        
        for (TituloPropiedad propiedade : propiedades) {
            Casilla aux = propiedade.getCasilla();
            devolver = devolver + aux.getCoste();
            
            int valorEdificios = ( aux.getNumCasas() + aux.getNumHoteles() ) * propiedade.getPrecioEdificar();
            
            devolver = devolver + valorEdificios;
            
            if(propiedade.getHipotecada())
                devolver = devolver - propiedade.getHipotecaBase();
        }
        
        return devolver;
    }
    
    public ArrayList<TituloPropiedad> obtenerPropiedadesHipotecadas (boolean hipotecadas){
        ArrayList<TituloPropiedad> propiedadesADevolver = new ArrayList<>();
        
        for (TituloPropiedad prop : propiedades) {
            if(prop.getHipotecada() == hipotecadas)
                propiedadesADevolver.add(prop);
        }
        
        return propiedadesADevolver;
    }
    
    public void pagarCobrarPorCasaYHotel (int cantidad){
        int numeroTotal = cuantasCasasHotelesTengo();
        modificarSaldo(cantidad*numeroTotal);
    }
    
    public boolean pagarLibertad (int cantidad){
        boolean tengoSaldo = tengoSaldo(cantidad);
        if(tengoSaldo)
        {
            modificarSaldo(cantidad);
        }
        return tengoSaldo;
    }
    
    public boolean puedoEdificarCasa (Casilla casilla){
        return esDeMiPropiedad(casilla) && tengoSaldo(casilla.getTitulo().getPrecioEdificar());
    }
    
    public boolean puedoEdificarHotel (Casilla casilla){
        return esDeMiPropiedad(casilla) && tengoSaldo(casilla.getTitulo().getPrecioEdificar());
    }
    
    public boolean puedoHipotecar (Casilla casilla){
        return esDeMiPropiedad(casilla);
    }
    
    public boolean puedoVenderPropiedad (Casilla casilla){
        return esDeMiPropiedad(casilla) && !casilla.estaHipotecada();
    }
    
    public boolean tengoCartaLibertad(){
        return cartaLibertad != null;
    }
    
    public void venderPropiedad (Casilla casilla){
        int precioVenta = casilla.venderTitulo();
        modificarSaldo(precioVenta);
        eliminarDeMisPropiedades(casilla);
    }
    
    public int cuantasCasasHotelesTengo (){
        int edificios = 0;
        for (TituloPropiedad propied : propiedades) {
            edificios = edificios + propied.getCasilla().getNumCasas() + propied.getCasilla().getNumHoteles();
        }
        return edificios;
    }
    
    public void eliminarDeMisPropiedades (Casilla casilla){
        boolean seguir = true;
        
        for (int i = 0; i < propiedades.size() && seguir; i++) 
        {
            if(propiedades.get(i).getCasilla() == casilla)
            {
                seguir = false;
                propiedades.remove(i);
            }
        }
    }
    
    public boolean esDeMiPropiedad (Casilla casilla){
        boolean seguir = true,
                laTengo = false;
        for (int i = 0; i < propiedades.size() && seguir; i++) 
        {
            if(propiedades.get(i).getCasilla() == casilla)
            {
                seguir = false;
                laTengo = true;
            }
        }
        
        return laTengo;
    }
    
    public boolean tengoSaldo (int cantidad){
        return saldo >= cantidad;
    }
}
