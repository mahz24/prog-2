package edu.prog2.services;

import java.io.IOException;
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
          if (trayecto.getDestino() == destino && trayecto.getOrigen() == origen) {
            for (Avion avion : aviones.getList()) {
              if (avion.getMatricula() == matricula) {
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

  public String getJSON(int index) {
    return new JSONObject(vuelos.get(index)).toString(2);
  }

  public String getJSON(Vuelo vuelo) {
    int index = vuelos.indexOf(vuelo);
    return index > -1 ? getJSON(index) : null;
  }
}
