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
public class Casilla {
    // ATRIBUTOS
    private final int numeroCasilla;
    private final int coste;
    private int numCasas;
    private int numHoteles;
    private final TipoCasilla tipo;
    private TituloPropiedad titulo;
    
//______________________________________________________________________________
    
    // CONSTRUCTORES
    // Casilla tipo = CALLE
    Casilla(int numeroCasilla, int coste, TituloPropiedad titulo)
    {
        this.numeroCasilla = numeroCasilla;
        this.coste = coste;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.tipo = TipoCasilla.CALLE;
        this.titulo = titulo;
    }
    
    // Casilla tipo DISTINTO de CALLE
    Casilla(int numeroCasilla, TipoCasilla tipo)
    {
        this.numeroCasilla = numeroCasilla;
        this.coste = 0;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.tipo = tipo;
        this.titulo = null;
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
    
    public int getNumCasas()
    {
        return numCasas;
    }
    
    public int getNumHoteles()
    {
        return numHoteles;
    }
    
    public TipoCasilla getTipo()
    {
        return tipo;
    }
    
    public TituloPropiedad getTitulo()
    {
        return titulo;
    }
    
//______________________________________________________________________________
    
    // MÉTODOS MODIFICADORES
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
    
    public boolean soyEdificable()
    {
        return tipo == TipoCasilla.CALLE;
    }
    
    public boolean estaHipotecada()
    {
        return titulo.getHipotecada();
    }
    
//______________________________________________________________________________
    
    // MÉTODOS SOBRESCRITOS
       @Override
       public String toString()
       {
           if(this.tipo == TipoCasilla.CALLE)
           {
               return "\n            *Número de Casilla:" + numeroCasilla +
                       "\n            *Coste:" + coste + "\n            *Número de Casas:" + numCasas +
                       "\n            *Número de Hoteles:" + numHoteles + "\n            *TipoCasilla:" + tipo +
                       "\n            *Título: \n" + titulo + "}";
           }
           else
           {
               return "\n            *Número de Casilla:" + numeroCasilla +
                       "\n            *TipoCasilla:" + tipo;
           }
       }
//______________________________________________________________________________
    
    public int cancelarHipoteca(){
        titulo.setHipotecada(false);
        return getCosteHipoteca();
    }
    
    public int getCosteHipoteca()
    {
        return (int)(calcularValorHipoteca()*1.1);
    }
    
    public int cobrarAlquiler(){
        int costeAlquilerBase = titulo.getAlquilerBase();
        int costeAlquiler = costeAlquilerBase + (int)(numCasas*0.5 + numHoteles*2);
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
    
    public int precioTotalComprar(){
        throw new UnsupportedOperationException("Sin implementar");
    }
    
    public boolean propietarioEncarcelado(){
        return titulo.propietarioEncarcelado();
    }
    
    public boolean sePuedeEdificarHotel(){
        return numCasas == 4 && numHoteles < 4;
    }
    
    public boolean tengoPropietario(){
        boolean tiene;
        if(titulo == null)
            tiene = false;
        else
            tiene = titulo.tengoPropietario();
        return tiene;
    }
    
    boolean sePuedeEdificarCasa()
    {
        return numCasas < 4 && numHoteles < 4;
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

    public Object getTituloPropiedad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}