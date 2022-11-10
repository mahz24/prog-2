package edu.prog2.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class Vuelo implements IFormatCSV {
  private LocalDateTime fechaHora;
  private Trayecto trayecto;
  private Avion avion;
  private boolean cancelado;

  public Vuelo() {
  }

  public Vuelo(LocalDateTime fechaHora, Trayecto trayecto, Avion avion) {
    this.fechaHora = fechaHora;
    this.trayecto = new Trayecto(trayecto);
    this.avion = new Avion(avion);
  }

  public Vuelo(JSONObject json) {
    this(
        LocalDateTime.parse(json.getString("fechaHora")),
        new Trayecto(json.getJSONObject("trayecto")),
        new Avion(json.getJSONObject("avion")));
    this.cancelado = json.getBoolean("cancelado");
  }

  public Vuelo(Vuelo v) {
    this(v.fechaHora, v.trayecto, v.avion);
  }

  /**
   * Este metodo permite obtener la fecha y hora del vuelo
   * 
   * @return La fecha y hora del vuelo
   */
  public LocalDateTime getDateTime() {
    return this.fechaHora;
  }

  /**
   * Este metodo permite cambiar la fecha y la hora del vuelo
   * 
   * @param l fecha y hora por la que se va a cambiar
   */
  public void setDateTime(LocalDateTime l) {
    this.fechaHora = l;
  }

  /**
   * Este metodo obtiene el trayecto que realizará el vuelo
   * 
   * @return El trayecto que realizará el vuelo
   */
  public Trayecto getTrayecto() {
    return this.trayecto;
  }

  /**
   * Este metodo permite cambiar el trayecto que va a realizar el vuelo
   * 
   * @param t Trayecto que se va a realizar
   */
  public void setTrayecto(Trayecto t) {
    this.trayecto = t;
  }

  /**
   * Este metodo obtiene el avión que va a realizar el vuelo
   * 
   * @return Avión que va a realizar el vuelo
   */
  public Avion getAvion() {
    return this.avion;
  }

  /**
   * Este metodo asignar otro avión para el vuelo
   * 
   * @param a Avión que reemplazará
   */
  public void setAvion(Avion a) {
    this.avion = a;
  }

  public boolean getCancelado() {
    return this.cancelado;
  }

  public void setCancelado(boolean c) {
    this.cancelado = c;
  }

  public String strFechaHora() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return fechaHora.format(formatter);
  }

  @Override
  public String toString() {
    System.out.println(this.trayecto);
    return String.format("%-10s%-20s%s%n", avion.getMatricula(), this.trayecto, strFechaHora());
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Vuelo)) {
      return false;
    }

    Vuelo vuelo = (Vuelo) object;
    return fechaHora.equals(vuelo.getDateTime()) && avion.equals(vuelo.getAvion())
        && trayecto.equals(vuelo.getTrayecto());
  }

  @Override
  public String toCSV() {
    return String.format("%s;%s;%s;%s%n", this.fechaHora, trayecto.getOrigen(), trayecto.getDestino(),
        avion.getMatricula());
  }
}
