package edu.prog2.services;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.models.Avion;
import edu.prog2.models.Pasajero;
import edu.prog2.models.Reserva;
import edu.prog2.models.ReservaVuelo;
import edu.prog2.models.Silla;
import edu.prog2.models.Trayecto;
import edu.prog2.models.Vuelo;

public class ReservasVuelosService {
  private ArrayList<ReservaVuelo> reservasVuelos;
  private ReservasService reservas;
  private VuelosService vuelos;
  private SillasService sillas;
  private AvionesService aviones;
  private PasajerosService pasajeros;
  private TrayectosService trayectos;
  private String fileName;

  /**
   * Constructor principal del controlador de las reservas en vuelo
   */
  public ReservasVuelosService(ReservasService reservas, VuelosService vuelos, SillasService sillas,
      AvionesService aviones, PasajerosService pasajeros, TrayectosService trayectos)
      throws IOException {
    reservasVuelos = new ArrayList<>();
    this.reservas = reservas;
    this.vuelos = vuelos;
    this.sillas = sillas;
    this.pasajeros = pasajeros;
    this.aviones = aviones;
    this.trayectos = trayectos;
    fileName = UtilFiles.FILE_PATH + "vuelos-reservas";

    if (UtilFiles.fileExists(fileName + ".csv")) {
      loadCSV();
    } else if (UtilFiles.fileExists(fileName + ".json")) {
      loadJSON();
    } else {
      System.out.println("Aún no se ha creado un archivo: " + fileName);
    }
  }

  /**
   * Este metodo añade una nueva instancia de reservasVuelo al arraylist
   * 
   * @param reserva que se va a añadir al arraylist
   * @return devuelve un true si se añade, de lo contrario devuelve un false
   */
  public boolean add(ReservaVuelo reserva) throws IOException {
    if (contains(reserva)) {
      throw new IOException(
          ("La reserva del vuelo ya se encuentra en la lista"));
    }
    boolean ok = reservasVuelos.add(reserva);
    UtilFiles.writeData(reservasVuelos, fileName);
    return ok;
  }

  public void add(JSONObject json) throws IOException {
    Pasajero pasajero = pasajeros.get(new Pasajero(json.getString("pasajero"), null, null));
    Reserva reserva = reservas
        .get(new Reserva(LocalDateTime.parse(json.getString("fechaHoraReserva")), null, pasajero));
    Trayecto trayecto = trayectos
        .get(new Trayecto(json.getString("origen"), json.getString("destino"), Duration.ZERO, 0));
    Avion avion = aviones.get(new Avion(json.getString("avion"), null));
    Silla silla = sillas.get(new Silla(json.getInt("fila"), json.getString("columna").charAt(0), avion));
    Vuelo vuelo = vuelos.get(new Vuelo(LocalDateTime.parse(json.getString("fechaHoraVuelo")), trayecto, avion));
    ReservaVuelo rv = new ReservaVuelo(reserva, vuelo, silla);
    this.add(rv);
  }

  public ReservaVuelo get(int index) {
    return reservasVuelos.get(index);
  }

  /**
   * Este metodo obtiene una instancia de ReservaVuelo del arraylist
   * 
   * @param index indice en el que se encuentra la reserva
   * @return la instancia, de lo contrario devuelve un null
   */
  public ReservaVuelo get(ReservaVuelo rv) {
    int index = reservasVuelos.indexOf(rv);
    return index > -1 ? reservasVuelos.get(index) : null;
  }

  /**
   * Este metodo obtiene el arraylist
   * 
   * @return arraylist de reservasVuelos
   */
  public ArrayList<ReservaVuelo> getList() {
    return this.reservasVuelos;
  }

  /**
   * Este metodo verifica si ya hay una instancia en el arraylist
   * 
   * @param reserva que se va a verificar
   * @return true si la instancia esta en arraylist, de lo contrario devuelve un
   *         false
   */
  public boolean contains(ReservaVuelo reserva) {
    return this.reservasVuelos.contains(reserva);
  }

  /**
   * Este metodo devuelve el tamaño del ArrayList de reservasVuelos
   * 
   * @return int que representa el tamaño del ArrayList
   */
  public int size() {
    return 0;
  }

  public ReservasService getReservas() {
    return reservas;
  }

  public VuelosService getVuelos() {
    return vuelos;
  }

  public SillasService getSillas() {
    return sillas;
  }

  public JSONObject get(String params) {
    String[] parts = params.split("&");
    Pasajero pasajero = this.pasajeros.get(new Pasajero(parts[1], null, null));
    Reserva reserva = this.reservas.get(new Reserva(LocalDateTime.parse(parts[0]), null, pasajero));
    Avion avion = this.aviones.get(new Avion(parts[5], null));
    Trayecto trayecto = this.trayectos.get(new Trayecto(parts[3], parts[4], Duration.ZERO, 0));
    Silla silla = this.sillas.get(new Silla(Integer.parseInt(parts[6]), parts[7].charAt(0), avion));
    Vuelo vuelo = this.vuelos.get(new Vuelo(LocalDateTime.parse(parts[2]), trayecto, avion));
    ReservaVuelo reservaVueloSearched = this.get(new ReservaVuelo(reserva, vuelo, silla));
    return new JSONObject(reservaVueloSearched);
  }

  public void update() throws IOException {
    reservasVuelos = new ArrayList<>();
    loadCSV();
    UtilFiles.writeJSON(reservasVuelos, fileName + ".json");
  }

  public JSONObject set(String params, JSONObject json) throws IOException {
    String[] parts = params.split("&");
    // encontrar los datos de la reserva en vuelo
    Pasajero pasajero = this.pasajeros.get(new Pasajero(parts[1], null, null));
    Reserva reserva = this.reservas.get(new Reserva(LocalDateTime.parse(parts[0]), null, pasajero));
    Avion avion = this.aviones.get(new Avion(parts[5], null));
    Trayecto trayecto = this.trayectos.get(new Trayecto(parts[3], parts[4],
        Duration.ZERO, 0));
    Silla silla = this.sillas.get(new Silla(Integer.parseInt(parts[6]),
        parts[7].charAt(0), avion));
    Vuelo vuelo = this.vuelos.get(new Vuelo(LocalDateTime.parse(parts[2]),
        trayecto, avion));
    ReservaVuelo reservaVueloSearched = this.get(new ReservaVuelo(reserva, vuelo,
        silla));
    int index = reservasVuelos.indexOf(reservaVueloSearched);

    // actualizar los datos de la reserva en vuelo
    if (json.has("fechaHoraVuelo") && json.has("destino") && json.has("origen") && json.has("avion")) {
      Trayecto trayectoVuelo = trayectos
          .get(new Trayecto(json.getString("origen"), json.getString("destino"), Duration.ZERO, 0));
      Avion avionVuelo = aviones.get(new Avion(json.getString("avion"), null));
      Vuelo vueloUpdate = vuelos
          .get(new Vuelo(LocalDateTime.parse(json.getString("fechaHoraVuelo")), trayectoVuelo, avionVuelo));
      Silla sillaUpdate = sillas
          .get(new Silla(json.getInt("fila"), json.getString("columna").charAt(0), avionVuelo));
      reservaVueloSearched = new ReservaVuelo(reserva, vueloUpdate, sillaUpdate);
    }
    reservasVuelos.set(index, reservaVueloSearched);
    UtilFiles.writeData(reservasVuelos, fileName);
    return new JSONObject(reservaVueloSearched);
  }

  public void remove(String params) throws Exception {
    String[] parts = params.split("&");
    // encontrar los datos de la reserva en vuelo
    Pasajero pasajero = this.pasajeros.get(new Pasajero(parts[1], null, null));
    Reserva reserva = this.reservas.get(new Reserva(LocalDateTime.parse(parts[0]), null, pasajero));
    Avion avion = this.aviones.get(new Avion(parts[5], null));
    Trayecto trayecto = this.trayectos.get(new Trayecto(parts[3], parts[4],
        Duration.ZERO, 0));
    Silla silla = this.sillas.get(new Silla(Integer.parseInt(parts[6]),
        parts[7].charAt(0), avion));
    Vuelo vuelo = this.vuelos.get(new Vuelo(LocalDateTime.parse(parts[2]),
        trayecto, avion));
    ReservaVuelo reservaVuelo = this.get(new ReservaVuelo(reserva, vuelo,
        silla));

    if (UtilFiles.exists(UtilFiles.FILE_PATH + "vuelos-reservas", "reserva", reserva)) {
      throw new Exception(String.format(
          "No se eliminó el vuelo reservado, porque la reserva %s existe", reserva));
    }

    if (UtilFiles.exists(UtilFiles.FILE_PATH + "vuelos-reservas", "vuelo", vuelo)) {
      throw new Exception(String.format(
          "No se eliminó el vuelo reservado, porque el vuelo %s existe", vuelo));
    }

    if (UtilFiles.exists(UtilFiles.FILE_PATH + "vuelos-reservas", "silla", silla)) {
      throw new Exception(String.format(
          "No se eliminó el vuelo reservado, porque la silla %s existe", silla));
    }

    if (!reservasVuelos.remove(reservaVuelo)) {
      throw new Exception("No se encontró el vuelo reservado");
    }

  }

  /**
   * Este metodo sube un archivo de datos de tipo csv a la carpeta data en la raiz
   * 
   * @throws IOException
   */
  private void loadCSV() throws IOException {

    String text = UtilFiles.readText(fileName + ".csv");

    try (Scanner sc = new Scanner(text).useDelimiter(";|[\n]+|[\r\n]+")) {
      while (sc.hasNext()) {
        LocalDateTime fechaHoraReserva = LocalDateTime.parse(sc.next());
        String identificacion = sc.next();
        LocalDateTime fechaHoraVuelo = LocalDateTime.parse(sc.next());
        String origen = sc.next();
        String destino = sc.next();
        String matircula = sc.next();
        int fila = sc.nextInt();
        char columna = sc.next().charAt(0);
        Boolean disponible = sc.nextBoolean();

        Pasajero pasajero = pasajeros.get(new Pasajero(identificacion, null, null));
        Reserva reserva = reservas.get(new Reserva(fechaHoraReserva, disponible, pasajero));
        Trayecto trayecto = trayectos.get(new Trayecto(origen, destino, Duration.ZERO, 0));
        Avion avion = aviones.get(new Avion(matircula, null));
        Vuelo vuelo = vuelos.get(new Vuelo(fechaHoraVuelo, trayecto, avion));
        Silla silla = sillas.get(new Silla(fila, columna, avion));

        reservasVuelos.add(new ReservaVuelo(reserva, vuelo, silla));
      }
    }
  }

  public ArrayList<ReservaVuelo> loadJSON() throws IOException {
    reservasVuelos = new ArrayList<>();
    String data = UtilFiles.readText(fileName + ".json");
    JSONArray jsonArr = new JSONArray(data);

    for (int i = 0; i < jsonArr.length(); i++) {
      JSONObject jsonObj = jsonArr.getJSONObject(i);
      reservasVuelos.add(new ReservaVuelo(jsonObj));
    }

    return reservasVuelos;
  }

  public JSONArray getJSON() throws IOException {
    return new JSONArray(UtilFiles.readText(fileName + ".json"));
  }

  public JSONObject getJSON(int index) {
    return new JSONObject(reservasVuelos.get(index));
  }

  public JSONObject getJSON(ReservaVuelo rs) {
    int index = reservasVuelos.indexOf(rs);
    return index > -1 ? getJSON(index) : null;
  }
}
