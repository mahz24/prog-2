package edu.prog2;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import edu.prog2.helpers.Keyboard;
import edu.prog2.models.*;
import edu.prog2.services.*;

public class App {

  static PasajerosService pasajeros;
  static TrayectosService trayectos;
  static AvionesService aviones;
  static VuelosService vuelos;
  static SillasService sillas;
  static ReservasService reservas;
  static ReservasVuelosService reservasVuelos;

  public static void main(String[] args) throws Exception {
    Locale.setDefault(Locale.ENGLISH);
    pasajeros = new PasajerosService();
    aviones = new AvionesService();
    trayectos = new TrayectosService();
    vuelos = new VuelosService(trayectos, aviones);
    sillas = new SillasService(aviones);
    reservas = new ReservasService(pasajeros);
    reservasVuelos = new ReservasVuelosService(reservas, vuelos, sillas, aviones, pasajeros, trayectos);
    menu();
  }

  /**
   * esta función enumera los casos que se van a mostrar en pantalla
   */
  private static void menu() {

    do {
      try {
        int opcion = leerOpcion();

        switch (opcion) {

          case 1:
            crearPasajero();
            break;

          case 2:
            crearTrayectos();
            break;

          case 3:
            crearAviones();
            break;

          case 4:
            programarVuelos();
            break;

          case 5:
            crearReservas();
            break;

          case 6:
            listarPasajeros();
            break;

          case 7:
            listarTrayectos();
            break;

          case 8:
            listarVuelos();
            break;

          case 9:
            listarReservas();
            break;

          case 10:
            listarAviones();
            break;

          case 11:
            listarSillas(aviones.getList().get(0));
            break;

          case 12:
            listarAvionesConSillas();
            break;

          case 13:
            listarPasajerosCSV();
            break;

          case 14:
            listarTrayectosCSV();
            break;

          case 15:
            listarAvionesCSV();
            break;

          case 16:
            listarVuelosCSV();
            break;

          case 17:
            listarSillasCSV();
            break;

          case 18:
            listarReservasCSV();
            break;

          case 19:
            listarReservasVuelosCSV();
            break;

          case 20:
            vuelos.listAll();
            break;

          case 99:
            System.exit(0);
            break;
          default:
            System.out.println("Opción inválida");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } while (true);
  }

  /**
   * Esta funcion muestra en pantalla el número de casos disponibles
   * 
   * @return String que enumera los casos y los muestra en pantalla
   */
  static int leerOpcion() {
    String opciones = "\nMenú de opciones: \n"
        + " 1 - crear pasajeros                       11 - listar sillas\n"
        + " 2 - crear trayectos                       12 - listar aviones con sus sillas\n"
        + " 3 - crear aviones                         13 - listar pasajeros csv\n"
        + " 4 - programar vuelos                      14 - listar trayectos csv\n"
        + " 5 - crear reservas                        15 - listar aviones csv\n"
        + " 6 - listar pasajeros                      16 - listar vuelos csv\n"
        + " 7 - listar trayectos                      17 - listar sillas csv\n"
        + " 8 - listar vuelos programados             18 - listar reservas csv\n"
        + " 9 - listar reservas                       19 - listar reservasVuelos csv\n"
        + "10 - listar aviones\n"
        + "99 - Salir                  \n"
        + "\nElija una opción (99 para salir) > ";

    int opcion = Keyboard.readInt(opciones);
    System.out.println();
    return opcion;
  }

  /**
   * Esta función crea una lista de pasajeros
   * 
   * @throws IOException
   */
  private static void crearPasajero() throws IOException {
    System.out.println("Ingreso de pasajeros\n");

    do {
      String identificacion = Keyboard.readString(
          "Identificación (Intro termina): ");

      if (identificacion.length() == 0) {
        return;
      }

      String nombres = Keyboard.readString(3, 25, "Nombres: ");
      String apellidos = Keyboard.readString(3, 25, "Apellidos: ");

      boolean ok = pasajeros.add(new Pasajero(
          identificacion, nombres, apellidos));

      if (ok) {
        System.out.printf(
            "Se agregó el pasajero con identificación: %s%n%n", identificacion);
      }

    } while (true);

  }

  /**
   * Esta función muestra la lista de pasajeros que hay
   */
  private static void listarPasajeros() {
    if (pasajeros == null) {
      System.out.println("Use primero la opcion de crear pasajeros");
    } else {
      for (Pasajero pasajero : pasajeros.getList()) {
        System.out.println(pasajero);
      }
    }
  }

  /**
   * Esta función crea una lista de trayectos
   */
  private static void listarTrayectos() {
    if (trayectos == null) {
      System.out.println("Use primero la opción crear array de trayectos");
    } else {
      for (Trayecto t : trayectos.getList()) {
        System.out.println(t);
      }
    }
  }

  /**
   * Esta función muestra la lista de trayectos
   */
  private static void crearTrayectos() throws IOException {

    System.out.println("Ingreso de trayectos\n");

    do {
      String origen = Keyboard.readString(
          "Origen (Intro termina): ");

      if (origen.length() == 0) {
        return;
      }

      String destino = Keyboard.readString(3, 25, "Destino: ");
      Duration duracion = Keyboard.readDuration("Duración: ");
      double costo = Keyboard.readDouble("Costo: ");

      boolean ok = trayectos.add(new Trayecto(origen, destino, duracion, costo));

      if (ok) {
        System.out.printf(
            "Se agregó el trayecto: %s-%s%n%n", origen, destino);
      }

    } while (true);
  }

  /**
   * Esta función pide los datos de un avión y crea instancias que se agregan al
   * array de aviones
   * 
   * @throws IOException posible error que se puede ocasionar al crear una
   *                     instancia de avión
   */
  private static void crearAviones() throws IOException {
    do {
      String matricula = Keyboard.readString("Matrícula (Intro termina): ");
      if (matricula.length() == 0) {
        return;
      }
      String modelo = Keyboard.readString(3, 15, "Modelo: ");
      int sillasEjecutivas = Keyboard.readInt("Cuántas sillas ejecutivas: ");
      int sillasEconomicas = Keyboard.readInt("Cuántas sillas económicas: ");

      Avion nuevoAvion = new Avion(matricula, modelo);
      boolean ok = aviones.add(nuevoAvion);

      if (ok) {
        sillas.create(nuevoAvion, sillasEjecutivas, sillasEconomicas);
      }
    } while (true);

  }

  /**
   * Esta función muestra la lista de aviones con sus modelos y matriculas
   */
  private static void listarAviones() {
    if (aviones == null) {
      System.out.println("Use primero la opción crear array de aviones");
    } else {
      for (Avion a : aviones.getList()) {
        System.out.println(a);
      }
    }
  }

  /**
   * Esta función programa los vuelos que se van a realizar
   */
  private static void programarVuelos() throws IOException {
    System.out.println("Programación de vuelos \n\n");

    do {
      System.out.println("Trayectos disponibles:");
      for (int i = 0; i < trayectos.size(); i++) {
        Trayecto t = trayectos.getList().get(i);
        System.out.printf("%2d: %-10s%-10s%-10s%-4s%n", (i + 1), t.getOrigen(),
            t.getDestino(), t.getCosto(), t.getDuration());
      }

      int i;
      do {
        i = Keyboard.readInt("Elija el índice del trayecto (0-Termina): ");
        if (i < 0 || i > pasajeros.size()) {
          System.out.printf("Error. Elija un índice entre 1 y %d%n",
              trayectos.size());
        }
      } while (i < 0 || i > pasajeros.size());

      if (i == 0) {
        return;
      }
      Trayecto trayectoVuelo = new Trayecto(trayectos.getList().get(i - 1));

      LocalDateTime fechaHora = Keyboard.readDateTime("Fecha y hora: ");

      System.out.println("\n Aviones disponibles:");
      for (int j = 0; j < aviones.size(); j++) {
        Avion a = aviones.getList().get(j);
        System.out.printf("%2d: %-5s - %-5s%n", (j + 1), a.getMatricula(),
            a.getModelo());
      }

      int in;
      do {
        in = Keyboard.readInt("Elija el índice del avión (0-Termina): ");
        if (in < 0 || in > aviones.size()) {
          System.out.printf("Error. Elija un índice entre 1 y %d%n",
              aviones.size());
        }
      } while (in < 0 || in > aviones.size());
      Avion avionVuelo = new Avion(aviones.getList().get(in - 1));

      Vuelo nuevoVuelo = new Vuelo(fechaHora, trayectoVuelo, avionVuelo);

      if (vuelos.add(nuevoVuelo)) {
        System.out.printf("Se agregó el vuelo con trayecto %s, %s, fecha/hora: %s (%s)%n%n", trayectoVuelo.getOrigen(),
            trayectoVuelo.getDestino(), avionVuelo.getMatricula(), fechaHora, trayectoVuelo.getCosto());
      }

    } while (true);

  }

  /**
   * Esta función muestra los vuelos disponibles en pantalla
   */
  private static void listarVuelos() {
    if (vuelos == null) {
      System.out.println("De haber al menos un vuelo programado");
    } else {
      for (Vuelo vuelo : vuelos.getList()) {
        System.out.println(vuelo);
      }
    }

  }

  /**
   * Esta función muestra las sillas de un avión en pantalla
   * 
   * @param avion Avion al cual se le van a mostrar las sillas
   */
  private static void listarSillas(Avion avion) {
    if (avion == null || sillas == null) {
      System.out.println("Problemas con los ArrayList de aviones y de sillas");
    } else {
      System.out.println("SILLAS DEL AVIÓN " + avion.getMatricula());
      for (Silla s : sillas.getList()) {
        if (s.getAvion().equals(avion)) {
          System.out.printf("%-5d%s%n", sillas.getList().indexOf(s), s);
        }
      }
    }
  }

  /**
   * Esta silla muestra en patalla el avión con sus sillas reapectivas
   */
  private static void listarAvionesConSillas() {
    for (Avion avion : aviones.getList()) {
      listarSillas(avion);
    }
  }

  /**
   * Esta función creará las reservas que sean necesarias
   * 
   * @throws Exception
   */
  private static void crearReservas() throws Exception {
    do {
      Pasajero pasajero = elegirPasajero();
      if (pasajero == null) {
        break;
      }
      System.out.println();
      LocalDateTime fechaHoraReserva = Keyboard
          .readDateTime("Ingrese la fecha y la hora de la reserva (AAAA-MM-DD HH:MM AM/PM): ");
      Reserva nuevaReserva = new Reserva(fechaHoraReserva, false, pasajero);
      ReservaVuelo nuevaReservaVuelo;
      System.out.println();
      Vuelo vueloElegido = elegirVuelo();
      if (vueloElegido == null) {
        break;
      }
      System.out.println();
      Silla sillaElegida = elegirSillaDisponible(vueloElegido);
      if (sillaDisponibleEnVuelo(sillaElegida, vueloElegido)) {
        sillaElegida.setDisponible(false);
      }
      nuevaReservaVuelo = new ReservaVuelo(nuevaReserva, vueloElegido, sillaElegida);
      if (reservasVuelos.add(nuevaReservaVuelo)) {
        reservas.add(nuevaReserva);
        System.out.println("Reserva agregada");
      }
    } while (true);

  }

  /**
   * Esta función muestra la información de una reserva
   * 
   * @param reserva que mostrará la información
   */
  private static void listarVuelosReserva(Reserva reserva) {
    System.out.println("Vuelo:");
    for (ReservaVuelo reservaVuelo : reservasVuelos.getList()) {
      if (reservaVuelo.getReserva().equals(reserva)) {
        System.out.printf("Fecha y hora: %s - Estado: %s - Cheking: %s Avión: %s - %s, Silla: %s%n",
            reserva.getFechaHora(),
            (reserva.getCancelada() == false) ? "Vigente" : "Cancelada",
            (reservaVuelo.getCheckIn() == true) ? "Pendiente" : "Despachado",
            reservaVuelo.getVuelo().getAvion().getMatricula(), reservaVuelo.getVuelo().getAvion().getModelo(),
            reservaVuelo.getSilla().getPosicion());
      }
    }
  }

  /**
   * Esta función muestra en consola las reservas con la información de cada una
   */
  private static void listarReservas() {
    for (Reserva reserva : reservas.getList()) {
      System.out.printf("Fecha y hora de la reserva: %s - Estado: %s - Pasajero: %s - %s %s%n%n",
          reserva.getFechaHora(),
          (reserva.getCancelada() == false) ? "Vigente" : "Cancelada", reserva.getPasajero().getIdentificacion(),
          reserva.getPasajero().getNombres(), reserva.getPasajero().getApellidos());
      listarVuelosReserva(reserva);
    }
  }

  /**
   * Esta función verifica que sillas estan reservadas en un vuelo
   * 
   * @param vuelo que contiene las sillas
   * @return arrayList con las sillas reservadas en ese vuelo
   */
  private static ArrayList<Silla> sillasReservadasEnVuelo(Vuelo vuelo) {
    ArrayList<Silla> sillasReservadas = new ArrayList<>();

    for (Silla silla : sillas.getList()) {
      if (silla.getAvion().equals(vuelo.getAvion())) {
        if (silla.getDisponible().equals(false)) {
          sillasReservadas.add(silla);
        }
      }
    }
    return sillasReservadas;
  }

  /**
   * Esta función verifica si unas silla esta disponible en un vuelo
   * 
   * @param silla a la que se va a verificar
   * @param vuelo al que pertenece la silla
   * @return Retorna true si la silla esta disponible de lo contrario retorna
   *         false
   */
  private static boolean sillaDisponibleEnVuelo(Silla silla, Vuelo vuelo) {
    boolean ok = true;

    for (Silla s : sillasReservadasEnVuelo(vuelo)) {
      if (s.equals(silla)) {
        ok = false;
      }
    }
    return ok;
  }

  /**
   * Este metodo se encarga de filtrar las sillas de acuerdo a si estan
   * disponibles
   * 
   * @param vuelo al que pertenecen las sillas
   * @return Retorna arrayList de sillas disponibles
   */
  private static ArrayList<Silla> sillasDisponiblesEnVuelo(Vuelo vuelo) {
    ArrayList<Silla> disponibles = new ArrayList<>();

    for (Silla silla : sillas.getList()) {
      if (silla.getAvion().equals(vuelo.getAvion())) {
        if (silla.getDisponible().equals(true)) {
          disponibles.add(silla);
        }
      }
    }

    return disponibles;
  }

  /**
   * Este metodo muestra en pantalla las sillas disponibles en un vuelo
   * 
   * @param vuelo que contiene las sillas
   */
  private static void listarSillasDisponiblesEnVuelo(Vuelo vuelo) {
    int counter = 1;
    System.out.printf("Listado de sillas disponibles el en vuelo %s: %s - %s - %s%n", vuelo.getAvion().getMatricula(),
        vuelo.getTrayecto().getOrigen(), vuelo.getTrayecto().getDestino(), vuelo.getDateTime());
    for (Silla silla : sillasDisponiblesEnVuelo(vuelo)) {
      if (silla.getDisponible() == true) {
        System.out.printf("%2s-%s-%-4s", counter, silla.getPosicion(), (silla instanceof SillaEjecutiva) ? "E" : "S");
      }
      counter++;
    }
    System.out.println();
  }

  /**
   * Este metodo permite elegir una silla en un vuelo
   * 
   * @param vuelo al que pertenece la silla
   * @return Retorna la silla elegida
   */
  private static Silla elegirSillaDisponible(Vuelo vuelo) {
    listarSillasDisponiblesEnVuelo(vuelo);
    int indexSilla;
    do {
      indexSilla = Keyboard.readInt("Elija el indice de la silla: ");
      if (indexSilla < 0 || indexSilla > sillasDisponiblesEnVuelo(vuelo).size()) {
        System.out.printf("Error. Elija un índice entre 0 y %d%n",
            sillasDisponiblesEnVuelo(vuelo).size());
      }
      sillasDisponiblesEnVuelo(vuelo).get(indexSilla).setDisponible(false);
    } while (indexSilla < 0 || indexSilla > sillasDisponiblesEnVuelo(vuelo).size());
    Silla s = sillasDisponiblesEnVuelo(vuelo).get(indexSilla);
    if (s instanceof SillaEjecutiva) {
      SillaEjecutiva ejecutiva = (SillaEjecutiva) s;
      Menu menu = Keyboard.readEnum(Menu.class);
      Licor licor = Keyboard.readEnum(Licor.class);
      ejecutiva.setMenu(menu);
      ejecutiva.setLicor(licor);
    }
    return s;
  }

  /**
   * Esta función permite elegir un pasajero
   * 
   * @return Retorna el pasajero elegido
   */
  private static Pasajero elegirPasajero() {
    System.out.println("Pasajeros:");
    for (int i = 0; i < pasajeros.size(); i++) {
      Pasajero p = pasajeros.getList().get(i);
      System.out.printf("%2d: %s-%s %s%n", (i + 1), p.getIdentificacion(),
          p.getNombres(), p.getApellidos());
    }

    int i;
    do {
      i = Keyboard.readInt("Elija el índice del pasajero (0-Termina): ");
      if (i < 0 || i > pasajeros.size()) {
        System.out.printf("Error. Elija un índice entre 1 y %d%n",
            pasajeros.size());
      }
    } while (i < 0 || i > pasajeros.size());

    return i > 0 ? pasajeros.getList().get(i - 1) : null;
  }

  /**
   * Esta función permite elegir un vuelo
   * 
   * @return Retorna el vuelo elegido
   */
  private static Vuelo elegirVuelo() {
    System.out.println("Vuelos pogramados:");
    for (int i = 0; i < vuelos.size(); i++) {
      Vuelo vuelo = vuelos.getList().get(i);
      System.out.printf("%2d: %s %s %s - %s%n", (i + 1), vuelo.getDateTime(),
          vuelo.getAvion().getMatricula(), vuelo.getTrayecto().getOrigen(), vuelo.getTrayecto().getDestino());
    }

    int i;
    do {
      i = Keyboard.readInt("Elija el índice del pasajero (0-Termina): ");
      if (i < 0 || i > vuelos.size()) {
        System.out.printf("Error. Elija un índice entre 1 y %d%n",
            vuelos.size());
      }
    } while (i < 0 || i > vuelos.size());

    return i > 0 ? vuelos.getList().get(i - 1) : null;
  }

  /**
   * Esta funcion muestra en pantalla la lista de aviones en formato CSV
   */
  private static void listarAvionesCSV() {
    for (Avion avion : aviones.getList()) {
      System.out.print(avion.toCSV());
    }
  }

  /**
   * Esta funcion muestra en pantalla la lista de pasajeros en formato CSV
   */
  private static void listarPasajerosCSV() {
    for (Pasajero p : pasajeros.getList()) {
      System.out.print(p.toCSV());
    }
  }

  /**
   * Esta funcion muestra en pantalla la lista de trayectos en formato CSV
   */
  private static void listarTrayectosCSV() {
    for (Trayecto t : trayectos.getList()) {
      System.out.print(t.toCSV());
    }
  }

  /**
   * Esta funcion muestra en pantalla la lista de vuelos en formato CSV
   */
  private static void listarVuelosCSV() {
    for (Vuelo v : vuelos.getList()) {
      System.out.print(v.toCSV());
    }
  }

  /**
   * Esta funcion muestra en pantalla la lista de sillas en formato CSV
   */
  private static void listarSillasCSV() {
    for (Silla s : sillas.getList()) {
      System.out.print(s.toCSV());
    }
  }

  /**
   * Esta funcion muestra en pantalla la lista de reservas en formato CSV
   */
  private static void listarReservasCSV() {
    for (Reserva r : reservas.getList()) {
      System.out.print(r.toCSV());
    }
  }

  /**
   * Esta funcion muestra en pantalla la lista de reservas en vuelos en formato
   * CSV
   */
  private static void listarReservasVuelosCSV() {
    for (ReservaVuelo rv : reservasVuelos.getList()) {
      System.out.print(rv.toCSV());
    }
  }
}
