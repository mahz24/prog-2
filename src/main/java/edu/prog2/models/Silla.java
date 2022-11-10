package edu.prog2.models;

import org.json.JSONObject;

public class Silla implements IFormatCSV {

  protected int fila;
  protected char columna;
  protected Avion avion;
  protected Ubicacion ubicacion;
  protected Boolean disponible;

  public Silla() {
  }

  public Silla(int fila, char columna, Avion avion) {
    if (columna == 'A' || columna == 'F') {
      this.ubicacion = Ubicacion.VENTANA;
    } else if (columna == 'B' || columna == 'E') {
      this.ubicacion = Ubicacion.CENTRO;
    } else if (columna == 'C' || columna == 'D') {
      this.ubicacion = Ubicacion.PASILLO;
    } else {
      try {
        throw new Exception("columna invalida");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    this.avion = avion;
    this.fila = fila;
    this.disponible = true;
    this.columna = columna;
  }

  public Silla(JSONObject json) {
    this(
        json.getInt("fila"),
        json.getString("columna").charAt(0),
        new Avion(json.getJSONObject("avion")));
    this.disponible = json.getBoolean("disponible");
    this.ubicacion = json.getEnum(Ubicacion.class, "ubicacion");
  }

  public Silla(Silla s) {
    this(s.fila, s.columna, s.avion);
  }

  /**
   * Este metodo permite obtener la fila en la que esta ubicada la silla
   * 
   * @return Int que indica el índica la fila en la que se encuentra la silla
   */
  public int getFila() {
    return this.fila;
  }

  /**
   * Este metodo permite cambiar la fila en la que se encuentra la silla
   * 
   * @param fila Int en la que se va a encontrar la silla
   */
  public void setFila(int fila) {
    this.fila = fila;
  }

  /**
   * Este metodo permite obtener la columna en la que esta ubicada la silla
   * 
   * @return Char que indica el índica la columna en la que se encuentra la silla
   */
  public char getColumna() {
    return this.columna;
  }

  /**
   * Este metodo permite cambiar la columna en la cual esta la silla
   * 
   * @param columna Columna en la cual se va a encontrar la silla
   */
  public void setColumna(char columna) {
    this.columna = columna;
  }

  /**
   * Este metodo permite obtener el avión en el que esta ubicada la silla
   * 
   * @return Avion en el cual esta la silla
   */
  public Avion getAvion() {
    return this.avion;
  }

  /**
   * Este metodo permite cambiar el avión en el cual se encuentra la silla
   * 
   * @param avion Avión el cual se va a cambiar la silla
   */
  public void setAvion(Avion avion) {
    this.avion = avion;
  }

  /**
   * Este metodo permite saber la ubicvacion de la silla en el avion
   * 
   * @return Devuelve el lugar en el que esta ubicada la silla
   */
  public Ubicacion getUbicacion() {
    return this.ubicacion;
  }

  /**
   * Permite setear la ubicación de la silla
   * 
   * @param ubicacion Lugar por el cual se va a cambiar el lugar de la silla
   */
  public void setUbication(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }

  /**
   * Este metodo permite saber si la silla estadisponible o no
   * 
   * @return Boolean que índica si la silla esta disponible o no
   */
  public Boolean getDisponible() {
    return this.disponible;
  }

  /**
   * Permite setiar la disponibilidad de la silla
   * 
   * @param dispo Boolean por el cual se va a setear la disponibilidad
   */
  public void setDisponible(Boolean dispo) {
    this.disponible = dispo;
  }

  /**
   * Este metodo devuelve un String que nos dice la posición exacta de la silla
   * 
   * @return String posición de la silla
   */
  public String getPosicion() {
    return String.format("%02d%c", fila, columna);
  }

  @Override
  public String toString() {
    return String.format("%-10s%-10s%-5s", getPosicion(), getUbicacion(), getDisponible());
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Silla)) {
      return false;
    }

    Silla silla = (Silla) object;
    return fila == silla.getFila() && columna == silla.getColumna() && avion.equals(silla.getAvion());
  }

  @Override
  public String toCSV() {
    return String.format("%s;%s;%s;%s;%s%n", getAvion().getMatricula(), getFila(), getColumna(), getUbicacion(),
        getDisponible());
  }
}
