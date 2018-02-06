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
public class Calle extends Casilla{
    // ATRIBUTOS
    public int numCasas;
    public int numHoteles;
    public TituloPropiedad titulo;
    
    public Calle(int numeroCasilla, int coste, TituloPropiedad titulo)
    {
        super(numeroCasilla, coste, TipoCasilla.CALLE);
        this.titulo = titulo;
    }
    
    public int getNumCasas()
    {
        return numCasas;
    }
    
    public int getNumHoteles()
    {
        return numHoteles;
    }
    
    public TituloPropiedad getTitulo()
    {
        return titulo;
    }
    
    public void setNumCasas(int numCasas)
    {
        this.numCasas = numCasas;
    }
    
    public void setNumHoteles(int numHoteles)
    {
        this.numHoteles = numHoteles;
    }
    
    private void setTituloPropiedad(TituloPropiedad titulo)
    {
        this.titulo = titulo;
    }
    
    public boolean estaHipotecada()
    {
        return titulo.getHipotecada();
    }
    
    public int cancelarHipoteca(){
        titulo.setHipotecada(false);
        return getCosteHipoteca();
    }
    
    public int getCosteHipoteca()
    {
        return (int)(calcularValorHipoteca()*1.1);
    }
    
    public int cobrarAlquiler(){
        int costeAlquiler = calcularValorHipoteca();
        
        titulo.cobrarAlquiler(costeAlquiler);
        
        return costeAlquiler;
    }
    
    public int edificarCasa(){
        setNumCasas(numCasas+1);
        return titulo.getPrecioEdificar();
    }
    
    public int edificarHotel(){
        setNumCasas(0);
        setNumHoteles(numHoteles+1);
        return titulo.getPrecioEdificar();
    }
    
    public int calcularValorHipoteca()
    {
        return titulo.getHipotecaBase() + (int)(numCasas*0.5*titulo.getHipotecaBase() + numHoteles*titulo.getHipotecaBase())*20;
    }
    
    public int hipotecar(){
        titulo.setHipotecada(true);
        int cantidadRecibida = calcularValorHipoteca();
        return cantidadRecibida;                
    }
    
    public boolean propietarioEncarcelado(){
        return titulo.propietarioEncarcelado();
    }
    
    public boolean sePuedeEdificarHotel(){
        int factor = titulo.getPropietario().getFactorEspeculador();
        int maximo = 4*factor;
                    
        return numCasas == maximo && numHoteles < maximo;
    }
    
    @Override
    public boolean tengoPropietario(){
        return titulo.tengoPropietario();
    }
    
    boolean sePuedeEdificarCasa()
    {
        int factor = titulo.getPropietario().getFactorEspeculador();
        int maximo = 4*factor;
        
        return numCasas < maximo && numHoteles < maximo;
    }
    
    public int venderTitulo(){
        int precioCompra = coste + (numCasas+numHoteles)*titulo.getPrecioEdificar();
        int precioVenta = (int) (precioCompra + precioCompra*titulo.getFactorRevalorizacion());
        
        titulo.setPropietario(null);
        setNumCasas(0);
        setNumHoteles(0);
        
        return precioVenta;
    }
    
    public TituloPropiedad asignarPropietario(Jugador jugador){
        titulo.setPropietario(jugador);
        return titulo;
    }
    
    ////////////////////////////////////////////////////////////////
    @Override
    public String toString()
    {
        return "\n            *Número de Casilla:" + numeroCasilla +
                       "\n            *Coste:" + coste + "\n            *Número de Casas:" + numCasas +
                       "\n            *Número de Hoteles:" + numHoteles + "\n            *TipoCasilla:" + tipo +
                       "\n            *Título: \n" + titulo + "}";
    }

}
