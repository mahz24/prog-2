package edu.prog2.controllers;

import static spark.Spark.*;

import java.time.Duration;
import java.time.LocalDateTime;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.models.Avion;
import edu.prog2.models.Trayecto;
import edu.prog2.models.Vuelo;
import edu.prog2.services.VuelosService;

public class VuelosController {

  public VuelosController(final VuelosService vuelosService) {

    path("/vuelos", () -> {

      get("", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          return new StandardResponse(201, "ok", vuelosService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

      get("/:vuelo", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          String[] params = req.params(":vuelo").split("&");
          Trayecto trayectoVuelo = new Trayecto(params[1], params[2], Duration.ZERO, 0);
          Avion avionVuelo = new Avion(params[3], null);
          LocalDateTime fechaHoraVuelo = LocalDateTime.parse(params[0]);
          Vuelo vuelo = new Vuelo(fechaHoraVuelo, trayectoVuelo, avionVuelo);
          JSONObject json = new JSONObject(vuelosService.get(vuelo));
          return new StandardResponse(201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });
    });
  }
}