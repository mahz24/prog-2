package edu.prog2.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.models.Pasajero;
import edu.prog2.models.Reserva;

public class ReservasService {
  ArrayList<Reserva> reservas;
  private String fileName;
  private PasajerosService pasajeros;
  private Pasajero p;

  /**
   * Constructor principal del controlador de reservas
   */
  public ReservasService(PasajerosService pasajeros) throws IOException {
    this.pasajeros = pasajeros;
    reservas = new ArrayList<>();
    fileName = UtilFiles.FILE_PATH + "reservas";

    if (UtilFiles.fileExists(fileName + ".csv")) {
      loadCSV();
    } else if (UtilFiles.fileExists(fileName + ".csv")) {
      loadJSON();
    } else {
      System.out.println("Aún no se ha creado un archivo: " + fileName);
    }
  }

  /**
   * Este metodo añade una instancia de reserva al arraylist de reservas
   * 
   * @param reserva que se va a añadir al arraylist
   * @return devuelve un true si de añade la instancia de reserva, de lo contrario
   *         devuelve un false
   */
  public boolean add(Reserva reserva) throws IOException {

    if (contains(reserva)) {
      throw new IOException(
          String.format("La reserva ya esta del pasajero %s", reserva.getPasajero().getIdentificacion()));
    }
    boolean ok = reservas.add(reserva);
    UtilFiles.writeData(reservas, fileName);
    return ok;
  }

  public Reserva get(int index) {
    return reservas.get(index);
  }

  /**
   * Este metodo busca una reserva en el arraylist y la devuelve
   * 
   * @param reserva que se va a obtener del arraylist
   * @return devuelve la reserva si la encuentra, de lo contrario retorna un null
   */
  public Reserva get(Reserva r) {
    int index = reservas.indexOf(r);
    return index > -1 ? reservas.get(index) : null;
  }

  /**
   * Este metodo devuelve el arraylist de reservas
   *
   * @return retorna el arraylist de reservas
   */
  public ArrayList<Reserva> getList() {
    return this.reservas;
  }

  /**
   * Este metodo verifica si una reserva ya esta en el arraylist de reservas
   * 
   * @param reserva a la que se le va a realizar la verificación
   * @return devuelve un true si la reserva esta en el arraylist, de lo contrario
   *         devuelve un false
   */
  public boolean contains(Reserva reserva) {
    return this.reservas.contains(reserva);
  }

  /**
   * Este metodo devuelve el tamaño del ArrayList de reservas
   * 
   * @return int que representa el tamaño del ArrayList
   */
  public int size() {
    return reservas.size();
  }

  public JSONObject get(String params) {
    String[] parts = params.split("&");
    LocalDateTime fechaHoraReserva = LocalDateTime.parse(parts[0]);
    Pasajero pasajeroReserva = pasajeros.get(new Pasajero(parts[1], null, null));
    Reserva reservaSearched = this.get(new Reserva(fechaHoraReserva, null, pasajeroReserva));
    return new JSONObject(reservaSearched);
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
        boolean cancelada = sc.nextBoolean();
        String identificacion = sc.next();

        for (Pasajero pasajero : pasajeros.getList()) {
          if (pasajero.getIdentificacion().equals(identificacion)) {
            p = new Pasajero(pasajero);
          }
        }
        reservas.add(new Reserva(fechaHora, cancelada, p));
        sc.nextLine();
      }
    }
  }

  public ArrayList<Reserva> loadJSON() throws IOException {
    reservas = new ArrayList<>();
    String data = UtilFiles.readText(fileName + ".json");
    JSONArray jsonArr = new JSONArray(data);

    for (int i = 0; i < jsonArr.length(); i++) {
      JSONObject jsonObj = jsonArr.getJSONObject(i);
      reservas.add(new Reserva(jsonObj));
    }

    return reservas;
  }

  public JSONArray getJSON() throws IOException {
    return new JSONArray(UtilFiles.readText(fileName + ".json"));
  }

  public String getJSON(int index) {
    return new JSONObject(reservas.get(index)).toString(2);
  }

  public String getJSON(Reserva reserva) {
    int index = reservas.indexOf(reserva);
    return index > -1 ? getJSON(index) : null;
  }
}
