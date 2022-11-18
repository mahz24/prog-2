package edu.prog2.models;

import java.time.Duration;

import org.json.JSONObject;

public class Trayecto implements IFormatCSV {
  private String origen;
  private String destino;
  private Double costo;
  private Duration duracion;

  public Trayecto() {
  }

  public Trayecto(String origen, String destino, Duration duracion, double costo) {
    this.costo = costo;
    this.destino = destino;
    this.origen = origen;
    this.duracion = duracion;
  }

  public Trayecto(JSONObject json) {
    this(
        json.getString("origen"),
        json.getString("destino"),
        Duration.parse(json.getString("duracion")),
        json.getDouble("costo"));
  }

  public Trayecto(Trayecto t) {
    this(t.origen, t.destino, t.duracion, t.costo);
  }

  /**
   * Este metodo devuelve el origén que tiene la instancia
   * 
   * @return el origén
   */
  public String getOrigen() {
    return this.origen;
  }

  /**
   * Este metodo modifica el valor del origen
   * 
   * @param o valor por el que se va a modificar el origen
   */
  public void setOrigen(String o) {
    this.origen = o;
  }

  /**
   * Este metodo devuelve el destino que tiene la instancia
   * 
   * @return el destino
   */
  public String getDestino() {
    return this.destino;
  }

  /**
   * Este metodo modifica el valor del destino
   * 
   * @param d valor por el que se va a modificar el destino
   */
  public void setDestino(String d) {
    this.destino = d;
  }

  /**
   * Este metodo devuelve el costo que tiene la instancia
   * 
   * @return el costo
   */
  public Double getCosto() {
    return this.costo;
  }

  /**
   * Este metodo setea el tiempo de duración del trayecto
   * 
   * @param d valor por el que se va a setear la duración
   */
  public void setDuration(Duration d) {
    this.duracion = d;
  }

  /**
   * Este metodo accede al valor de la duración del trayecto
   * 
   * @return Retorna la duración del trayecto
   */
  public Duration getDuration() {
    return this.duracion;
  }

  /**
   * Este metodo modifica el valor del costo
   * 
   * @param c valor por el que se va a modificar el costo
   */
  public void setCosto(Double c) {
    this.costo = c;
  }

  /**
   * Este metodo le da formato al los datos de una hora y sus minutos
   * 
   * @return Retorna un string con el formato de una hora y sus minutos
   */
  public String strDuracion() {
    long hh = duracion.toHours();
    long mm = duracion.toMinutesPart();
    return String.format("%02d:%02d", hh, mm);
  }

  @Override
  public String toString() {
    return String.format("%-15s%-15s%-10s%-10s", this.origen, this.destino, this.costo, strDuracion());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Trayecto other = (Trayecto) obj;
    if (origen == null) {
      if (other.origen != null)
        return false;
    } else if (!origen.equals(other.origen))
      return false;
    if (destino == null) {
      if (other.destino != null)
        return false;
    } else if (!destino.equals(other.destino))
      return false;
    return true;
  }

  @Override
  public String toCSV() {
    return String.format("%s;%s;%s;%s%n", this.origen, this.destino, this.duracion, this.costo);
  }
}
