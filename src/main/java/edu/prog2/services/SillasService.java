package edu.prog2.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.models.Avion;
import edu.prog2.models.Licor;
import edu.prog2.models.Menu;
import edu.prog2.models.Silla;
import edu.prog2.models.SillaEjecutiva;

public class SillasService {
  ArrayList<Silla> sillas;
  private AvionesService aviones;
  private String fileName;

  /**
   * Constructor princiopal del controlador de las sillas
   */
  public SillasService(AvionesService aviones) throws IOException {
    sillas = new ArrayList<>();
    this.aviones = aviones;
    fileName = UtilFiles.FILE_PATH + "sillas";

    if (UtilFiles.fileExists(fileName + ".csv")) {
      loadCSV();
    } else if (UtilFiles.fileExists(fileName + ".json")) {
      loadCSV();
    } else {
      System.out.println("Aún no se ha creado un archivo: " + fileName);
    }
  }

  /**
   * Este metodo añade una instancia de silla al arraylist de sillas
   * 
   * @param silla que se va añadir al arrayist
   * @return retorna true si se añade la silla, de lo contrario retorna un false
   * @throws IOException
   */
  public boolean add(Silla silla) throws IOException {
    if (contains(silla)) {
      throw new IOException(
          String.format("No agregada, la silla %s ya existe%n", silla.getPosicion()));
    }

    boolean ok = sillas.add(silla);
    return ok;
  }

  public Silla get(int index) {
    return sillas.get(index);
  }

  /**
   * Este metodo obtiene una silla del arraylist de sillas
   * 
   * @param silla que se va a buscar en el arralylist
   * @return retorna la silla si la encuentra, de lo contrario retorna un false
   */
  public Silla get(Silla s) {
    int index = sillas.indexOf(s);
    return index > -1 ? sillas.get(index) : null;
  }

  /**
   * Este metodo obtiene el arraylist de sillas
   * 
   * @return retorna el arraylist de sillas
   */
  public ArrayList<Silla> getList() {
    return this.sillas;
  }

  /**
   * Este metodo verifica si la silla esta en el arraylist de sillas
   * 
   * @param silla que se va a buscar en el arraylist
   * @return retorna true si la silla esta en el arraylist, de lo contrario
   *         retorna un false
   */
  public boolean contains(Silla silla) {
    return this.sillas.contains(silla);
  }

  public AvionesService getAviones() {
    return aviones;
  }

  public JSONObject get(String params) {
    String[] parts = params.split("&");
    Avion avionSilla = aviones.get(new Avion(parts[2], null));
    int fila = Integer.parseInt(parts[0]);
    char columna = parts[1].charAt(0);
    Silla sillaSearched = this.get(new Silla(fila, columna, avionSilla));
    return new JSONObject(sillaSearched);
  }

  /**
   * Este metodo sube un archivo de datos de tipo csv a la carpeta data en la raiz
   * 
   * @throws IOException
   */
  private void loadCSV() throws IOException {
    String linea;
    String matricula;
    int fila;
    char columna;
    try (BufferedReader archivo = Files.newBufferedReader(Paths.get(fileName + ".csv"))) {
      while ((linea = archivo.readLine()) != null) {
        String data[] = linea.split(";");

        matricula = data[0];
        fila = Integer.parseInt(data[1]);
        columna = data[2].charAt(0);
        Avion avion = aviones.get(new Avion(matricula, ""));

        if (data.length == 7) { // ejecutivas
          Menu menu = Menu.valueOf(data[5]);
          Licor licor = Licor.valueOf(data[6]);
          sillas.add(new SillaEjecutiva(fila, columna, menu, licor, avion));
        } else if (data.length == 5) { // económicas
          sillas.add(new Silla(fila, columna, avion));
        } else {
          throw new IOException("Se esperaban 5 o 7 datos por línea");
        }

      }
    }
  }

  /**
   * Este metodo rellena las sillas de un avión
   * 
   * @param avion      al que se le van a añadir las sillas
   * @param ejecutivas que hay en el avion
   * @param economicas que hay en el avion
   * @throws IOException
   */
  public void create(Avion avion, int ejecutivas, int economicas) throws IOException {
    create(avion, ejecutivas, new char[] { 'A', 'B', 'C', 'D' }, 1);
    create(avion, economicas, new char[] { 'A', 'B', 'C', 'D', 'E', 'F' },
        (ejecutivas / 4) + 1);
    UtilFiles.writeData(sillas, fileName);
    System.out.println("Sillas creadas exitosamente \n");
  }

  /**
   * Este metodo crea las sillas que va tener un avion
   * 
   * @param avion       al que se le van a crear las sillas
   * @param totalSillas número de sillas que se van a crear
   * @param columnas    colunmas que tiene el avion
   * @param inicio      fila en la que se va a comenzar a crear las sillas
   * @throws IOException
   */
  public void create(Avion avion, int totalSillas, char[] columnas, int inicio) throws IOException {
    try {
      if ((totalSillas % 4) == 0) {
        int aux = 0;
        for (int i = 0; i < totalSillas; i++) {
          sillas.add(new SillaEjecutiva(inicio, columnas[aux], Menu.INDEFINIDO, Licor.NINGUNO, avion));
          aux++;
          if (aux == 4) {
            aux = 0;
            inicio++;
          }
        }
      } else {
        if ((totalSillas % 6) == 0) {
          int aux = 0;
          for (int i = 0; i < totalSillas; i++) {
            sillas.add(new Silla(inicio, columnas[aux], avion));
            aux++;
            if (aux == 6) {
              aux = 0;
              inicio++;
            }
          }
        } else {
          throw new IndexOutOfBoundsException("Número de sillas inválido");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void create(
      String matriculaAvion, int ejecutivas, int economicas) throws IOException {
    Avion avion = aviones.get(new Avion(matriculaAvion, ""));
    create(avion, ejecutivas, economicas);
  }

  public ArrayList<Silla> loadJSON() throws IOException {
    sillas = new ArrayList<>();
    String data = UtilFiles.readText(fileName + ".json");
    JSONArray jsonArr = new JSONArray(data);

    for (int i = 0; i < jsonArr.length(); i++) {
      JSONObject jsonObj = jsonArr.getJSONObject(i);
      sillas.add(new Silla(jsonObj));
    }

    return sillas;
  }

  @SuppressWarnings("unchecked") // ver esto…
  public void listAll() throws Exception {
    ArrayList<String> list = (ArrayList<String>) (ArrayList<?>) (sillas);
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
    return new JSONObject(sillas.get(index));
  }

  public JSONObject getJSON(Silla silla) {
    int index = sillas.indexOf(silla);
    return index > -1 ? getJSON(index) : null;
  }
}
