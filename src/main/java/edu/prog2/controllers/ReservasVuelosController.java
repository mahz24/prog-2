package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.services.ReservasVuelosService;
import edu.prog2.helpers.StandardResponse;

public class ReservasVuelosController {

  public ReservasVuelosController(final ReservasVuelosService reservasVuelosService) {

    path("/vuelos-reservas", () -> {

      get("", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          return new StandardResponse(201, "ok", reservasVuelosService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

      get("/:reserva-vuelo", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          String params = req.params(":reserva-vuelo");
          JSONObject json = reservasVuelosService.get(params);
          return new StandardResponse(201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

    });
  }
}