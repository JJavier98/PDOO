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
    static int FactorEspeculador = 1;

    Jugador(String n) {
        encarcelado = false;
        nombre = n;
        saldo = 0;
        cartaLibertad = null;
        casillaActual = null;
        propiedades = new ArrayList<>();
        FactorEspeculador = 1;
    }

    protected Jugador(Jugador jug) {
        this.encarcelado = jug.encarcelado;
        this.nombre = jug.nombre;
        this.saldo = jug.saldo;
        this.cartaLibertad = jug.cartaLibertad;
        this.casillaActual = jug.casillaActual;
        this.propiedades = jug.propiedades;
        this.FactorEspeculador = jug.FactorEspeculador;
    }

    public int getFactorEspeculador() {
        return FactorEspeculador;
    }
    
    public String getNombrePropiedades()
    {
        String s = "";
        for(int i = 0 ; i < propiedades.size() ; ++i)
        {
            if(i != propiedades.size()-1)
                s += propiedades.get(i).getCasilla().getNumeroCasilla() + " " + propiedades.get(i).getNombre() + "\n";
            else
                s += propiedades.get(i).getCasilla().getNumeroCasilla() + " " + propiedades.get(i).getNombre();
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "\n      nombre:" + nombre + "\n      saldo:" + saldo + "\n      encarcelado:" + encarcelado +
                "\n      cartaLibertad:" + cartaLibertad + "\n      casillaActual: " + casillaActual.getNumeroCasilla() + " ";
        if(casillaActual instanceof Calle)
        {
            Calle ca = ((Calle)casillaActual);
            s += ca.getTitulo().getNombre() + "\n      propiedades:\n";
            
            for(TituloPropiedad tit : propiedades)
            {
                s += "            " + tit.getNombre() + "\n";
            }
        }
        else
        {
            s += casillaActual.getTipo() + "\n";
        }
        return s;
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
    public boolean tengoPropiedades() {
        return propiedades.size() > 0;
    }

    protected void pagarImpuestos(int cantidad) {
        modificarSaldo(-cantidad);
    }

    protected Especulador convertirme(int fianza) {
        Especulador especulador = new Especulador(this, fianza);
        return especulador;
    }

    protected boolean actualizarPosicion(Casilla casilla) {
        if (casilla.getNumeroCasilla() < casillaActual.getNumeroCasilla()) {
            modificarSaldo(1000);
        }

        setCasillaActual(casilla);

        if (casillaActual.soyEdificable()) {
            boolean tengoPropietario = ((Calle)casillaActual).tengoPropietario();

            if (tengoPropietario) {
                boolean encarcelado = ((Calle)casillaActual).propietarioEncarcelado();

                if (!encarcelado) {
                    int costeAlquiler = ((Calle)casillaActual).cobrarAlquiler();

                    modificarSaldo(-costeAlquiler);
                }
            }
        } else {
            if (casilla.getTipo() == TipoCasilla.IMPUESTO) /* NO IRIA FUERA DE "SOY EDIFICABLE" ¿?¿? */ /* VA EN UN ELSE¿?¿?¿?*/ {
                int coste = casilla.getCoste();
                pagarImpuestos(coste);
            }
        }
        return casillaActual.tengoPropietario();
    }

    public boolean comprarTitulo() {
        boolean puedoComprar = false;
        if (casillaActual.soyEdificable()) {
            boolean tengoPropietario = casillaActual.tengoPropietario();

            if (!tengoPropietario) {
                int costeCompra = casillaActual.getCoste();

                if (tengoSaldo(costeCompra)) {
                    TituloPropiedad titulo = ((Calle)casillaActual).asignarPropietario(this);
                    propiedades.add(titulo);
                    modificarSaldo(-costeCompra);

                    puedoComprar = true;
                }
            }
        }

        return puedoComprar;
    }

    public boolean puedoPagarHipoteca(Calle casilla) {
        boolean tengoSaldo = false;

        ArrayList<TituloPropiedad> hipotecadas = obtenerPropiedadesHipotecadas(true);
        if (hipotecadas.contains(casilla.getTitulo())) {
            int costeHipoteca = casilla.getCosteHipoteca();
            tengoSaldo = tengoSaldo(costeHipoteca);
        }

        return tengoSaldo;
    }

    public Sorpresa devolverCartaLibertad() {
        Sorpresa carta_a_devolver = cartaLibertad;
        cartaLibertad = null;
        return carta_a_devolver;
    }

    protected void irACarcel(Casilla casilla) {
        setCasillaActual(casilla);
        setEncarcelado(true);
    }

    public void modificarSaldo(int cantidad) {
        saldo += cantidad;
    }

    public int obtenerCapital() {
        int devolver = saldo;

        for (TituloPropiedad propiedade : propiedades) {
            Calle aux = propiedade.getCasilla();
            devolver = devolver + aux.getCoste();

            int valorEdificios = (aux.getNumCasas() + aux.getNumHoteles()) * propiedade.getPrecioEdificar();

            devolver = devolver + valorEdificios;

            if (propiedade.getHipotecada()) {
                devolver = devolver - propiedade.getHipotecaBase();
            }
        }
        return devolver;
    }

    public ArrayList<TituloPropiedad> obtenerPropiedadesHipotecadas(boolean hipotecadas) {
        ArrayList<TituloPropiedad> propiedadesADevolver = new ArrayList<>();

        for (TituloPropiedad prop : propiedades) {
            if (prop.getHipotecada() == hipotecadas) {
                propiedadesADevolver.add(prop);
            }
        }

        return propiedadesADevolver;
    }

    public void pagarCobrarPorCasaYHotel(int cantidad) {
        int numeroTotal = cuantasCasasHotelesTengo();
        modificarSaldo(cantidad * numeroTotal);
    }

    public boolean pagarLibertad(int cantidad) {
        boolean tengoSaldo = tengoSaldo(cantidad);
        if (tengoSaldo) {
            modificarSaldo(cantidad);
        }
        return tengoSaldo;
    }

    public boolean puedoEdificarCasa(Calle casilla) {
        return esDeMiPropiedad(casilla) && tengoSaldo(casilla.getTitulo().getPrecioEdificar());
    }

    public boolean puedoEdificarHotel(Calle casilla) {
        return esDeMiPropiedad(casilla) && tengoSaldo(casilla.getTitulo().getPrecioEdificar());
    }

    public boolean puedoHipotecar(Calle casilla) {
        return esDeMiPropiedad(casilla);
    }

    public boolean puedoVenderPropiedad(Calle casilla) {
        return esDeMiPropiedad(casilla) && !casilla.estaHipotecada();
    }

    public boolean tengoCartaLibertad() {
        return cartaLibertad != null;
    }

    public void venderPropiedad(Calle casilla) {
        int precioVenta = casilla.venderTitulo();
        modificarSaldo(precioVenta);
        eliminarDeMisPropiedades(casilla);
    }

    public int cuantasCasasHotelesTengo() {
        int edificios = 0;
        for (TituloPropiedad propied : propiedades) {
            edificios = edificios + propied.getCasilla().getNumCasas() + propied.getCasilla().getNumHoteles();
        }
        return edificios;
    }

    public void eliminarDeMisPropiedades(Calle casilla) {
        boolean seguir = true;

        for (int i = 0; i < propiedades.size() && seguir; i++) {
            if (propiedades.get(i).getCasilla() == casilla) {
                seguir = false;
                propiedades.remove(i);
            }
        }
    }

    public boolean esDeMiPropiedad(Calle casilla) {
        boolean seguir = true,
                laTengo = false;
        for (int i = 0; i < propiedades.size() && seguir; i++) {
            if (propiedades.get(i).getCasilla() == casilla) {
                seguir = false;
                laTengo = true;
            }
        }

        return laTengo;
    }

    public boolean tengoSaldo(int cantidad) {
        return saldo >= cantidad;
    }
}
