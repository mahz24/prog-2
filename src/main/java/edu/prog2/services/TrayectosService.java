package edu.prog2.services;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.models.Trayecto;;

public class TrayectosService {
  ArrayList<Trayecto> trayectos;
  private String fileName;

  /**
   * Contructor principal del controlador de los trayectos
   */
  public TrayectosService() throws IOException {
    trayectos = new ArrayList<>();
    fileName = UtilFiles.FILE_PATH + "trayectos";

    if (UtilFiles.fileExists(fileName + ".csv")) {
      loadCSV();
    } else {
      System.out.println("Aún no se ha creado un archivo: " + fileName);
    }
  }

  /**
   * Este metodo añade una nueva instancia de Trayecto al arraylist de trayectos
   * 
   * @param trayecto que se va a añadir al arraylist de trayectos
   * @return retorna true si se añade el avion, de lo contrario retorna un false
   * @throws IOException
   */
  public boolean add(Trayecto trayecto) throws IOException {
    if (contains(trayecto)) {

      throw new IOException(
          String.format("No agregado, el trayecto %s-%s ya existe", trayecto.getOrigen(), trayecto.getDestino()));
    }

    boolean ok = trayectos.add(trayecto);
    UtilFiles.writeData(trayectos, fileName);
    return ok;
  }

  public Trayecto get(int index) {
    return trayectos.get(index);
  }

  /**
   * Este metodo obtiene una instancia de Trayecto que se encuentra en el
   * arraylist de trayectos
   * 
   * @param index indice en el que se encuentra el trayecto
   * @return retorna el trayecto si lo tiene, de lo contrario retorna null
   */
  public Trayecto get(Trayecto t) {
    int index = trayectos.indexOf(t);
    return index > -1 ? trayectos.get(index) : null;
  }

  /**
   * Este metodo obtiene el arraylist de trayectos
   * 
   * @return retorna el arraylist de trayectos
   */
  public ArrayList<Trayecto> getList() {
    return this.trayectos;
  }

  /**
   * Este metodo verifica si una instancia de Trayecto se encuentra en el
   * arraylist de trayectos
   * 
   * @param trayecto que se va a verificar
   * @return retorna true si el trayecto esta en el arraylist de trayectos, de lo
   *         contrario retorna un false
   */
  public boolean contains(Trayecto trayecto) {
    return this.trayectos.contains(trayecto);
  }

  /**
   * Este metodo devuelve el tamaño del ArrayList de reservasVuelos
   * 
   * @return int que representa el tamaño del ArrayList
   */
  public int size() {
    return trayectos.size();
  }

  public JSONObject get(String params) {
    String[] parts = params.split("&");
    Trayecto trayectoSearched = this.get(new Trayecto(parts[0], parts[1], Duration.ZERO, 0));
    return new JSONObject(trayectoSearched);
  }

  public JSONObject set(JSONObject json, String params) throws IOException {
    String[] parts = params.split("&");
    Trayecto trayecto = new Trayecto(
        parts[0], parts[1], Duration.ZERO, 0.0);
    trayecto = get(trayecto);
    int index = trayectos.indexOf(trayecto);
    trayecto.setDestino(json.getString("destino"));
    trayecto.setOrigen(json.getString("origen"));
    trayecto.setCosto(json.getDouble("costo"));
    trayecto.setDuration(Duration.parse(json.getString("duracion")));
    trayectos.set(index, trayecto);
    UtilFiles.writeData(trayectos, fileName);
    return new JSONObject(trayecto);
  }

  /**
   * Este metodo añade los datos del trayecto al archivo .csv en la carpeta data
   * 
   * @throws IOException
   */
  public void loadCSV() throws IOException {
    String text = UtilFiles.readText(fileName + ".csv");

    try (Scanner sc = new Scanner(text).useDelimiter(";|[\n]+|[\r\n]+")) {
      while (sc.hasNext()) {
        String origen = sc.next();
        String destino = sc.next();
        Duration duration = Duration.parse(sc.next());
        Double costo = sc.nextDouble();
        trayectos.add(new Trayecto(origen, destino, duration, costo));
        sc.nextLine();
      }
    }
  }

  public ArrayList<Trayecto> loadJSON() throws IOException {
    trayectos = new ArrayList<>();
    String data = UtilFiles.readText(fileName + ".json");
    JSONArray jsonArr = new JSONArray(data);

    for (int i = 0; i < jsonArr.length(); i++) {
      JSONObject jsonObj = jsonArr.getJSONObject(i);
      trayectos.add(new Trayecto(jsonObj));
    }

    return trayectos;
  }

  public JSONArray getJSON() throws IOException {
    return new JSONArray(UtilFiles.readText(fileName + ".json"));
  }

  public JSONObject getJSON(int index) {
    return new JSONObject(trayectos.get(index));
  }

  public JSONObject getJSON(Trayecto trayecto) {
    int index = trayectos.indexOf(trayecto);
    return index > -1 ? getJSON(index) : null;
  }

  @SuppressWarnings("unchecked") // ver esto…
  public void listAll() throws Exception {
    ArrayList<String> list = (ArrayList<String>) (ArrayList<?>) (trayectos);
    int length = 72;
    String title = "LISTADO DE TRAYECTOS";

    list.add(0, " ".repeat((length - title.length()) / 2) + title);
    list.add(1, "-".repeat(length));
    list.add(2, "ORIGEN         DESTINO        PRECIO  DUR.  ");
    list.add(3, "-".repeat(length));
    list.add("-".repeat(length));

    UtilFiles.writeText(list, fileName + ".txt");
  }
}
