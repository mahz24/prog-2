package edu.prog2.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.models.Avion;

public class AvionesService {
  ArrayList<Avion> aviones;
  private String fileName;

  /**
   * Controlador parametrizado del controlador de aviones
   * 
   * @throws IOException
   */
  public AvionesService() throws IOException {
    aviones = new ArrayList<>();
    fileName = UtilFiles.FILE_PATH + "aviones";

    if (UtilFiles.fileExists(fileName + ".csv")) {
      loadCSV();
    } else if (UtilFiles.fileExists(fileName + ".json")) {
      loadJSON();
    } else {
      System.out.println("Aún no se ha creado un archivo: " + fileName);
    }
  }

  /**
   * Este metodo añade una instancia de Avion al arralist
   * 
   * @param avion que se va a añadir
   * @return Devuelve un true si se añade el avión de lo contrario un false
   * @throws IOException
   */
  public boolean add(Avion avion) throws IOException {
    // "No agregado, el avion %s ya existe%n");
    if (contains(avion)) {
      throw new IOException(
          String.format("No agregado, el avion %s ya existe", avion.getMatricula()));
    }
    boolean ok = aviones.add(avion);
    UtilFiles.writeData(aviones, fileName);
    return ok;
  }

  public Avion get(int index) {
    return aviones.get(index);
  }

  /**
   * Este metodo obtiene un avion
   * 
   * @param avion que se va a devolver
   * @return una instancia de avion
   */
  public Avion get(Avion avion) {
    int index = aviones.indexOf(avion);
    return index > -1 ? aviones.get(index) : null;
  }

  /**
   * Este metodo devuelve el arreglo de aviones
   * 
   * @return lista de aviones
   */
  public ArrayList<Avion> getList() {
    return this.aviones;

  }

  /**
   * Este metodo verifica si un avion ya esta en la lista
   * 
   * @param avion que se va a verificar
   * @return devuelve un true si el avión esta en la lista de lo contrario
   *         devuelve un false
   */
  public boolean contains(Avion avion) {
    return this.aviones.contains(avion);
  }

  /**
   * Este metodo devuelve el tamaño del arrayList de aviones
   * 
   * @return
   */
  public int size() {
    return aviones.size();
  }

  public JSONObject get(String matricula) {
    Avion avionSearched = this.get(new Avion(matricula, null));
    return new JSONObject(avionSearched);
  }

  public JSONObject set(String matricula, JSONObject json) throws IOException {
    Avion avion = new Avion(matricula, null);
    int index = aviones.indexOf(avion);
    avion.setMatricula(json.getString("matricula"));
    avion.setModelo(json.getString("modelo"));
    aviones.set(index, avion);
    UtilFiles.writeData(aviones, fileName);
    return new JSONObject(avion);
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
        String matricula = sc.next();
        String modelo = sc.next();
        aviones.add(new Avion(matricula, modelo));
        sc.nextLine();
      }
    }
  }

  public ArrayList<Avion> loadJSON() throws IOException {
    aviones = new ArrayList<>();
    String data = UtilFiles.readText(fileName + ".json");
    JSONArray jsonArr = new JSONArray(data);

    for (int i = 0; i < jsonArr.length(); i++) {
      JSONObject jsonObj = jsonArr.getJSONObject(i);
      aviones.add(new Avion(jsonObj));
    }

    return aviones;
  }

  public JSONArray getJSON() throws IOException {
    return new JSONArray(UtilFiles.readText(fileName + ".json"));
  }

  public JSONObject getJSON(int index) {
    return new JSONObject(aviones.get(index));
  }

  public JSONObject getJSON(Avion avion) {
    int index = aviones.indexOf(avion);
    return index > -1 ? getJSON(index) : null;
  }
}
