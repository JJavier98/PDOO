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
public class TituloPropiedad {
    // ATRIBUTOS
    private String nombre; // nombre de la calle
    private boolean hipotecada;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    private Jugador propietario;
    private Calle casilla;
    
//______________________________________________________________________________
    
    // CONSTRUCTOR
    TituloPropiedad(String nombre, int alquilerBase, float factorRevalorizacion,
            int hipotecaBase, int precioEdificar)
    {
        this.nombre = nombre;
        this.hipotecada = false;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
        this.propietario = null;
    }
    
//______________________________________________________________________________
    
    // MÉTODOS CONSULTORES
    public String getNombre()
    {
        return nombre;
    }
    
    public boolean getHipotecada()
    {
        return hipotecada;
    }
    
    public int getAlquilerBase()
    {
        return alquilerBase;
    }
    
    public float getFactorRevalorizacion()
    {
        return factorRevalorizacion;
    }
    
    public int getHipotecaBase()
    {
        return hipotecaBase;
    }
    
    public int getPrecioEdificar()
    {
        return precioEdificar;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public Calle getCasilla() {
        return casilla;
    }
    
    public void setCasilla(Calle cas)
    {
        casilla = cas;
    }
    
    public boolean tengoPropietario(){
        return propietario != null;
    }
    
    public boolean propietarioEncarcelado()
    {
        return propietario.isEncarcelado();
    }
    
    public void cobrarAlquiler(int costeAlquiler){
        propietario.modificarSaldo(costeAlquiler);
    }
    
    void setPropietario(Jugador jugador)
    {
        propietario = jugador;
    }
        
//______________________________________________________________________________
    
    // MÉTODOS MODIFICADORES
    void setHipotecada(boolean hipotecada)
    {
        this.hipotecada = hipotecada;
    }
    
//______________________________________________________________________________
    
    // MÉTODOS SOBRESCRITOS
    @Override
    public String toString()
    {
        return "\n               -Nombre:" + nombre + "\n               -Hipotecada:" +
                hipotecada + "\n               -Alquiler Base:" + alquilerBase +
                "\n               -Factor de Revalorización:" + factorRevalorizacion +
                "\n               -Hipoteca Base:" + hipotecaBase + "\n               -Precio para Edificar:" +
                precioEdificar;
    }
}
