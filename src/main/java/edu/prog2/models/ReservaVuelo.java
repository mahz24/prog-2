package edu.prog2.models;

import org.json.JSONObject;

public class ReservaVuelo implements IFormatCSV {
  private boolean checkIn;
  private Vuelo vuelo;
  private Reserva reserva;
  private Silla silla;

  public ReservaVuelo() {
  }

  public ReservaVuelo(Boolean checkIn) {
    this.checkIn = checkIn;
  }

  public ReservaVuelo(ReservaVuelo rv) {
    this(rv.checkIn);
  }

  public ReservaVuelo(Reserva reserva, Vuelo vuelo, Silla silla) {
    this.reserva = reserva;
    this.vuelo = vuelo;
    this.silla = silla instanceof SillaEjecutiva ? new SillaEjecutiva((SillaEjecutiva) silla) : new Silla(silla);
  }

  public ReservaVuelo(JSONObject json) {
    this(
        new Reserva(json.getJSONObject("reserva")),
        new Vuelo(json.getJSONObject("vuelo")),
        new Silla(json.getJSONObject("silla")));
    this.checkIn = json.getBoolean("checkIn");
  }

  /**
   * Este metodo permite saber el estado de la reservación
   * 
   * @return Booleano que indica el estado de la reservación
   */
  public Boolean getCheckIn() {
    return this.checkIn;
  }

  /**
   * Este metodo permite setear el estado de la reservación con relación al vuelo
   * 
   * @param n Booleano por el cual se va a setear el estado
   */
  public void setCheckIn(Boolean n) {
    this.checkIn = n;
  }

  /**
   * Este metodo obtiene la reserva que esta relacionada
   * 
   * @return Restorna la reserva a la que esta relacionada
   */
  public Reserva getReserva() {
    return this.reserva;
  }

  /**
   * Este metodo cambia el valor que tiene la reserva
   * 
   * @param reserva por la que se va a cambiar
   */
  public void setReserva(Reserva reserva) {
    this.reserva = reserva;
  }

  /**
   * Este metodo obtiene el vuelo que esta relacionado
   * 
   * @return Retorna el vuelo al que esta relacvionado
   */
  public Vuelo getVuelo() {
    return this.vuelo;
  }

  /**
   * Este metodo cambia el vuelo por el que se pasa por parametros
   * 
   * @param vuelo
   */
  public void setVuelo(Vuelo vuelo) {
    this.vuelo = vuelo;
  }

  /**
   * Este metodo obtiene la silla a la que esta relacionada
   * 
   * @return Retorna la silla relacionada
   */
  public Silla getSilla() {
    return this.silla;
  }

  /**
   * Este metodo cambia el valor que tiene la silla por la instancia que se pide
   * por parametros
   * 
   * @param silla que reemplazará
   */
  public void setSilla(Silla silla) {
    this.silla = silla;
  }

  @Override
  public String toString() {
    return String.format(
        "Fecha y hora de la reserva : %s - Estado: %s%nPasajero: %s%n     Fecha y hora: %s - Estado: %s - Checkin: %s%n     Avion: %s, Silla: %s%n",
        reserva.getFechaHora(),
        reserva.getCancelada() == false ? "Vigente" : "Cancelada", reserva.getPasajero(), reserva.getFechaHora(),
        reserva.getCancelada() == false ? "Vigente" : "Cancelada", checkIn == false ? "Pendiente" : "Concluida",
        vuelo.getAvion(), silla);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ReservaVuelo other = (ReservaVuelo) obj;
    if (vuelo == null) {
      if (other.vuelo != null)
        return false;
    } else if (!vuelo.equals(other.vuelo))
      return false;
    if (reserva == null) {
      if (other.reserva != null)
        return false;
    } else if (!reserva.equals(other.reserva))
      return false;
    if (silla == null) {
      if (other.silla != null)
        return false;
    } else if (!silla.equals(other.silla))
      return false;
    return true;
  }

  @Override
  public String toCSV() {
    return String.format("%s;%s;%s;%s;%s;%s;%s;%s%n", reserva.getFechaHora(), reserva.getPasajero().getIdentificacion(),
        vuelo.getTrayecto().getOrigen(), vuelo.getTrayecto().getDestino(), vuelo.getAvion().getMatricula(),
        silla.getFila(), silla.getColumna(), silla.getDisponible());
  }
}
