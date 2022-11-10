package edu.prog2;

import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;

import edu.prog2.helpers.Keyboard;
import edu.prog2.helpers.UtilFiles;
import edu.prog2.models.Avion;
import edu.prog2.models.Licor;
import edu.prog2.models.Menu;
import edu.prog2.models.Pasajero;
import edu.prog2.models.Reserva;
import edu.prog2.models.ReservaVuelo;
import edu.prog2.models.Silla;
import edu.prog2.models.SillaEjecutiva;
import edu.prog2.models.Trayecto;
import edu.prog2.models.Vuelo;

public class AppJSON {

  private static JSONObject jsonPasajero;
  private static JSONObject jsonTrayecto;
  private static JSONObject jsonAvion;
  private static JSONObject jsonVuelo;
  private static JSONObject jsonSilla;
  private static JSONObject jsonSillaEjecutiva;
  private static JSONObject jsonReserva;
  private static JSONObject jsonReservaVuelo;
  private static JSONObject jsonPruebasInstancias;

  // … y los demás requeridos…
  private static JSONArray jsonArray;

  public static void main(String[] args) {
    menu();
  }

  private static void menu() {
    do {
      try {
        int opcion = leerOpcion();
        switch (opcion) {
          case 1:
            testPutTrayecto();
            break;

          case 2:
            testPutPasajero();
            break;

          case 3:
            testPutAvion();
            break;

          case 4:
            testPutVuelo();
            break;

          case 5:
            testPutSilla();
            break;

          case 6:
            testPutSillaEjecutiva();
            break;

          case 7:
            testPutReserva();
            break;

          case 8:
            testPutReservaVuelo();
            break;

          case 9:
            pruebasInstanciasObjetos();
            break;

          case 10:
            instanciasJava();
            break;

          case 11:
            pruebasGetXTrayecto();
            break;

          case 12:
            pruebasGetXSillaEjecutiva();
            break;

          case 13:
            pruebasHas();
            break;

          case 14:
            addJsonArray();
            break;

          case 15:
            vistasJsonArray();
            break;

          case 16:
            instanceTests();
            break;

          case 17:
            jsonArrayTest();
            break;
          // …
          case 0:
            System.exit(0);
          default:
            System.out.println("Opción inválida");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } while (true);
  }

  static int leerOpcion() {
    String opciones = "\nMenú de opciones:\n"
        + " 1 - testPutTrayecto                  9 - pruebasInstanciasObjetos\n"
        + " 2 - testPutPasajero                 10 - instanciasJava\n"
        + " 3 - testPutAvion                    11 - pruebasGetXTrayecto\n"
        + " 4 - testPutVuelo                    12 - pruebasGetXSillaEjecutiva\n"
        + " 5 - testPutSilla                    13 - pruebasHas\n"
        + " 6 - testPutSillaEjecutiva           14 - addJsonArray\n"
        + " 7 - testPutReserva                  15 - vistasJsonArray\n"
        + " 8 - testPutReservaVuelo             16 - instanceTests\n"
        + "                                     17 - jsonArrayTest\n"

        + "\nElija una opción (0 para salir) > ";

    int opcion = Keyboard.readInt(opciones);
    System.out.println();
    return opcion;
  }

  private static void testPutTrayecto() {
    jsonTrayecto = new JSONObject();
    jsonTrayecto.put("origen", "Manizales");
    jsonTrayecto.put("destino", "Bogotá");
    jsonTrayecto.put("duracion", "PT45M");
    jsonTrayecto.put("costo", 120000);

    System.out.println(jsonTrayecto.toString(2));
  }

  private static void testPutPasajero() {
    jsonPasajero = new JSONObject();
    jsonPasajero.put("identificacion", "P001");
    jsonPasajero.put("nombres", "Marco Antonio");
    jsonPasajero.put("apellidos", "Hurtado Zuñiga");

    System.out.println(jsonPasajero.toString(2));
  }

  private static void testPutAvion() {
    jsonAvion = new JSONObject();
    jsonAvion.put("matricula", "HK2014");
    jsonAvion.put("modelo", "Airbour 02");

    System.out.println(jsonAvion.toString(2));
  }

  private static void testPutVuelo() {
    jsonVuelo = new JSONObject();
    jsonVuelo.put("fechaHora", "2022-09-10T08:30");
    jsonVuelo.put("trayecto", jsonTrayecto);
    jsonVuelo.put("avion", jsonAvion);

    System.out.println(jsonVuelo.toString(2));
  }

  private static void testPutSilla() {
    jsonSilla = new JSONObject();
    jsonSilla.put("fila", 1);
    jsonSilla.put("columna", "A");
    jsonSilla.put("avion", jsonAvion);
    System.out.println(jsonSilla.toString(2));
  }

  private static void testPutSillaEjecutiva() {
    jsonSillaEjecutiva = new JSONObject();
    jsonSillaEjecutiva.put("fila", 1);
    jsonSillaEjecutiva.put("columna", "A");
    jsonSillaEjecutiva.put("avion", jsonAvion);
    jsonSillaEjecutiva.put("menu", "VEGETARIANO");
    jsonSillaEjecutiva.put("licor", "VINO");
    System.out.println(jsonSillaEjecutiva.toString(2));
  }

  private static void testPutReserva() {
    jsonReserva = new JSONObject();
    jsonReserva.put("fechaHora", "2022-09-10T08:30");
    jsonReserva.put("cancelada", false);
    jsonReserva.put("pasajero", jsonPasajero);
    System.out.println(jsonReserva.toString(2));
  }

  private static void testPutReservaVuelo() {
    jsonReservaVuelo = new JSONObject();
    jsonReservaVuelo.put("reserva", jsonReserva);
    jsonReservaVuelo.put("vuelo", jsonVuelo);
    jsonReservaVuelo.put("silla", jsonSilla);
    System.out.println(jsonReservaVuelo.toString(2));
  }

  private static void pruebasInstanciasObjetos() {
    jsonPasajero = new JSONObject(
        "{\"identificacion\": \"P002\", \"nombres\": \"Daniel\", \"apellidos\": \"Zuluaga\"}");
    jsonTrayecto = new JSONObject(
        "{\"origen\": \"Cali\", \"destino\": \"Bogota\", \"duracion\": \"01:00\", \"costo\": 12}");
    jsonAvion = new JSONObject("{ \"matricula\": \"HK2010\", \"modelo\": \"Airbus 330\" }");
    jsonPruebasInstancias = new JSONObject();
    jsonPruebasInstancias.put("pasajero", jsonPasajero);
    jsonPruebasInstancias.put("trayecto", jsonTrayecto);
    jsonPruebasInstancias.put("avion", jsonAvion);
    System.out.println(jsonPruebasInstancias.toString(2));
  }

  private static void instanciasJava() {
    Pasajero pasajero = new Pasajero("P003", "Santiago", "Garcia");
    JSONObject jsonPasajero = new JSONObject(pasajero);
    Trayecto trayecto = new Trayecto("Bucaramanga", "Neiva", Duration.parse("PT45M"), 100000);
    JSONObject jsonTrayecto = new JSONObject(trayecto);
    Avion avion = new Avion("HK2015", "Airbus 330");
    JSONObject jsonAvion = new JSONObject(avion);
    Vuelo vuelo = new Vuelo(LocalDateTime.parse("2022-09-10T08:30"), trayecto, avion);
    JSONObject jsonVuelo = new JSONObject(vuelo);
    jsonPruebasInstancias = new JSONObject();
    jsonPruebasInstancias.put("pasajero", jsonPasajero);
    jsonPruebasInstancias.put("trayecto", jsonTrayecto);
    jsonPruebasInstancias.put("avion", jsonAvion);
    jsonPruebasInstancias.put("vuelo", jsonVuelo);
    System.out.println(jsonPruebasInstancias.toString(2));
  }

  private static void pruebasGetXTrayecto() {
    System.out.printf("Origen: %s%n", jsonTrayecto.getString("origen"));
    System.out.printf("Destino: %s%n", jsonTrayecto.getString("destino"));
    System.out.printf("Duración: %s%n", jsonTrayecto.getString("duracion"));
    System.out.printf("Costo: %.0f%n", jsonTrayecto.getDouble("costo"));
  }

  private static void pruebasGetXSillaEjecutiva() {
    System.out.printf("fila: %s%n", jsonSillaEjecutiva.getInt("fila"));
    System.out.printf("columna: %s%n", jsonSillaEjecutiva.getString("columna"));
    System.out.printf("avion: %s%n", jsonSillaEjecutiva.getJSONObject("avion"));
    System.out.printf("menu: %s%n", jsonSillaEjecutiva.getEnum(Menu.class, "menu"));
    System.out.printf("licor: %s%n", jsonSillaEjecutiva.getEnum(Licor.class, "licor"));
  }

  private static void pruebasHas() {
    if (jsonSilla.has("licor")) {
      System.out.println("Es una silla ejecutiva");
    } else {
      System.out.println("Es una silla económica");
    }
    System.out.println("Existe = " + jsonTrayecto.has("duración"));
    System.out.println("Existe = " + jsonTrayecto.has("duracion"));
  }

  private static void addJsonArray() {
    jsonArray = new JSONArray();
    jsonArray.put(jsonAvion);
    jsonArray.put(jsonTrayecto);
    jsonArray.put(jsonPasajero);
    jsonArray.put(jsonVuelo);
    jsonArray.put(jsonSilla);
    jsonArray.put(jsonReserva);
    System.out.println("se añadieron datos de forma correcta");
  }

  private static void vistasJsonArray() {
    for (int i = 0; i < jsonArray.length(); i++) {
      System.out.println(jsonArray.get(i));
    }
  }

  private static void instanceTests() throws IOException {
    String strPasajero = UtilFiles.readText("./data/tests/tests/test-pasajero.json");
    jsonPasajero = new JSONObject(strPasajero);
    Pasajero pasajero = new Pasajero(jsonPasajero);
    System.out.println(pasajero);

    String strAvion = UtilFiles.readText("./data/tests/tests/test-avion.json");
    jsonAvion = new JSONObject(strAvion);
    Avion avion = new Avion(jsonAvion);
    System.out.println(avion);

    String strSilla = UtilFiles.readText("./data/tests/tests/test-silla.json");
    jsonSilla = new JSONObject(strSilla);
    Silla silla = new Silla(jsonSilla);
    System.out.println(silla);

    String strSillaEjecutiva = UtilFiles.readText("./data/tests/tests/test-silla-ejecutiva.json");
    jsonSillaEjecutiva = new JSONObject(strSillaEjecutiva);
    SillaEjecutiva sillaEjecutiva = new SillaEjecutiva(jsonSillaEjecutiva);
    System.out.println(sillaEjecutiva);

    String strTrayecto = UtilFiles.readText("./data/tests/tests/test-trayecto.json");
    jsonTrayecto = new JSONObject(strTrayecto);
    Trayecto trayecto = new Trayecto(jsonTrayecto);
    System.out.println(trayecto);

    String strVuelo = UtilFiles.readText("./data/tests/tests/test-vuelo.json");
    jsonVuelo = new JSONObject(strVuelo);
    Vuelo vuelo = new Vuelo(jsonVuelo);
    System.out.print(vuelo);

    String strReservaVuelo = UtilFiles.readText("./data/tests/tests/test-vuelo-reserva.json");
    jsonReservaVuelo = new JSONObject(strReservaVuelo);
    ReservaVuelo rv = new ReservaVuelo(jsonReservaVuelo);
    System.out.print(rv);

    String strReserva = UtilFiles.readText("./data/tests/tests/test-reserva.json");
    jsonReserva = new JSONObject(strReserva);
    Reserva r = new Reserva(jsonReserva);
    System.out.print(r);

    UtilFiles.writeText("Hola mundo\nAdios mundo", "./data/tests/tests/prueba1.txt");
    UtilFiles.writeText(jsonReservaVuelo.toString(2), "./data/tests/tests/prueba2.txt");
  }

  private static void jsonArrayTest() throws Exception {
    instanceTests();

    ArrayList<Object> list = new ArrayList<>();

    list.add(new Pasajero(jsonPasajero));
    list.add(new Avion(jsonAvion));
    list.add(new Silla(jsonSilla));
    list.add(new SillaEjecutiva(jsonSillaEjecutiva));
    list.add(new Trayecto(jsonTrayecto));
    list.add(new Vuelo(jsonVuelo));
    list.add(new Reserva(jsonReserva));
    list.add(new ReservaVuelo(jsonReservaVuelo));

    UtilFiles.writeJSON(list, "./data/tests/tests/array.json");
  }

}