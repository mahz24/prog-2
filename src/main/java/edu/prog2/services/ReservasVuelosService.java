package edu.prog2.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.models.Reserva;
import edu.prog2.models.ReservaVuelo;
import edu.prog2.models.Silla;
import edu.prog2.models.Vuelo;

public class ReservasVuelosService {
  private ArrayList<ReservaVuelo> reservasVuelos;
  private ReservasService reservas;
  private VuelosService vuelos;
  private SillasService sillas;
  private String fileName;
  private Reserva r;
  private Vuelo v;
  private Silla s;

  /**
   * Constructor principal del controlador de las reservas en vuelo
   */
  public ReservasVuelosService(ReservasService reservas, VuelosService vuelos, SillasService sillas)
      throws IOException {
    reservasVuelos = new ArrayList<>();
    this.reservas = reservas;
    this.vuelos = vuelos;
    this.sillas = sillas;
    fileName = UtilFiles.FILE_PATH + "vuelos-de-reservas";

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
        String identificacion = sc.next();
        String origen = sc.next();
        String destino = sc.next();
        String matircula = sc.next();
        int fila = sc.nextInt();
        char columna = sc.next().charAt(0);
        Boolean disponible = sc.nextBoolean();

        for (Reserva reserva : reservas.getList()) {
          if (reserva.getFechaHora() == fechaHora && reserva.getPasajero().getIdentificacion() == identificacion) {
            r = new Reserva(reserva);
          }
        }
        for (Vuelo vuelo : vuelos.getList()) {
          if (vuelo.getTrayecto().getDestino() == destino && vuelo.getTrayecto().getOrigen() == origen
              && vuelo.getAvion().getMatricula() == matircula) {
            v = new Vuelo(vuelo);
          }
        }
        for (Silla silla : sillas.getList()) {
          if (silla.getFila() == fila && silla.getColumna() == columna && silla.getDisponible() == disponible) {
            s = new Silla(silla);
          }
        }

        reservasVuelos.add(new ReservaVuelo(r, v, s));
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

  public String getJSON() throws IOException {
    return UtilFiles.readText(fileName + ".json");
  }

  public String getJSON(int index) {
    return new JSONObject(reservasVuelos.get(index)).toString(2);
  }

  public String getJSON(ReservaVuelo rs) {
    int index = reservasVuelos.indexOf(rs);
    return index > -1 ? getJSON(index) : null;
  }
}
