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
import edu.prog2.models.Trayecto;
import edu.prog2.models.Vuelo;

public class VuelosService {
  ArrayList<Vuelo> vuelos;
  private String fileName;
  AvionesService aviones;
  TrayectosService trayectos;

  /**
   * Constructor principal del controlador de vuelos
   */
  public VuelosService(TrayectosService trayectos, AvionesService aviones) throws IOException {
    this.trayectos = trayectos;
    this.aviones = aviones;
    vuelos = new ArrayList<>();
    fileName = UtilFiles.FILE_PATH + "vuelos";

    if (UtilFiles.fileExists(fileName + ".csv")) {
      loadCSV();
    } else if (UtilFiles.fileExists(fileName + ".json")) {
      loadJSON();
    } else {
      System.out.println("Aún no se ha creado un archivo: " + fileName);
    }
  }

  /**
   * Este metodo añade una instancia de Vuelo al arraylist de vuelos
   * 
   * @param vuelo que se va añadir al arraylist
   * @return retorna true si añade al vuelo, de lo contrario retorna un false
   */
  public boolean add(Vuelo vuelo) throws IOException {
    if (contains(vuelo)) {
      throw new IOException(
          String.format("EL vuelo con destino a %s ya se encuentra en la lista", vuelo.getTrayecto().getDestino()));
    }
    boolean ok = vuelos.add(vuelo);
    UtilFiles.writeData(vuelos, fileName);
    return ok;
  }

  public void add(JSONObject json) throws IOException {
    LocalDateTime fechaHora = LocalDateTime.parse(
        json.getString("fechaHora"));

    Trayecto trayecto = new Trayecto(
        json.getString("origen"),
        json.getString("destino"),
        Duration.ZERO, 0.0);

    trayecto = trayectos.get(trayecto);
    Avion avion = aviones.get(new Avion(json.getString("avion"), ""));

    add(new Vuelo(fechaHora, trayecto, avion));
  }

  public Vuelo get(int index) {
    return vuelos.get(index);
  }

  /**
   * Este metodo obtiene un vuelo del arraylist de vuelos
   * 
   * @param kindex indice en el que se va a buscar el vuelo
   * @return retorna el vuelo si lo encuentra, de lo contrario retorna null
   */
  public Vuelo get(Vuelo v) {
    int index = vuelos.indexOf(v);
    return index > -1 ? vuelos.get(index) : null;
  }

  /**
   * Este metodo obtiene la lista de vuelos
   * 
   * @return retorna el arralist de vuelos
   */
  public ArrayList<Vuelo> getList() {
    return this.vuelos;
  }

  /**
   * Este metodo verifica la lista de vuelos contien un vuelo
   * 
   * @param vuelo que se va a verificar
   * @return retorna true si el vuelo esta en la lista, de lo contraio rentorna
   *         false
   */
  public boolean contains(Vuelo vuelo) {
    return this.vuelos.contains(vuelo);
  }

  /**
   * Este metodo muestra el tamaño del arraylist
   * 
   * @return retorna cuantos vuelos hay en el arraylist de vuelos
   */
  public int size() {
    return vuelos.size();
  }

  public JSONObject set(JSONObject json, String params) throws IOException {
    String[] parts = params.split("&");
    Trayecto trayecto = trayectos.get(new Trayecto(parts[1], parts[2], null, 0));
    Avion avion = aviones.get(new Avion(parts[3], null));
    Vuelo vuelo = this.get(new Vuelo(LocalDateTime.parse(parts[0]), trayecto, avion));
    int index = vuelos.indexOf(vuelo);

    if (json.has("origen") && json.has("destino") && json.has("avion")) {
      Trayecto trayectoUpdate = trayectos
          .get(new Trayecto(json.getString("origen"), json.getString("destino"), null, 0));
      Avion avionUpdate = aviones.get(new Avion(json.getString("avion"), null));
      vuelo.setAvion(avionUpdate);
      vuelo.setCancelado(json.getBoolean("cancelado"));
      vuelo.setTrayecto(trayectoUpdate);
      vuelo.setDateTime(LocalDateTime.parse(json.getString("fechaHora")));
    }

    vuelos.set(index, vuelo);
    UtilFiles.writeData(vuelos, fileName);
    return new JSONObject(vuelo);
  }

  public JSONObject getVuelo(String params) {
    String[] parts = params.split("&");
    LocalDateTime fechaHoraVuelo = LocalDateTime.parse(parts[0]);
    Trayecto trayectoSearched = trayectos.get(new Trayecto(parts[1], parts[2], Duration.ZERO, 0));
    Avion avionSearched = aviones.get(new Avion(parts[3], null));
    Vuelo vueloSearched = this.get(new Vuelo(fechaHoraVuelo, trayectoSearched, avionSearched));
    return new JSONObject(vueloSearched);
  }

  public JSONObject get(String paramsVuelo) throws IOException {

    JSONObject json = UtilFiles.paramsToJson(paramsVuelo);

    Avion avion = aviones.get(new Avion(json.getString("avion"), ""));

    Trayecto trayecto = trayectos.get(
        new Trayecto(
            json.getString("origen"), json.getString("destino"), Duration.ZERO, 0.0));

    LocalDateTime fechaHora = LocalDateTime.parse(json.getString("fechaHora"));
    return getJSON(new Vuelo(fechaHora, trayecto, avion));
  }

  public void update() throws IOException {
    vuelos = new ArrayList<>();
    loadCSV();
    UtilFiles.writeJSON(vuelos, fileName + ".json");
  }

  public void remove(String params) throws Exception {
    String[] parts = params.split("&");
    Avion avion = aviones.get(new Avion(parts[3], null));
    Trayecto trayecto = trayectos.get(
        new Trayecto(
            parts[1], parts[2], Duration.ZERO, 0.0));
    LocalDateTime fechaHora = LocalDateTime.parse(parts[0]);

    Vuelo vuelo = this.get(new Vuelo(fechaHora, trayecto, avion));

    if (UtilFiles.exists(UtilFiles.FILE_PATH + "vuelos-reservas", "vuelo", vuelo)) {
      throw new Exception(String.format(
          "No se eliminó el vuelo reservado, porque el vuelo %s existe", vuelo));
    }

    if (!vuelos.remove(vuelo)) {
      throw new Exception("No se encontró el vuelo");
    }

    UtilFiles.writeData(vuelos, fileName);
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
        LocalDateTime fechaHora = LocalDateTime.parse(sc.next());
        String origen = sc.next();
        String destino = sc.next();
        String matricula = sc.next();

        for (Trayecto trayecto : trayectos.getList()) {
          if (trayecto.getDestino().equals(destino) && trayecto.getOrigen().equals(origen)) {
            for (Avion avion : aviones.getList()) {
              if (avion.getMatricula().equals(matricula)) {
                vuelos.add(new Vuelo(fechaHora, trayecto, avion));
              }
            }
          }
        }

        sc.nextLine();
      }
    }
  }

  public ArrayList<Vuelo> loadJSON() throws IOException {
    vuelos = new ArrayList<>();
    String data = UtilFiles.readText(fileName + ".json");
    JSONArray jsonArr = new JSONArray(data);

    for (int i = 0; i < jsonArr.length(); i++) {
      JSONObject jsonObj = jsonArr.getJSONObject(i);
      vuelos.add(new Vuelo(jsonObj));
    }

    return vuelos;
  }

  @SuppressWarnings("unchecked") // ver esto…
  public void listAll() throws Exception {
    ArrayList<String> list = (ArrayList<String>) (ArrayList<?>) (vuelos);
    int length = 72;
    String title = "LISTADO DE VUELOS";

    list.add(0, " ".repeat((length - title.length()) / 2) + title);
    list.add(1, "-".repeat(length));
    list.add(2, "MATR.   ORIGEN         DESTINO            PRECIO  DUR.  FECHA Y HORA");
    list.add(3, "-".repeat(length));
    list.add("-".repeat(length));

    UtilFiles.writeText(list, fileName + ".txt");
  }

  public JSONArray getJSON() throws IOException {
    return new JSONArray(UtilFiles.readText(fileName + ".json"));
  }

  public JSONObject getJSON(int index) {
    return new JSONObject(vuelos.get(index));
  }

  public JSONObject getJSON(Vuelo vuelo) {
    int index = vuelos.indexOf(vuelo);
    return index > -1 ? getJSON(index) : null;
  }
}
