package edu.prog2.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.models.Pasajero;

public class PasajerosService {
  ArrayList<Pasajero> pasajeros;
  private String fileName;

  /**
   * Constructor parametrizado del controlador de pasajeros
   * 
   * @throws IOException
   */
  public PasajerosService() throws IOException {
    pasajeros = new ArrayList<>();
    fileName = UtilFiles.FILE_PATH + "pasajeros";

    if (UtilFiles.fileExists(fileName + ".csv")) {
      loadCSV();
    } else if (UtilFiles.fileExists(fileName + ".json")) {
      loadJSON();
    } else {
      System.out.println("Aún no se ha creado un archivo: " + fileName);
    }

  }

  /**
   * Este metodo añade una instancia de pasajero al arraylist de pasajeros
   * 
   * @param pasajero que se va a añadir
   * @return devuelve un true si se añadión al pasajero, de lo contrario devuelve
   *         un false
   * @throws IOException
   */
  public boolean add(Pasajero pasajero) throws IOException {
    if (contains(pasajero)) {
      throw new IOException(
          String.format(
              "No agregado, el pasajero %s ya existe",
              pasajero.getIdentificacion()));
    }

    boolean ok = pasajeros.add(pasajero);
    UtilFiles.writeData(pasajeros, fileName);
    return ok;
  }

  public Pasajero get(int index) {
    return pasajeros.get(index);
  }

  /**
   * Este metodo busca un pasajero y si esta en el arraylist lo devuelve
   * 
   * @param p que se va a buscar
   * @return devuelve al pasajero si lo encuentra de lo contrario devuelve un null
   */
  public Pasajero get(Pasajero p) {
    int index = pasajeros.indexOf(p);
    return index > -1 ? pasajeros.get(index) : null;
  }

  /**
   * Este metodo devuelve un array de pasajeros
   * 
   * @return devuelve el array con los pasajeros que tiene
   */
  public ArrayList<Pasajero> getList() {
    return pasajeros;
  }

  /**
   * Este metodo verifica si el pasajero esta en el arraylist de pasajeros
   * 
   * @param pasajero que se va a verificar
   * @return devuelve true si el pasajero esta en el arraylist, de lo contrario
   *         devuelve un falsa
   */
  public boolean contains(Pasajero pasajero) {
    return this.pasajeros.contains(pasajero);
  }

  /**
   * Este metodo devuelve el tamaño del arraylist de pasajeros
   * 
   * @return numero de pasajeros que hay en el arraylist de pasajeros
   */
  public int size() {
    return pasajeros.size();
  }

  public JSONObject get(String id) {
    Pasajero pasajeroSearched = this.get(new Pasajero(id, null, null));
    return new JSONObject(pasajeroSearched);
  }

  public JSONObject set(String identificacion, JSONObject json) throws IOException {
    Pasajero pasajero = new Pasajero(json);
    pasajero.setIdentificacion(identificacion);
    int index = pasajeros.indexOf(pasajero);
    pasajeros.set(index, pasajero);
    UtilFiles.writeData(pasajeros, fileName);
    return new JSONObject(pasajero);
  }

  public void remove(String identificacion) throws Exception {
    Pasajero pasajero = this.get(new Pasajero(identificacion, "", ""));
    if (UtilFiles.exists(UtilFiles.FILE_PATH + "reservas", "pasajero", pasajero)) {
      throw new Exception(String.format(
          "No se eliminó el pasajero %s, porque tiene reservas", identificacion));
    }
    if (!pasajeros.remove(pasajero)) {
      throw new Exception(String.format(
          "No se encontró el pasajero con identificación %s", identificacion));
    }

    UtilFiles.writeData(pasajeros, fileName);
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
        String identificacion = sc.next();
        String nombres = sc.next();
        String apellidos = sc.next();
        pasajeros.add(new Pasajero(identificacion, nombres, apellidos));
        sc.nextLine();
      }
    }
  }

  public ArrayList<Pasajero> loadJSON() throws IOException {
    pasajeros = new ArrayList<>();
    String data = UtilFiles.readText(fileName + ".json");
    JSONArray jsonArr = new JSONArray(data);

    for (int i = 0; i < jsonArr.length(); i++) {
      JSONObject jsonObj = jsonArr.getJSONObject(i);
      pasajeros.add(new Pasajero(jsonObj));
    }

    return pasajeros;
  }

  @SuppressWarnings("unchecked") // ver esto…
  public void listAll() throws Exception {
    ArrayList<String> list = (ArrayList<String>) (ArrayList<?>) (pasajeros);
    int length = 72;
    String title = "LISTADO DE VUELOS";

    list.add(0, " ".repeat((length - title.length()) / 2) + title);
    list.add(1, "-".repeat(length));
    list.add(2, "IDENTIFICACIÓN   NOMBRES           APELLIDOS");
    list.add(3, "-".repeat(length));
    list.add("-".repeat(length));

    UtilFiles.writeText(list, fileName + ".txt");
  }

  public JSONArray getJSON() throws IOException {
    return new JSONArray(UtilFiles.readText(fileName + ".json"));

  }

  public JSONObject getJSON(int index) {
    return new JSONObject(pasajeros.get(index));
  }

  public JSONObject getJSON(Pasajero pasajero) {
    int index = pasajeros.indexOf(pasajero);
    return index > -1 ? getJSON(index) : null;
  }
}
