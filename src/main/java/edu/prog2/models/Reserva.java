package edu.prog2.models;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class Reserva implements IFormatCSV {
  private LocalDateTime fechaHora;
  private Boolean cancelada;
  private Pasajero pasajero;

  public Reserva() {
  }

  public Reserva(LocalDateTime fechaHora, Boolean cancelada, Pasajero pasajero) {
    this.fechaHora = fechaHora;
    this.cancelada = cancelada;
    this.pasajero = pasajero;
  }

  public Reserva(JSONObject json) {
    this(
        LocalDateTime.parse(json.getString("fechaHora")), json.getBoolean("cancelada"),
        new Pasajero(json.getJSONObject("pasajero")));

  }

  public Reserva(Reserva r) {
    this(r.fechaHora, r.cancelada, r.pasajero);
  }

  /**
   * Este metodo devuelve la hora y fecha en la que se hizo la reserva
   * 
   * @return Fecha y hora en la que se hizo la reserva
   */
  public LocalDateTime getFechaHora() {
    return this.fechaHora;
  }

  /**
   * Este metodo informa si la reserva fué cancelada o no.
   * 
   * @return Booleno informando si la reserva fue cancelada o no
   */
  public Boolean getCancelada() {
    return this.cancelada;
  }

  /**
   * Este metodo sirve para cambiar el estado de la reserva
   * 
   * @param c Booleanao por el cual será cambiado el valor
   */
  public void setCancelada(Boolean c) {
    this.cancelada = c;
  }

  /**
   * Este metodo permite cambiar la hora y la fecha en la que se realizo la
   * reserva
   * 
   * @param f Fecha y hora por la cual se va a cambiar
   */
  public void setFechaHora(LocalDateTime f) {
    this.fechaHora = f;
  }

  /**
   * Este metodo retorna el pasajero que esta relacionado con la reserva
   * 
   * @return Retorna el pasajero que esta relacionado
   */
  public Pasajero getPasajero() {
    return this.pasajero;
  }

  /**
   * Este metodo cambia el pasajero de ra reserva por el que se pide por
   * parametros
   * 
   * @param pasajero que reemplazará
   */
  public void setPasajero(Pasajero pasajero) {
    this.pasajero = pasajero;
  }

  @Override
  public String toString() {
    return String.format("%s - %s - %s%n", fechaHora, cancelada, pasajero.getIdentificacion());
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Reserva)) {
      return false;
    }
    Reserva reserva = (Reserva) object;
    return fechaHora.equals(reserva.fechaHora);
  }

  @Override
  public String toCSV() {
    return String.format("%s;%s;%s%n", fechaHora, cancelada, pasajero.getIdentificacion());
  }
}
