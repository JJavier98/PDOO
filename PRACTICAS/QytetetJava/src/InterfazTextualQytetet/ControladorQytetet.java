/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazTextualQytetet;

import java.util.ArrayList;
import java.util.Scanner;
import modeloqytetet.*;

/**
 *
 * @author jjavier98
 */
public class ControladorQytetet {

    private static Qytetet juego;
    private static Jugador jugador;
    private static Casilla casilla;
    private static VistaTextualQytetet vista;

    public static void inicializacionJuego() {
        Scanner scan = new Scanner(System.in);
        String n;

        juego = Qytetet.getInstance();
        vista = new VistaTextualQytetet();
        juego.inicializarJuego(vista.obtenerNombreJugadores());

        jugador = juego.getJugadorActual();
        casilla = jugador.getCasillaActual();

        System.out.println(juego);
    }

    public static void desarrolloDelJuego() {

        boolean libre, tienePropietario;
        boolean fin = false;
        int metodo_salir;
        MetodoSalirCarcel m;

        while (!fin) {

            vista.mostrar("\n\n\n            ****** Es el turno de: ******           \n" + jugador);

            if (jugador.isEncarcelado()) {
                metodo_salir = vista.menuSalirCarcel();

                if (metodo_salir == 0) {
                    m = MetodoSalirCarcel.PAGANDOLIBERTAD;
                } else {
                    m = MetodoSalirCarcel.TIRANDODADO;
                }

                libre = juego.intentarSalirCarcel(m);

                if (libre) {
                    vista.mostrar("Has quedado libre de la carcel.\n");
                } else {
                    vista.mostrar("Sigues encarcelado.\n");
                }
            }

            if (!jugador.isEncarcelado()) {
                tienePropietario = juego.jugar();
                casilla = jugador.getCasillaActual();

                if (jugador.getSaldo() > 0 && !jugador.isEncarcelado()) // NO ESTÁ EN BANCARROTA NI ESTÁ ENCARCELADO
                {

                    if (casilla.getTipo() == TipoCasilla.CALLE) {
                        if (!tienePropietario) {
                            vista.mostrar("            ****** Estás en: ******             \n" + casilla);

                            boolean comprar = vista.elegirQuieroComprar();
                            if (comprar) {
                                if (jugador.comprarTitulo()) {
                                    vista.mostrar("\n\nHas comprado la casilla.\n");
                                } else {
                                    vista.mostrar("\n\nNo puedes comprar la casilla.\n");
                                }
                            } else {
                                vista.mostrar("\n\nNo has comprado la casilla.\n");
                            }
                        }
                        else
                        {
                            int alquiler = ((Calle)casilla).getCosteHipoteca();
                            vista.mostrar("\n\nLa casilla tiene propietario. Pagas: " + alquiler + "\n");
                            ((Calle)casilla).cobrarAlquiler();
                        }
                    } else if (casilla.getTipo() == TipoCasilla.SORPRESA) {
                        tienePropietario = juego.aplicarSorpresa();
                        casilla = jugador.getCasillaActual();

                        if (!jugador.isEncarcelado() && jugador.getSaldo() > 0 && casilla.getTipo() == TipoCasilla.CALLE && !tienePropietario) {
                            vista.mostrar("            ****** Estás en: ******             \n" + casilla);
                            if (vista.elegirQuieroComprar()) {
                                if (jugador.comprarTitulo()) {
                                    vista.mostrar("\n\nHas comprado la casilla.\n");
                                } else {
                                    vista.mostrar("\n\nNo puedes comprar la casilla.\n");
                                }
                            } else {
                                vista.mostrar("\n\nNo has comprado la casilla.\n");
                            }
                        }
                    }

                    if (!jugador.isEncarcelado() && jugador.getSaldo() > 0 && jugador.tengoPropiedades()) {
                        int opcion = vista.menuGestionInmobiliaria();

                        while (opcion != 0) {
                            ArrayList<Calle> cas = new ArrayList<>();

                            for (TituloPropiedad titulo : jugador.getPropiedades()) {
                                cas.add(titulo.getCasilla());
                            }
                            Calle casilla_elegida;
                            casilla_elegida = elegirPropiedad(cas);
                            switch (opcion) {
                                case 1:
                                    if (casilla_elegida.estaHipotecada()) {
                                        vista.mostrar("\n--> No puedes construir una casa porque la propiedad está HIPOTECADA\n");
                                    } else if (juego.edificarCasa(casilla_elegida)) {
                                        vista.mostrar("\n--> Número de casas en " + casilla_elegida.getTitulo().getNombre() + " es: " + casilla_elegida.getNumCasas() + "\n");
                                    } else {
                                        vista.mostrar("\n--> No puedes construir casa" + "\n");
                                    }
                                    break;
                                case 2:
                                    if (casilla_elegida.estaHipotecada()) {
                                        vista.mostrar("\n--> No puedes construir un hotel porque la propiedad está HIPOTECADA\n");
                                    } else if (juego.edificarHotel(casilla_elegida)) {
                                        vista.mostrar("\n--> Número de hoteles en " + casilla_elegida.getTitulo().getNombre() + " es: " + casilla_elegida.getNumHoteles() + "\n");
                                    } else {
                                        vista.mostrar("\n--> No puedes construir hotel" + "\n");
                                    }
                                    break;
                                case 3:
                                    if (juego.venderPropiedad(casilla_elegida)) {
                                        vista.mostrar("\n--> Has vendido la propiedad \n");
                                        opcion = 0;
                                    } else {
                                        vista.mostrar("\n--> No puedes vender tu propiedad\n");
                                    }
                                    break;
                                case 4:
                                    if (casilla_elegida.estaHipotecada()) {
                                        vista.mostrar("\n--> La propiedad ya estaba HIPOTECADA\n");
                                    } else if (juego.hipotecarPropiedad(casilla_elegida)) {
                                        vista.mostrar("\n--> Has hipotecado la propiedad \n");
                                    } else {
                                        vista.mostrar("\n--> No puedes hipotecar tu propiedad\n");
                                    }
                                    break;
                                case 5:
                                    if (!casilla_elegida.estaHipotecada()) {
                                        vista.mostrar("\n--> La propiedad NO estaba HIPOTECADA\n");
                                    } else if (juego.cancelarHipoteca(casilla_elegida)) {
                                        vista.mostrar("\n--> Has cancelado la hipoteca de la propiedad \n");
                                    } else {
                                        vista.mostrar("\n--> No puedes cancelar la hipoteca de tu propiedad\n");
                                    }
                                    break;
                            }
                            if (jugador.getPropiedades().size() > 0) {
                                opcion = vista.menuGestionInmobiliaria();
                            }
                        }
                    }
                }
            }

            jugador = juego.getJugadorActual();
            vista.mostrar("\n            ****** El estado final del jugador es: ******           \n" + jugador);

            fin = jugador.obtenerCapital() <= 0;

            if (!fin) {
                juego.siguienteJugador();
                jugador = juego.getJugadorActual();
                casilla = jugador.getCasillaActual();
                fin = jugador.getSaldo() <= 0;
            }

            if (fin) {
                vista.mostrar("ranking" + juego.obtenerRanking());
            }
        }
    }

    //metodo que muestra en pantalla el string que recibe como argumento
    public void mostrar(String texto) {

        vista.mostrar(texto);
    }

    public static Calle elegirPropiedad(ArrayList<Calle> propiedades) {
        //    este metodo toma una lista de propiedades y genera una lista de strings, con el numero y nombre de las propiedades
        //   luego llama a la vista para que el usuario pueda elegir.

        int seleccion;
        ArrayList<String> listaPropiedades = new ArrayList<>();

        vista.mostrar("\n\tCasilla\tTitulo");
        for (Calle casilla : propiedades) {
            listaPropiedades.add("\t" + casilla.getNumeroCasilla() + "\t" + casilla.getTitulo().getNombre());
            /*getNumeroCasilla y getNombre no eran publicos*/
        }

        /*for (String lista : listaPropiedades) {
            System.out.println(lista);
        }
         */
        seleccion = vista.menuElegirPropiedad(listaPropiedades);

        return propiedades.get(seleccion);
    }

    public static void main(String[] args) {
        inicializacionJuego();
        desarrolloDelJuego();
    }

}
