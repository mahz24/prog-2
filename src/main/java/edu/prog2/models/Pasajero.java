package edu.prog2.models;

import org.json.JSONObject;

public class Pasajero implements IFormatCSV {
  private String identificacion;
  private String nombres;
  private String apellidos;

  public Pasajero() {
  }

  public Pasajero(String identificacion, String nombres, String apellidos) {
    this.identificacion = identificacion;
    this.nombres = nombres;
    this.apellidos = apellidos;
  }

  public Pasajero(Pasajero p) {
    this(p.identificacion, p.nombres, p.apellidos);
  }

  public Pasajero(JSONObject json) {
    this(
        json.getString("identificacion"),
        json.getString("nombres"),
        json.getString("apellidos"));
  }

  /**
   * Este metodo devuelve la identificación que tiene la instancia
   * 
   * @return la identificación del pasajero
   */
  public String getIdentificacion() {
    return identificacion;
  }

  /**
   * Este metodo modifica la identificación del pasajero
   * 
   * @param id valor por el que se va a modificar la identificación
   */
  public void setIdentificacion(String id) {
    this.identificacion = id;
  }

  /**
   * Este metodo devuelve el nombre que tiene la instancia
   * 
   * @return el nombre del pasajero
   */
  public String getNombres() {
    return nombres;
  }

  /**
   * Este metodo modifica ños nombres del pasajero
   * 
   * @param nombres valor por el que se va a modificar los nombres del pasajero
   */
  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  /**
   * Este metodo devuelve los apellidos que tiene la instancia
   * 
   * @return los apellidos del pasajero
   */
  public String getApellidos() {
    return apellidos;
  }

  /**
   * Este metodo modifica los apellidos del pasajero
   * 
   * @param d valor por el que se va a modificar los apellidos del pasajero
   */
  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  @Override
  public String toString() {
    return String.format("%-10s%-20s%s", this.identificacion, this.nombres, this.apellidos);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Pasajero)) {
      return false;
    }
    Pasajero p = (Pasajero) obj;
    return identificacion.equals(p.identificacion);

  }

  @Override
  public String toCSV() {
    return String.format("%s;%s;%s%n", identificacion, nombres, apellidos);
  }
}
