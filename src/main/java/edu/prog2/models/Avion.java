package edu.prog2.models;

import org.json.JSONObject;

public class Avion implements IFormatCSV {
  private String matricula;
  private String modelo;

  public Avion() {
  };

  public Avion(String matricula, String modelo) {
    this.matricula = matricula;
    this.modelo = modelo;
  }

  public Avion(JSONObject json) {
    this(
        json.getString("matricula"), json.getString("modelo"));
  }

  public Avion(Avion a) {
    this(a.matricula, a.modelo);
  }

  /**
   * Este metodo devuelve la matricula del avión
   * 
   * @return la matricula del avión
   */
  public String getMatricula() {
    return this.matricula;
  }

  /**
   * Este metodo modifica la matrícula del avión
   * 
   * @param m Valor por el que se va a modificar la matrícula
   */
  public void setMatricula(String m) {
    this.matricula = m;
  }

  /**
   * Este metodo devuelve el modelo del avión
   * 
   * @return el modelo del avión
   */
  public String getModelo() {
    return this.modelo;
  }

  /**
   * Este metodo modifica el modelo del avión
   * 
   * @param m Valor por el que se va a modificar el modelo
   */
  public void setModelo(String m) {
    this.modelo = m;
  }

  @Override
  public String toString() {
    return String.format("%s - %s", modelo, matricula);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Avion other = (Avion) obj;
    if (matricula == null) {
      if (other.matricula != null)
        return false;
    } else if (!matricula.equals(other.matricula))
      return false;
    return true;
  }

  @Override
  public String toCSV() {
    return String.format("%s;%s%n", matricula, modelo);
  }
}
