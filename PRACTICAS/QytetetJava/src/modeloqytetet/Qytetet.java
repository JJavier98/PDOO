/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import GUIQytetet.Dado;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author jjavier98
 */
public class Qytetet {

    private static Qytetet instance = new Qytetet();

    public static Qytetet getInstance() {
        return instance;
    }

    public static final int MAX_JUGADORES = 4;
    public static final int MAX_CARTAS = 10;
    public static final int MAX_CASILLAS = 20;
    public static final int PRECIO_LIBERTAD = 200;
    public static final int SALDO_SALIDA = 7500;

    private Sorpresa cartaActual;
    private final ArrayList<Sorpresa> mazo;
    private ArrayList<Jugador> jugadores;
    private Jugador jugadorActual;
    private Tablero tablero;
    private Dado dado;

    private Qytetet() {
        cartaActual = null;
        mazo = new ArrayList<>();
        jugadores = new ArrayList<>();
        jugadorActual = null;
        dado = GUIQytetet.Dado.getInstance();
        tablero = null;
    }

    private void inicializarCartasSorpresas() {
        mazo.add(new Sorpresa("Te has convertido en un jugador ESPECULADOR tipo 1!", TipoSorpresa.CONVERTIRME, 3000));
        mazo.add(new Sorpresa("Te has convertido en un jugador ESPECULADOR tipo 2!!", TipoSorpresa.CONVERTIRME, 5000));
        mazo.add(new Sorpresa("Gracias a tus poderosos contactos quedas libre de la cárcel", TipoSorpresa.SALIRCARCEL, 0));
        mazo.add(new Sorpresa("Un fan anónimo ha pagado tu fianza. Sales de la cárcel", TipoSorpresa.SALIRCARCEL, 0));
        mazo.add(new Sorpresa("Te hemos pillado con chanclas y calcetines. Lo sentimos, ¡debes ir a la carcel!", TipoSorpresa.IRACASILLA, tablero.getCarcel().getNumeroCasilla()));
        mazo.add(new Sorpresa("¡Un OVNI te ha abducido y te ha llevado hasta la casilla de SALIDA! Cobra tu recompensa por sobrevivir.", TipoSorpresa.IRACASILLA, 0));
        mazo.add(new Sorpresa("Gracias a tus poderosos contactos quedas libre de la cárcel", TipoSorpresa.SALIRCARCEL, 0));
        mazo.add(new Sorpresa("¡Levantas una piedra y encuentras dinero!", TipoSorpresa.PAGARCOBRAR, 300));
        mazo.add(new Sorpresa("Por no recoger los excrementos de tu perro pagas a cada jugador por las molestias", TipoSorpresa.PAGARCOBRAR, -100));
        mazo.add(new Sorpresa("Eres un químico muy patoso y provocas una explosión que afecta a todo el vecindario. Pagas por cada casa/hotel que haya en tu misma arista del tablero", TipoSorpresa.PORCASAHOTEL, 100));
        mazo.add(new Sorpresa("Recibes el pago mensual por alquiler de casas y reservas de hotel.", TipoSorpresa.PORCASAHOTEL, -150));
        mazo.add(new Sorpresa("Haces una fiesta privada y asisten todos los jugadores. El precio de la entrada es:", TipoSorpresa.PORJUGADOR, -150));
        mazo.add(new Sorpresa("¡Se te va de las manos! Por escándalo público pagas: ", TipoSorpresa.PORJUGADOR, 150));

        //Collections.shuffle(mazo);
    }

    private void inicializarJugadores(ArrayList<String> nombres) {
        for (String nom : nombres) {
            jugadores.add(new Jugador(nom));
        }
    }

    private void inicializarTablero() {
        tablero = new Tablero();
    }

    public boolean comprarTituloPropiedad() {
        return jugadorActual.comprarTitulo();
    }

    public boolean hipotecarPropiedad(Calle casilla) {
        boolean puedoHipotecarPropiedad = false;

        if (casilla.soyEdificable()) {
            boolean sePuedeHipotecar = !casilla.estaHipotecada();

            if (sePuedeHipotecar) {
                if (jugadorActual.puedoHipotecar(casilla)) {
                    puedoHipotecarPropiedad = true;
                    int cantidadRecibida = casilla.hipotecar();
                    jugadorActual.modificarSaldo(cantidadRecibida);
                }
            }
        }

        return puedoHipotecarPropiedad;

    }

    public boolean cancelarHipoteca(Calle casilla) {
        boolean puedoCancelar = jugadorActual.puedoPagarHipoteca(casilla);
        if (puedoCancelar) {
            int costeHipoteca = casilla.cancelarHipoteca();
            jugadorActual.modificarSaldo(-costeHipoteca);
        }

        return puedoCancelar;
    }

    public void inicializarJuego(ArrayList<String> nombres) {
        inicializarTablero();
        inicializarJugadores(nombres);
        inicializarCartasSorpresas();
        salidaJugadores();
    }

    public Sorpresa getCartaActual() {
        return cartaActual;
    }

    public boolean edificarCasa(Calle casilla) {
        boolean puedoEdificar = false;

        if (casilla.soyEdificable()) {
            boolean sePuedeEdificar = casilla.sePuedeEdificarCasa();
            if (sePuedeEdificar) {
                puedoEdificar = jugadorActual.puedoEdificarCasa(casilla);

                if (puedoEdificar) {
                    int costeEdificarCasa = casilla.edificarCasa();
                    jugadorActual.modificarSaldo(-costeEdificarCasa);
                }
            }
        }
        return puedoEdificar;
    }

    public boolean edificarHotel(Calle casilla) {
        boolean puedoEdificar = false;

        if (casilla.soyEdificable()) {
            boolean sePuedeEdificar = casilla.sePuedeEdificarHotel();
            if (sePuedeEdificar) {
                puedoEdificar = jugadorActual.puedoEdificarHotel(casilla);

                if (puedoEdificar) {
                    int costeEdificar = casilla.edificarHotel();
                    jugadorActual.modificarSaldo(-costeEdificar);
                }
            }
        }
        return puedoEdificar;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public void siguienteJugador() {
        boolean seguir = true;

        for (int i = 0; i < jugadores.size() && seguir; ++i) {
            if (jugadores.get(i) == jugadorActual) {
                seguir = false;
                jugadorActual = jugadores.get((i + 1) % jugadores.size());
            }
        }
    }

    public boolean venderPropiedad(Calle casilla) {
        boolean puedoVender = false;

        if (casilla.soyEdificable()) {
            puedoVender = jugadorActual.puedoVenderPropiedad(casilla);

            if (puedoVender) {
                jugadorActual.venderPropiedad(casilla);
            }
        }

        return puedoVender;
    }

    public boolean aplicarSorpresa() {
        boolean tienePropietario = false;

        TipoSorpresa tipo = cartaActual.getTipo();

        System.out.println(cartaActual.getTexto());

        if (null != tipo) {
            switch (tipo) {
                case PAGARCOBRAR:
                    jugadorActual.modificarSaldo(cartaActual.getValor());
                    break;
                case IRACASILLA:
                    boolean esCarcel = tablero.esCasillaCarcel(cartaActual.getValor());
                    if (esCarcel) {
                        encarcelarJugador();
                    } else {
                        Casilla nuevaCasilla = tablero.obtenerCasillaNumero(cartaActual.getValor());
                        tienePropietario = jugadorActual.actualizarPosicion(nuevaCasilla);
                    }
                    break;
                case PORCASAHOTEL:
                    jugadorActual.pagarCobrarPorCasaYHotel(cartaActual.getValor());
                    break;
                case PORJUGADOR:
                    for (Jugador jug : jugadores) {
                        if (jugadorActual != jug) {
                            jug.modificarSaldo(cartaActual.getValor());
                            jugadorActual.modificarSaldo(-cartaActual.getValor());
                        }
                    }
                    break;
                case CONVERTIRME:
                    Especulador espec = jugadorActual.convertirme(cartaActual.getValor());
                    boolean encontrado = false;
                    for (int i = 0; i < jugadores.size() && !encontrado; i++) {
                        if (jugadores.get(i) == jugadorActual) {
                            encontrado = true;
                            jugadores.set(i, espec);
                            jugadorActual = jugadores.get(i);
                        }
                    }
                default:
                    break;
            }
        }

        if (tipo == TipoSorpresa.SALIRCARCEL) {
            jugadorActual.setCartaLibertad(cartaActual);
        } else {
            mazo.add(cartaActual); //SOLO SE DEVUELVE LA CARTA SI ES TIPO CACEL??
        }

        return tienePropietario;
    }

    private void salidaJugadores() {
        for (Jugador jug : jugadores) {
            jug.setCasillaActual(tablero.obtenerCasillaNumero(0));
            jug.setSaldo(SALDO_SALIDA);
        }
        int numero = (int) (Math.random() * jugadores.size());
        jugadorActual = jugadores.get(numero);
    }

    public boolean jugar() {
        Scanner scan = new Scanner(System.in);

        boolean tienePropietario;

        Dado dado = GUIQytetet.Dado.getInstance();
        int valorDado = dado.nextNumber("DADO QYTETET", "¡Tira el dado!");
        System.out.println("Has sacado: " + valorDado);

        Casilla casillaPosicion = jugadorActual.getCasillaActual();
        Casilla nuevaCasilla = tablero.obtenerNuevaCasilla(casillaPosicion, valorDado);
        
        if(nuevaCasilla.getTipo() != TipoCasilla.CALLE)
            System.out.println("\nCaes en la casilla:\n   *númeroCasilla: " +
                    nuevaCasilla.getNumeroCasilla() + "\n   *Tipo: " + nuevaCasilla.getTipo() + "\n");
        else
            if(nuevaCasilla.getTipo() == TipoCasilla.CALLE && nuevaCasilla.tengoPropietario())
                System.out.println("\nCaes en la casilla:\n   *númeroCasilla: " +
                        nuevaCasilla.getNumeroCasilla() + "\n   *Tipo: " + nuevaCasilla.getTipo() +
                        "\n   *Propietario: " + ((Calle)nuevaCasilla).getTitulo().getPropietario().getNombre() + "\n");
        
        tienePropietario = jugadorActual.actualizarPosicion(nuevaCasilla);

        if (!nuevaCasilla.soyEdificable()) {
            if (nuevaCasilla.getTipo() == TipoCasilla.JUEZ) {
                encarcelarJugador();
            } else if (nuevaCasilla.getTipo() == TipoCasilla.SORPRESA) {
                cartaActual = mazo.get(0);
                mazo.remove(0); // SE QUITA PORQUE AL DEVOLVER LA CARTA SE AÑADIRÁ AL FINAL DEL MAZO
            }
        }

        return tienePropietario;
    }

    public ArrayList<Jugador> obtenerRanking() {
        ArrayList<Jugador> ranking = new ArrayList<>();
        ArrayList<Jugador> aux = new ArrayList<>();
        aux = jugadores;

        while (aux.size() > 0) {
            Jugador max = aux.get(0);

            for (Jugador jug : aux) {
                if (jug.obtenerCapital() > max.obtenerCapital()) {
                    max = jug;
                }
            }
            ranking.add(max);
            aux.remove(max);
        }

        return ranking;
    }

    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo) {
        Scanner scan = new Scanner(System.in);
        boolean libre = false;

        if (metodo == MetodoSalirCarcel.TIRANDODADO) {
            int valorDado = dado.nextNumber("DADO QYTETET", "¡Tira el dado!");
            System.out.println("Has sacado: " + valorDado);
            libre = valorDado > 5;
        } else if (metodo == MetodoSalirCarcel.PAGANDOLIBERTAD) {
            boolean tengoSaldo = jugadorActual.pagarLibertad(-PRECIO_LIBERTAD);
            libre = tengoSaldo;
        }

        if (libre) {
            jugadorActual.setEncarcelado(false);
        }

        return libre;
    }

    public void encarcelarJugador() {
        if (!jugadorActual.tengoCartaLibertad()) {
            Casilla casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
        } else {
            Sorpresa carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);
        }
    }

    public ArrayList<Casilla> propiedadesHipotecadasJugador(boolean hipotecadas) {
        ArrayList<Casilla> devolver = new ArrayList<>();
        for (TituloPropiedad tp : jugadorActual.getPropiedades()) {
            if (tp.getHipotecada() == hipotecadas) {
                devolver.add(tp.getCasilla());
            }
        }
        return devolver;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public String toString() {
        String resultado = "Qytetet!! \n\n Jugadores: ";
        for (Jugador j : jugadores) {
            resultado += j.toString();
            resultado += "\n";
        }
        resultado += "\n\n Mazo Sorpresas: \n";
        for (Sorpresa m : mazo) {
            resultado += m.toString();
        }
        resultado += "\n\n      Tablero:\n         " + tablero + "\n JugadorActual:" + jugadorActual + "\n CartaSorpresaActual:" + cartaActual + '\n';
        return resultado;
    }

}
